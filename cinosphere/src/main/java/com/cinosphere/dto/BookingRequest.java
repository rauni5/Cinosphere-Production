package com.cinosphere.dto;

import jakarta.validation.constraints.*;
import java.util.List;

/**
 * DTO for creating a new booking.
 * Replaces the large collection of request.getParameter() calls
 * inside BookingServlet.doPost().
 *
 * seatIds         — the seats the user selected on the seat map
 * showtimeId      — which show they are booking
 * paymentMethod   — e.g. "CARD", "CASH", "ESEWA"
 * usePoints       — true if the user wants to redeem loyalty points
 */
public class BookingRequest {

    @NotNull(message = "Showtime ID is required")
    private Integer showtimeId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<Integer> seatIds;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    private boolean usePoints;

    public Integer getShowtimeId()          { return showtimeId; }
    public void setShowtimeId(Integer v)    { this.showtimeId = v; }

    public List<Integer> getSeatIds()       { return seatIds; }
    public void setSeatIds(List<Integer> v) { this.seatIds = v; }

    public String getPaymentMethod()        { return paymentMethod; }
    public void setPaymentMethod(String v)  { this.paymentMethod = v; }

    public boolean isUsePoints()            { return usePoints; }
    public void setUsePoints(boolean v)     { this.usePoints = v; }
}