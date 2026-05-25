package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.cinosphere.model.BookingModel;
import com.cinosphere.model.MembershipModel;
import com.cinosphere.model.MovieModel;
import com.cinosphere.model.ScreenModel;
import com.cinosphere.model.SeatModel;
import com.cinosphere.model.ShowtimeModel;
import com.cinosphere.model.TheatreModel;
import com.cinosphere.model.TicketModel;
import com.cinosphere.model.UsersModel;
import com.cinosphere.service.BookingService;
import com.cinosphere.service.MembershipService;
import com.cinosphere.service.MovieService;
import com.cinosphere.service.ScreenService;
import com.cinosphere.service.SeatService;
import com.cinosphere.service.ShowtimeService;
import com.cinosphere.service.TheatreService;
import com.cinosphere.service.TicketService;
import com.cinosphere.utils.SessionUtil;

/**
 * Servlet implementation class UserPanelServlet
 * 
 * This servlet is responsible for handling the user profile page. It retrieves
 * all necessary information related to the logged-in user such as booking history,
 * upcoming bookings, membership details, and loyalty points. It also gathers
 * associated movie, showtime, seat, screen, and theatre information required
 * to display a complete user dashboard view. The collected data is then passed
 * to the JSP page for rendering.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/profile" })
public class UserPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" d MMM, EEEE"); //Formatter used to display dates in a readable format such as day, date, and month.
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a"); //Formatter used to display showtime in 12-hour format with AM/PM.
	private BookingService bookingService = new BookingService();
	private MembershipService membershipService = new MembershipService();
	private TicketService ticketService = new TicketService();
	private SeatService seatService = new SeatService();
	private MovieService movieService = new MovieService();
	private ShowtimeService showtimeService = new ShowtimeService();
	private ScreenService screenService = new ScreenService();
	private TheatreService theatreService = new TheatreService();
	
	/**
	 * Handles GET requests for the user profile page by retrieving the logged-in user,
	 * checking access permissions, and loading all relevant booking and profile-related
	 * data required to display the user dashboard.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		// Redirect admin users away from user panel
		UsersModel user = (UsersModel) SessionUtil.getAttribute(request, "user");
		if("ADMIN".equals(user.getUserRole())) {
			response.sendRedirect(request.getContextPath()+"/admin");
			return;
		}
		// Fetch userdata
		int userId= user.getUserId();
		int totalBooking = bookingService.getTotalBookings(userId);
		int upcomingBooking = bookingService.getTotalUpcomingBookings(userId);
		LocalDate nearestUpcomming = bookingService.getLatestComingBookingDate(userId);
		int loyaltyPointsEarned = bookingService.getLatestLoyaltyPointsEarned(userId);
		MembershipModel membership = membershipService.getByUserId(userId);
		int monthTotal = bookingService.getTotalBookingsThisMonth(userId);
		
		List<BookingModel> bookings = bookingService.getUpcomingBookings(userId);
		List<Integer> movieIds      = new ArrayList<>();
        List<String>  movieNames    = new ArrayList<>();
        List<String>  showDates     = new ArrayList<>();
        List<String>  startTimes    = new ArrayList<>();
        List<String>  cities        = new ArrayList<>();
        List<String>  screenNames   = new ArrayList<>();
        List<String>  seatLabels    = new ArrayList<>();  
        List<Integer> seatCounts    = new ArrayList<>();
        List<String>  bookingStatuses = new ArrayList<>();
        
        for (BookingModel booking : bookings) {
            
            List<TicketModel> tickets = ticketService.getTicketByBooking(booking.getBookingId());
            TicketModel firstTicket = tickets.get(0);
            ShowtimeModel showtime  = showtimeService.getShowtimeById(firstTicket.getShowtimeId());
            MovieModel    movie     = movieService.getMovieById(showtime.getMovieId());
            ScreenModel   screen    = screenService.getScreenById(showtime.getScreenId());
            TheatreModel  theatre   = theatreService.getTheatreById(screen.getTheatreId());
            
            List<String> seats = new ArrayList<>();
            for (TicketModel t : tickets) {
                SeatModel seat = seatService.getSeatById(t.getSeatId());
                seats.add(seat.getRowNumber() + seat.getSeatNumber());
            }
            
            movieIds.add(movie.getMovieId());
            movieNames.add(movie.getMovieName());
            showDates.add(showtime.getShowDate().format(formatter));
            startTimes.add(showtime.getStartTime().format(timeFormatter));
            cities.add(theatre.getCity());
            screenNames.add(screen.getScreenName());
            seatLabels.add(String.join(", ", seats));
            seatCounts.add(tickets.size());
            bookingStatuses.add(booking.getBookingStatus());
            
            
            }
        // Set attributes for JSP rendering
        request.setAttribute("totalBooking",        totalBooking);
        request.setAttribute("upcomingBooking",     upcomingBooking);
        request.setAttribute("loyaltyPointsEarned", loyaltyPointsEarned);
        request.setAttribute("upcommingDate",nearestUpcomming != null ? nearestUpcomming.format(formatter) : "—");
        request.setAttribute("today", LocalDate.now().format(formatter));
        request.setAttribute("membership", membership);
        request.setAttribute("bookingMonthTotal",  monthTotal);
        request.setAttribute("bookings",         movieIds);
        request.setAttribute("movieIds",         movieIds);
        request.setAttribute("movieNames",       movieNames);
        request.setAttribute("showDates",        showDates);
        request.setAttribute("startTimes",       startTimes);
        request.setAttribute("cities",           cities);
        request.setAttribute("screenNames",      screenNames);
        request.setAttribute("seatLabels",       seatLabels);
        request.setAttribute("seatCounts",       seatCounts);
        request.setAttribute("bookingStatuses",  bookingStatuses);
		} catch (Exception e) {
			request.setAttribute("error", "Failed to load profile details.");
			e.printStackTrace();
		}
		// Forward to user profile JSP
		request.getRequestDispatcher("/WEB-INF/pages/userPanel.jsp").forward(request, response);
	}
	/**
	 * Handles POST requests for the profile page. It delegates processing to the GET
	 * method as both requests share the same functionality.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
