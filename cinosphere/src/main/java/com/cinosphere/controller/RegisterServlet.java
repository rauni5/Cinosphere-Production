package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import com.cinosphere.service.RegisterService;

/**
 * Servlet implementation class RegisterServlet
 * 
 * This servlet handles user registration functionality for the system. It is
 * responsible for displaying the registration page and processing user input
 * during account creation. The servlet validates user-provided details through
 * the service layer, and if the registration is successful, it redirects the
 * user to the login page. In case of validation failure or errors, appropriate
 * error messages are forwarded back to the registration page for user feedback.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RegisterService register = new RegisterService();
	
	/**
	 * Handles GET requests for the registration page. It simply forwards the user
	 * to the register JSP page where the registration form is displayed.
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response); 
	}

	/**
	 * Handles POST requests for user registration. It retrieves user input from the
	 * form, validates the provided data through the registration service, and checks
	 * for errors such as invalid input or mismatched passwords. If validation fails,
	 * the user is redirected back to the registration page with error messages.
	 * Otherwise, a new user account is created and the user is redirected to the
	 * login page upon successful registration.
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		String status = null;
		String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        LocalDate dob = LocalDate.parse(request.getParameter("dob"));
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String username= request.getParameter("username");
        
        status = register.Authentication(firstName, lastName,gender, username, dob, email, password, confirmPassword);
        
        if (status != null) {
            request.setAttribute("error", status);
            request.setAttribute("username",username);
            request.setAttribute("firstName",firstName);
            request.setAttribute("lastName",lastName);
            request.setAttribute("email",email);
            request.setAttribute("dob", dob);
            request.setAttribute("gender", gender);
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }
        
        register.addCustomer(firstName, lastName, username, email, dob, gender,password);
        response.sendRedirect(request.getContextPath()+"/login");
        return;
		} catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("error", "Unexpected error occurred. Please try again.");
	            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	    }
	}

}
