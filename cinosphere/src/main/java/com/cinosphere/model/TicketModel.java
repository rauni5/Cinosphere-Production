package com.cinosphere.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
/**
 * Model class representing database table Ticket and its attributes
 */
@Entity
@Table(name = "ticket")
public class TicketModel {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "showtime_id")
    private int showtimeId;

    @Column(name = "seat_id")
    private int seatId;

    @Column(name = "ticket_type")
    private String ticketType;

    @Column(name = "ticket_status")
    private String ticketStatus;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "ticket_price")
    private BigDecimal ticketPrice;
    
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
    public BigDecimal getTicketPrice() { 
    	return ticketPrice; 
    }

    /**
     * Sets the ticket price.
     * 
     * @param ticketPrice
     */
    public void setTicketPrice(BigDecimal ticketPrice) { 
    	this.ticketPrice = ticketPrice; 
    }
}
