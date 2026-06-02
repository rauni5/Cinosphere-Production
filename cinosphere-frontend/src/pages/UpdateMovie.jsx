import { useState, useEffect } from 'react'
import { Link, useParams, useNavigate } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import ErrorBox from '../components/ErrorBox'
import api from '../api'
import './css/updateMovie.css';
const INITIAL_FORM = {
  movieName: '', duration: '', director: '', genre: '',
  movieLanguage: '', description: '', releaseDate: '',
  movieStatus: '', ageRating: '',
}

export default function UpdateMovie({ mode }) {
  const { id }    = useParams()    // only present in edit mode
  const navigate  = useNavigate()

  const [form,       setForm]       = useState(INITIAL_FORM)
  const [poster,     setPoster]     = useState(null)
  const [bgPoster,   setBgPoster]   = useState(null)
  const [posterPrev, setPosterPrev] = useState(null)
  const [bgPrev,     setBgPrev]     = useState(null)
  const [screens,    setScreens]    = useState([])
  const [theatres,   setTheatres]   = useState([])
  const [showtimes,  setShowtimes]  = useState([{ screenId: '', showDate: '', startTime: '' }])
  const [error,      setError]      = useState('')
  const [busy,       setBusy]       = useState(false)
  const [loading,    setLoading]    = useState(true)

  const isEdit = mode === 'update'

  useEffect(() => {
    const requests = [
      api.get('/api/screens'),
      api.get('/api/theatres'),
    ]
    if (isEdit && id) requests.push(
      api.get(`/api/movies/${id}`),
      api.get(`/api/showtimes?movieId=${id}`)
    )

    Promise.all(requests).then(([sc, th, movie,show]) => {
      setScreens(sc)
      setTheatres(th)
      setShowtimes(show)
      if (movie) {
        setForm({
          movieName:     movie.movieName     ?? '',
          duration:      movie.duration      ?? '',
          director:      movie.director      ?? '',
          genre:         movie.genre         ?? '',
          movieLanguage: movie.movieLanguage ?? '',
          description:   movie.description   ?? '',
          releaseDate:   movie.releaseDate   ?? '',
          movieStatus:   movie.movieStatus   ?? '',
          ageRating:     movie.ageRating     ?? '',
        })
        setPosterPrev(`x`)
        setBgPrev(`x`)
      }
    }).catch(() => setError('Failed to load data'))
      .finally(() => setLoading(false))
  }, [id, isEdit])

  function onField(e) {
    setForm(f => ({ ...f, [e.target.name]: e.target.value }))
  }

  function onPoster(e) {
    const file = e.target.files[0]
    if (file) { setPoster(file); setPosterPrev(URL.createObjectURL(file)) }
  }

  function onBg(e) {
    const file = e.target.files[0]
    if (file) { setBgPoster(file); setBgPrev(URL.createObjectURL(file)) }
  }

  function addShowtimeRow() {
    setShowtimes(prev => [...prev, { screenId: '', showDate: '', startTime: '' }])
  }

  function removeShowtimeRow(idx) {
    setShowtimes(prev => prev.filter((_, i) => i !== idx))
  }

  function onShowtime(idx, field, val) {
    setShowtimes(prev => prev.map((r, i) => i === idx ? { ...r, [field]: val } : r))
  }

  async function onSubmit(e) {
    e.preventDefault()
    setError('')
    setBusy(true)
    try {
      const fd = new FormData()
      Object.entries(form).forEach(([k, v]) => fd.append(k, v))
      if (poster)   fd.append('poster',           poster)
      if (bgPoster) fd.append('backgroundPoster', bgPoster)

      // Only send non-empty showtimes
      const validSt = showtimes.filter(s => s.screenId && s.showDate && s.startTime)
      fd.append('showtimes', JSON.stringify(validSt))

      if (isEdit) {
        await api.putForm(`/api/movies/${id}`, fd)
      } else {
        await api.postForm('/api/movies', fd)
      }
      navigate('/admin')
    } catch (err) {
      setError(err.message)
    } finally {
      setBusy(false)
    }
  }

  if (loading) return <><Header /><p style={{ padding: '4rem', color: 'var(--text-muted)' }}>Loading…</p><Footer /></>

  return (
    <>
      <Header />
      <div className="admin_layout_container">

        {/* Sidebar */}
        <aside className="admin_sidebar_panel">
          <div className="admin_sidebar_inner">
            <nav className="admin_navigation_menu">
              <div className="admin_menu_group">
                <span className="admin_navigation_label">Main</span>
                <Link to="/admin" className="admin_navigation_item">
                  <div className="admin_navigation_icon_box">📊</div>
                  <span className="admin_navigation_text">Dashboard</span>
                </Link>
              </div>
              <div className="admin_menu_group">
                <span className="admin_navigation_label">Actions</span>
                <Link to="/admin/movies/add" className="admin_navigation_item active">
                  <div className="admin_navigation_icon_box">🎬</div>
                  <span className="admin_navigation_text">Movies</span>
                </Link>
              </div>
            </nav>
          </div>
        </aside>

        <main className="admin_dashboard_panel">

          {/* Hero */}
          <section className="admin_dashboard_hero_section">
            <div className="admin_dashboard_hero_background"></div>
            <div className="admin_dashboard_hero_content">
              <div className="admin_dashboard_hero_layout">
                <div className="admin_dashboard_heading_block">
                  <h1 className="admin_dashboard_heading_title">
                    {isEdit ? 'Modify' : 'Add'} <em>Movie</em>
                  </h1>
                  <p className="update_movie_heading_subtitle">
                    {isEdit ? 'Modify active listings, adjust release metadata, status and artwork.' : 'Add a new film to the CinoSphere catalogue.'}
                  </p>
                </div>
              </div>
            </div>
          </section>

          <ErrorBox message={error} />

          <div className="admin_movie_content_wrapper">
            <form onSubmit={onSubmit} className="admin_workspace_form" encType="multipart/form-data">

              {/* ── Film Configuration ───────────── */}
              <div className="admin_workspace_card">
                <div className="admin_card_header">
                  <span className="admin_card_eyebrow">Manage Movies</span>
                  <h2 className="admin_card_heading">Film <em>Configuration</em></h2>
                </div>

                <div className="admin_form_layout_grid">

                  {/* Poster uploads */}
                  <div className="admin_avatar_upload_pane">
                    <label className="admin_field_label_av">Movie Poster</label>
                    <div className="admin_avatar_poster_wrapper">
                      <label htmlFor="poster_input" className="admin_avatar_circle_frame">
                        {posterPrev && <img src={posterPrev} alt="poster preview" />}
                        <div className="admin_avatar_hover_overlay"><span>Select File</span></div>
                      </label>
                      <input type="file" id="poster_input" accept="image/*" className="admin_hidden_file_input" onChange={onPoster} />
                    </div>

                    <label className="admin_field_label_av" style={{ paddingLeft: '2rem' }}>Movie Background</label>
                    <div className="admin_background_upload_wrapper">
                      <label htmlFor="bg_input" className="admin_background_frame">
                        {bgPrev && <img src={bgPrev} alt="bg preview" className="admin_background_preview" />}
                        <div className="admin_background_overlay"><span>Select Background</span></div>
                      </label>
                      <input type="file" id="bg_input" accept="image/*" className="admin_hidden_file_input" onChange={onBg} />
                    </div>
                  </div>

                  {/* Text fields */}
                  <div className="admin_fields_entry_pane">
                    <div className="form_single_row">
                      <div className="admin_field_group">
                        <label className="admin_field_label">Movie Title *</label>
                        <input type="text" name="movieName" className="admin_form_input"
                          placeholder="e.g. Raja Shivaji" value={form.movieName} onChange={onField} required />
                      </div>
                    </div>

                    <div className="form_grid_3">
                      <div className="admin_field_group">
                        <label className="admin_field_label">Language *</label>
                        <select name="movieLanguage" className="admin_form_select" value={form.movieLanguage} onChange={onField} required>
                          <option value="">Select</option>
                          {['Hindi', 'English', 'Nepali'].map(l => <option key={l}>{l}</option>)}
                        </select>
                      </div>
                      <div className="admin_field_group">
                        <label className="admin_field_label">Genre *</label>
                        <select name="genre" className="admin_form_select" value={form.genre} onChange={onField} required>
                          <option value="">Select Genre</option>
                          {['Action', 'Drama', 'Comedy', 'Sci-Fi', 'Thriller', 'Romance', 'Horror'].map(g => <option key={g}>{g}</option>)}
                        </select>
                      </div>
                      <div className="admin_field_group">
                        <label className="admin_field_label">Duration (min) *</label>
                        <input type="number" name="duration" className="admin_form_input"
                          placeholder="148" min="1" value={form.duration} onChange={onField} required />
                      </div>
                    </div>

                    <div className="form_grid_2">
                      <div className="admin_field_group">
                        <label className="admin_field_label">Certificate *</label>
                        <select name="ageRating" className="admin_form_select" value={form.ageRating} onChange={onField} required>
                          <option value="">Select</option>
                          <option>PG</option><option>A</option>
                        </select>
                      </div>
                      <div className="admin_field_group">
                        <label className="admin_field_label">Status *</label>
                        <select name="movieStatus" className="admin_form_select" value={form.movieStatus} onChange={onField} required>
                          <option value="">Select</option>
                          <option value="NOW_SHOWING">Showing</option>
                          <option value="COMING_SOON">Upcoming</option>
                          <option value="ARCHIVE">Archived</option>
                        </select>
                      </div>
                    </div>

                    <div className="form_grid_2 form_row_spacer">
                      <div className="admin_fields_stacked_column">
                        <div className="admin_field_group">
                          <label className="admin_field_label">Director *</label>
                          <input type="text" name="director" className="admin_form_input"
                            placeholder="Director name" value={form.director} onChange={onField} required />
                        </div>
                        <div className="admin_field_group">
                          <label className="admin_field_label">Release Date *</label>
                          <input type="date" name="releaseDate" className="admin_form_input"
                            value={form.releaseDate} onChange={onField} required />
                        </div>
                      </div>
                      <div className="admin_field_group structural_textarea_fill">
                        <label className="admin_field_label">Description *</label>
                        <textarea name="description" className="admin_form_textarea execution_fill"
                          placeholder="Write a 2–3 sentence synopsis…" value={form.description}
                          onChange={onField} required />
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              {/* ── Showtimes ────────────────────────── */}
              <div className="admin_workspace_card">
                <div className="admin_card_header">
                  <span className="admin_card_eyebrow">Timings &amp; Venues</span>
                  <h2 className="admin_card_heading">Showtimes &amp; <em>Schedules</em></h2>
                </div>

                <div className="admin_schedule_matrix_block">
                  <div className="schedule_header_grid">
                    <span className="schedule_grid_header_title">Hall</span>
                    <span className="schedule_grid_header_title">Date</span>
                    <span className="schedule_grid_header_title">Time</span>
                    <span></span>
                  </div>

                  <div id="scheduleContainer">
                    {showtimes.map((row, idx) => (
                      <div key={idx} className="schedule_add_row">
                        <select className="admin_form_select"
                          value={row.screenId} onChange={e => onShowtime(idx, 'screenId', e.target.value)}>
                          <option value="">Select Hall</option>
                          {screens.map((sc, si) => {
                            const th = theatres[si]
                            return <option key={sc.screenId} value={sc.screenId}>{sc.screenName} ~ {th?.city}</option>
                          })}
                        </select>
                        <input type="date" className="admin_form_input"
                          value={row.showDate} onChange={e => onShowtime(idx, 'showDate', e.target.value)} />
                        <input type="time" className="admin_form_input"
                          value={row.startTime} onChange={e => onShowtime(idx, 'startTime', e.target.value)} />
                        <button type="button" className="remove_button" onClick={() => removeShowtimeRow(idx)}>🗑️</button>
                      </div>
                    ))}
                    <button type="button" className="add_button" onClick={addShowtimeRow}>＋</button>
                  </div>
                </div>

                <div className="admin_workspace_form_footer">
                  <Link to="/admin"><button type="button" className="button_model_secondary">Cancel</button></Link>
                  <button type="submit" className="button_model_primary" disabled={busy}>
                    {busy ? 'Saving…' : isEdit ? 'Save Modifications' : 'Add Movie'}
                  </button>
                </div>
              </div>

            </form>
          </div>
        </main>
      </div>
      <Footer />
    </>
  )
}