package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.cinosphere.utils.SessionUtil;

/**
 * Servlet implementation class LogoutServlet
 * 
 * This servlet handles user logout functionality. It invalidates the current
 * user session, effectively logging the user out of the system, and redirects
 * them back to the landing page. Since session validation is already handled
 * by filters, this servlet focuses solely on session termination and redirecting
 * the user to a public page.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * Handles GET requests for logging out the user. It invalidates the current
     * session and redirects the user to the homepage after logout.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SessionUtil.invalidateSession(request);
		response.sendRedirect(request.getContextPath() + "/");
	}
}
