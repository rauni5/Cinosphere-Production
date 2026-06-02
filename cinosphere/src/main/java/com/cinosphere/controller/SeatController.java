package com.cinosphere.controller;

import com.cinosphere.dto.ApiResponse;
import com.cinosphere.model.SeatModel;
import com.cinosphere.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GET /api/seats?screenId={id} — all seats for a screen, used to render the seat map
 *
 * Used by: Booking.jsx
 */
@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired private SeatService seatService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SeatModel>>> getSeats(
            @RequestParam int screenId) {

        return ResponseEntity.ok(ApiResponse.ok("OK", seatService.getSeatsByScreenId(screenId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatModel>> getSeat(@PathVariable int id) {
        return ResponseEntity.ok(ApiResponse.ok("OK", seatService.getSeatById(id)));
    }
}

