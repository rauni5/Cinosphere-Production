package com.cinosphere.controller;

import com.cinosphere.dto.ApiResponse;
import com.cinosphere.dto.ShowtimeRequest;
import com.cinosphere.model.ShowtimeModel;
import com.cinosphere.service.ShowtimeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GET  /api/showtimes/{id}          — single showtime (used by Booking.jsx)
 * GET  /api/showtimes?screenId={id} — all showtimes for a screen
 * POST /api/showtimes  [ADMIN]      — create showtime
 */
@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    @Autowired private ShowtimeService showtimeService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowtimeModel>> getShowtime(@PathVariable int id) {
        return ResponseEntity.ok(ApiResponse.ok("OK", showtimeService.getShowtimeById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowtimeModel>>> getShowtimes(
            @RequestParam(required = false) Integer screenId,
            @RequestParam(required = false) Integer movieId) {

        List<ShowtimeModel> list;
        if (screenId != null)     list = showtimeService.getShowtimesByScreenId(screenId);
        else if (movieId != null) list = showtimeService.getShowtimesByMovieId(movieId);
        else                      list = List.of();

        return ResponseEntity.ok(ApiResponse.ok("OK", list));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ShowtimeModel>> createShowtime(
            @Valid @RequestBody ShowtimeRequest request) {

        ShowtimeModel saved = showtimeService.createShowtime(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Showtime created", saved));
    }
}
