import { useState, useEffect } from 'react'
import { useParams, Link } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import MovieCard from '../components/MovieCard'
import api from '../api'
import './css/movieDetail.css';
export default function MovieDetail() {
  const { id } = useParams()
  const [movie,   setMovie]   = useState(null)
  const [related, setRelated] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    setLoading(true)
    Promise.all([
      api.get(`/api/movies/${id}`),
      api.get('/api/movies?status=NOW_SHOWING'),
    ])
      .then(([m, all]) => {
        setMovie(m)
        setRelated((Array.isArray(all) ? all : []).filter(x => x.movieId !== Number(id)).slice(0, 4))
      })
      .catch(() => {})
      .finally(() => setLoading(false))
  }, [id])

  if (loading) return <><Header /><p style={{ padding: '4rem', color: 'var(--text-muted)' }}>Loading…</p><Footer /></>
  if (!movie)  return <><Header /><p style={{ padding: '4rem', color: 'var(--crimson-bright)' }}>Movie not found.</p><Footer /></>

  return (
    <>
      <Header />
      <main className="movieDetail_screen">

        {/* Hero */}
        <div className="detail_hero">
          <div className="detail_hero_background">
            <img
              src={`/api/uploads/movies/background/${movie.movieId}`}
              alt={`${movie.movieName} background`}
              className="detail_hero_background_poster"
            />
          </div>
          <div className="detail_hero_content">
            <div className="detail_poster">
              <img
                src={`/api/uploads/movies/${movie.movieId}`}
                alt={`${movie.movieName} poster`}
                className="detail_poster_image"
              />
              <span className="detail_certification">{movie.ageRating}</span>
            </div>
            <div className="detail_info">
              <div className="detail_description_row">
                <span className="detail_pill">{movie.genre}</span>
                <span className="detail_pill">{movie.movieLanguage}</span>
                <span className="detail_pill">{movie.duration} min</span>
              </div>
              <h1 className="detail_title">{movie.movieName}</h1>
            </div>
          </div>
        </div>

        {/* Body */}
        <div className="detail_body">
          <div className="detail_about_section">
            <h2 className="detail_section_title">About this <em>Movie</em></h2>
            <p className="detail_description">{movie.description}</p>
            <div className="detail_meta_section">
              <div className="detail_meta_grid">
                <div className="detail_meta_item">
                  <div className="detail_meta_label">Director</div>
                  <div className="detail_meta_value">{movie.director}</div>
                </div>
                <div className="detail_meta_item">
                  <div className="detail_meta_label">Release Date</div>
                  <div className="detail_meta_value">{movie.releaseDate}</div>
                </div>
              </div>
            </div>

            <Link to={`/schedules?movieSearch=${encodeURIComponent(movie.movieName)}`} className="primary_gold_button" style={{ display:'inline-block', marginTop:'1.5rem' }}>
              Book Tickets
            </Link>
          </div>
        </div>

        {/* Related */}
        {related.length > 0 && (
          <section className="detail_related_section">
            <div className="detail_related_header">
              <div className="detail_related_header_left">
                <span className="detail_section_eyebrow">You May Also Like</span>
                <h2 className="detail_section_title">More <em>Films</em></h2>
              </div>
              <Link to="/movies" className="detail_view_all_link">
                <span className="detail_view_all_text">View All</span>
              </Link>
            </div>
            <div className="detail_related_grid">
              {related.map(m => <MovieCard key={m.movieId} movie={m} />)}
            </div>
          </section>
        )}
      </main>
      <Footer />
    </>
  )
}