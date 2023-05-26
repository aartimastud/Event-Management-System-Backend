package com.ani.ems.repository;

import java.util.List;
import java.util.Optional;

import com.ani.ems.model.Event;
import com.ani.ems.model.Ticket;
import com.ani.ems.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
//    List<User> findEventById(Long eventId);


}
