package com.ani.ems.controller;

import java.util.List;

import javax.validation.Valid;

import com.ani.ems.dto.EventListDto;
import com.ani.ems.dto.TicketDto;
import com.ani.ems.dto.UserEventDto;
import com.ani.ems.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ani.ems.util.AppResponse;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/attendee")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/{userId}/event/{eventId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<Integer>> newEvent(@Valid @PathVariable Long userId, @PathVariable Long eventId) {
        Integer bookEvent = userService.bookEvent(userId, eventId);
        AppResponse<Integer> response = AppResponse.<Integer>builder()
                .msg("new event booked successfully.")
                .bd(bookEvent)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserEventDto>> findAll(@PathVariable Long userId) {

        return ResponseEntity.ok().body(userService.getAllEvents(userId));
    }

//    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<EventListDto>> findEventById(@RequestParam String location) {
//
//        return ResponseEntity.ok().body(userService.getEventsByLocation(location));
//    }

    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventListDto>> findAllByLocation(@RequestParam String location) {

        return ResponseEntity.ok().body(userService.getEventsByLocation(location));
    }

    @GetMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventListDto>> findAllByTitle(@RequestParam String title) {

        return ResponseEntity.ok().body(userService.getEventsByName(title));
    }

    @GetMapping(value = "/{userId}/event/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<AppResponse<UserEventDto>> findEventById(@PathVariable Long userId,@PathVariable Long eventId) {

        UserEventDto dto = userService.getEvent(userId,eventId);

        AppResponse<UserEventDto> response = AppResponse.<UserEventDto>builder()
                .msg("Event Details")
                .bd(dto)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/tickets/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TicketDto>> findAllTicketsByEventId(@PathVariable Long eventId) {

        return ResponseEntity.ok().body(userService.getAllTicketsEventId(eventId));
    }
}
