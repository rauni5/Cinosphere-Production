package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.cinosphere.model.UsersModel;
import com.cinosphere.service.UserService;
import com.cinosphere.utils.SessionUtil;

/**
 * Servlet implementation class DeleteAccountServlet
 * 
 * This servlet is responsible for handling user account deletion requests.
 * It deactivates the user account in the system and invalidates the current
 * session to log the user out. After successful deactivation, the user is
 * redirected to the application homepage.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/deleteaccount" })
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests for account deletion. It retrieves the currently logged-in
     * user from the session, deactivates the account via the service layer, and
     * invalidates the session before redirecting the user to the homepage.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService service = new UserService();
		UsersModel customer = (UsersModel) SessionUtil.getAttribute(request, "user");
		try {
			service.deactivateUser(customer.getUserId());
			SessionUtil.invalidateSession(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/");
	}

	/**
	 * Handles POST requests for account deletion. This servlet delegates POST
	 * requests to the GET method since both perform the same deactivation operation.
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
