package com.ani.ems.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ani.ems.model.Event;
import com.ani.ems.model.Ticket;
import lombok.AllArgsConstructor;
import com.ani.ems.dto.EventListDto;
import com.ani.ems.dto.NewEventDto;
import com.ani.ems.dto.TicketDto;
import com.ani.ems.dto.UpdateEventDto;
import com.ani.ems.exception.InvalidTicketException;
import com.ani.ems.exception.NoEventFoundException;
import com.ani.ems.repository.AdminRepository;
import com.ani.ems.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ani.ems.util.DynamicMapper;



@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final AdminRepository adminRepository;
    private final TicketRepository ticketRepository;
    private final DynamicMapper dynamicMapper;

    @Override
    public Integer createNewEvent(NewEventDto dto) {
        Event event = dynamicMapper.convertor(dto, new Event());
        adminRepository.save(event);
        return 1;
    }

    @Override
    public List<EventListDto> getAllEvents() {
        List<EventListDto> collect = adminRepository.findAll()
                .stream().map(event -> dynamicMapper.convertor(event, new EventListDto()))
                .collect(Collectors.toList());
        if (collect.isEmpty())
            throw new NoEventFoundException("No event found create one.");

        return collect;
    }

    @Override
    public Integer deleteEvent(Long id) {
        isEventPresent(id);
        adminRepository.deleteById(id);
        return 1;
    }

    @Override
    public Integer updateEvent(UpdateEventDto dto) {
        isEventPresent(dto.getId());
        adminRepository.save(dynamicMapper.convertor(dto, new Event()));
        return 1;
    }

    @Override
    public NewEventDto getEvent(Long id) {
        isEventPresent(id);
        Event event = adminRepository.getReferenceById(id);
        return dynamicMapper.convertor(event, new NewEventDto());
    }

    private void isEventPresent(Long id) {
        adminRepository.findById(id).
                orElseThrow(() -> new NoEventFoundException("No Event found for " + id + " ID"));
    }

    @Override
    public Integer createTicket(Long id, TicketDto dto) {
        if (!isValidTicketType(dto.getType()))
            throw new InvalidTicketException("Invalid ticket type");

        Event event = adminRepository.findById(id)
                .orElseThrow(() -> new NoEventFoundException("Event not Found for " + id + " id"));

        Ticket ticket = dynamicMapper.convertor(dto, new Ticket());
        ticket.setEvent(event);
        ticketRepository.save(ticket);
        return 1;
    }

    private boolean isValidTicketType(String type) {
        return type.equals("vip") || type.equals("earlybird") || type.equals("group");
    }
}
