import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import MovieCard from '../components/MovieCard'
import api from '../api'
import './css/register.css';
export default function Home() {
  const [movies, setMovies] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    api.get('/api/movies?status=NOW_SHOWING')
      .then(data => setMovies(Array.isArray(data) ? data.slice(0, 4) : []))
      .catch(() => setMovies([]))
      .finally(() => setLoading(false))
  }, [])

  return (
    <>
      <Header />

      <main>
        {/* Hero */}
        <section className="landing_hero">
          <div className="hero_content">
            <div className="hero_row">
              <div className="row"></div>
              <span className="row_text">Nepal's First and Finest IMAX Experience</span>
              <div className="row_bottom"></div>
            </div>
            <h1 className="hero_title">Cinema<br /><em>Reimagined</em></h1>
            <p className="hero_subtitle">
              An experience beyond cinema — Stories. Vision. Emotion. Impact.
            </p>
            <div className="hero_actions">
              <Link to="/schedules" className="hero_button">
                <span className="hero_button_text">Reserve Now</span>
              </Link>
            </div>
          </div>
        </section>

        {/* Location strip */}
        <section className="location_strip">
          <div className="location_row">
            <span className="row_label">Launching At Your</span>
            <div className="row_list">
              <div className="row_item_wrap"><span className="row_name">Kathmandu</span><span className="row_tag far">Open</span></div>
              <div className="row_item_wrap"><span className="row_name">Pokhara</span><span className="row_tag far">Open</span></div>
              <div className="row_item_wrap"><span className="row_name">Butwal</span><span className="row_tag soon">Opening Soon</span></div>
            </div>
            <span className="row_label">Nearest 3 Locations</span>
          </div>
        </section>

        {/* Now Showing */}
        <section className="movie_display_section">
          <div className="movie_decorative_accent_line line_top"></div>
          <div className="movie_main_content_container">
            <div className="movie_section_header_container centered_layout">
              <h2 className="section_display_title">On<em> Screen Now</em></h2>
            </div>
            <div className="movie_category_navigation_bar">
              <div className="movie_category_tab_switcher">
                <button className="movie_category_tab_button active_tab">Now Showing</button>
              </div>
              <Link to="/movies" className="movie_navigation_link_all">
                View All Films →
              </Link>
            </div>
            <div className="movie_cards_presentation_grid">
              {loading
                ? <p style={{ color: 'var(--text-muted)', padding: '2rem' }}>Loading…</p>
                : movies.map(m => <MovieCard key={m.movieId} movie={m} />)
              }
            </div>
          </div>
        </section>

        {/* Features strip */}
        <section className="features_strip">
          <div className="strip_content">
            <div className="strip_label_wrap">
              <div className="strip_line"></div>
              <span className="strip_label">Why CinoSphere?</span>
              <div className="strip_line"></div>
            </div>
            <h2 className="strip_title">Cinematic Experience <em>Beyond Extraordinary</em></h2>
          </div>
        </section>

        {/* Screen details */}
        <section className="screen_details">
          <div className="details_grid">
            <div className="details_info">
              <h2 className="details_title">Every Screen<em>Built for Stories.</em></h2>
              <p className="details_description">A geometry that dissolves cinematic reality. Built for Detail. Emotion. Impact.</p>
              <div className="details_stats">
                <div className="stat_item"><span className="stat_value">18K</span><span className="stat_label">sq. Ft. Screen</span></div>
                <div className="stat_item"><span className="stat_value">350+</span><span className="stat_label">Seats</span></div>
                <div className="stat_item"><span className="stat_value">44k</span><span className="stat_label">Audio</span></div>
              </div>
            </div>
            <div className="details_visual">
              <div className="visual_screen_img" style={{ background: '#1a1a2e', height: '320px', borderRadius: '8px' }}></div>
            </div>
          </div>
        </section>

        {/* Newsletter */}
        <section className="newsletter_subscription_section" id="s-newsletter">
          <div className="newsletter_accent_line line_top"></div>
          <div className="newsletter_content_container centered_layout">
            <div className="newsletter_column_registration">
              <h2 className="section_display_title">Behind the<em> Curtains</em></h2>
              <p className="newsletter_informative_text">
                Get exclusive early access to premieres, exclusive member offers, weekly film highlights and behind the scenes stories.
              </p>
              <div className="newsletter_interaction_form">
                <div className="newsletter_input_field_wrapper">
                  <input type="email" className="newsletter_input_element" placeholder="aditya@gmail.com" />
                </div>
                <button className="newsletter_submit_button">Subscribe</button>
              </div>
            </div>
          </div>
          <div className="newsletter_accent_line line_bottom"></div>
        </section>
      </main>

      <Footer />
    </>
  )
}