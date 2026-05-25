package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.cinosphere.model.UsersModel;
import com.cinosphere.service.UpdatePasswordService;
import com.cinosphere.utils.SessionUtil;

/**
 * Servlet implementation class UpdatePasswordServlet
 * 
 * This servlet handles password update functionality for logged-in users.
 * It processes the current password, new password, and confirmation password
 * submitted by the user, validates the credentials through the service layer,
 * and updates the password if all conditions are satisfied. Based on the result,
 * the user is either redirected back to the profile page or shown an error
 * message on the update profile page.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/updatepassword" })
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,
	    maxFileSize = 1024 * 1024 * 10,
	    maxRequestSize = 1024 * 1024 * 50
	)
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Handles POST requests for updating the user's password. It retrieves the
	 * current password, new password, and confirmation password from the request,
	 * validates them through the service layer, and updates the password if the
	 * provided data is valid. The user is redirected based on the success or
	 * failure of the operation.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword  = request.getParameter("confirmPassword");
		UpdatePasswordService update = new UpdatePasswordService();
		UsersModel user = (UsersModel) SessionUtil.getAttribute(request, "user");
		String status = update.authenticate(request, user, currentPassword, newPassword, confirmPassword);
		System.out.print(status);
		if ("Success".equals(status)) {
	        	response.sendRedirect(request.getContextPath() + "/profile");
	        	
	        	System.out.print(status);
	        }else {
	        	response.sendRedirect(request.getContextPath() + "/updateprofile");
	        }
	}

}
