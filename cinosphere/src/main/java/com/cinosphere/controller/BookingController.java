package com.cinosphere.controller;

import com.cinosphere.dto.BookingRequest;
import com.cinosphere.dto.BookingResponse;
import com.cinosphere.model.BookingModel;
import com.cinosphere.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // GET /api/bookings/my  — current user's bookings
    @GetMapping("/my")
    public ResponseEntity<List<BookingModel>> getMyBookings(Authentication auth) {
        int username = Integer.parseInt(auth.getName());
        return ResponseEntity.ok(bookingService.getBookingsByUserId(username));
    }

    // POST /api/bookings  — create booking
    @PostMapping
    public ResponseEntity<?> createBooking(
            @RequestBody BookingRequest request,
            Authentication auth) {
        int username = Integer.parseInt(auth.getName());
        BookingResponse result = bookingService.createBooking(request, username);
        return ResponseEntity.ok(result);
    }
}