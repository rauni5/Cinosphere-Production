package com.cinosphere.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.cinosphere.utils.SessionUtil;



/**
 * Servlet Filter implementation class AuthenticationFilter
 * This filter filters out request made to user dependent pages from users who aren't logged in
 * Users without session are redirected toward login page
 * 
 * @author Raunit Giri
 */
@WebFilter({"/logout","/profile","/updateprofile","/admin","/booking","/updatepassword","/updatemovie","/updatebookingstatus","/deleteaccount","/archivemovie","/adminactivateaccount","/addmovie"})
public class AuthenticationFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	/**
	 * Main filter method that checks whether the user is logged in or not.
	 * If logged in → request is passed to next filter/servlet.
	 * If not logged in → user is redirected to login page and cache is disabled
	 * to prevent accessing protected pages using back button.
	 *
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		boolean isLoggedIn = SessionUtil.getAttribute(httpRequest, "user") != null;
		if(isLoggedIn) {
			httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			chain.doFilter(request, response);
		}else
		{
			httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
