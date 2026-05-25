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

import com.cinosphere.model.MovieModel;
import com.cinosphere.model.ScreenModel;
import com.cinosphere.model.ShowtimeModel;
import com.cinosphere.model.TheatreModel;
import com.cinosphere.service.MovieService;
import com.cinosphere.service.ScreenService;
import com.cinosphere.service.ShowtimeService;
import com.cinosphere.service.TheatreService;
import com.cinosphere.utils.FileuploadUtil;
/**
 * Servlet implementation class UpdateMovieServlet
 * 
 * This servlet manages the update functionality for movies in the system.
 * It handles both displaying existing movie details and processing updates
 * including movie metadata, scheduling showtimes, and media uploads such as
 * posters and background images. It also supports dynamic addition and removal
 * of showtime rows and ensures that updated schedules are properly stored in
 * the database. The servlet acts as the central controller for modifying movie
 * information from the admin panel.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/updatemovie" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize    = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class UpdateMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = System.getProperty("user.home") + File.separator + "webassets" + File.separator + "poster";
    private static final String BACKGROUND_UPLOAD_DIR =System.getProperty("user.home") + File.separator + "webassets" + File.separator + "background";

    private ScreenService   screenService   = new ScreenService();
    private TheatreService  theatreService  = new TheatreService();
    private MovieService    movieService    = new MovieService();
    private ShowtimeService showtimeService = new ShowtimeService();
    
    /**
     * Handles GET requests for loading the movie update page. It retrieves the
     * selected movie details using the movie ID, loads existing showtime schedules,
     * and prepares screen and theatre data required for editing. The data is then
     * forwarded to the update movie JSP page for display.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String movieIdStr = request.getParameter("movieId");
        if (movieIdStr == null || movieIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }

        try {
            int movieId = Integer.parseInt(movieIdStr);
            MovieModel movie = movieService.getMovieById(movieId);
            request.setAttribute("type",          "update");
            request.setAttribute("movieId",          movie.getMovieId());
            request.setAttribute("movieTitle",       movie.getMovieName());
            request.setAttribute("movieGenre",       movie.getGenre());
            request.setAttribute("movieLanguage",    movie.getMovieLanguage());
            request.setAttribute("movieCertificate", movie.getAgeRating());
            request.setAttribute("movieStatus",      movie.getMovieStatus());
            request.setAttribute("movieDirector",    movie.getDirector());
            request.setAttribute("movieDescription", movie.getDescription());
            request.setAttribute("movieReleaseDate", movie.getReleaseDate().toString());
            request.setAttribute("movieDuration",    movie.getDuration());
            loadShowtimesIntoRequest(request, movieId);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load movie data.");
        }

        loadScreen(request);
        request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
    }

   
    /**
     * Handles POST requests for updating movie information. It processes form data
     * including movie details, showtime schedules, and uploaded media files. Based
     * on the operation type (add row, delete row, or save), it dynamically updates
     * the schedule rows, validates input data, updates the movie record, manages
     * showtime records, and handles file uploads for posters and background images.
     * After successful processing, the user is redirected to the admin panel or
     * back to the update page in case of errors or validation failures.
     *
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String operation    = request.getParameter("operation");
        String deleteRow = request.getParameter("deleteRow");
        String movieIdStr = request.getParameter("movieId");  
        String rows         = request.getParameter("rows");
        String movieName    = request.getParameter("movieTitle");
        String genre        = request.getParameter("movieGenre");
        String movieLanguage    = request.getParameter("movieLanguage");
        String ageRating    = request.getParameter("movieCertificate");
        String movieStatus  = request.getParameter("movieStatus");
        String director     = request.getParameter("movieDirector");
        String description  = request.getParameter("movieDescription");
        String releaseDateStr = request.getParameter("movieReleaseDate");
        String durationStr  = request.getParameter("movieDuration");

        String[] halls = request.getParameterValues("scheduleHall[]");
        String[] dates = request.getParameterValues("scheduleDate[]");
        String[] times = request.getParameterValues("scheduleTime[]");

        int row = 0;
        if (rows != null && !rows.isEmpty()) {
            row = Integer.parseInt(rows);
        } 
        if ("add".equals(operation)) {
            row++;
            loadAttributes(request, movieIdStr, row, halls, dates, times, movieName, genre, movieLanguage, ageRating, movieStatus, director, description, releaseDateStr, durationStr);
            loadScreen(request);
            request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
            return;
        }
        if (deleteRow != null) {
		    int deleteIndex = Integer.parseInt(deleteRow);
		    if(row==0) {
		    	loadAttributes(request, movieIdStr, row, halls, dates, times, movieName, genre, movieLanguage, ageRating, movieStatus,director, description, releaseDateStr, durationStr);
			    loadScreen(request);
			    request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
			    return;
		    }
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
		    loadAttributes(request, movieIdStr, row, halls, dates, times, movieName, genre, movieLanguage, ageRating, movieStatus,director, description, releaseDateStr, durationStr);
		    loadScreen(request);
		    request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
		    return;
		}
        if ("save".equals(operation)) {
            loadAttributes(request, movieIdStr, row, halls, dates, times, movieName, genre, movieLanguage, ageRating, movieStatus,director, description, releaseDateStr, durationStr);
            if (movieIdStr == null || movieIdStr.isEmpty()) {
                request.setAttribute("error", "Movie ID is missing.");
                loadScreen(request);
                request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
                return;
            }
            if (movieName == null || movieName.isBlank()) {
                request.setAttribute("error", "Enter movie name.");
                loadScreen(request);
                request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
                return;
            }
            if (durationStr == null || !durationStr.matches("\\d+")) {
                request.setAttribute("error", "Duration must be a number.");
                loadScreen(request);
                request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
                return;
            }
            if (halls == null || dates == null || times == null) {
                request.setAttribute("error", "At least one schedule is required.");
                loadScreen(request);
                request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
                return;
            }
            try {
                int movieId   = Integer.parseInt(movieIdStr);
                int duration  = Integer.parseInt(durationStr);
                LocalDate releaseDate = LocalDate.parse(releaseDateStr);

                movieService.updateMovie(movieId, movieName, duration, director, genre, movieLanguage, description, releaseDate, movieStatus, ageRating);
                uploadImgIfPresent(request, "moviePoster",     movieId, UPLOAD_DIR);
                uploadImgIfPresent(request, "movieBackground", movieId, BACKGROUND_UPLOAD_DIR);
                showtimeService.deleteShowtimesByMovieId(movieId);
                for (int i = 0; i < halls.length; i++) {
                    if (halls[i].isBlank() || dates[i].isBlank() || times[i].isBlank()) continue;
                    LocalTime start = LocalTime.parse(times[i]);
                    LocalTime end   = start.plus(Duration.ofMinutes(duration));
                    showtimeService.insertShowtime(
                        Integer.parseInt(halls[i]), movieId,
                        LocalDate.parse(dates[i]), start, end,
                        "ACTIVE", "STANDARD");
                }

                response.sendRedirect(request.getContextPath() + "/admin");
                return;

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to update movie: " + e.getMessage());
                loadScreen(request);
                request.getRequestDispatcher("/WEB-INF/pages/updateMovie.jsp").forward(request, response);
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/updatemovie?movieId=" + movieIdStr);
    }
    /**
     * Populates request attributes with movie and schedule-related data so that
     * the JSP page can retain form values after add, delete, or validation actions.
     * This ensures that user-entered data is not lost during page refresh or
     * re-rendering of the update form.
     */
    private void loadAttributes(HttpServletRequest request, String movieId, int rows, String[] halls, String[] dates, String[] times, String movieName, String genre, String movieLang, String ageRating, String movieStatus, String director, String description, String releaseDateStr, String durationStr) {

    	request.setAttribute("type",          "update");
        request.setAttribute("movieId",          movieId);
        request.setAttribute("rows",             rows);
        request.setAttribute("scheduleHall",     halls);
        request.setAttribute("scheduleDate",     dates);
        request.setAttribute("scheduleTime",     times);
        request.setAttribute("movieTitle",       movieName);
        request.setAttribute("movieGenre",       genre);
        request.setAttribute("movieLanguage",    movieLang);
        request.setAttribute("movieCertificate", ageRating);
        request.setAttribute("movieStatus",      movieStatus);
        request.setAttribute("movieDirector",    director);
        request.setAttribute("movieDescription", description);
        request.setAttribute("movieReleaseDate", releaseDateStr);
        request.setAttribute("movieDuration",    durationStr);
    }
    /**
     * Loads all available screens and their corresponding theatre information
     * from the database and attaches them to the request. This data is used to
     * populate dropdowns or selection lists in the movie update interface.
     */
    private void loadScreen(HttpServletRequest request) {
        try {
            List<ScreenModel>  screens  = screenService.getAllScreens();
            List<TheatreModel> theatres = new ArrayList<>();
            for (ScreenModel screen : screens) {
                theatres.add(theatreService.getTheatreById(screen.getTheatreId()));
            }
            request.setAttribute("screens",  screens);
            request.setAttribute("theatres", theatres);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Retrieves existing showtime records for a specific movie and converts them
     * into arrays of hall IDs, dates, and times. These values are stored in the
     * request so that the update form can display and edit existing schedules.
     * If no showtimes exist, the method initializes an empty state for the form.
     */
    private void loadShowtimesIntoRequest(HttpServletRequest request, int movieId)
            throws Exception {
        List<ShowtimeModel> showtimes = showtimeService.getShowtimesByMovieId(movieId);
        if (showtimes == null || showtimes.isEmpty()) {
            request.setAttribute("rows", 0);
            return;
        }
        int size         = showtimes.size();
        String[] halls   = new String[size];
        String[] dates   = new String[size];
        String[] times   = new String[size];
        for (int i = 0; i < size; i++) {
            ShowtimeModel st = showtimes.get(i);
            halls[i] = String.valueOf(st.getScreenId());
            dates[i] = st.getShowDate().toString();         
            times[i] = st.getStartTime().toString();
        }
        request.setAttribute("rows",         size);
        request.setAttribute("scheduleHall", halls);
        request.setAttribute("scheduleDate", dates);
        request.setAttribute("scheduleTime", times);
    }
    /**
     * Handles optional image upload for movie assets such as posters and background
     * images. It validates whether a file is provided and is a valid image, removes
     * any existing file for the same movie ID, and saves the new file to the
     * configured upload directory. This ensures that only the latest image is
     * stored for each movie.
     */
    private void uploadImgIfPresent(HttpServletRequest request, String fieldName,
            int movieId, String uploadDir) {
        try {
            Part filePart = request.getPart(fieldName);
            if (filePart == null || filePart.getSize() == 0
                    || !FileuploadUtil.isImage(filePart)) {
                return;
            }
            File folder = new File(uploadDir);
            File[] old  = folder.listFiles((d, n) -> n.startsWith(movieId + "."));
            if (old != null) for (File f : old) f.delete();

            String ext      = FileuploadUtil.getFileExtension(filePart.getSubmittedFileName());
            String fileName = movieId + ext;
            FileuploadUtil.saveFile(filePart, uploadDir, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}