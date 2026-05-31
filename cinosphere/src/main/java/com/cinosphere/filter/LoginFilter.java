package com.cinosphere.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.cinosphere.utils.SessionUtil;
import com.cinosphere.model.UsersModel;

/**
 * Servlet Filter implementation class LoginFilter
 * This filter filters out request made to /login and /register from users who are logged in
 * User with active session are redirected toward userpanel
 * 
 * @author Raunit Giri
 */
@WebFilter({"/login","/register"})
public class LoginFilter extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = 1L;
	/**
     * Main filter method that intercepts requests to /login and /register.
     * Redirects logged in users away from authentication pages.
     * 
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		UsersModel user =  (UsersModel) SessionUtil.getAttribute(httpRequest, "user");
		boolean isLoggedOut = user == null;
		if(isLoggedOut) {
			httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			chain.doFilter(request, response);
			return;
		}else
		{
			if(!user.getUserRole().equals("ADMIN")) {
			httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/profile");
			}else {
				httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin");
			}
		}
	}
}
