package com.cinosphere.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cinosphere.service.SchedulesService;
import com.cinosphere.utils.CookieUtil;

/**
 * Servlet implementation class SchedulesServlet
 * 
 * This servlet is responsible for displaying and managing the movie schedules
 * page. It retrieves filter values such as date, time, location, language, and
 * format from both cookies and request parameters, ensuring that user-selected
 * preferences are preserved across sessions. It then fetches the relevant
 * schedule data from the service layer and forwards it to the schedules JSP
 * for rendering the movie listing based on applied filters.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/schedules" })
public class SchedulesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SchedulesService schedulesService = new SchedulesService();

	/**
	 * Handles GET requests for the schedules page. It retrieves filter values from
	 * cookies and request parameters, stores updated preferences back into cookies,
	 * and fetches filtered movie schedule data based on the selected criteria. The
	 * prepared data is then forwarded to the schedules JSP page for display.
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String selectedDate   = CookieUtil.getCookieValue(request, "schedule_date");
		String movieSearch    = CookieUtil.getCookieValue(request, "schedule_search");
		String timeFilter     = CookieUtil.getCookieValue(request, "schedule_time");
		String locationFilter = CookieUtil.getCookieValue(request, "schedule_location");
		String langFilter     = CookieUtil.getCookieValue(request, "schedule_lang");
		String formatFilter   = CookieUtil.getCookieValue(request, "schedule_format");
		
		if(request.getParameterMap().containsKey("selectedDate")) {
			selectedDate   = request.getParameter("selectedDate");
		}
		if(request.getParameterMap().containsKey("movieSearch")) {
			movieSearch    = request.getParameter("movieSearch");
		}
		if(request.getParameterMap().containsKey("timeFilter")) {
			timeFilter     = request.getParameter("timeFilter");
		}
		if(request.getParameterMap().containsKey("locationFilter")){
			locationFilter = request.getParameter("locationFilter");
		}
		if(request.getParameterMap().containsKey("langFilter")) {
			langFilter     = request.getParameter("langFilter");
		}
		if(request.getParameterMap().containsKey("format")) {
			formatFilter   = request.getParameter("format");
		}
		int cookieAge=60 * 24 * 7;
		CookieUtil.addCookie(response, "schedule_date",     safeString(selectedDate),   cookieAge);
		CookieUtil.addCookie(response, "schedule_search",   safeString(movieSearch),    cookieAge);
		CookieUtil.addCookie(response, "schedule_time",     safeString(timeFilter),     cookieAge);
		CookieUtil.addCookie(response, "schedule_location", safeString(locationFilter), cookieAge);
		CookieUtil.addCookie(response, "schedule_lang",     safeString(langFilter),     cookieAge);
		CookieUtil.addCookie(response, "schedule_format",   safeString(formatFilter),   cookieAge);
		try {
			
			if(selectedDate == null||selectedDate.isEmpty()) {
				selectedDate = LocalDate.now().toString();
			}
			
			
			List<Map<String, String>> dateList = schedulesService.getDateStrip();
			Map<String, Object> schedules = schedulesService.getSchedules(selectedDate, timeFilter, formatFilter, langFilter, movieSearch,locationFilter);

			request.setAttribute("dateList",         dateList);
			request.setAttribute("selectedDate",     selectedDate);
			request.setAttribute("movieList",        schedules.get("movieList"));
			request.setAttribute("hallsList",        schedules.get("hallsList"));
			request.setAttribute("searchKeyword",    movieSearch);
			request.setAttribute("selectedTime",     timeFilter);
			request.setAttribute("selectedLocation", locationFilter);
			request.setAttribute("selectedLanguage", langFilter);
			request.setAttribute("selectedformat",   formatFilter);

		} catch (Exception e) {
			request.setAttribute("error", "Could not load schedules. Please try again later.");
			e.printStackTrace();
		}

		request.getRequestDispatcher("/WEB-INF/pages/schedules.jsp").forward(request, response);
	}
	/**
	 * Ensures that null values are safely converted into empty strings to prevent
	 * null-related errors when storing values in cookies or processing filters.
	 */
	private String safeString(String value) {
		return value != null ? value : "";
	}
}