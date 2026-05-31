package com.cinosphere.model;

import jakarta.persistence.*;

/**
 * Model class representing database table Screen and its attributes
 */
@Entity
@Table(name = "screen")
public class ScreenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_id")
    private int screenId;

    @Column(name = "theatre_id")
    private int theatreId;

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "screen_type")
    private String screenType;

    @Column(name = "screen_status")
    private String screenStatus;

    @Column(name = "total_capacity")
    private int totalCapacity;

    @Column(name = "base_price")
    private double basePrice;
    /**
     * Returns the unique screen ID.
     * 
     * @return int
     */
    public int getScreenId() { 
    	return screenId; 
    }

    /**
     * Sets the unique screen ID.
     * 
     * @param screenId
     */
    public void setScreenId(int screenId) { 
    	this.screenId = screenId; 
    }

    /**
     * Returns the theatre ID associated with the screen.
     * 
     * @return int
     */
    public int getTheatreId() { 
    	return theatreId; 
    }

    /**
     * Sets the theatre ID associated with the screen.
     * 
     * @param theatreId
     */
    public void setTheatreId(int theatreId) { 
    	this.theatreId = theatreId; 
    }

    /**
     * Returns the screen name.
     * 
     * @return String
     */
    public String getScreenName() { 
    	return screenName; 
    }

    /**
     * Sets the screen name.
     * 
     * @param screenName
     */
    public void setScreenName(String screenName) { 
    	this.screenName = screenName; 
    }

    /**
     * Returns the screen type.
     * 
     * @return String
     */
    public String getScreenType() { 
    	return screenType; 
    }

    /**
     * Sets the screen type.
     * 
     * @param screenType
     */
    public void setScreenType(String screenType) { 
    	this.screenType = screenType; 
    }

    /**
     * Returns the screen status.
     * 
     * @return String
     */
    public String getScreenStatus() { 
    	return screenStatus; 
    }

    /**
     * Sets the screen status.
     * 
     * @param screenStatus
     */
    public void setScreenStatus(String screenStatus) { 
    	this.screenStatus = screenStatus; 
    }

    /**
     * Returns the total seating capacity of the screen.
     * 
     * @return int
     */
    public int getTotalCapacity() { 
    	return totalCapacity; 
    }

    /**
     * Sets the total seating capacity of the screen.
     * 
     * @param totalCapacity
     */
    public void setTotalCapacity(int totalCapacity) { 
    	this.totalCapacity = totalCapacity; 
    }

    /**
     * Returns the base price of the screen.
     * 
     * @return double
     */
    public double getBasePrice() { 
    	return basePrice; 
    }

    /**
     * Sets the base price of the screen.
     * 
     * @param basePrice
     */
    public void setBasePrice(double basePrice) { 
    	this.basePrice = basePrice; 
    }
}
