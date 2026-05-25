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
 * Servlet implementation class MovieDetailServlet
 * 
 * This servlet is responsible for displaying detailed information about a
 * selected movie. It retrieves the movie ID from the request, fetches the
 * corresponding movie details from the service layer, and also loads a list
 * of additional active movies for recommendation purposes. The collected data
 * is then forwarded to the movie detail JSP page for rendering.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/moviedetail" })
public class MovieDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MovieService movieService = new MovieService();
    /**
     * Handles GET requests for the movie detail page. It retrieves the movie ID
     * from the request, validates it, fetches movie details along with additional
     * recommended movies, and forwards the data to the movie detail JSP page.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<MovieModel> filteredMovies = null;
		String movieId = request.getParameter("movieId");
		try {
			if(movieId==null||movieId.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/movies");
				return;
			}
			int id = Integer.parseInt(movieId);
			filteredMovies = movieService.get4ActiveMovies();
			request.setAttribute("movie",movieService.getMovieById(id));
			request.setAttribute("filteredMovies",filteredMovies);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/pages/movieDetail.jsp").forward(request, response);
	}
}
