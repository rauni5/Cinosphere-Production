package com.cinosphere.model;
/**
 * Model class representing database table Feedback and its attributes
 */
public class SeatModel {
	private int seatId;
    private int screenId;
    private int seatNumber;
    private String rowNumber;
    private String seatType;
    private String seatStatus;
    /**
     * Returns the unique seat ID.
     * 
     * @return int
     */
    public int getSeatId() { 
    	return seatId;
    }

    /**
     * Sets the unique seat ID.
     * 
     * @param seatId
     */
    public void setSeatId(int seatId) { 
    	this.seatId = seatId; 
    }

    /**
     * Returns the screen ID associated with the seat.
     * 
     * @return int
     */
    public int getScreenId() { 
    	return screenId; 
    }

    /**
     * Sets the screen ID associated with the seat.
     * 
     * @param screenId
     */
    public void setScreenId(int screenId) { 
    	this.screenId = screenId; 
    }

    /**
     * Returns the seat number.
     * 
     * @return int
     */
    public int getSeatNumber() { 
    	return seatNumber; 
    }

    /**
     * Sets the seat number.
     * 
     * @param seatNumber
     */
    public void setSeatNumber(int seatNumber) { 
    	this.seatNumber = seatNumber; 
    }

    /**
     * Returns the row number of the seat.
     * 
     * @return String
     */
    public String getRowNumber() { 
    	return rowNumber; 
    }

    /**
     * Sets the row number of the seat.
     * 
     * @param rowNumber
     */
    public void setRowNumber(String rowNumber) { 
    	this.rowNumber = rowNumber; 
    }

    /**
     * Returns the seat type.
     * 
     * @return String
     */
    public String getSeatType() { 
    	return seatType; 
    }

    /**
     * Sets the seat type.
     * 
     * @param seatType
     */
    public void setSeatType(String seatType) { 
    	this.seatType = seatType; 
    }

    /**
     * Returns the seat status.
     * 
     * @return String
     */
    public String getSeatStatus() { 
    	return seatStatus; 
    }

    /**
     * Sets the seat status.
     * 
     * @param seatStatus
     */
    public void setSeatStatus(String seatStatus) { 
    	this.seatStatus = seatStatus; 
    }
}
