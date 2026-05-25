package com.cinosphere.model;

import java.time.LocalDate;
/**
 * Model class representing database table Ticket and its attributes
 */
public class TicketModel {
	private int ticketId;
    private int bookingId;
    private int showtimeId;
    private int seatId;
    private String ticketType;
    private String ticketStatus;
    private LocalDate issueDate;
    private double ticketPrice;
    
    /**
     * Returns the unique ticket ID.
     * 
     * @return int
     */
    public int getTicketId() {
    	return ticketId;
    }

    /**
     * Sets the unique ticket ID.
     * 
     * @param ticketId
     */
    public void setTicketId(int ticketId) { 
    	this.ticketId = ticketId; 
    }

    /**
     * Returns the booking ID associated with the ticket.
     * 
     * @return int
     */
    public int getBookingId() {
    	return bookingId; 
    }

    /**
     * Sets the booking ID associated with the ticket.
     * 
     * @param bookingId
     */
    public void setBookingId(int bookingId) { 
    	this.bookingId = bookingId; 
    }

    /**
     * Returns the showtime ID associated with the ticket.
     * 
     * @return int
     */
    public int getShowtimeId() { 
    	return showtimeId; 
    }

    /**
     * Sets the showtime ID associated with the ticket.
     * 
     * @param showtimeId
     */
    public void setShowtimeId(int showtimeId) { 
    	this.showtimeId = showtimeId; 
    }

    /**
     * Returns the seat ID associated with the ticket.
     * 
     * @return int
     */
    public int getSeatId() { 
    	return seatId; 
    }

    /**
     * Sets the seat ID associated with the ticket.
     * 
     * @param seatId
     */
    public void setSeatId(int seatId) { 
    	this.seatId = seatId; 
    }

    /**
     * Returns the ticket type.
     * 
     * @return String
     */
    public String getTicketType() { 
    	return ticketType; 
    }

    /**
     * Sets the ticket type.
     * 
     * @param ticketType
     */
    public void setTicketType(String ticketType) { 
    	this.ticketType = ticketType; 
    }

    /**
     * Returns the ticket status.
     * 
     * @return String
     */
    public String getTicketStatus() { 
    	return ticketStatus;
    }

    /**
     * Sets the ticket status.
     * 
     * @param ticketStatus
     */
    public void setTicketStatus(String ticketStatus) { 
    	this.ticketStatus = ticketStatus;
    }

    /**
     * Returns the issue date of the ticket.
     * 
     * @return LocalDate
     */
    public LocalDate getIssueDate() { 
    	return issueDate; 
    }

    /**
     * Sets the issue date of the ticket.
     * 
     * @param issueDate
     */
    public void setIssueDate(LocalDate issueDate) {
    	this.issueDate = issueDate; 
    }

    /**
     * Returns the ticket price.
     * 
     * @return double
     */
    public double getTicketPrice() { 
    	return ticketPrice; 
    }

    /**
     * Sets the ticket price.
     * 
     * @param ticketPrice
     */
    public void setTicketPrice(double ticketPrice) { 
    	this.ticketPrice = ticketPrice; 
    }
}
