package com.cinosphere.model;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Model class representing database table Payment and its attributes
 */
public class PaymentModel {
	private int paymentId;
    private int bookingId;
    private String paymentMethod;
    private double paymentAmount;
    private LocalDate paymentDate;
    private LocalTime paymentTime;
    private String paymentStatus;
    /**
     * Returns the unique payment ID.
     * 
     * @return int
     */
    public int getPaymentId() { 
    	return paymentId; 
    }

    /**
     * Sets the unique payment ID.
     * 
     * @param paymentId
     */
    public void setPaymentId(int paymentId) { 
    	this.paymentId = paymentId;
    }

    /**
     * Returns the booking ID associated with the payment.
     * 
     * @return int
     */
    public int getBookingId() { 
    	return bookingId; 
    }

    /**
     * Sets the booking ID associated with the payment.
     * 
     * @param bookingId
     */
    public void setBookingId(int bookingId) { 
    	this.bookingId = bookingId; 
    }

    /**
     * Returns the payment method used.
     * 
     * @return String
     */
    public String getPaymentMethod() { 
    	return paymentMethod; 
    }

    /**
     * Sets the payment method used.
     * 
     * @param paymentMethod
     */
    public void setPaymentMethod(String paymentMethod) { 
    	this.paymentMethod = paymentMethod; 
    }

    /**
     * Returns the payment amount.
     * 
     * @return double
     */
    public double getPaymentAmount() { 
    	return paymentAmount; 
    }

    /**
     * Sets the payment amount.
     * 
     * @param paymentAmount
     */
    public void setPaymentAmount(double paymentAmount) { 
    	this.paymentAmount = paymentAmount;
    }

    /**
     * Returns the payment date.
     * 
     * @return LocalDate
     */
    public LocalDate getPaymentDate() { 
    	return paymentDate; 
    }

    /**
     * Sets the payment date.
     * 
     * @param paymentDate
     */
    public void setPaymentDate(LocalDate paymentDate) { 
    	this.paymentDate = paymentDate; 
    }

    /**
     * Returns the payment time.
     * 
     * @return LocalTime
     */
    public LocalTime getPaymentTime() { 
    	return paymentTime; 
    }

    /**
     * Sets the payment time.
     * 
     * @param paymentTime
     */
    public void setPaymentTime(LocalTime paymentTime) { 
    	this.paymentTime = paymentTime; 
    }

    /**
     * Returns the payment status.
     * 
     * @return String
     */
    public String getPaymentStatus() { 
    	return paymentStatus; 
    }

    /**
     * Sets the payment status.
     * 
     * @param paymentStatus
     */
    public void setPaymentStatus(String paymentStatus) { 
    	this.paymentStatus = paymentStatus; 
    }
}
