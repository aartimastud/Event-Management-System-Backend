package com.ani.ems.repository;

import java.util.List;
import java.util.Optional;

import com.ani.ems.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByType(String role);

    List<Ticket> findByEventId(Long eventId);
}
