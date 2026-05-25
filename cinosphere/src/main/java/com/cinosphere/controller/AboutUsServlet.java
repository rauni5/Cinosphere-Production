package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AboutUsServlet.
 * 
 * This servlet is responsible for displaying the About Us page of the application.
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/aboutus" })
public class AboutUsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Handles GET requests for the About Us page.
	 * 
	 * This method forwards the request directly to the About Us JSP page
	 * located under WEB-INF, ensuring that the page is not directly accessible
	 * from the browser URL.
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/pages/aboutus.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for the About Us page.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
