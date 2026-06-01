package com.cinosphere.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO returned after a booking is created or when listing a user's bookings.
 * Returns only the fields the client needs — never the raw DB model.
 */
public class BookingResponse {

    private int bookingId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private String bookingStatus;
    private double totalAmount;
    private int loyaltyPointsEarned;
    private String paymentMethod;

    // Enriched showtime info so the client can display confirmation details
    private int showtimeId;
    private String movieName;
    private LocalDate showDate;
    private LocalTime startTime;
    private String screenName;
    private String theatreCity;

    private List<TicketInfo> tickets;

    // ---- nested ticket summary ----
    public static class TicketInfo {
        private int ticketId;
        private String seatLabel;   // e.g. "A3"
        private String seatType;    // STANDARD / PREMIUM / VIP
        private double price;

        public int    getTicketId()   { return ticketId; }
        public String getSeatLabel()  { return seatLabel; }
        public String getSeatType()   { return seatType; }
        public double getPrice()      { return price; }

        public void setTicketId(int v)      { this.ticketId = v; }
        public void setSeatLabel(String v)  { this.seatLabel = v; }
        public void setSeatType(String v)   { this.seatType = v; }
        public void setPrice(double v)      { this.price = v; }
    }

    // ---- getters / setters ----
    public int getBookingId()                  { return bookingId; }
    public void setBookingId(int v)            { this.bookingId = v; }

    public LocalDate getBookingDate()          { return bookingDate; }
    public void setBookingDate(LocalDate v)    { this.bookingDate = v; }

    public LocalTime getBookingTime()          { return bookingTime; }
    public void setBookingTime(LocalTime v)    { this.bookingTime = v; }

    public String getBookingStatus()           { return bookingStatus; }
    public void setBookingStatus(String v)     { this.bookingStatus = v; }

    public double getTotalAmount()             { return totalAmount; }
    public void setTotalAmount(double v)       { this.totalAmount = v; }

    public int getLoyaltyPointsEarned()        { return loyaltyPointsEarned; }
    public void setLoyaltyPointsEarned(int v)  { this.loyaltyPointsEarned = v; }

    public String getPaymentMethod()           { return paymentMethod; }
    public void setPaymentMethod(String v)     { this.paymentMethod = v; }

    public int getShowtimeId()                 { return showtimeId; }
    public void setShowtimeId(int v)           { this.showtimeId = v; }

    public String getMovieName()               { return movieName; }
    public void setMovieName(String v)         { this.movieName = v; }

    public LocalDate getShowDate()             { return showDate; }
    public void setShowDate(LocalDate v)       { this.showDate = v; }

    public LocalTime getStartTime()            { return startTime; }
    public void setStartTime(LocalTime v)      { this.startTime = v; }

    public String getScreenName()              { return screenName; }
    public void setScreenName(String v)        { this.screenName = v; }

    public String getTheatreCity()             { return theatreCity; }
    public void setTheatreCity(String v)       { this.theatreCity = v; }

    public List<TicketInfo> getTickets()              { return tickets; }
    public void setTickets(List<TicketInfo> v)        { this.tickets = v; }
}