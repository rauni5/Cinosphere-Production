package com.cinosphere.model;

import java.time.LocalDate;

import jakarta.persistence.*;
/**
 * Model class representing database table Customer and its attributes				
 */
@Entity
@Table(name = "users") 
public class UsersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
	private int userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "gender")
    private String gender;
    @Column(name = "hash_password")
    private String hashPassword;
     @Column(name = "registration_date")
    private LocalDate registrationDate;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "user_role")
    private String userRole;
    /**
     * Returns the unique user ID.
     * 
     * @return int
     */
    public int getUserId() { 
    	return userId; 
    }

    /**
     * Sets the unique user ID.
     * 
     * @param customerId
     */
    public void setUserId(int customerId) { 
    	this.userId = customerId; 
    }

    /**
     * Returns the first name of the user.
     * 
     * @return String
     */
    public String getFirstName() {
    	return firstName;
    }

    /**
     * Sets the first name of the user.
     * 
     * @param firstName
     */
    public void setFirstName(String firstName) { 
    	this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     * 
     * @return String
     */
    public String getLastName() { 
    	return lastName; 
    }

    /**
     * Sets the last name of the user.
     * 
     * @param lastName
     */
    public void setLastName(String lastName) { 
    	this.lastName = lastName; 
    }

    /**
     * Returns the username of the user.
     * 
     * @return String
     */
    public String getUsername() { 
    	return username; 
    }

    /**
     * Sets the username of the user.
     * 
     * @param username
     */
    public void setUsername(String username) { 
    	this.username = username;
    }

    /**
     * Returns the email address of the user.
     * 
     * @return String
     */
    public String getEmail() { 
    	return email; 
    }

    /**
     * Sets the email address of the user.
     * 
     * @param email
     */
    public void setEmail(String email) { 
    	this.email = email; 
    }

    /**
     * Returns the date of birth of the user.
     * 
     * @return LocalDate
     */
    public LocalDate getDateOfBirth() { 
    	return dateOfBirth; 
    }

    /**
     * Sets the date of birth of the user.
     * 
     * @param dateOfBirth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) { 
    	this.dateOfBirth = dateOfBirth; 
    }

    /**
     * Returns the gender of the user.
     * 
     * @return String
     */
    public String getGender() { 
    	return gender; 
    }

    /**
     * Sets the gender of the user.
     * 
     * @param gender
     */
    public void setGender(String gender) { 
    	this.gender = gender; 
    }

    /**
     * Returns the hashed password of the user.
     * 
     * @return String
     */
    public String getHashPassword() { 
    	return hashPassword; 
    }

    /**
     * Sets the hashed password of the user.
     * 
     * @param hashPassword
     */
    public void setHashPassword(String hashPassword) { 
    	this.hashPassword = hashPassword; 
    }

    /**
     * Returns the registration date of the user.
     * 
     * @return LocalDate
     */
    public LocalDate getRegistrationDate() { 
    	return registrationDate; 
    }

    /**
     * Sets the registration date of the user.
     * 
     * @param registrationDate
     */
    public void setRegistrationDate(LocalDate registrationDate) { 
    	this.registrationDate = registrationDate;
    }

    /**
     * Returns the active status of the user.
     * 
     * @return boolean
     */
    public boolean getisActive() { 
    	return isActive;
    }

    /**
     * Sets the active status of the user.
     * 
     * @param active
     */
    public void setisActive(boolean active) { 
    	isActive = active;
    }

    /**
     * Returns the role of the user.
     * 
     * @return String
     */
    public String getUserRole() { 
    	return userRole;
    }

    /**
     * Sets the role of the user.
     * 
     * @param customerRole
     */
    public void setUserRole(String customerRole) { 
    	this.userRole = customerRole; 
    }
}
