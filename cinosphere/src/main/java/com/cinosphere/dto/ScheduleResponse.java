package com.cinosphere.dto;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO returned by GET /api/schedules.
 * Replaces the raw Map<String,Object> that SchedulesService used to return
 * with a proper typed structure the client can work with reliably.
 *
 * dateStrip  — 14-day date navigation strip
 * schedules  — one entry per movie that has matching showtimes
 */
public class ScheduleResponse {

    private List<DateEntry> dateStrip;
    private List<MovieSchedule> schedules;

    // ---- Date strip entry ----
    public static class DateEntry {
        private String value;   // "2024-08-15"
        private String day;     // "WED"
        private int    number;  // 15
        private String month;   // "AUG"

        public DateEntry(String value, String day, int number, String month) {
            this.value  = value;
            this.day    = day;
            this.number = number;
            this.month  = month;
        }

        public String getValue()  { return value; }
        public String getDay()    { return day; }
        public int    getNumber() { return number; }
        public String getMonth()  { return month; }
    }

    // ---- One movie's schedule block ----
    public static class MovieSchedule {
        private int    movieId;
        private String movieName;
        private String genre;
        private String movieLanguage;
        private String ageRating;
        private int    duration;
        private List<HallSchedule> halls;

        public int    getMovieId()      { return movieId; }
        public String getMovieName()    { return movieName; }
        public String getGenre()        { return genre; }
        public String getMovieLanguage(){ return movieLanguage; }
        public String getAgeRating()    { return ageRating; }
        public int    getDuration()     { return duration; }
        public List<HallSchedule> getHalls() { return halls; }

        public void setMovieId(int v)           { this.movieId = v; }
        public void setMovieName(String v)      { this.movieName = v; }
        public void setGenre(String v)          { this.genre = v; }
        public void setMovieLanguage(String v)  { this.movieLanguage = v; }
        public void setAgeRating(String v)      { this.ageRating = v; }
        public void setDuration(int v)          { this.duration = v; }
        public void setHalls(List<HallSchedule> v) { this.halls = v; }
    }

    // ---- One screen/hall block inside a movie ----
    public static class HallSchedule {
        private int    screenId;
        private String screenName;
        private String screenType;   // 2D / 3D / IMAX
        private String city;
        private List<ShowtimeSlot> showtimes;

        public int    getScreenId()    { return screenId; }
        public String getScreenName()  { return screenName; }
        public String getScreenType()  { return screenType; }
        public String getCity()        { return city; }
        public List<ShowtimeSlot> getShowtimes() { return showtimes; }

        public void setScreenId(int v)           { this.screenId = v; }
        public void setScreenName(String v)      { this.screenName = v; }
        public void setScreenType(String v)      { this.screenType = v; }
        public void setCity(String v)            { this.city = v; }
        public void setShowtimes(List<ShowtimeSlot> v) { this.showtimes = v; }
    }

    // ---- Individual showtime slot ----
    public static class ShowtimeSlot {
        private int       showtimeId;
        private LocalTime startTime;
        private LocalTime endTime;

        public int       getShowtimeId() { return showtimeId; }
        public LocalTime getStartTime()  { return startTime; }
        public LocalTime getEndTime()    { return endTime; }

        public void setShowtimeId(int v)       { this.showtimeId = v; }
        public void setStartTime(LocalTime v)  { this.startTime = v; }
        public void setEndTime(LocalTime v)    { this.endTime = v; }
    }

    // ---- Root getters / setters ----
    public List<DateEntry> getDateStrip()              { return dateStrip; }
    public void setDateStrip(List<DateEntry> v)        { this.dateStrip = v; }

    public List<MovieSchedule> getSchedules()          { return schedules; }
    public void setSchedules(List<MovieSchedule> v)    { this.schedules = v; }
}