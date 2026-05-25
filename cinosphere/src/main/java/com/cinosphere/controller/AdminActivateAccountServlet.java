package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.cinosphere.service.UserService;

/**
 * Servlet implementation class AdminActivateAccountServlet
 *
 * This servlet handles activation and deactivation of user accounts from the admin panel.
 * It updates the user status based on the current account state, allowing administrators
 * to enable or disable user access without permanently deleting user data.
 *
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/adminactivateaccount" })
public class AdminActivateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Handles POST requests for toggling user account status between active and inactive states.
	 * Based on the current status of the user, it either activates or deactivates the account.
	 * After processing, the admin is redirected back to the user management section.
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userIdstr =request.getParameter("userId");
	    boolean current = Boolean.parseBoolean(request.getParameter("currentStatus"));
	    UserService service = new UserService();
	        try {
	        	int userId =  Integer.parseInt(userIdstr);
	            if(current){
	                service.deactivateUser(userId);
	            }
	            else{
	                service.activateUser(userId);
	            }

	        } catch(Exception e){
	            e.printStackTrace();
	        }

	        response.sendRedirect(request.getContextPath()+"/admin#user_management");
	    }	

}
