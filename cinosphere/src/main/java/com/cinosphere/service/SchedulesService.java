package com.cinosphere.service;

import com.cinosphere.dto.ScheduleFilterRequest;
import com.cinosphere.dto.ScheduleResponse;
import com.cinosphere.dto.ScheduleResponse.*;
import com.cinosphere.model.*;
import com.cinosphere.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Builds the schedule page data.
 *
 * Changes from original SchedulesService:
 *  - Accepts a ScheduleFilterRequest DTO instead of five raw String parameters.
 *  - Returns a typed ScheduleResponse DTO instead of a raw Map<String,Object>
 *    with an encoded String for hall data — the old String format
 *    ("City — Screen — Type|10:30 AM.5,12:00 PM.6|3") was fragile and hard
 *    to consume on the client side.
 *  - Injected repositories instead of new *DAO() instances.
 *  - Private helper methods retained but simplified now that the result
 *    is built as proper objects rather than concatenated strings.
 */
@Service
public class SchedulesService {

    @Autowired private ShowtimeRepository showtimeRepository;
    @Autowired private MovieRepository    movieRepository;
    @Autowired private ScreenRepository   screenRepository;
    @Autowired private TheatreRepository  theatreRepository;

    private static final DateTimeFormatter DAY_FMT   = DateTimeFormatter.ofPattern("EEE");
    private static final DateTimeFormatter MONTH_FMT = DateTimeFormatter.ofPattern("MMM");

    /**
     * Builds a 14-day date navigation strip starting from today.
     */
    public List<DateEntry> getDateStrip() {
        LocalDate today = LocalDate.now();
        List<DateEntry> strip = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            LocalDate d = today.plusDays(i);
            strip.add(new DateEntry(
                    d.toString(),
                    d.format(DAY_FMT).toUpperCase(),
                    d.getDayOfMonth(),
                    d.format(MONTH_FMT).toUpperCase()
            ));
        }
        return strip;
    }

    /**
     * Returns the full schedule response for a given filter.
     */
    public ScheduleResponse getSchedules(ScheduleFilterRequest filter) {
        List<ShowtimeModel> showtimes = getFilteredShowtimes(filter);

        // group showtimes by movieId, preserving insertion order
        Map<Integer, List<ShowtimeModel>> byMovie = showtimes.stream()
                .collect(Collectors.groupingBy(ShowtimeModel::getMovieId, LinkedHashMap::new, Collectors.toList()));

        List<MovieSchedule> schedules = buildMovieSchedules(byMovie, filter);

        ScheduleResponse response = new ScheduleResponse();
        response.setDateStrip(getDateStrip());
        response.setSchedules(schedules);
        return response;
    }

    // ---- private helpers ----

    private List<ShowtimeModel> getFilteredShowtimes(ScheduleFilterRequest filter) {
        List<ShowtimeModel> showtimes = showtimeRepository
                .findByShowDate(LocalDate.parse(filter.getSelectedDate()));

        if (filter.getTimeFilter() != null && !filter.getTimeFilter().isEmpty()) {
            showtimes = showtimes.stream()
                    .filter(st -> matchesTimeFilter(st.getStartTime(), filter.getTimeFilter()))
                    .collect(Collectors.toList());
        }

        if (filter.getFormatFilter() != null &&
                !filter.getFormatFilter().isEmpty() &&
                !filter.getFormatFilter().equalsIgnoreCase("all")) {
            showtimes = showtimes.stream()
                    .filter(st -> filter.getFormatFilter().equalsIgnoreCase(st.getShowType()))
                    .collect(Collectors.toList());
        }

        return showtimes;
    }

    private List<MovieSchedule> buildMovieSchedules(
            Map<Integer, List<ShowtimeModel>> byMovie, ScheduleFilterRequest filter) {

        List<MovieSchedule> result = new ArrayList<>();

        for (Map.Entry<Integer, List<ShowtimeModel>> entry : byMovie.entrySet()) {
            MovieModel movie = movieRepository.findById(entry.getKey()).orElse(null);
            if (movie == null || "ARCHIVE".equals(movie.getMovieStatus())) continue;

            // language filter
            if (filter.getLangFilter() != null && !filter.getLangFilter().isEmpty() &&
                    !movie.getMovieLanguage().equalsIgnoreCase(filter.getLangFilter())) continue;

            // keyword search
            if (filter.getMovieSearch() != null && !filter.getMovieSearch().trim().isEmpty() &&
                    !movie.getMovieName().toLowerCase()
                            .contains(filter.getMovieSearch().trim().toLowerCase())) continue;

            List<HallSchedule> halls = buildHallSchedules(entry.getValue(), filter.getLocationFilter());
            if (halls.isEmpty()) continue;  // all halls filtered out by location — skip the movie

            MovieSchedule schedule = new MovieSchedule();
            schedule.setMovieId(movie.getMovieId());
            schedule.setMovieName(movie.getMovieName());
            schedule.setGenre(movie.getGenre());
            schedule.setMovieLanguage(movie.getMovieLanguage());
            schedule.setAgeRating(movie.getAgeRating());
            schedule.setDuration(movie.getDuration());
            schedule.setHalls(halls);
            result.add(schedule);
        }

        return result;
    }

    private List<HallSchedule> buildHallSchedules(
            List<ShowtimeModel> showtimes, String locationFilter) {

        // group by screenId
        Map<Integer, List<ShowtimeModel>> byScreen = showtimes.stream()
                .collect(Collectors.groupingBy(ShowtimeModel::getScreenId, LinkedHashMap::new, Collectors.toList()));

        List<HallSchedule> halls = new ArrayList<>();

        for (Map.Entry<Integer, List<ShowtimeModel>> entry : byScreen.entrySet()) {
            ScreenModel screen = screenRepository.findById(entry.getKey()).orElse(null);
            if (screen == null) continue;

            TheatreModel theatre = theatreRepository.findById(screen.getTheatreId()).orElse(null);
            if (theatre == null) continue;

            // location filter
            if (locationFilter != null && !locationFilter.isEmpty() &&
                    !locationFilter.equalsIgnoreCase("all") &&
                    !theatre.getCity().equalsIgnoreCase(locationFilter)) continue;

            List<ShowtimeSlot> slots = entry.getValue().stream().map(st -> {
                ShowtimeSlot slot = new ShowtimeSlot();
                slot.setShowtimeId(st.getShowtimeId());
                slot.setStartTime(st.getStartTime());
                slot.setEndTime(st.getEndTime());
                return slot;
            }).collect(Collectors.toList());

            HallSchedule hall = new HallSchedule();
            hall.setScreenId(screen.getScreenId());
            hall.setScreenName(screen.getScreenName());
            hall.setScreenType(screen.getScreenType());
            hall.setCity(theatre.getCity());
            hall.setShowtimes(slots);
            halls.add(hall);
        }

        return halls;
    }

    private boolean matchesTimeFilter(LocalTime time, String filter) {
        return switch (filter) {
            case "morning"   -> time.isBefore(LocalTime.NOON);
            case "afternoon" -> !time.isBefore(LocalTime.NOON) && time.isBefore(LocalTime.of(17, 0));
            case "evening"   -> !time.isBefore(LocalTime.of(17, 0)) && time.isBefore(LocalTime.of(21, 0));
            case "night"     -> !time.isBefore(LocalTime.of(21, 0));
            default          -> true;
        };
    }
}