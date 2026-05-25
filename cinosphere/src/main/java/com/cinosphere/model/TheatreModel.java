package com.cinosphere.model;
/**
 * Model class representing database table Theatre and its attributes
 */
public class TheatreModel {
	private int theatreId;
    private String theatreName;
    private String city;
    private String email;
    private String contactNumber;
    private int totalScreens;
    private String theatreStatus;
    /**
     * Returns the unique theatre ID.
     * 
     * @return int
     */
    public int getTheatreId() { 
    	return theatreId; 
    }

    /**
     * Sets the unique theatre ID.
     * 
     * @param theatreId
     */
    public void setTheatreId(int theatreId) { 
    	this.theatreId = theatreId; 
    }

    /**
     * Returns the theatre name.
     * 
     * @return String
     */
    public String getTheatreName() { 
    	return theatreName; 
    }

    /**
     * Sets the theatre name.
     * 
     * @param theatreName
     */
    public void setTheatreName(String theatreName) { 
    	this.theatreName = theatreName; 
    }

    /**
     * Returns the city of the theatre.
     * 
     * @return String
     */
    public String getCity() { 
    	return city; 
    }

    /**
     * Sets the city of the theatre.
     * 
     * @param city
     */
    public void setCity(String city) { 
    	this.city = city; 
    }

    /**
     * Returns the email of the theatre.
     * 
     * @return String
     */
    public String getEmail() { 
    	return email;
    }

    /**
     * Sets the email of the theatre.
     * 
     * @param email
     */
    public void setEmail(String email) { 
    	this.email = email; 
    }

    /**
     * Returns the contact number of the theatre.
     * 
     * @return String
     */
    public String getContactNumber() { 
    	return contactNumber; 
    }

    /**
     * Sets the contact number of the theatre.
     * 
     * @param contactNumber
     */
    public void setContactNumber(String contactNumber) { 
    	this.contactNumber = contactNumber; 
    }

    /**
     * Returns the total number of screens in the theatre.
     * 
     * @return int
     */
    public int getTotalScreens() { 
    	return totalScreens; 
    }

    /**
     * Sets the total number of screens in the theatre.
     * 
     * @param totalScreens
     */
    public void setTotalScreens(int totalScreens) { 
    	this.totalScreens = totalScreens; 
    }

    /**
     * Returns the theatre status.
     * 
     * @return String
     */
    public String getTheatreStatus() { 
    	return theatreStatus; 
    }

    /**
     * Sets the theatre status.
     * 
     * @param theatreStatus
     */
    public void setTheatreStatus(String theatreStatus) { 
    	this.theatreStatus = theatreStatus; 
    }
}
