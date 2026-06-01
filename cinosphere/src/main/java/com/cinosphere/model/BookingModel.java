package com.cinosphere.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;
/**
 * Model class representing database table Booking and its attributes
 */
@Entity
@Table(name = "booking")
public class BookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "booking_time")
    private LocalTime bookingTime;

    @Column(name = "booking_status")
    private String bookingStatus;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "booking_channel")
    private String bookingChannel;

    @Column(name = "loyalty_points_earned")
    private int loyaltyPointsEarned;
    /**
     * Returns the unique booking ID.
     * 
     * @return int
     */
    public int getBookingId() { 
    	return bookingId; 
    }

    /**
     * Sets the unique booking ID.
     * 
     * @param bookingId
     */
    public void setBookingId(int bookingId) { 
    	this.bookingId = bookingId; 
    }

    /**
     * Returns the user ID associated with the booking.
     * 
     * @return int
     */
    public int getUserId() { 
    	return userId; 
    }

    /**
     * Sets the user ID associated with the booking.
     * 
     * @param customerId
     */
    public void setUserId(int customerId) { 
    	this.userId = customerId; 
    }

    /**
     * Returns the booking date.
     * 
     * @return LocalDate
     */
    public LocalDate getBookingDate() { 
    	return bookingDate; 
    }

    /**
     * Sets the booking date.
     * 
     * @param bookingDate
     */
    public void setBookingDate(LocalDate bookingDate) { 
    	this.bookingDate = bookingDate; 
    }

    /**
     * Returns the booking time.
     * 
     * @return LocalTime
     */
    public LocalTime getBookingTime() { 
    	return bookingTime; 
    }

    /**
     * Sets the booking time.
     * 
     * @param bookingTime
     */
    public void setBookingTime(LocalTime bookingTime) { 
    	this.bookingTime = bookingTime; 
    }

    /**
     * Returns the booking status.
     * 
     * @return String
     */
    public String getBookingStatus() { 
    	return bookingStatus; 
    }

    /**
     * Sets the booking status.
     * 
     * @param bookingStatus
     */
    public void setBookingStatus(String bookingStatus) { 
    	this.bookingStatus = bookingStatus; 
    }

    /**
     * Returns the total booking amount.
     * 
     * @return double
     */
    public BigDecimal getTotalAmount() { 
    	return totalAmount; 
    }

    /**
     * Sets the total booking amount.
     * 
     * @param totalAmount
     */
    public void setTotalAmount(BigDecimal totalAmount) { 
    	this.totalAmount = totalAmount; 
    }

    /**
     * Returns the booking channel.
     * 
     * @return double
     */
    public String getBookingChannel() { 
    	return bookingChannel; 
    }

    /**
     * Sets the booking channel.
     * 
     * @param bookingChannel
     */
    public void setBookingChannel(String bookingChannel) { 
    	this.bookingChannel = bookingChannel; 
    }

    /**
     * Returns the loyalty points earned.
     * 
     * @return int
     */
    public int getLoyaltyPointsEarned() { 
    	return loyaltyPointsEarned; 
    }

    /**
     * Sets the loyalty points earned.
     * 
     * @param loyaltyPointsEarned
     */
    public void setLoyaltyPointsEarned(int loyaltyPointsEarned) { 
    	this.loyaltyPointsEarned = loyaltyPointsEarned; 
    }
}
