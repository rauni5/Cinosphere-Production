

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

import com.cinosphere.model.UsersModel;
import com.cinosphere.utils.SessionUtil;

/**
 * Servlet Filter implementation class AdminAuthenticationFIlter
 * This filter filters out request made to admin dependent pages from users who aren't logged in
 * and who don't poses administrator privileges
 * 
 * @author Raunit Giri
 */
@WebFilter({"/admin","/updatemovie","/updatebookingstatus","/archivemovie","/adminactivateaccount","/addmovie"})
public class AdminAuthenticationFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	/**
     * Main filter method that checks whether the user is logged in and has ADMIN role.
     * If not logged in → redirects to login page.
     * If logged in but not ADMIN → returns HTTP 403 Forbidden.
     * 
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		boolean isLoggedIn = SessionUtil.getAttribute(httpRequest, "user") != null;
		if (isLoggedIn) {
			UsersModel user = (UsersModel) SessionUtil.getAttribute(httpRequest, "user");
			boolean isAdmin = user.getUserRole().equals("ADMIN");
		 if(isAdmin) {
			 httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			 chain.doFilter(request, response);
		 }else {
			 httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		 }
		}else {
			httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
		}
			

		
		
	}


}

