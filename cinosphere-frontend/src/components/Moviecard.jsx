import { useNavigate } from 'react-router-dom'
import './css/movieCard.css'

export default function MovieCard({ movie }) {
  const navigate = useNavigate()

  const {
    movieId,
    movieName,
    movieLanguage,
    genre,
    duration,
    ageRating,
    movieStatus,
    releaseDate
  } = movie

  const isComingSoon = movieStatus === 'COMING_SOON'

  return (
    <div className={`movie_feature_film_card ${isComingSoon ? 'coming_soon_card' : ''}`}>

      {isComingSoon && (
        <div className="movie_release_header">
          <span className="release_date_text">
            {releaseDate}
          </span>
        </div>
      )}

      <div className="movie_poster_visual_wrapper">
        <div className="movie_status_badge_group">
          <span className="movie_certification_badge">
            {ageRating}
          </span>
        </div>

        <img
          src={`/api/uploads/movies/${movieId}`}
          alt={`${movieName} poster`}
          className="movie_poster_image_element"
          onError={(e) => {
            e.target.style.display = 'none'
          }}
        />

        <div className="movie_poster_gradient_overlay"></div>
      </div>

      <div className="movie_information_panel">

        <h3 className="movie_title">
          {movieName}
        </h3>

        <p className="movie_description">
          {movieLanguage} | {genre}
          <span>{duration} min</span>
        </p>

        <div className="movie_action_button_bar">

          {movieStatus === 'NOW_SHOWING' ? (
            <button
              className="movie_booking_primary_button"
              onClick={(e) => {
                e.stopPropagation()
                navigate('/schedules')
              }}
            >
              Book Now
            </button>
          ) : (
            <button
              className="movie_booking_primary_button"
              onClick={(e) => e.stopPropagation()}
            >
              Notify Me
            </button>
          )}

          <div
            className="movie_quick_view_icon_wrapper"
            onClick={(e) => {
              e.stopPropagation()
              navigate(`/movies/${movieId}`)
            }}
          >
            <img
              src="/icon?name=info"
              alt="Info"
            />
          </div>

        </div>
      </div>
    </div>
  )
}