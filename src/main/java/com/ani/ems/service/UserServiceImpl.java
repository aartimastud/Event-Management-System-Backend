package com.ani.ems.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ani.ems.dto.EventListDto;
import com.ani.ems.dto.TicketDto;
import com.ani.ems.dto.UserEventDto;
import com.ani.ems.exception.InvalidRoleException;
import com.ani.ems.exception.NoEventFoundException;
import com.ani.ems.model.Event;
import com.ani.ems.model.User;
import com.ani.ems.repository.AdminRepository;
import com.ani.ems.repository.TicketRepository;
import com.ani.ems.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ani.ems.exception.DuplicateEventException;
import com.ani.ems.exception.UserNotFoundException;
import com.ani.ems.util.DynamicMapper;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final TicketRepository ticketRepository;
    private final DynamicMapper dynamicMapper;


    @Override
    public Integer bookEvent(Long userId, Long eventId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No User found for " + userId + " ID"));

        if (user.getRole().equals("admin"))
            throw new InvalidRoleException("Admin can't book Event");
        Event event = adminRepository.findById(eventId)
                .orElseThrow(() -> new NoEventFoundException("Event not Found for " + eventId + " id"));

        if (user.getEvents().contains(event))
            throw new DuplicateEventException("Event already booked...");
        user.getEvents().add(event);
        userRepository.save(user);
        return 1;
    }

    @Override
    public List<UserEventDto> getAllEvents(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No User found for " + userId + " ID"));

        if (user.getRole().equals("admin"))
            throw new InvalidRoleException("No bookings for Admin");

        List<UserEventDto> collect = user.getEvents()
                .stream()
                .map(event -> dynamicMapper.convertor(event, new UserEventDto()))
                .collect(Collectors.toList());
        if (collect.isEmpty())
            throw new NoEventFoundException("No event found book one.");

        return collect;
    }

    @Override
    public List<EventListDto> getEventsByLocation(String location) {
        List<EventListDto> collect = adminRepository.findAllByLocation(location)
                .stream()
                .map(event -> dynamicMapper.convertor(event, new EventListDto()))
                .collect(Collectors.toList());
        if (collect.isEmpty())
            throw new NoEventFoundException("No event found for location");

        return collect;
    }

    @Override
    public List<EventListDto> getEventsByName(String title) {
        List<EventListDto> collect = adminRepository.findAllByTitle(title)
                .stream()
                .map(event -> dynamicMapper.convertor(event, new EventListDto()))
                .collect(Collectors.toList());
        if (collect.isEmpty())
            throw new NoEventFoundException("No event found for location");

        return collect;
    }



    @Override
    public UserEventDto getEvent(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No User found for " + userId + " ID"));

        if (user.getRole().equals("admin"))
            throw new InvalidRoleException("Admin can't book Event");
        return user.getEvents().stream()
                .filter(event -> event.getId().equals(eventId))
                .findFirst().map(event -> dynamicMapper.convertor(event, new UserEventDto()))
                .orElseThrow(() -> new NoEventFoundException("Event not found"));
    }

    @Override
    public List<TicketDto> getAllTicketsEventId(Long eventId) {
        adminRepository.findById(eventId)
                .orElseThrow(() -> new NoEventFoundException("Event not Found for " + eventId + " id"));
        return ticketRepository.findByEventId(eventId)
                .stream()
                .map(ticket -> dynamicMapper.convertor(ticket, new TicketDto()))
                .collect(Collectors.toList());
    }
}
