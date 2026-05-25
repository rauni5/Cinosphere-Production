package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import com.cinosphere.model.MovieModel;
import com.cinosphere.service.MovieService;

/**
 * Servlet implementation class LandingServlet
 * 
 * This servlet handles the landing page (home page) of the application. It is
 * responsible for loading a limited set of active movies from the database and
 * passing them to the index page for display. This provides users with a preview
 * of currently available movies on the platform.
 * 
 * @author Raunit Giri
 */
@WebServlet(
		asyncSupported = true, 
		urlPatterns = {"/home"})
public class LandingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LandingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Handles GET requests for the home page. It retrieves a limited list of active
     * movies from the service layer and forwards them to the landing page for display.
     * If an error occurs during data retrieval, an error message is set for the view.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		MovieService service = new MovieService();
		try {
			List<MovieModel> activeMovies  = service.get4ActiveMovies();
			request.setAttribute("activeMovies",activeMovies);
		} catch (Exception e) {
			request.setAttribute("error", "Could not load movies Please try again later.");
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for the home page. This servlet delegates POST requests
	 * to the GET method since both operations perform the same function of loading
	 * landing page data.
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
