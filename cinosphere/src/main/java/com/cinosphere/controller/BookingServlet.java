package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cinosphere.model.MembershipModel;
import com.cinosphere.model.ScreenModel;
import com.cinosphere.model.SeatModel;
import com.cinosphere.model.ShowtimeModel;
import com.cinosphere.model.TheatreModel;
import com.cinosphere.model.UsersModel;
import com.cinosphere.service.BookingService;
import com.cinosphere.service.MovieService;
import com.cinosphere.service.PaymentService;
import com.cinosphere.service.MembershipService;
import com.cinosphere.service.ScreenService;
import com.cinosphere.service.SeatService;
import com.cinosphere.service.ShowtimeService;
import com.cinosphere.service.TheatreService;
import com.cinosphere.service.TicketService;
import com.cinosphere.utils.SessionUtil;
/**
 * Servlet implementation class Booking
 *
 * This servlet handles the booking process for movie tickets including seat selection,
 * pricing calculation, loyalty point application, and payment processing. It also
 * prepares all required movie, screen, theatre, and showtime data for rendering the
 * booking page and final confirmation.
 *
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/booking" })
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final double STANDARD = 1.0;
	private static final double PREMIUM  = 1.5;
	private static final double VIP      = 2.0;
	private static final int POINT_MIN  = 300;
    private static final int DISCOUNT    = 15;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" d MMM, EEEE");
	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
	MovieService  movieService  = new MovieService();
	SeatService   seatService   = new SeatService();
	ShowtimeService   showtimeService  = new ShowtimeService();
	ScreenService screenService = new ScreenService();
	TheatreService theatreService = new TheatreService();
	BookingService bookingService = new BookingService();
	MembershipService membershipService = new MembershipService();
	TicketService ticketService = new TicketService();
	PaymentService paymentService = new PaymentService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Handles GET requests for the booking page by loading all required booking data
     * such as movie details, showtime information, seat layout, pricing breakdown,
     * and membership-based discount calculations. The prepared data is forwarded
     * to the booking JSP for rendering the seat selection and checkout interface.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String movieIdStr    = request.getParameter("movieId");
		String showtimeIdStr = request.getParameter("showtimeId");
		boolean useSphere    = "true".equals(request.getParameter("useSpherePoints"));
		
		if(movieIdStr == null||showtimeIdStr == null ||movieIdStr.isEmpty() || showtimeIdStr.isEmpty()) {
			response.sendRedirect("schedules");
			return;
		}
		try {
			int movieId = Integer.parseInt(movieIdStr);
			int showtimeId = Integer.parseInt(showtimeIdStr);
			
			request.setAttribute("movie",movieService.getMovieById(movieId));
			ShowtimeModel showtime = showtimeService.getShowtimeById(showtimeId);
			int screenId = showtime.getScreenId();
			request.setAttribute("showtime",showtime);
			request.setAttribute("time",showtime.getStartTime().format(timeFormatter));
			request.setAttribute("date",showtime.getShowDate().format(formatter));
			ScreenModel screen = screenService.getScreenById(screenId);
			request.setAttribute("screen",screen);
			TheatreModel theatre = theatreService.getTheatreById(screen.getTheatreId());
			request.setAttribute("theatre",theatre);
			request.setAttribute("basePrice", screen.getBasePrice());
			List<SeatModel> seats = seatService.getSeatsByScreenId(screenId);
			request.setAttribute("seats", seats);
			Set<Integer> takenIds    = bookingService.getConfirmedSeatIdsByShowtime(showtimeId);
            request.setAttribute("takenSeatIds",    takenIds);
            String[] checked = request.getParameterValues("selectedSeats");
            List<Integer> checkedIds = new ArrayList<>();
            if (checked != null) {
                for (String s : checked) {
                    try { 
                    	checkedIds.add(Integer.parseInt(s)); 
                    	} catch (NumberFormatException e) {
                    		e.printStackTrace();
                    	}
                }
            } 
            request.setAttribute("checkedSeatIds", checkedIds);
            double stdSub = 0, premSub = 0, vipSub = 0;
            int stdCount = 0, premCount = 0, vipCount = 0;
            List<String> selectedSeatLabels = new ArrayList<>();
            double basePrice = screen.getBasePrice();
            
            for (int sid : checkedIds) {
                if (takenIds.contains(sid)) continue;
                SeatModel sm = seatService.getSeatById(sid);
                int seatNum = sm.getSeatNumber();
                String label = sm.getRowNumber() + sm.getSeatNumber();
                selectedSeatLabels.add(label);
                if (seatNum <= 90) {
                    stdSub += round(basePrice * STANDARD);
                    stdCount++;
                } else if (seatNum <= 180) {
                    premSub += round(basePrice * PREMIUM);
                    premCount++;
                } else {
                    vipSub += round(basePrice * VIP);
                    vipCount++;
                }
            }
            double subtotal = round(stdSub + premSub + vipSub);
            UsersModel user = (UsersModel) SessionUtil.getAttribute(request, "user");
            MembershipModel membership = membershipService.getByUserId(user.getUserId());
            int spherePoints = membership.getTotalLoyaltyPoints();
            int discountPct  = spherePoints>POINT_MIN?DISCOUNT:0;
            double discountAmt=0;
            if (useSphere && discountPct > 0) {
                 discountAmt = round(subtotal * discountPct / 100.0);
            }else {
            	useSphere=false;
            }
            double totalAmount = round(subtotal - discountAmt);
			String hallName = theatre.getCity() + " — " + screen.getScreenName();
			
			StringBuilder CheckedIdsString = new StringBuilder();
	        for (int i = 0; i < checkedIds.size(); i++) {
	            if (i > 0) {
	            	CheckedIdsString.append(',');
	            }
	            CheckedIdsString.append(checkedIds.get(i));
	        }
	        request.setAttribute("movieId", movieId);
	        request.setAttribute("showtimeId", showtimeId);
	        request.setAttribute("screenId", screenId);
	        request.setAttribute("useSpherePoints", useSphere);
			request.setAttribute("hallName", hallName);
			request.setAttribute("stdCount",   stdCount);
            request.setAttribute("premCount",  premCount);
            request.setAttribute("vipCount",   vipCount);
            request.setAttribute("stdPrice",   round(basePrice * STANDARD));
            request.setAttribute("premPrice",  round(basePrice * PREMIUM));
            request.setAttribute("vipPrice",   round(basePrice * VIP));
            request.setAttribute("stdSub",     stdSub);
            request.setAttribute("premSub",    premSub);
            request.setAttribute("vipSub",     vipSub);
            request.setAttribute("subtotal",   subtotal);
            request.setAttribute("discountAmt", discountAmt);
            request.setAttribute("discountPct", discountPct);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("seatCount",  stdCount + premCount + vipCount);
            request.setAttribute("spherePoints",    spherePoints);
            request.setAttribute("sphereDiscountPct", discountPct);
            request.setAttribute("selectedSeatLabels", selectedSeatLabels);
            request.setAttribute("checkedSeatIdsString",checkedIds.isEmpty() ? "" : CheckedIdsString);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Could not load booking details. Please try again.");
		}
		request.getRequestDispatcher("/WEB-INF/pages/booking.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for processing a booking transaction including seat validation,
	 * price calculation, loyalty point application, ticket creation, and payment recording.
	 * On successful booking, the user is redirected to the profile page, otherwise the
	 * booking page is reloaded with an error message.
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsersModel user = (UsersModel) SessionUtil.getAttribute(request, "user");
		String[] seatIds  = request.getParameterValues("selectedSeats");
		String movieIdStr   = request.getParameter("movieId");
		String showtimeIdStr = request.getParameter("showtimeId");
		String screenIdStr  = request.getParameter("screenId");
	    String paymentMethod    = request.getParameter("paymentMethod");
	    boolean useSphere       = "true".equals(request.getParameter("useSpherePoints"));	
	    request.setAttribute("movieId",movieIdStr);
		request.setAttribute("showtimeId",showtimeIdStr);
	    if (seatIds == null || seatIds.length == 0 || paymentMethod == null || paymentMethod.isEmpty()) {
	    	request.setAttribute("error", "Select a payment method");
	    	doGet(request, response);
			return;
		}
	    try {
	    	 int showtimeId = Integer.parseInt(showtimeIdStr);
	    	 int screenId = Integer.parseInt(screenIdStr);
	         ScreenModel   screen   = screenService.getScreenById(screenId);
	         double basePrice = screen.getBasePrice();
	         double subtotal = 0;
	         List<Integer> validSeatIds = new ArrayList<>();
	         Set<Integer> takenIds    = bookingService.getConfirmedSeatIdsByShowtime(showtimeId);
	         for (String s : seatIds) {
	                int sid = Integer.parseInt(s);
	                if (takenIds.contains(sid)) continue;
	                SeatModel sm = seatService.getSeatById(sid);
	                validSeatIds.add(sid);
	                subtotal += round(basePrice * multiplier(sm));
	         }
	         if (validSeatIds.isEmpty()) {
	                request.setAttribute("error", "None of the selected seats are available.");
	                doGet(request, response);
	                return;
	            }
	         double discountAmt = 0;
	            MembershipModel membership = membershipService.getByUserId(user.getUserId());
	            int spherePoints = membership != null ? membership.getTotalLoyaltyPoints() : 0;
	            int discountPct  = spherePoints>POINT_MIN?DISCOUNT:0;
	            if (useSphere && discountPct > 0) {
	                discountAmt = round(subtotal * discountPct / 100.0);
	            }
	            double totalAmount = round(subtotal - discountAmt);
	            int pointsEarned = (int) (totalAmount / 10);
	            LocalDate today = LocalDate.now();
	            LocalTime now = LocalTime.now();
	            int bookingId = bookingService.insertAndGetId(user.getUserId(), today, now, "confirmed",totalAmount, "online", pointsEarned);
	            if (bookingId == -1) throw new Exception("Booking insert failed.");
	            for (int sid : validSeatIds) {
	                SeatModel sm = seatService.getSeatById(sid);
	                double ticketPrice = round(basePrice * multiplier(sm));
	                
	                String ticketType  = sm.getSeatType();
	                ticketService.createTicket(bookingId, showtimeId, sid, ticketType, "ACTIVE", today, ticketPrice);
	            }
	            paymentService.createPayment(bookingId, paymentMethod, totalAmount);
	            int newPoints = (useSphere ? spherePoints-POINT_MIN : spherePoints) + pointsEarned; //deduction of 300 points for applying the discount
                membershipService.updateMembershipLoyaltyPoints(user.getUserId(), newPoints);
                response.sendRedirect(request.getContextPath()+ "/profile");
	    }catch(Exception e){
	    	 request.setAttribute("error", "Booking failed");
	    	 e.printStackTrace();
	    	 request.setAttribute("movieId",movieIdStr);
			 request.setAttribute("showtimeId",showtimeIdStr);
	         doGet(request, response);
	    }
	}
	/**
	 * Returns a rounded value up to 2 decimal places.
	 * @param v the raw numeric value
	 * @return rounded value up to 2 decimal places
	 */
    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
    /**
	 * Determines the pricing multiplier based on seat type.
	 * @param sm seat model containing seat type information
	 * @return pricing multiplier based on seat category
	 */
    private double multiplier(SeatModel sm) {
        if (sm == null) return STANDARD;
        String t = sm.getSeatType() == null ? "" : sm.getSeatType().toLowerCase();
        if (t.contains("premium")) return PREMIUM;
        if (t.contains("vip"))     return VIP;
        return STANDARD;
    }
}
