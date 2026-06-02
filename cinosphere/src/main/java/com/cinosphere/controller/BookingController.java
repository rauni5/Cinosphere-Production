package com.cinosphere.controller;

import com.cinosphere.dto.ApiResponse;
import com.cinosphere.dto.BookingRequest;
import com.cinosphere.dto.BookingResponse;
import com.cinosphere.model.BookingModel;
import com.cinosphere.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * POST /api/bookings                              — create booking (authenticated)
 * GET  /api/bookings/my                           — current user's bookings
 * GET  /api/bookings/taken-seats?showtimeId={id}  — confirmed seat IDs for a showtime
 *
 * Used by: Booking.jsx, UserPanel.jsx
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired private BookingService bookingService;

    // ----------------------------------------------------------------
    // Create a new booking — full transactional flow in BookingService
    // ----------------------------------------------------------------
    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody BookingRequest request,
            Authentication auth) {

        BookingResponse response = bookingService.createBooking(request, Integer.parseInt(auth.getName()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Booking confirmed", response));
    }

    // ----------------------------------------------------------------
    // Current user's booking history (most recent first)
    // limit param lets UserPanel.jsx ask for just the last 5
    // ----------------------------------------------------------------
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getMyBookings(
            Authentication auth,
            @RequestParam(defaultValue = "20") int limit) {

        // Resolve userId from the JWT principal's username
        List<BookingModel> raw = bookingService.getBookingsByUserId(Integer.parseInt(auth.getName()));
        List<BookingResponse> result = raw.stream()
                .limit(limit)
                .map(bookingService::toResponse)
                .toList();

        return ResponseEntity.ok(ApiResponse.ok("OK", result));
    }

    // ----------------------------------------------------------------
    // Returns set of already-booked seatIds for a showtime
    // Used by Booking.jsx to grey out taken seats on the map
    // ----------------------------------------------------------------
    @GetMapping("/taken-seats")
    public ResponseEntity<ApiResponse<Set<Integer>>> getTakenSeats(
            @RequestParam int showtimeId) {

        Set<Integer> takenIds = bookingService.getConfirmedSeatIdsByShowtime(showtimeId);
        return ResponseEntity.ok(ApiResponse.ok("OK", takenIds));
    }
}
