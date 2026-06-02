package com.cinosphere.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Enriched booking row for the admin booking management table.
 * Combines BookingModel + showtime + movie + screen + username into one flat object
 * so AdminPanel.jsx can render each table row without extra fetches.
 */
public class AdminBookingDto {

    private int       bookingId;
    private String    username;
    private String    movieName;
    private String    screenName;
    private LocalDate showDate;
    private LocalTime startTime;
    private double    totalAmount;
    private int       loyaltyPointsEarned;
    private String    bookingStatus;
    private LocalDate bookingDate;

    // ---- getters / setters ----

    public int       getBookingId()               { return bookingId; }
    public void      setBookingId(int v)           { this.bookingId = v; }

    public String    getUsername()                 { return username; }
    public void      setUsername(String v)         { this.username = v; }

    public String    getMovieName()                { return movieName; }
    public void      setMovieName(String v)        { this.movieName = v; }

    public String    getScreenName()               { return screenName; }
    public void      setScreenName(String v)       { this.screenName = v; }

    public LocalDate getShowDate()                 { return showDate; }
    public void      setShowDate(LocalDate v)      { this.showDate = v; }

    public LocalTime getStartTime()                { return startTime; }
    public void      setStartTime(LocalTime v)     { this.startTime = v; }

    public double    getTotalAmount()              { return totalAmount; }
    public void      setTotalAmount(double v)      { this.totalAmount = v; }

    public int       getLoyaltyPointsEarned()       { return loyaltyPointsEarned; }
    public void      setLoyaltyPointsEarned(int v)  { this.loyaltyPointsEarned = v; }

    public String    getBookingStatus()            { return bookingStatus; }
    public void      setBookingStatus(String v)    { this.bookingStatus = v; }

    public LocalDate getBookingDate()              { return bookingDate; }
    public void      setBookingDate(LocalDate v)   { this.bookingDate = v; }
}
