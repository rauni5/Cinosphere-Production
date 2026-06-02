import { useNavigate } from 'react-router-dom'
import './css/scheduleCard.css';

export default function ScheduleCard({ movie, halls, date }) {
  const navigate = useNavigate()

  function goToBooking(showtimeId, screenId) {
    navigate(`/booking?movieId=${movie.movieId}&showtimeId=${showtimeId}&screenId=${screenId}&date=${date}`)
  }

  return (
    <div className="schedules_row">
      
      <div className="schedules_row_movie">
        
        <div className="schedules_row_poster">
          <img
            src={`/uploads/movies/poster_${movie.movieId}.jpg`}
            alt={`${movie.movieName} poster`}
            className="movie_poster_image_element"
          />
        </div>

        <div className="schedules_row__information">
          
          <div className="schedules_row_title">
            {movie.movieName}
          </div>

          <div className="description_group">
            <div className="schedules_row_description">
              {movie.movieLanguage} · {movie.genre}
            </div>
          </div>

          <div className="schedule_row_badges">
            <span className="age_rating_badge">
              {movie.ageRating}
            </span>
            <span className="format_badge">
              {movie.duration} min
            </span>
          </div>

        </div>
      </div>

      <div className="schedules_row_times">
        {halls.map(hall => (
          <div key={hall.screenId} className="time_hall_block">

            <div className="time_hall_label">
              {hall.city} — {hall.screenName} — {hall.screenType}
            </div>

            <div className="times_slots">
              {hall.showtimes.map(st => (
                <button
                  key={st.showtimeId}
                  className="time_slot"
                  onClick={() => goToBooking(st.showtimeId, hall.screenId)}
                >
                  <div className="time_slot_times">
                    {st.startTime ? st.startTime.slice(0, 5) : ''}
                  </div>
                </button>
              ))}
            </div>

          </div>
        ))}
      </div>

    </div>
  )
}