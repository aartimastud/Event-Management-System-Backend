package com.ani.ems.service;

import java.util.List;

import com.ani.ems.dto.EventListDto;
import com.ani.ems.dto.NewEventDto;
import com.ani.ems.dto.TicketDto;
import com.ani.ems.dto.UpdateEventDto;

public interface AdminService {
    Integer createNewEvent(NewEventDto dto);

    List<EventListDto> getAllEvents();

    Integer deleteEvent(Long id);

    Integer updateEvent(UpdateEventDto dto);

    NewEventDto getEvent(Long id);

    Integer createTicket(Long id, TicketDto dto);
}
