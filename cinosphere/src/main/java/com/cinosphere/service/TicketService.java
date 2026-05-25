package com.cinosphere.service;

import java.time.LocalDate;
import java.util.List;

import com.cinosphere.dao.TicketDAO;
import com.cinosphere.model.TicketModel;
/**
 * Service Class that is the bridge between Servlet and TicketDAO
 * Contains methods used to call methods of DAO and perform interaction with DB Ticket Table
 * 
 * @author Raunit Giri
 */
public class TicketService {
	private TicketDAO ticketDAO = new TicketDAO();
	/**
	 * Finds tickets using Booking Id
	 * @param booking_id
	 * @return Ticket List
	 * @throws Exception
	 */
	public List<TicketModel> getTicketByBooking(int booking_id) throws Exception {
		return ticketDAO.findByBookingId(booking_id);
	}
	/**
	 * Create ticket record using details provided
	 * @param bookingId
	 * @param showtimeId
	 * @param seatId
	 * @param ticketType
	 * @param ticketPrice
	 * @return boolean
	 * @throws Exception
	 */
	public boolean createTicket(int bookingId, int showtimeId, int seatId, String ticketType,String ticketStatus,LocalDate issueDate, double ticketPrice) throws Exception {
		return ticketDAO.insert(bookingId, showtimeId, seatId, ticketType, ticketStatus, issueDate, ticketPrice);
	}
	
}
