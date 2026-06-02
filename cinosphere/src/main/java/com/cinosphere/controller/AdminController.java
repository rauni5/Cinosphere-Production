package com.cinosphere.controller;

import com.cinosphere.dto.*;
import com.cinosphere.model.*;
import com.cinosphere.repository.*;
import com.cinosphere.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * All endpoints are ADMIN-only (enforced at class level via @PreAuthorize).
 *
 * GET  /api/admin/stats                   — dashboard metric cards
 * GET  /api/admin/users                   — all users enriched with membership + booking count
 * PUT  /api/admin/users/{id}/toggle       — activate / deactivate a user
 * GET  /api/admin/bookings                — all bookings enriched with movie + screen + username
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired private BookingService      bookingService;
    @Autowired private UserService         userService;
    @Autowired private MembershipService   membershipService;
    @Autowired private MovieRepository     movieRepository;
    @Autowired private ShowtimeRepository  showtimeRepository;
    @Autowired private ScreenRepository    screenRepository;
    @Autowired private BookingRepository   bookingRepository;
    @Autowired private TicketRepository    ticketRepository;

    // ----------------------------------------------------------------
    // GET /api/admin/stats
    // Populates the four metric cards at the top of AdminPanel.jsx
    // ----------------------------------------------------------------
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<AdminStatsDto>> getStats() {
        AdminStatsDto stats = new AdminStatsDto();

        stats.setRevenueToday(bookingService.getTodayRevenue());
        stats.setRevenueYesterday(bookingService.getYesterdayRevenue());
        stats.setTicketsSoldToday(bookingService.getTodayBookingCount());
        stats.setTicketsSoldYesterday(bookingService.getYesterdayBookingCount());
        stats.setNewMembersToday(userService.getTodayNewUsers());
        stats.setNewMembersYesterday(userService.getYesterdayNewUsers());
        stats.setTotalBookings(bookingService.getTotalBookings());
        stats.computeChanges();   // derives the ↑/↓ % strings

        return ResponseEntity.ok(ApiResponse.ok("OK", stats));
    }

    // ----------------------------------------------------------------
    // GET /api/admin/users
    // User management table — each row is UsersModel + membership + booking count
    // ----------------------------------------------------------------
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<AdminUserDto>>> getAllUsers() {
        List<UsersModel> users = userService.getAllUsers();

        List<AdminUserDto> dtos = users.stream().map(u -> {
            AdminUserDto dto = new AdminUserDto();
            dto.setUserId(u.getUserId());
            dto.setUsername(u.getUsername());
            dto.setEmail(u.getEmail());
            dto.setFirstName(u.getFirstName());
            dto.setLastName(u.getLastName());
            dto.setUserRole(u.getUserRole());
            dto.setActive(u.getisActive());

            // Membership info — safe default if not found
            try {
                MembershipModel mem = membershipService.getByUserId(u.getUserId());
                dto.setMembershipType(mem.getMembershipType());
                dto.setTotalLoyaltyPoints(mem.getTotalLoyaltyPoints());
            } catch (IllegalArgumentException e) {
                dto.setMembershipType("NONE");
                dto.setTotalLoyaltyPoints(0);
            }

            dto.setTotalBookings(bookingService.getTotalBookingsByUser(u.getUserId()));

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.ok("OK", dtos));
    }

    // ----------------------------------------------------------------
    // PUT /api/admin/users/{id}/toggle
    // Activate or deactivate a user account — the body carries { active: true/false }
    // AdminPanel.jsx sends the desired NEW state
    // ----------------------------------------------------------------
    @PutMapping("/users/{id}/toggle")
    public ResponseEntity<ApiResponse<Void>> toggleUser(
            @PathVariable int id,
            @RequestBody Map<String, Boolean> body) {

        Boolean activate = body.get("active");
        if (activate == null) throw new IllegalArgumentException("'active' field is required");

        if (activate) {
            userService.activateUser(id);
        } else {
            userService.deactivateUser(id);
        }

        return ResponseEntity.ok(ApiResponse.ok(activate ? "User activated" : "User deactivated"));
    }

    // ----------------------------------------------------------------
    // GET /api/admin/bookings
    // Booking management table — each row is enriched with movie name,
    // screen name, username, and showtime details
    // ----------------------------------------------------------------
    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<List<AdminBookingDto>>> getAllBookings() {
        List<BookingModel> bookings = bookingService.getAllBookings();

        List<AdminBookingDto> dtos = bookings.stream().map(b -> {
            AdminBookingDto dto = new AdminBookingDto();
            dto.setBookingId(b.getBookingId());
            dto.setTotalAmount(b.getTotalAmount().doubleValue());
            dto.setLoyaltyPointsEarned(b.getLoyaltyPointsEarned());
            dto.setBookingStatus(b.getBookingStatus());
            dto.setBookingDate(b.getBookingDate());

            // Username
            try {
                UsersModel u = userService.getUserById(b.getUserId());
                dto.setUsername(u.getUsername());
            } catch (IllegalArgumentException e) {
                dto.setUsername("Unknown");
            }

            // Showtime → movie name + screen name + show date + start time
            ticketRepository.findByBookingId(b.getBookingId())
                    .stream().findFirst().ifPresent(ticket -> {
                        showtimeRepository.findById(ticket.getShowtimeId()).ifPresent(st -> {
                            dto.setShowDate(st.getShowDate());
                            dto.setStartTime(st.getStartTime());

                            movieRepository.findById(st.getMovieId())
                                    .ifPresent(m -> dto.setMovieName(m.getMovieName()));

                            screenRepository.findById(st.getScreenId())
                                    .ifPresent(sc -> dto.setScreenName(sc.getScreenName()));
                        });
                    });

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.ok("OK", dtos));
    }
}
