package com.cinosphere.controller;

import com.cinosphere.dto.ApiResponse;
import com.cinosphere.dto.ScheduleFilterRequest;
import com.cinosphere.dto.ScheduleResponse;
import com.cinosphere.service.SchedulesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * GET /api/schedules?selectedDate=2024-08-15&timeFilter=evening&langFilter=English&...
 *
 * All params except selectedDate are optional.
 * Returns a typed ScheduleResponse containing:
 *   - dateStrip  (14-day nav strip)
 *   - schedules  (movies → halls → showtimes)
 *
 * Used by: Schedules.jsx
 */
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired private SchedulesService schedulesService;

    @GetMapping
    public ResponseEntity<ApiResponse<ScheduleResponse>> getSchedules(
            @RequestParam String selectedDate,
            @RequestParam(required = false) String timeFilter,
            @RequestParam(required = false) String formatFilter,
            @RequestParam(required = false) String langFilter,
            @RequestParam(required = false) String movieSearch,
            @RequestParam(required = false) String locationFilter) {

        ScheduleFilterRequest filter = new ScheduleFilterRequest();
        filter.setSelectedDate(selectedDate);
        filter.setTimeFilter(timeFilter);
        filter.setFormatFilter(formatFilter);
        filter.setLangFilter(langFilter);
        filter.setMovieSearch(movieSearch);
        filter.setLocationFilter(locationFilter);

        ScheduleResponse response = schedulesService.getSchedules(filter);
        return ResponseEntity.ok(ApiResponse.ok("OK", response));
    }
}
