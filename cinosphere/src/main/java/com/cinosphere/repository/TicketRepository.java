package com.cinosphere.repository;

import com.cinosphere.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketModel, Integer> {

    // SELECT * FROM ticket WHERE booking_id = ?
    List<TicketModel> findByBookingId(int bookingId);

    // SELECT * FROM ticket WHERE ticket_id = ?
    TicketModel findByTicketId(int ticketId);

    List<TicketModel> findByShowtimeId(int showtimeId);
}