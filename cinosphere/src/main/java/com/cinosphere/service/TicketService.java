package com.cinosphere.service;

import com.cinosphere.model.TicketModel;
import com.cinosphere.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles ticket queries.
 *
 * Note: ticket *creation* now happens inside BookingService.createBooking()
 * as part of the single @Transactional booking flow — it is no longer called
 * separately from a servlet. This service is kept for read operations
 * (e.g. showing a user's ticket history or an admin's ticket list).
 *
 * Changes from original TicketService:
 *  - createTicket() removed — creation is handled transactionally in BookingService.
 *  - Injected TicketRepository instead of new TicketDAO().
 */
@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<TicketModel> getTicketsByBookingId(int bookingId) {
        return ticketRepository.findByBookingId(bookingId);
    }

    public List<TicketModel> getTicketsByShowtimeId(int showtimeId) {
        return ticketRepository.findByShowtimeId(showtimeId);
    }
}