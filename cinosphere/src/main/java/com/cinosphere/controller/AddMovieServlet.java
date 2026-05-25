package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cinosphere.model.ScreenModel;
import com.cinosphere.model.TheatreModel;
import com.cinosphere.service.ScreenService;
import com.cinosphere.service.ShowtimeService;
import com.cinosphere.service.TheatreService;
import com.cinosphere.utils.FileuploadUtil;
import com.cinosphere.service.MovieService;

/**
 * Servlet implementation class AddMovieServlet.
 * 
 * This servlet provides functionality for administrators to add new movies
 * along with their schedules, screening details, and media assets.
 * It supports dynamic form operations such as adding or removing schedule rows,
 * validates movie input data, handles file uploads (poster and background images),
 * and creates associated showtime records in the system.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/addmovie" })
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,
	    maxFileSize = 1024 * 1024 * 10,
	    maxRequestSize = 1024 * 1024 * 50
	)
public class AddMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR =System.getProperty("user.home")+ File.separator+ "webassets"+ File.separator+"poster";  
	private static final String BACKGROUND_UPLOAD_DIR =System.getProperty("user.home")+ File.separator+ "webassets"+ File.separator+"background";  
	ScreenService screenService = new ScreenService();
	TheatreService theatreService = new TheatreService();
	MovieService movieService = new MovieService();
	ShowtimeService showtimeService = new ShowtimeService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Handles GET requests for the Add Movie servlet.  This method initializes the movie creation form by loading all available screens
     * and their associated theatres. It also sets the default state of the form  and forwards the request to the movie update page.
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loadScreen(request);
		request.setAttribute("rows", 0);
        request.setAttribute("type","add");
		request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for the Add Movie servlet. 
	 * This method processes form operations related to movie creation and scheduling.
	 * It supports adding schedule rows, deleting schedule rows, and saving the movie.
	 * During save, it validates input data, inserts the movie into the database,
	 * uploads poster and background images, and creates associated showtimes..
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operation = request.getParameter("operation");
		String deleteRow = request.getParameter("deleteRow");
		String rows = request.getParameter("rows");
		String movieName = request.getParameter("movieTitle");
		String genre = request.getParameter("movieGenre");
		String movieLanguage = request.getParameter("movieLanguage");
		String ageRating = request.getParameter("movieCertificate");
		String movieStatus = request.getParameter("movieStatus");
		String director = request.getParameter("movieDirector");
		String description = request.getParameter("movieDescription");
		String releaseDateStr = request.getParameter("movieReleaseDate");
		String durationStr = request.getParameter("movieDuration");

		String[] halls = request.getParameterValues("scheduleHall[]");
		String[] dates = request.getParameterValues("scheduleDate[]");
		String[] times = request.getParameterValues("scheduleTime[]");


		int row = 0;
		if (rows != null && !rows.isEmpty()) {
			row = Integer.parseInt(rows);
		}
		if ("add".equals(operation)) {

			row++;

			loadAttributes(request, row, halls, dates, times, movieName, genre, movieLanguage,
					ageRating, movieStatus, director, description, releaseDateStr, durationStr);

			loadScreen(request);
			request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
			return;
		}
		if (deleteRow != null) {
		    int deleteIndex = Integer.parseInt(deleteRow);
		    List<String> hallList = new ArrayList<>(Arrays.asList(halls));
		    List<String> dateList = new ArrayList<>(Arrays.asList(dates));
		    List<String> timeList = new ArrayList<>(Arrays.asList(times));
		    hallList.remove(deleteIndex);
		    dateList.remove(deleteIndex);
		    timeList.remove(deleteIndex);
		    halls = hallList.toArray(new String[0]);
		    dates = dateList.toArray(new String[0]);
		    times = timeList.toArray(new String[0]);
		    row--;
		    loadAttributes(request, row, halls, dates, times,movieName, genre, movieLanguage,ageRating, movieStatus, director,description, releaseDateStr, durationStr);
		    loadScreen(request);
		    request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
		    return;
		}

		if ("save".equals(operation)) {

			loadAttributes(request, row, halls, dates, times, movieName, genre, movieLanguage,
					ageRating, movieStatus, director, description, releaseDateStr, durationStr);

			try {
				if (movieName == null || movieName.isBlank()) {
					request.setAttribute("error", "Enter movie name");
					request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
					return;
				}

				if (durationStr == null || !durationStr.matches("\\d+")) {
					request.setAttribute("error", "Duration must be a number");
					request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
					return;
				}

				int duration = Integer.parseInt(durationStr);
				LocalDate releaseDate = LocalDate.parse(releaseDateStr);


				if (halls == null || dates == null || times == null) {
					request.setAttribute("error", "At least one schedule required");
					request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
					return;
				}


				int movieId = movieService.insertAndGetId(movieName, duration, director, genre,
				movieLanguage, description, releaseDate, movieStatus, ageRating);
				uploadImg(request,"moviePoster", movieId, BACKGROUND_UPLOAD_DIR);
				uploadImg(request,"movieBackground", movieId, UPLOAD_DIR);
				

				for (int i = 0; i < halls.length; i++) {


					if (halls[i].isBlank() || dates[i].isBlank() || times[i].isBlank()) {
						continue;
					}

					LocalTime start = LocalTime.parse(times[i]);
					LocalTime end = start.plus(Duration.ofMinutes(duration));

					showtimeService.insertShowtime(Integer.parseInt(halls[i]),movieId,LocalDate.parse(dates[i]),start,end,"ACTIVE","STANDARD");}



			} catch (Exception e) {

				e.printStackTrace();
				request.setAttribute("error", "Failed to save movie");
				request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
				return;
			}
		}

		loadAttributes(request, row, halls, dates, times, movieName, genre, movieLanguage,
				ageRating, movieStatus, director, description, releaseDateStr, durationStr);

		response.sendRedirect(request.getContextPath() + "/updatemovie");
	}
	/**
	 * Loads all movie form data and schedule arrays into the request scope.
	 * This method is used to persist form state across forward operations such as
	 * add row, delete row, or validation failure.
	 *
	 */
	private void loadAttributes(HttpServletRequest request, int rows, String[] halls,
			String[] dates, String[] times, String movieName, String genre,
			String movieLanguage, String ageRating, String movieStatus,
			String director, String description,
			String releaseDateStr, String durationStr) {

		request.setAttribute("rows", rows);
		request.setAttribute("scheduleHall", halls);
		request.setAttribute("scheduleDate", dates);
		request.setAttribute("scheduleTime", times);
		request.setAttribute("movieTitle", movieName);
		request.setAttribute("movieGenre", genre);
		request.setAttribute("movieLanguage", movieLanguage);
		request.setAttribute("movieCertificate", ageRating);
		request.setAttribute("movieStatus", movieStatus);
		request.setAttribute("movieDirector", director);
		request.setAttribute("movieDescription", description);
		request.setAttribute("movieReleaseDate", releaseDateStr);
		request.setAttribute("movieDuration", durationStr);
	}
	/**
	 * Loads all available screens and their associated theatres into the request scope.
	 * @param request the HTTP request object used to store screen and theatre lists
	 */
	private void loadScreen(HttpServletRequest request) {
		List<ScreenModel> screens = null;
		List<TheatreModel> theatres = new ArrayList<>();
		try {

			screens = screenService.getAllScreens();

			for (ScreenModel screen : screens) {
				theatres.add(theatreService.getTheatreById(screen.getTheatreId()));
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("screens", screens);
		request.setAttribute("theatres", theatres);
	}
	/**
	 * Uploads and saves an image file for a movie.
	 * If a new image is provided, it deletes any existing image for the same movie ID
	 * before saving the new one
	 */
	private void uploadImg(HttpServletRequest request,String fieldName,int movieId, String path) throws Exception {
		Part filePart = request.getPart(fieldName);
		 if (FileuploadUtil.isImage(filePart)) {
			 
			 String userId = String.valueOf(movieId);
			 File folder = new File(path);
			    File[] oldFiles = folder.listFiles((dir, name) -> name.startsWith(userId + "."));
			    if (oldFiles != null) {
			        for (File old : oldFiles) {
			            old.delete();
			        }
			    }
		 }
		 String extension = FileuploadUtil.getFileExtension(filePart.getSubmittedFileName());
        String fileName = movieId + extension;
        FileuploadUtil.saveFile(filePart, path, fileName);
	}
}