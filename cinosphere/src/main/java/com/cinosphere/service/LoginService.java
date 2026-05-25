package com.cinosphere.service;
import com.cinosphere.dao.UsersDAO;
import com.cinosphere.model.UsersModel;
import com.cinosphere.utils.CookieUtil;
import com.cinosphere.utils.PasswordUtil;
import com.cinosphere.utils.SessionUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Service class to handle login operation
 * Contains methods for authentication, add user to session and Cookie interactions
 * 
 * @author Raunit Giri
 * 
 */
public class LoginService {

	private UsersDAO userDAO = new UsersDAO();
	/**
	 * Check if username exists and password matches.
	 * if match found calls login method with userData and request
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	public String authenticate(String username,String password,HttpServletRequest request) {
		if (username == null || username.trim().isEmpty()) {
            return "Username is required";
        }
        if (password == null || password.isEmpty()) {
            return "Password is required";
        }
        try {
            UsersModel user = userDAO.findByUsername(username);
            if (user == null) {
                return "User doesn't exists";
            }
            if(!user.getisActive()) {
            	 return "Wait for Admin to verify";
            }

            // Verify the password using PAsswordUtil
            if (PasswordUtil.checkPassword(password, user.getHashPassword())) {
                return login(user,request);
            } 
            else {
                return "Password is incorrect";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Service unavailable";
        }
	}
	/**
	 * Use user data to create session on current request
	 * @param userData
	 * @param request
	 * @return
	 */
	public String login(UsersModel userData,HttpServletRequest request){
		try {  	
    	SessionUtil.setAttribute(request, "user", userData, 3600);
    	return "Success";
		}
		catch(Exception e){
			e.printStackTrace();
			return "Service unavailable";
		}

	}
	/**
	 * Create a cookie of latest login username
	 * @param response
	 * @param userName
	 * @param time
	 */
	public void createLoginCookie(HttpServletResponse response,String username,int time) {
		CookieUtil.addCookie(response, "username", username, time);
	}
	/**
	 * Finds value of login cookie
	 * @param request
	 * @param name
	 * @return
	 */
	public String getLoginCookie(HttpServletRequest request,String name) {
		return CookieUtil.getCookieValue(request, name);
	}
}
