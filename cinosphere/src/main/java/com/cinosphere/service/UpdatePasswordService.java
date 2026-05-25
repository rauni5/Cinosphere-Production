package com.cinosphere.service;

import com.cinosphere.dao.UsersDAO;
import com.cinosphere.model.UsersModel;
import com.cinosphere.utils.PasswordUtil;
import com.cinosphere.utils.SessionUtil;

import jakarta.servlet.http.HttpServletRequest;
/**
 * Service Class which facilitates password update
 * Contains methods to authenticate and updatepassowrd
 * 
 * @author Raunit Giri
 */
public class UpdatePasswordService {
	private UsersDAO userdao = new UsersDAO();
	/**
	 * Validates input values and updates password if conditions are correct
	 * @param request
	 * @param user
	 * @param currentPassword
	 * @param newPassword
	 * @param confirmPassword
	 * @return Status
	 */
	public String authenticate(HttpServletRequest request,UsersModel user,String currentPassword, String newPassword, String confirmPassword) {
		System.out.print("authenticateRan");
		if (currentPassword == null || currentPassword.trim().isEmpty()) {
            return "Current is required";
        }
        if (newPassword == null || newPassword.isEmpty()) {
            return "New Password is required";
        }
        else if (newPassword.length() < 8) {
            return "Password must be at least 8 characters";
        }
        if(!newPassword.equals(confirmPassword)) {
        	return "New Passwords dont match";
        }
        try {
        if(PasswordUtil.checkPassword(currentPassword,user.getHashPassword())) {
        	return updatePassword(request,user,PasswordUtil.getHashPassword(newPassword));
        	
        }else {
        	return "Current password is incorrect";
        }
      } catch (Exception e) {
          e.printStackTrace();
          return "Service unavailable";
      }
	}
	
	
	/**
	 * Helper method to update password of user
	 * @param request
	 * @param user
	 * @param Password
	 * @return
	 */
	private String updatePassword(HttpServletRequest request,UsersModel  user, String Password) {
		try {
			userdao.UpdateUserPassword(user.getUserId(), Password);
			user.setHashPassword(Password);
			SessionUtil.setAttribute(request, "user", user, 3600);
			return "Success";
		} catch (Exception e) {
			
			e.printStackTrace();
			return "service unavaiable";
		}
	}
}
