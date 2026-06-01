package com.cinosphere.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for updating a screen's base ticket price (admin only).
 */
public class UpdateBasePriceRequest {

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.01", message = "Base price must be greater than 0")
    private Double basePrice;

    public Double getBasePrice()        { return basePrice; }
    public void setBasePrice(Double v)  { this.basePrice = v; }
}