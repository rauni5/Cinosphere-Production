package com.cinosphere.service;

import java.time.LocalDate;
import java.util.List;

import com.cinosphere.dao.UsersDAO;
import com.cinosphere.dao.MembershipDAO;
import com.cinosphere.model.UsersModel;
import com.cinosphere.utils.PasswordUtil;

/**
 *  service class to handle registration operation
 *  Contains method to add user,validate,check email and username
 *  
 *  @author Raunit Giri
 */
public class RegisterService {
	private UsersDAO  customerdao = new UsersDAO();
	private MembershipDAO membership = new MembershipDAO();
	/**
	 * register customer using customerDAO method
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param email
	 * @param dateOfBirth
	 * @param gender
	 * @param password
	 * @throws Exception
	 */
	public void addCustomer(String firstName, String lastName, String username, String email, LocalDate dateOfBirth, String gender,String password) throws Exception {
		password = PasswordUtil.getHashPassword(password);
		boolean status = customerdao.insert(firstName, lastName, username, email, dateOfBirth, gender, password,"CUSTOMER");
		if(!status) {
			throw new Exception("Failed to insert customer");
		}
		UsersModel customer = customerdao.findByUsername(username);
		int customerid = customer.getUserId();
		status = membership.insert(customerid,"STARTER" ,"Active", 0, 0);
		if(!status) {
			throw new Exception("Failed to create membership");
		}
	}
	/**
	 * Validates entered filed before registering user
	 * This makes it so that only correct and complete values are entered.
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param userName
	 * @param dob
	 * @param email
	 * @param password
	 * @param confirmPassword
	 * @return Status of Authentication checks
	 * @throws Exception
	 */
	public String Authentication(String firstName,String lastName,String gender,String userName, LocalDate dob, String email,String password,String confirmPassword) throws Exception {
        String status = null;
		if (firstName == null || firstName.trim().isEmpty()) {
            status = "Invalid first name";
        } 
        else if (!firstName.matches("[a-zA-Z]+")) {
            status = "First name must contain only letters";
        }
        else if (lastName == null || lastName.trim().isEmpty()) {
            status = "Invalid last name";
        }
        else if (!lastName.matches("[a-zA-Z]+")) {
            status = "First name must contain only letters";
        } 
        else if (!lastName.matches("[a-zA-Z]+")) {
            status = "last name must contain only letters";
        } 
        else if (dob.isAfter(LocalDate.now())) {
            status = "Invalid date of birth";
        }else if (password == null || password.length() < 8) {
            status = "Password must be at least 8 characters";
        }
        else if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
        	status = "Enter correct email";
        }
        else if(!gender.toLowerCase().equals("male")&&!gender.toLowerCase().equals("female")&&!gender.toLowerCase().equals("other")) {
        	status = "Gender must be male/female/other";
        }
        else if (!password.equals(confirmPassword)) {
            status = "Passwords do not match";
        } 
        else if (EmailCheck(email)) {
          
        	status = "Email already exists";
        } 
        else if (UsernameCheck(userName)) {
            status = "Username already exists";
        }
        return status;
	}
	
	
	/**
	 * Checks if email already exists
	 * @param email
	 * @return boolean
	 * @throws Exception
	 */
	public boolean EmailCheck(String email) throws Exception {
			if(customerdao.findByEmail(email)!=null) {
				return true;
			}
		return false;
	}
	/**
	 * Checks if username already exists
	 * @param username
	 * @return boolean
	 * @throws Exception
	 */
	public boolean UsernameCheck(String username) throws Exception {
			if(customerdao.findByUsername(username)!=null) {
				return true;
			}
		return false;
	}
	}
