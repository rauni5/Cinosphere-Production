package com.cinosphere.controller;

import com.cinosphere.dto.ApiResponse;
import com.cinosphere.model.TheatreModel;
import com.cinosphere.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GET /api/theatres — all theatres, used by UpdateMovie.jsx to label screens by city
 *
 * Used by: UpdateMovie.jsx
 */
@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    @Autowired private TheatreService theatreService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TheatreModel>>> getAllTheatres() {
        return ResponseEntity.ok(ApiResponse.ok("OK", theatreService.getAllTheatres()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TheatreModel>> getTheatre(@PathVariable int id) {
        return ResponseEntity.ok(ApiResponse.ok("OK", theatreService.getTheatreById(id)));
    }
}
