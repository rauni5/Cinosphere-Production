import { useState, useEffect } from 'react'
import { useSearchParams } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import HeroBanner from '../components/HeroBanner'
import MovieCard from '../components/MovieCard'
import ErrorBox from '../components/ErrorBox'
import api from '../api'
import './css/movies.css';
export default function Movies() {
  const [searchParams, setSearchParams] = useSearchParams()
  const [movies,  setMovies]  = useState([])
  const [loading, setLoading] = useState(true)
  const [error,   setError]   = useState('')

  // controlled filter state — mirrors JSP params
  const [keyword, setKeyword] = useState(searchParams.get('movieSearch') || '')
  const [lang,    setLang]    = useState(searchParams.get('langFilter')   || '')
  const [genre,   setGenre]   = useState(searchParams.get('genreFilter')  || '')
  const [status,  setStatus]  = useState(searchParams.get('status')       || 'all')

  useEffect(() => { fetchMovies() }, [searchParams])

  function fetchMovies() {
    setLoading(true); setError('')
    const q = new URLSearchParams()
    if (searchParams.get('movieSearch')) q.set('keyword',  searchParams.get('movieSearch'))
    if (searchParams.get('langFilter'))  q.set('language', searchParams.get('langFilter'))
    if (searchParams.get('genreFilter')) q.set('genre',    searchParams.get('genreFilter'))
    if (searchParams.get('status') && searchParams.get('status') !== 'all')
      q.set('status', searchParams.get('status'))

    api.get(`/api/movies?${q.toString()}`)
      .then(data => setMovies(Array.isArray(data) ? data : []))
      .catch(e  => setError(e.message))
      .finally(() => setLoading(false))
  }

  function applyFilters(e) {
    e.preventDefault()
    setSearchParams({ movieSearch: keyword, langFilter: lang, genreFilter: genre, status })
  }

  return (
    <>
      <Header />
      <main className="movie_screen">
        <HeroBanner
          titleMain="What's"
          titleEm="Playing"
          subtitle="Storytelling under spectacle, explore the latest blockbusters now showing."
        />

        {/* Filter bar */}
        <section className="movie_filter_section">
          <form onSubmit={applyFilters}>
            <div className="movie_filter_container">
              <div className="movie_search_wrapper">
                <span className="movie_search_icon_container">🔍</span>
                <input
                  type="text"
                  className="movie_search_input"
                  placeholder="Search movie names..."
                  value={keyword}
                  onChange={e => setKeyword(e.target.value)}
                />
              </div>

              <div className="movie_filter_dropdown_group">
                <div className="movie_select_wrapper">
                  <select className="movie_filter_select" value={lang} onChange={e => setLang(e.target.value)}>
                    <option value="">All Languages</option>
                    <option value="english">English</option>
                    <option value="hindi">Hindi</option>
                    <option value="nepali">Nepali</option>
                  </select>
                  <span className="movie_select_arrow">▾</span>
                </div>

                <div className="movie_select_wrapper">
                  <select className="movie_filter_select" value={genre} onChange={e => setGenre(e.target.value)}>
                    <option value="">All Genres</option>
                    <option value="action">Action</option>
                    <option value="drama">Drama</option>
                    <option value="comedy">Comedy</option>
                    <option value="sci-fi">Sci-Fi</option>
                    <option value="horror">Horror</option>
                    <option value="biography">Biography</option>
                  </select>
                  <span className="movie_select_arrow">▾</span>
                </div>

                <div className="movie_select_wrapper">
                  <select className="movie_filter_select" value={status} onChange={e => setStatus(e.target.value)}>
                    <option value="all">All</option>
                    <option value="NOW_SHOWING">Now Showing</option>
                    <option value="COMING_SOON">Coming Soon</option>
                  </select>
                  <span className="movie_select_arrow">▾</span>
                </div>

                <div className="movie_select_wrapper">
                  <button type="submit" className="movie_filter_pill">APPLY</button>
                </div>
              </div>
            </div>
          </form>
        </section>

        {/* Results */}
        <section className="movie_section">
          <div className="movie_main_content_container">
            <ErrorBox message={error} />
            {loading ? (
              <p style={{ color: 'var(--text-muted)', padding: '2rem' }}>Loading…</p>
            ) : movies.length === 0 ? (
              <p style={{ color: 'var(--crimson-bright)', padding: '2rem' }}>
                No movies match your current filters.
              </p>
            ) : (
              <div className="movie_cards_presentation_grid">
                {movies.map(m => <MovieCard key={m.movieId} movie={m} />)}
              </div>
            )}
          </div>
        </section>
      </main>
      <Footer />
    </>
  )
}