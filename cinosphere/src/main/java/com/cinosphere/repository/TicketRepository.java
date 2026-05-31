package com.cinosphere.repository;

import com.cinosphere.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketModel, Integer> {

    // SELECT * FROM ticket WHERE booking_id = ?
    List<TicketModel> findByBookingId(int bookingId);

    // SELECT * FROM ticket WHERE ticket_id = ?
    Optional<TicketModel> findByTicketId(int ticketId);

    // UPDATE ticket SET ticket_status = ? WHERE ticket_id = ?
    // (JPA handles updates via save(), but you can still define a custom update if needed)
}