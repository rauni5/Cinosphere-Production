package com.cinosphere.service;

import com.cinosphere.dto.BookingRequest;
import com.cinosphere.dto.BookingResponse;
import com.cinosphere.model.*;
import com.cinosphere.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles booking creation, querying, and status updates.
 *
 * Changes from original BookingService:
 *  - createBooking() combines what was spread across BookingServlet.doPost(),
 *    BookingService, PaymentService, TicketService, and MembershipService —
 *    one transactional call that does everything atomically.
 *  - No HttpServletRequest / SessionUtil — the calling username comes from
 *    the JWT principal (passed in from the controller).
 *  - Returns typed DTOs instead of raw model lists where enriched data is needed.
 *  - Injected repositories instead of new *DAO() instances.
 */
@Service
public class BookingService {

    private static final double STANDARD_MULTIPLIER = 1.0;
    private static final double PREMIUM_MULTIPLIER  = 1.5;
    private static final double VIP_MULTIPLIER      = 2.0;
    private static final int    POINTS_THRESHOLD    = 300;
    private static final int    DISCOUNT_PERCENT    = 15;

    @Autowired private BookingRepository    bookingRepository;
    @Autowired private TicketRepository     ticketRepository;
    @Autowired private PaymentRepository    paymentRepository;
    @Autowired private MembershipRepository membershipRepository;
    @Autowired private UserRepository       userRepository;
    @Autowired private ShowtimeRepository   showtimeRepository;
    @Autowired private SeatRepository       seatRepository;
    @Autowired private ScreenRepository     screenRepository;
    @Autowired private MovieRepository      movieRepository;
    @Autowired private TheatreRepository    theatreRepository;

    // ---- queries ----

    public List<BookingModel> getBookingsByUserId(int userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<BookingModel> getBookingsByUserIdAndStatus(int userId, String status) {
        return bookingRepository.findByUserIdAndBookingStatus(userId, status);
    }

    public List<BookingModel> getUpcomingBookings(int userId) {
        return bookingRepository.findUpcomingByUserId(userId);
    }

    public List<BookingModel> getAllBookings() {
        return bookingRepository.findAll();
    }

    public int getTotalBookings() {
        return (int) bookingRepository.count();
    }

    public int getTotalBookingsByUser(int userId) {
        return bookingRepository.countByUserId(userId);
    }

    public int getTotalUpcomingBookings(int userId) {
        return bookingRepository.countUpcomingByUserId(userId);
    }

    public int getTotalBookingsThisMonth(int userId) {
        return bookingRepository.countCurrentMonthBookingsByUserId(userId);
    }

    public int getLatestLoyaltyPointsEarned(int userId) {
        return bookingRepository.findLatestComingByUserId(userId)
                .map(BookingModel::getLoyaltyPointsEarned)
                .orElse(0);
    }

    public LocalDate getLatestComingBookingDate(int userId) {
        return bookingRepository.findLatestComingByUserId(userId)
                .map(BookingModel::getBookingDate)
                .orElse(null);
    }

    public double getTodayRevenue() {
        return bookingRepository.getRevenueByDate(LocalDate.now());
    }

    public int getTodayBookingCount() {
        return bookingRepository.countByBookingDate(LocalDate.now());
    }

    public double getYesterdayRevenue() {
        return bookingRepository.getRevenueByDate(LocalDate.now().minusDays(1));
    }

    public int getYesterdayBookingCount() {
        return bookingRepository.countByBookingDate(LocalDate.now().minusDays(1));
    }

    public Set<Integer> getConfirmedSeatIdsByShowtime(int showtimeId) {
        return bookingRepository.getConfirmedSeatIdsByShowtime(showtimeId);
    }

    // ---- admin actions ----

    public void archiveBooking(int bookingId) {
        BookingModel booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));
        booking.setBookingStatus("ARCHIVE");
        bookingRepository.save(booking);
    }

    // ---- create booking (main transactional flow) ----

    /**
     * Creates a booking, tickets, payment, and updates loyalty points in one
     * atomic transaction.
     *
     * Consolidates the logic that was previously split across:
     *  BookingServlet.doPost(), BookingService, PaymentService,
     *  TicketService, MembershipService.
     *
     * @param request  validated BookingRequest DTO from the controller
     * @param username authenticated user's username from JWT principal
     * @return a BookingResponse DTO with confirmation details
     */
    @Transactional
    public BookingResponse createBooking(BookingRequest request, int username) {
        // 1. resolve the authenticated user
        UsersModel user = userRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 2. resolve showtime
        ShowtimeModel showtime = showtimeRepository.findById(request.getShowtimeId())
                .orElseThrow(() -> new IllegalArgumentException("Showtime not found"));

        // 3. resolve screen + base price
        ScreenModel screen = screenRepository.findById(showtime.getScreenId())
                .orElseThrow(() -> new IllegalArgumentException("Screen not found"));

        // 4. resolve selected seats and check for conflicts
        Set<Integer> alreadyBooked = bookingRepository.getConfirmedSeatIdsByShowtime(showtime.getShowtimeId());
        List<SeatModel> seats = new ArrayList<>();
        for (int seatId : request.getSeatIds()) {
            if (alreadyBooked.contains(seatId)) {
                throw new IllegalArgumentException("Seat " + seatId + " is already taken");
            }
            seats.add(seatRepository.findById(seatId)
                    .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + seatId)));
        }

        // 5. calculate total amount
        double total = calculateTotal(seats, screen.getBasePrice().doubleValue());

        // 6. apply loyalty-point discount if requested
        MembershipModel membership = membershipRepository.findByUserId(user.getUserId())
                .orElse(null);
        if (request.isUsePoints() && membership != null &&
                membership.getTotalLoyaltyPoints() >= POINTS_THRESHOLD) {
            total = total * (1 - DISCOUNT_PERCENT / 100.0);
            membership.setTotalLoyaltyPoints(
                    membership.getTotalLoyaltyPoints() - POINTS_THRESHOLD);
        }

        // 7. calculate points earned (1 point per currency unit, rounded)
        int pointsEarned = (int) Math.round(total);

        // 8. create booking record
        BookingModel booking = new BookingModel();
        booking.setUserId(user.getUserId());
        booking.setBookingDate(LocalDate.now());
        booking.setBookingTime(LocalTime.now());
        booking.setBookingStatus("CONFIRMED");
        booking.setTotalAmount(BigDecimal.valueOf(total));
        booking.setBookingChannel("ONLINE");
        booking.setLoyaltyPointsEarned(pointsEarned);
        booking = bookingRepository.save(booking);

        // 9. create one ticket per seat
        List<BookingResponse.TicketInfo> ticketInfos = new ArrayList<>();
        for (SeatModel seat : seats) {
            TicketModel ticket = new TicketModel();
            ticket.setBookingId(booking.getBookingId());
            ticket.setShowtimeId(showtime.getShowtimeId());
            ticket.setSeatId(seat.getSeatId());
            ticket.setTicketType(seat.getSeatType());
            ticket.setTicketStatus("VALID");
            ticket.setIssueDate(LocalDate.now());
            ticket.setTicketPrice(BigDecimal.valueOf(priceForSeat(seat.getSeatType(), screen.getBasePrice().doubleValue())));
            ticket = ticketRepository.save(ticket);

            BookingResponse.TicketInfo info = new BookingResponse.TicketInfo();
            info.setTicketId(ticket.getTicketId());
            info.setSeatLabel(seat.getRowNumber() + seat.getSeatNumber());
            info.setSeatType(seat.getSeatType());
            info.setPrice(ticket.getTicketPrice().doubleValue());
            ticketInfos.add(info);
        }

        // 10. create payment record
        PaymentModel payment = new PaymentModel();
        payment.setBookingId(booking.getBookingId());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentAmount(BigDecimal.valueOf(total));
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentTime(LocalTime.now());
        payment.setPaymentStatus("COMPLETED");
        paymentRepository.save(payment);

        // 11. update membership loyalty points
        if (membership != null) {
            membership.setTotalLoyaltyPoints(
                    membership.getTotalLoyaltyPoints() + pointsEarned);
            membershipRepository.save(membership);
        }

        // 12. build response DTO
        MovieModel movie   = movieRepository.findById(showtime.getMovieId()).orElse(null);
        TheatreModel theatre = theatreRepository.findById(screen.getTheatreId()).orElse(null);

        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getBookingId());
        response.setBookingDate(booking.getBookingDate());
        response.setBookingTime(booking.getBookingTime());
        response.setBookingStatus(booking.getBookingStatus());
        response.setTotalAmount(booking.getTotalAmount());
        response.setLoyaltyPointsEarned(pointsEarned);
        response.setPaymentMethod(request.getPaymentMethod());
        response.setShowtimeId(showtime.getShowtimeId());
        response.setMovieName(movie != null ? movie.getMovieName() : "");
        response.setShowDate(showtime.getShowDate());
        response.setStartTime(showtime.getStartTime());
        response.setScreenName(screen.getScreenName());
        response.setTheatreCity(theatre != null ? theatre.getCity() : "");
        response.setTickets(ticketInfos);

        return response;
    }

    // ---- methods used by BookingController ----

    /**
     * Returns bookings for the currently authenticated user, resolved by username.
     * BookingController calls this so it never needs to resolve userId itself.
     */
    public List<BookingModel> getBookingsByUsername(int username) {
        UsersModel user = userRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return bookingRepository.findByUserId(user.getUserId());
    }

    /**
     * Converts a BookingModel to a BookingResponse DTO by joining showtime,
     * movie, screen, theatre, and ticket data.
     * Used by BookingController.getMyBookings() to enrich the booking list.
     */
    public BookingResponse toResponse(BookingModel booking) {
        BookingResponse res = new BookingResponse();
        res.setBookingId(booking.getBookingId());
        res.setBookingDate(booking.getBookingDate());
        res.setBookingTime(booking.getBookingTime());
        res.setBookingStatus(booking.getBookingStatus());
        res.setTotalAmount(booking.getTotalAmount());
        res.setLoyaltyPointsEarned(booking.getLoyaltyPointsEarned());

        // Enrich from the first ticket's showtime
        ticketRepository.findByBookingId(booking.getBookingId())
                .stream().findFirst().ifPresent(ticket -> {
                    res.setShowtimeId(ticket.getShowtimeId());
                    showtimeRepository.findById(ticket.getShowtimeId()).ifPresent(st -> {
                        res.setShowDate(st.getShowDate());
                        res.setStartTime(st.getStartTime());
                        movieRepository.findById(st.getMovieId())
                                .ifPresent(m -> res.setMovieName(m.getMovieName()));
                        screenRepository.findById(st.getScreenId()).ifPresent(sc -> {
                            res.setScreenName(sc.getScreenName());
                            theatreRepository.findById(sc.getTheatreId())
                                    .ifPresent(th -> res.setTheatreCity(th.getCity()));
                        });
                    });

                    // Ticket list
                    List<BookingResponse.TicketInfo> tickets = ticketRepository
                            .findByBookingId(booking.getBookingId()).stream().map(t -> {
                                BookingResponse.TicketInfo info = new BookingResponse.TicketInfo();
                                info.setTicketId(t.getTicketId());
                                info.setSeatType(t.getTicketType());
                                info.setPrice(t.getTicketPrice().doubleValue());
                                seatRepository.findById(t.getSeatId()).ifPresent(s ->
                                        info.setSeatLabel(s.getRowNumber() + String.valueOf(s.getSeatNumber())));
                                return info;
                            }).toList();
                    res.setTickets(tickets);
                });

        return res;
    }

    // ---- private helpers ----

    private double calculateTotal(List<SeatModel> seats, double basePrice) {
        return seats.stream()
                .mapToDouble(s -> priceForSeat(s.getSeatType(), basePrice))
                .sum();
    }

    private double priceForSeat(String seatType, double basePrice) {
        return switch (seatType.toUpperCase()) {
            case "PREMIUM" -> basePrice * PREMIUM_MULTIPLIER;
            case "VIP"     -> basePrice * VIP_MULTIPLIER;
            default        -> basePrice * STANDARD_MULTIPLIER;
        };
    }
}
