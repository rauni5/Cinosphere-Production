package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cinosphere.model.BookingModel;
import com.cinosphere.model.ShowtimeModel;
import com.cinosphere.model.TicketModel;
import com.cinosphere.service.BookingService;
import com.cinosphere.service.ShowtimeService;
import com.cinosphere.service.TicketService;

/**
 * Servlet implementation class UpdateAllBookingStatusServlet
 * 
 * This servlet is responsible for automatically updating booking statuses
 * based on showtime schedules. It checks all bookings in the system and
 * marks them as archived if their corresponding showtime date or time has
 * already passed. This ensures that outdated bookings are properly maintained
 * and no longer treated as active records in the system.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/updatebookingstatus" })
public class UpdateAllBookingStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookingService bookingService = new BookingService();
	TicketService ticketService = new TicketService();
	ShowtimeService showtimeService = new ShowtimeService();

    /**
     * Handles GET requests for updating all booking statuses. It retrieves all
     * bookings, checks their associated showtime date and time, and updates the
     * booking status to "archive" if the showtime has already passed. After
     * processing, the user is redirected back to the admin booking management
     * section.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<BookingModel> bookings;
		try {
			bookings = bookingService.getAllBookings();
		
		for (BookingModel booking : bookings) {
			if(booking.getBookingStatus().equals("archive")) continue;
			List<TicketModel> tickets = ticketService.getTicketByBooking(booking.getBookingId());
			TicketModel firstTicket = tickets.get(0);
			ShowtimeModel showtime  = showtimeService.getShowtimeById(firstTicket.getShowtimeId());
			LocalDate date = showtime.getShowDate();
			LocalTime time = showtime.getStartTime();
			LocalDate today = LocalDate.now();
			LocalTime now = LocalTime.now();
			if(date.isBefore(today)) {
				bookingService.updateBookingStatusToArchive(booking.getBookingId());
				continue;
			}
			if(date.isEqual(today)) {
				if(time.isBefore(now)) {
					bookingService.updateBookingStatusToArchive(booking.getBookingId());
					continue;
				}
			}
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("admin#booking_management");
	}
	
	

}
