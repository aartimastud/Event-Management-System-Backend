package com.ani.ems.repository;

import com.ani.ems.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByLocation(String location);
    List<Event> findAllByTitle(String title);
}
