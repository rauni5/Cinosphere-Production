package com.cinosphere.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for creating a new showtime (admin only).
 * Replaces the raw parameter reads inside AddMovieServlet / AdminPanelServlet.
 */
public class ShowtimeRequest {

    @NotNull(message = "Screen ID is required")
    private Integer screenId;

    @NotNull(message = "Movie ID is required")
    private Integer movieId;

    @NotNull(message = "Show date is required")
    @FutureOrPresent(message = "Show date cannot be in the past")
    private LocalDate showDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotBlank(message = "Show status is required")
    private String showStatus;

    @NotBlank(message = "Show type is required")
    private String showType;

    public Integer getScreenId()          { return screenId; }
    public void setScreenId(Integer v)    { this.screenId = v; }

    public Integer getMovieId()           { return movieId; }
    public void setMovieId(Integer v)     { this.movieId = v; }

    public LocalDate getShowDate()        { return showDate; }
    public void setShowDate(LocalDate v)  { this.showDate = v; }

    public LocalTime getStartTime()       { return startTime; }
    public void setStartTime(LocalTime v) { this.startTime = v; }

    public LocalTime getEndTime()         { return endTime; }
    public void setEndTime(LocalTime v)   { this.endTime = v; }

    public String getShowStatus()         { return showStatus; }
    public void setShowStatus(String v)   { this.showStatus = v; }

    public String getShowType()           { return showType; }
    public void setShowType(String v)     { this.showType = v; }
}