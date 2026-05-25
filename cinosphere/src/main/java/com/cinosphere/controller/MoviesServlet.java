package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.cinosphere.model.MovieModel;
import com.cinosphere.service.MovieService;

/**
 * Servlet implementation class MoviesServlet
 * 
 * This servlet is responsible for handling the movie listing page. It retrieves
 * filter parameters such as language, genre, status, and search keywords from
 * the request, and fetches the corresponding filtered movie list from the service
 * layer. The resulting data is then forwarded to the movies JSP page for display,
 * allowing users to browse and filter available movies dynamically.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/movies" })
public class MoviesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private MovieService service = new MovieService();
    /**
     * Handles GET requests for the movies page. It retrieves filter parameters from
     * the request, applies them to fetch a filtered list of movies from the service
     * layer, and forwards the processed data to the movies JSP page for rendering.
     * In case of errors, an appropriate message is displayed to the user.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String language = request.getParameter("langFilter");
		String genre = request.getParameter("genreFilter");
		String status = request.getParameter("status");
		String search = request.getParameter("movieSearch");
		
		List<MovieModel> filteredMovies = null;
		try {
			filteredMovies = service.getFilteredMovies(language, genre, status, search);	
		request.setAttribute("selectedLanguage",   language);
        request.setAttribute("selectedGenre",  genre);
        request.setAttribute("searchKeyword",  search);
        request.setAttribute("selectedStatus", status != null ? status : "all");
        request.setAttribute("filteredMovies",filteredMovies);
			
		} catch (Exception e) {
			request.setAttribute("error", "Could not load movies Please try again later.");
			e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/pages/movies.jsp").forward(request, response);
	}
}

