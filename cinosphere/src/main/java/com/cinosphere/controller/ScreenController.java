package com.cinosphere.controller;

import com.cinosphere.dto.ApiResponse;
import com.cinosphere.dto.UpdateBasePriceRequest;
import com.cinosphere.model.ScreenModel;
import com.cinosphere.service.ScreenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GET  /api/screens          — list all screens (used by UpdateMovie.jsx admin dropdown)
 * GET  /api/screens/{id}     — single screen + basePrice (used by Booking.jsx for pricing)
 * PUT  /api/screens/{id}/price [ADMIN] — update base ticket price
 */
@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    @Autowired private ScreenService screenService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ScreenModel>>> getAllScreens() {
        return ResponseEntity.ok(ApiResponse.ok("OK", screenService.getAllScreens()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScreenModel>> getScreen(@PathVariable int id) {
        return ResponseEntity.ok(ApiResponse.ok("OK", screenService.getScreenById(id)));
    }

    @PutMapping("/{id}/price")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ScreenModel>> updateBasePrice(
            @PathVariable int id,
            @Valid @RequestBody UpdateBasePriceRequest request) {

        ScreenModel updated = screenService.updateBasePrice(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Base price updated", updated));
    }
}
