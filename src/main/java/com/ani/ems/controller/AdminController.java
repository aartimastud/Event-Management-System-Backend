package com.ani.ems.controller;

import java.util.List;

import javax.validation.Valid;

import com.ani.ems.dto.EventListDto;
import com.ani.ems.dto.NewEventDto;
import com.ani.ems.dto.TicketDto;
import com.ani.ems.dto.UpdateEventDto;
import com.ani.ems.model.Ticket;
import com.ani.ems.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ani.ems.util.AppResponse;

import lombok.AllArgsConstructor;
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping(value = "/newevent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<Integer>> newEvent(@Valid @RequestBody NewEventDto dto) {
        Integer createNewEvent = adminService.createNewEvent(dto);
        AppResponse<Integer> response = AppResponse.<Integer>builder()
                .msg("new event created successfully.").bd(createNewEvent).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventListDto>> findAll() {
        return ResponseEntity.ok().body(adminService.getAllEvents());
    }

    @GetMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<AppResponse<NewEventDto>> findEventById(@PathVariable Long id) {

        NewEventDto dto = adminService.getEvent(id);

        AppResponse<NewEventDto> response = AppResponse.<NewEventDto>builder()
                .msg("Event Details").bd(dto).build();
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<Integer>> deleteInvoice(@PathVariable Long id) {

        final Integer sts = adminService.deleteEvent(id);

        final AppResponse<Integer> response = AppResponse.<Integer>builder()
                .msg(id+" ID Event Deleted Successfully")
                .bd(sts)
                .build();

        return ResponseEntity.status(200).body(response);
    }

    @PutMapping(value = "/updateevent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<Integer>> updateNewInvoice(@Valid @RequestBody UpdateEventDto dto) {

        final Integer sts = adminService.updateEvent(dto);

        final AppResponse<Integer> response = AppResponse.<Integer>builder()
                .msg("Event Updated Successfully").bd(sts).build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/events/{eventId}/tickets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<Integer>> createTicketForEvent(@Valid @PathVariable Long eventId, @RequestBody TicketDto ticketDto) {
        Integer setTicket = adminService.createTicket(eventId,ticketDto);
        AppResponse<Integer> response = AppResponse.<Integer>builder()
                .msg("ticket created.").bd(setTicket).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/eventlocation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventListDto>> findAllByLocation(@RequestParam String location) {

        return ResponseEntity.ok().body(adminService.getEventsByLocation(location));
    }

    @GetMapping(value = "/eventname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventListDto>> findAllByTitle(@RequestParam String title) {

        return ResponseEntity.ok().body(adminService.getEventsByName(title));
    }

//    @GetMapping(value="/show/tickets")
//    public List<Ticket> getTicket(){
//        return adminService.showTicket();
//    }
}
