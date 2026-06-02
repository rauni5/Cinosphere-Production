import { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import ErrorBox from '../components/ErrorBox'
import { useAuth } from '../context/AuthContext'
import api from '../api'
import './css/AdminPanel.css';
const MOVIE_TABS = ['all', 'NOW_SHOWING', 'COMING_SOON', 'ARCHIVE']
const USER_TABS  = ['all', 'active', 'inactive']

export default function AdminPanel() {
  const { user }     = useAuth()
  const navigate     = useNavigate()

  const [stats,      setStats]      = useState(null)
  const [movies,     setMovies]     = useState([])
  const [users,      setUsers]      = useState([])
  const [bookings,   setBookings]   = useState([])
  const [movieTab,   setMovieTab]   = useState('all')
  const [userTab,    setUserTab]    = useState('all')
  const [movieQ,     setMovieQ]     = useState('')
  const [userQ,      setUserQ]      = useState('')
  const [error,      setError]      = useState('')
  const [loading,    setLoading]    = useState(true)

  const today = new Date().toLocaleDateString('en-US', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })

  useEffect(() => {
    Promise.all([
      api.get('/api/admin/stats'),
      api.get('/api/movies'),
      api.get('/api/admin/users'),
      api.get('/api/admin/bookings'),
    ]).then(([s, m, u, b]) => {
      setStats(s); setMovies(m); setUsers(u); setBookings(b)
    }).catch(() => setError('Failed to load admin data'))
      .finally(() => setLoading(false))
  }, [])

  async function toggleUser(userId, isActive) {
    try {
      await api.put(`/api/admin/users/${userId}/toggle`, { active: !isActive })
      setUsers(prev => prev.map(u => u.userId === userId ? { ...u, active: !isActive } : u))
    } catch (err) {
      setError(err.message)
    }
  }

  async function archiveMovie(movieId) {
    if (!window.confirm('Archive this movie?')) return
    try {
      await api.delete(`/api/movies/${movieId}`)
      setMovies(prev => prev.map(m => m.movieId === movieId ? { ...m, movieStatus: 'ARCHIVE' } : m))
    } catch (err) {
      setError(err.message)
    }
  }

  const filteredMovies = movies.filter(m => {
    const statusOk = movieTab === 'all' || m.movieStatus === movieTab
    const searchOk = m.movieName.toLowerCase().includes(movieQ.toLowerCase())
    return statusOk && searchOk
  })

  const filteredUsers = users.filter(u => {
    const statusOk = userTab === 'all' || (userTab === 'active' ? u.active : !u.active)
    const searchOk = u.username.toLowerCase().includes(userQ.toLowerCase())
    return statusOk && searchOk
  })

  const statusPill = s => {
    if (s === 'NOW_SHOWING') return 'status_confirmed'
    if (s === 'COMING_SOON') return 'status_upcoming'
    return 'status_archived'
  }

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
                <a href="#dashboard" className="admin_navigation_item active">
                  <div className="admin_navigation_icon_box">📊</div>
                  <span className="admin_navigation_text">Dashboard</span>
                </a>
              </div>
              <div className="admin_menu_group">
                <span className="admin_navigation_label">Actions</span>
                <Link to="/admin/movies/add" className="admin_navigation_item">
                  <div className="admin_navigation_icon_box">🎬</div>
                  <span className="admin_navigation_text">Movies</span>
                </Link>
              </div>
            </nav>
            <div className="admin_sidebar_footer">
              <div className="admin_profile_card">
                <div className="admin_profile_avatar">
                  <img src={`/api/uploads/profiles/${user?.userId}`} alt="avatar"
                    onError={e => e.target.style.display = 'none'} />
                </div>
                <div className="admin_profile_details">
                  <span className="admin_profile_name">{user?.firstName} {user?.lastName}</span>
                  <span className="admin_profile_role">{user?.userRole}</span>
                </div>
              </div>
            </div>
          </div>
        </aside>

        <main className="admin_dashboard_panel" id="dashboard">

          {/* Hero */}
          <section className="admin_dashboard_hero_section">
            <div className="admin_dashboard_hero_background"></div>
            <div className="admin_dashboard_hero_content">
              <div className="admin_dashboard_hero_layout">
                <div className="admin_dashboard_heading_block">
                  <h1 className="admin_dashboard_heading_title">Welcome, <em>{user?.firstName}</em></h1>
                  <p className="admin_dashboard_heading_subtitle">{today}</p>
                </div>
                <div className="admin_dashboard_action_group">
                  <Link to="/admin/movies/add">
                    <button className="admin_dashboard_primary_button">+ Add Movie</button>
                  </Link>
                </div>
              </div>
            </div>
          </section>

          <ErrorBox message={error} />

          {/* Metrics */}
          {!loading && stats && (
            <section className="admin_metrics_section">
              <div className="admin_metrics_grid">
                <div className="admin_metric_card">
                  <span className="admin_metric_value admin_metric_gold">Rs {stats.revenueToday ?? 0}</span>
                  <span className="admin_metric_title">Revenue Today</span>
                  <span className="admin_metric_change">{stats.revenueChange ?? '—'} vs yesterday</span>
                </div>
                <div className="admin_metric_card">
                  <span className="admin_metric_value admin_metric_gold">{stats.ticketsSoldToday ?? 0}</span>
                  <span className="admin_metric_title">Tickets Sold Today</span>
                  <span className="admin_metric_change">{stats.ticketsChange ?? '—'} vs yesterday</span>
                </div>
                <div className="admin_metric_card">
                  <span className="admin_metric_value admin_metric_gold">{stats.newMembersToday ?? 0}</span>
                  <span className="admin_metric_title">New Members Today</span>
                  <span className="admin_metric_change">{stats.usersChange ?? '—'} vs yesterday</span>
                </div>
                <div className="admin_metric_card">
                  <span className="admin_metric_value admin_metric_gold">{stats.totalBookings ?? 0}</span>
                  <span className="admin_metric_title">Total Bookings</span>
                  <span className="admin_metric_change">{stats.ticketsSoldToday ?? 0} new today</span>
                </div>
              </div>
            </section>
          )}

          {/* ── Movie Management ─────────────────────── */}
          <section className="admin_movie_management_section">
            <div className="admin_panel_card">
              <div className="admin_panel_header">
                <h3 className="admin_panel_title">Movie Management</h3>
                <div className="admin_panel_actions">
                  <div className="admin_search_wrapper">
                    <span className="admin_search_icon">🔍</span>
                    <input type="text" className="admin_search_input" placeholder="Search movies…"
                      value={movieQ} onChange={e => setMovieQ(e.target.value)} />
                  </div>
                </div>
              </div>

              <div className="admin_movie_tabs">
                {MOVIE_TABS.map(tab => (
                  <button key={tab} className={`admin_movie_tab ${movieTab === tab ? 'active' : ''}`}
                    onClick={() => setMovieTab(tab)}>
                    {tab === 'all' ? 'All' : tab === 'NOW_SHOWING' ? 'Showing' : tab === 'COMING_SOON' ? 'Upcoming' : 'Archived'}
                  </button>
                ))}
              </div>

              <div className="admin_movie_table_wrapper">
                <table className="admin_movie_table">
                  <thead>
                    <tr><th>Film</th><th>Release Date</th><th>Duration</th><th>Age Rating</th><th>Status</th><th>Actions</th></tr>
                  </thead>
                  <tbody>
                    {filteredMovies.length === 0 ? (
                      <tr><td colSpan={6} style={{ padding: '1rem', color: 'var(--text-muted)' }}>No movies found</td></tr>
                    ) : filteredMovies.map(m => (
                      <tr key={m.movieId}>
                        <td>
                          <div className="admin_movie_info">
                            <div className="admin_movie_poster">
                              <img src={`api/uploads/movies/${m.movieId}`} alt={m.movieName} className="admin_movie_poster_image" />
                            </div>
                            <span className="admin_movie_name">{m.movieName}</span>
                          </div>
                        </td>
                        <td>{m.releaseDate}</td>
                        <td>{m.duration} min</td>
                        <td>{m.ageRating}</td>
                        <td><span className={`status_pill ${statusPill(m.movieStatus)}`}>{m.movieStatus}</span></td>
                        <td>
                          <div className="admin_movie_action_group">
                            <button className="admin_action_button edit" onClick={() => navigate(`/admin/movies/${m.movieId}`)}>✏️</button>
                            <button className="admin_action_button delete" onClick={() => archiveMovie(m.movieId)}>🗑️</button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </section>

          {/* ── User Management ──────────────────────── */}
          <section id="user_management" className="admin_user_management_section">
            <div className="admin_panel_card">
              <div className="admin_panel_header">
                <h3 className="admin_panel_title">User Management</h3>
                <div className="admin_panel_actions">
                  <div className="admin_search_wrapper">
                    <span className="admin_search_icon">🔍</span>
                    <input type="text" className="admin_search_input" placeholder="Search users…"
                      value={userQ} onChange={e => setUserQ(e.target.value)} />
                  </div>
                </div>
              </div>

              <div className="admin_movie_tabs">
                {USER_TABS.map(tab => (
                  <button key={tab} className={`admin_movie_tab ${userTab === tab ? 'active' : ''}`}
                    onClick={() => setUserTab(tab)}>
                    {tab === 'all' ? 'All Users' : tab === 'active' ? 'Active' : 'Inactive'}
                  </button>
                ))}
              </div>

              <div className="admin_movie_table_wrapper">
                <table className="admin_movie_table">
                  <thead>
                    <tr><th>Name</th><th>Email</th><th>Role</th><th>Tier</th><th>Bookings</th><th>Points</th><th>Status</th><th>Toggle</th></tr>
                  </thead>
                  <tbody>
                    {filteredUsers.length === 0 ? (
                      <tr><td colSpan={8} style={{ padding: '1rem', color: 'var(--text-muted)' }}>No users found</td></tr>
                    ) : filteredUsers.map(u => (
                      <tr key={u.userId}>
                        <td>
                          <div className="admin_user_identity">
                            <div className="admin_user_avatar">
                              <img src={`/api/uploads/profiles/${u.userId}`} alt={u.username}
                                onError={e => e.target.style.display = 'none'} />
                            </div>
                            <span className="admin_user_name">{u.username}</span>
                          </div>
                        </td>
                        <td className="admin_email_cell">{u.email}</td>
                        <td className="admin_user_cell">{u.userRole}</td>
                        <td><span className="admin_tier_badge elite">{u.membershipType}</span></td>
                        <td className="admin_booking_cell">{u.totalBookings ?? 0}</td>
                        <td className="admin_points_cell">{u.totalLoyaltyPoints ?? 0}</td>
                        <td><span className={`status_pill ${u.active ? 'status_confirmed' : 'status_archived'}`}>{u.active ? 'ACTIVE' : 'INACTIVE'}</span></td>
                        <td>
                          <label className="admin_status_toggle">
                            <input type="checkbox" checked={u.active} onChange={() => toggleUser(u.userId, u.active)} />
                            <span className="admin_toggle_slider"></span>
                          </label>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </section>

          {/* ── Booking Management ───────────────────── */}
          <section id="booking_management" className="admin_user_management_section">
            <div className="admin_panel_card">
              <div className="admin_panel_header">
                <h3 className="admin_panel_title">Booking Management</h3>
              </div>
              <div className="admin_movie_table_wrapper">
                <table className="admin_movie_table">
                  <thead>
                    <tr><th>ID</th><th>Movie</th><th>User</th><th>Hall</th><th>Date</th><th>Time</th><th>Total</th><th>Points</th><th>Status</th></tr>
                  </thead>
                  <tbody>
                    {bookings.length === 0 ? (
                      <tr><td colSpan={9} style={{ padding: '1rem', color: 'var(--text-muted)' }}>No bookings found</td></tr>
                    ) : bookings.map(b => (
                      <tr key={b.bookingId}>
                        <td>{b.bookingId}</td>
                        <td>{b.movieName}</td>
                        <td>{b.username}</td>
                        <td>{b.screenName}</td>
                        <td>{b.showDate}</td>
                        <td>{b.startTime?.slice(0, 5)}</td>
                        <td>Rs {b.totalAmount}</td>
                        <td className="admin_points_cell">{b.loyaltyPointsEarned}</td>
                        <td><span className={`status_pill ${b.bookingStatus === 'CONFIRMED' ? 'status_confirmed' : 'status_archived'}`}>{b.bookingStatus}</span></td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </section>

        </main>
      </div>
      <Footer />
    </>
  )
}