package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.cinosphere.service.MovieService;

/**
 * Servlet implementation class AdminArchiveMovieServlet
 *
 * This servlet handles the archiving of movies from the admin panel. It updates the movie
 * status to ARCHIVE, effectively removing it from active listings while preserving its data
 * in the system for record-keeping and management purposes.
 *
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/archivemovie" })
public class AdminArchiveMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String STATUS = "ARCHIVE";
	MovieService movieService = new MovieService();
	
       
	/**
	 * Handles GET requests for archiving a movie by updating its status to ARCHIVE based on
	 * the provided movie ID. After successful processing, the admin is redirected back to
	 * the movie management section of the admin panel.
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String movieId = request.getParameter("movieId");
		if(movieId==null||movieId.isEmpty()) {
			response.sendRedirect(request.getContextPath()+"/admin");
			return;
		}
		int id = Integer.parseInt(movieId);
		try {
			movieService.updateMovieStatus(id, "ARCHIVE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/admin#movie_management");
	}

	/**
	 * Handles POST requests for archiving a movie by delegating the request to the GET method.
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
