package com.cinosphere.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for the schedule page filter query.
 * Replaces the five request.getParameter() calls in SchedulesServlet.doGet().
 * All fields are optional except selectedDate — the schedule page always
 * needs a date to query showtimes.
 */
public class ScheduleFilterRequest {

    @NotBlank(message = "Selected date is required")
    private String selectedDate;   // ISO format: "2024-08-15"

    private String timeFilter;     // morning | afternoon | evening | night
    private String formatFilter;   // 2D | 3D | IMAX | all
    private String langFilter;     // e.g. "English", "Nepali"
    private String movieSearch;    // partial movie name
    private String locationFilter; // city name or "all"

    public String getSelectedDate()          { return selectedDate; }
    public void setSelectedDate(String v)    { this.selectedDate = v; }

    public String getTimeFilter()            { return timeFilter; }
    public void setTimeFilter(String v)      { this.timeFilter = v; }

    public String getFormatFilter()          { return formatFilter; }
    public void setFormatFilter(String v)    { this.formatFilter = v; }

    public String getLangFilter()            { return langFilter; }
    public void setLangFilter(String v)      { this.langFilter = v; }

    public String getMovieSearch()           { return movieSearch; }
    public void setMovieSearch(String v)     { this.movieSearch = v; }

    public String getLocationFilter()        { return locationFilter; }
    public void setLocationFilter(String v)  { this.locationFilter = v; }
}