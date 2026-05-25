package com.cinosphere.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cinosphere.dao.MovieDAO;
import com.cinosphere.dao.ScreenDAO;
import com.cinosphere.dao.ShowtimeDAO;
import com.cinosphere.dao.TheatreDAO;
import com.cinosphere.model.MovieModel;
import com.cinosphere.model.ScreenModel;
import com.cinosphere.model.ShowtimeModel;
import com.cinosphere.model.TheatreModel;
/**
 * Service class for schedule operation
 * Contains methods to create Date strip, and create Schedules
 * 
 * @author Raunit Giri
 */
public class SchedulesService {
	private ShowtimeDAO showtimeDAO = new ShowtimeDAO();
	private MovieDAO    movieDAO    = new MovieDAO();
	private ScreenDAO   screenDAO   = new ScreenDAO();
	private TheatreDAO theatreDAO = new TheatreDAO();
	/**
	 * Creates List of maped String containging details to create Date card
	 * @return List of Map String
	 */
	public List<Map<String, String>> getDateStrip() {
		List<Map<String, String>> dateList = new ArrayList<>();
		LocalDate today    = LocalDate.now();
		DateTimeFormatter dayFmt   = DateTimeFormatter.ofPattern("EEE");
		DateTimeFormatter monthFmt = DateTimeFormatter.ofPattern("MMM");

		for (int i = 0; i < 14; i++) {
			LocalDate d = today.plusDays(i);
			Map<String, String> dateMap = new LinkedHashMap<>();
			dateMap.put("value",  d.toString());
			dateMap.put("day",    d.format(dayFmt).toUpperCase());
			dateMap.put("number", String.valueOf(d.getDayOfMonth()));
			dateMap.put("month",  d.format(monthFmt).toUpperCase());
			dateList.add(dateMap);
		}
		return dateList;
	}
	
	
	/**
	 * Using the filters creates Object with values to create a schedule Card
	 * Uses multiple helper method
	 * @param selectedDate
	 * @param timeFilter
	 * @param formatFilter
	 * @param langFilter
	 * @param movieSearch
	 * @return Map<String, Object> which contains movieList and hallsList
	 * @throws Exception
	 */
	public Map<String, Object> getSchedules(String selectedDate, String timeFilter, String formatFilter, String langFilter, String movieSearch, String locationFilter) throws Exception {

		
		List<ShowtimeModel> filteredShowtimes =getFilteredShowtimes(selectedDate, timeFilter, formatFilter);
		
		Map<Integer, List<ShowtimeModel>> byMovie =groupShowtimesByMovie(filteredShowtimes);
		
		List<MovieModel> movieList =createMovieList(byMovie, langFilter, movieSearch,locationFilter);
		
		List<String> hallsList =createHallsList(byMovie, langFilter, movieSearch,locationFilter);
		
		Map<String, Object> result = new LinkedHashMap<>();result.put("movieList", movieList);result.put("hallsList", hallsList);
	
	return result;
	}
	/**
	 * Helper method to get showtimes using filter
	 * @param selectedDate
	 * @param timeFilter
	 * @param formatFilter
	 * @return SHowtime List
	 * @throws Exception
	 */
	private List<ShowtimeModel> getFilteredShowtimes(String selectedDate, String timeFilter, String formatFilter) throws Exception {
		
		LocalDate date = LocalDate.parse(selectedDate);
		List<ShowtimeModel> showtimes = showtimeDAO.findByDate(date.isBefore(LocalDate.now())?LocalDate.now():date);
		
		if (date.equals(LocalDate.now())) {
	        showtimes = showtimes.stream().filter(st -> st.getStartTime().isAfter(LocalTime.now())).collect(Collectors.toList());
	    }
	    if (timeFilter != null && !timeFilter.isEmpty()) {
	        showtimes = showtimes.stream().filter(st -> matchesTimeFilter(st.getStartTime(), timeFilter)) .collect(Collectors.toList());
	    }

	    if (formatFilter != null && !formatFilter.isEmpty() &&!formatFilter.equals("all")) {
	    	showtimes = showtimes.stream().filter(st -> formatFilter.equalsIgnoreCase(st.getShowType())).collect(Collectors.toList());
	    }
	    return showtimes;
	}
	/**
	 * helper method to create map of showtime grouped by movie 
	 * @param showtimes
	 * @return Map of movieId, showtime
	 */
	private Map<Integer, List<ShowtimeModel>> groupShowtimesByMovie(List<ShowtimeModel> showtimes) {

	    Map<Integer, List<ShowtimeModel>> mapMovie = new LinkedHashMap<>();

	    for (ShowtimeModel st : showtimes) {
	        mapMovie.computeIfAbsent( st.getMovieId(),k -> new ArrayList<>()).add(st);
	    }

	    return mapMovie;
	}
	/**
	 * helper method to create movie List wiht filters and maped showtime
	 * @param byMovie
	 * @param langFilter
	 * @param movieSearch
	 * @return movie List
	 * @throws Exception
	 */
	private List<MovieModel> createMovieList(Map<Integer, List<ShowtimeModel>> mapMovie,String langFilter,String movieSearch,String locationFilter) throws Exception {

		List<MovieModel> movieList = new ArrayList<>();

	    for (Map.Entry<Integer, List<ShowtimeModel>> movieEntry : mapMovie.entrySet()) {

	        MovieModel movie = movieDAO.findById(movieEntry.getKey());
	        if(movie.getMovieStatus().equals("ARCHIVE")) continue;

	        if (langFilter != null && !langFilter.isEmpty() && !movie.getMovieLanguage().equalsIgnoreCase(langFilter)) continue;

	        if ("ARCHIVE".equals(movie.getMovieStatus())) continue;
	        
	        if (movieSearch != null && !movieSearch.trim().isEmpty() && !movie.getMovieName().toLowerCase().contains(movieSearch.trim().toLowerCase())) continue;

	        if (locationFilter != null && !locationFilter.isEmpty() && !locationFilter.equals("all")) {
	            boolean hasMatchingHall = false;
	            for (ShowtimeModel st : movieEntry.getValue()) {
	                ScreenModel screen = screenDAO.findByScreenId(st.getScreenId());
	                TheatreModel theatre = theatreDAO.findById(screen.getTheatreId());
	                if (theatre.getCity().equalsIgnoreCase(locationFilter)) {
	                    hasMatchingHall = true;
	                    break;
	                }
	            }
	            if (!hasMatchingHall) continue;
	        }

	        movieList.add(movie);
	    }

	    return movieList;
	}
	/**
	 * helper method to create String of details needed to construct Schedule cards
	 * @param byMovie
	 * @param langFilter
	 * @param movieSearch
	 * @return String List
	 * @throws Exception
	 */
	private List<String> createHallsList( Map<Integer, List<ShowtimeModel>> mapMovie, String langFilter,String movieSearch,String locationFilter) throws Exception{

	    List<String> hallsList = new ArrayList<>();
	    DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mm a");

	    for (Map.Entry<Integer,List<ShowtimeModel>> movieEntry : mapMovie.entrySet()) {

	        MovieModel movie =movieDAO.findById(movieEntry.getKey());

	        if (langFilter != null && !langFilter.isEmpty() &&!movie.getMovieLanguage().equalsIgnoreCase(langFilter)) continue;

	        if (movieSearch != null &&!movieSearch.trim().isEmpty() &&!movie.getMovieName() .toLowerCase() .contains(movieSearch.trim().toLowerCase())) continue;

	        Map<Integer, List<ShowtimeModel>> byScreen = new LinkedHashMap<>();

	        for (ShowtimeModel st : movieEntry.getValue()) {
	        	byScreen.computeIfAbsent( st.getScreenId(), k -> new ArrayList<>()).add(st);
	        }        
	        StringBuilder halls = new StringBuilder();

	        for (Map.Entry<Integer,List<ShowtimeModel>> screenEntry : byScreen.entrySet()) {

	            ScreenModel screen =screenDAO.findByScreenId(screenEntry.getKey());
	            TheatreModel theatre = theatreDAO.findById(screen.getTheatreId());
	            
	            if (locationFilter != null && !locationFilter.isEmpty() && !locationFilter.equals("all") && !theatre.getCity().equalsIgnoreCase(locationFilter)) continue;
	            String hallLabel = theatre.getCity()+ " — " +screen.getScreenName() + " — " + screen.getScreenType();

	            String times = screenEntry.getValue().stream().map(st -> st.getStartTime().format(timeFmt)+ "." +st.getShowtimeId()).collect( Collectors.joining(","));
	            
	            if (halls.length() > 0)
	                halls.append(";");

	            halls.append(hallLabel).append("|").append(times).append("|").append(screen.getScreenId());
	        }
	        System.out.print(halls);
	        
	        hallsList.add(halls.toString());
	    }
	    
	    return hallsList;
	}
	
	/**
	 * @param time   showtime start time
	 * @param filter value from <select name="timeFilter">
	 * @return boolean
	 */
	private boolean matchesTimeFilter(LocalTime time, String filter) {
		switch (filter) {
			case "morning":   
				return time.isBefore(LocalTime.NOON);
			case "afternoon": 
				return !time.isBefore(LocalTime.NOON) && time.isBefore(LocalTime.of(17, 0));
			case "evening":   
				return !time.isBefore(LocalTime.of(17, 0))&& time.isBefore(LocalTime.of(21, 0));
			case "night":    
				return !time.isBefore(LocalTime.of(21, 0));
			default:          
				return true;
		}
	}
}