package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.cinosphere.dao.UsersDAO;
import com.cinosphere.model.UsersModel;
import com.cinosphere.service.LoginService;
import com.cinosphere.utils.SessionUtil;



/**
 * Servlet implementation class LoginServlet
 * 
 * This servlet handles user authentication for the system. It is responsible
 * for displaying the login page and processing login credentials submitted by
 * the user. On successful authentication, it creates a user session and redirects
 * the user either to the admin dashboard or user profile depending on their role.
 * On failure, it returns the user back to the login page with an appropriate
 * error message.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService loginService= new LoginService();
	/**
	 * Handles GET requests for the login page. It loads any saved login cookie
	 * data (such as username) to improve user experience and forwards the request
	 * to the login JSP page.
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("typedUser", loginService.getLoginCookie(request,"username"));
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for login authentication. It retrieves user credentials
	 * from the request, validates them through the login service, creates a session
	 * and login cookie on success, and redirects the user based on their role.
	 * If authentication fails, the user is returned to the login page with an error
	 * message.
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Extracting and using details from request to authenticate and log user
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String status = loginService.authenticate(username, password,request); // logs user if credentials match and creates session
        if ("Success".equals(status)) {
        	UsersModel user= (UsersModel) SessionUtil.getAttribute(request, "user");
			try {
				loginService.createLoginCookie(response, username,  43200);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	if(user!=null && user.getUserRole().equals("ADMIN")) {
        		response.sendRedirect(request.getContextPath() + "/admin");
        	}else {
        		response.sendRedirect(request.getContextPath() + "/profile");
        	}
        	
        }else {
        	//forwarding error message
        	request.setAttribute("error", status);
            request.setAttribute("typedUser", username); 
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
	}

}
