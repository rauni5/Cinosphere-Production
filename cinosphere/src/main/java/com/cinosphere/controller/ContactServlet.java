package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ContactServlet
 * 
 * This servlet is responsible for displaying the contact/experience page of
 * the application. It simply forwards user requests to the contact JSP page.
 * Since this page does not require any dynamic backend processing, the servlet
 * acts as a dispatcher to render the static view.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/experience" })
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 

    /**
     * Handles GET requests for the contact/experience page. It forwards the
     * request directly to the contact JSP page for rendering.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for the contact/experience page. This servlet
	 * delegates POST requests to the GET method since no separate processing
	 * is required.
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
