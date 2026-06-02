import { useState, useEffect } from 'react'
import { Link, useSearchParams } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import { useAuth } from '../context/AuthContext'
import api from '../api'
import './css/userPanel.css';
export default function UserPanel() {
  const { user }      = useAuth()
  const [params]      = useSearchParams()
  const justBooked    = params.get('confirmed')

  const [stats,      setStats]      = useState(null)
  const [bookings,   setBookings]   = useState([])
  const [membership, setMembership] = useState(null)
  const [loading,    setLoading]    = useState(true)

  useEffect(() => {
    Promise.all([
      api.get('/api/users/me/stats'),
      api.get('/api/bookings/my?limit=5'),
      api.get('/api/memberships/me'),
    ]).then(([s, b, m]) => {
      setStats(s)
      setBookings(b)
      setMembership(m)
    }).catch(() => {})
      .finally(() => setLoading(false))
  }, [])

  const today = new Date().toLocaleDateString('en-US', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })
  const progress = membership ? Math.min(100, Math.round((membership.totalLoyaltyPoints / 3000) * 100)) : 0

  return (
    <>
      <Header />
      <div className="panel_layout_wrapper">

        {/* Sidebar */}
        <aside className="dashboard_sidebar">
          <div className="sidebar_nav_container">
            <nav className="sidebar_nav_menu">
              <span className="sidebar_section_label">Main</span>
              <Link to="/profile" className="sidebar_nav_item active">
                <div className="sidebar_icon_box">🏠</div>
                <span className="sidebar_nav_label">Dashboard</span>
              </Link>
              <span className="sidebar_section_label">Account</span>
              <Link to="/profile/edit" className="sidebar_nav_item">
                <div className="sidebar_icon_box">👤</div>
                <span className="sidebar_nav_label">Profile Settings</span>
              </Link>
            </nav>
            <div className="sidebar_footer">
              <div className="footer_divider"></div>
              <div className="sidebar_user_profile">
                <div className="user_avatar_circle">
                  <img src={`x`} alt={user?.firstName}
                    style={{ width:'100%', height:'100%', objectFit:'cover', display:'block' }}
                    onError={e => e.target.style.display = 'none'} />
                </div>
                <div className="user_info_stack">
                  <span className="user_name">{user?.firstName}</span>
                  <span className="membership_tier">{membership?.membershipType}</span>
                </div>
              </div>
            </div>
          </div>
        </aside>

        {/* Main dashboard */}
        <main className="dashboard_main_panel">

          {justBooked && (
            <div className="success_box" style={{ margin: '1rem 2rem' }}>
              🎬 Booking #{justBooked} confirmed! Enjoy the show.
            </div>
          )}

          <section className="hero_overlay_section">
            <div className="hero_radial_bg"></div>
            <div className="hero_content_inner">
              <div className="hero_flex_layout">
                <div className="greeting_text_block">
                  <h2 className="greeting_primary">Welcome back, <em>{user?.firstName}</em></h2>
                  <p className="greeting_secondary">{today}</p>
                </div>
                <div className="hero_action_cluster">
                  <Link to="/schedules">
                    <button className="hero_button_primary">Book Now</button>
                  </Link>
                </div>
              </div>
            </div>
          </section>

          {/* Stats row */}
          {!loading && (
            <div className="dashboard_scroll_area">
              <div className="stats-row">
                <div className="stat-card">
                  <span className="stat-num">{stats?.totalBookings ?? 0}</span>
                  <span className="stat-label">Total Bookings</span>
                  <span className="stat-delta">↑ {stats?.bookingsThisMonth ?? 0} this month</span>
                </div>
                <div className="stat-card">
                  <span className="stat-num" style={{ color: 'var(--gold)' }}>{membership?.totalLoyaltyPoints ?? 0}</span>
                  <span className="stat-label">Sphere Points</span>
                  <span className="stat-delta">↑ {stats?.lastPointsEarned ?? 0} pts earned</span>
                </div>
                <div className="stat-card">
                  <span className="stat-num" style={{ color: 'var(--crimson-bright)' }}>{stats?.upcomingBookings ?? 'None'}</span>
                  <span className="stat-label">Upcoming Bookings</span>
                  <span className="stat-delta">Next: {stats?.nextShowDate ?? 'None'}</span>
                </div>
                <div className="stat-card">
                  <span className="stat-num">Rs. 500</span>
                  <span className="stat-label">Welcome Credit</span>
                  <span className="stat-delta">Valid until Oct 2026</span>
                </div>
              </div>
            </div>
          )}

          {/* Bookings + membership grid */}
          <section className="booking-membership-grid">

            <div className="booking-column">
              <div className="panel-glass">
                <div className="section-title">Upcoming Bookings</div>
                {loading ? (
                  <p style={{ color: 'var(--text-muted)', padding: '1rem 0' }}>Loading…</p>
                ) : bookings.length === 0 ? (
                  <p style={{ color: 'var(--text-muted)', padding: '1rem 0' }}>No upcoming bookings.</p>
                ) : (
                  bookings.map(b => (
                    <div key={b.bookingId} className="booking-row">
                      <div className="booking-poster">
                        <img src={`x`}
                          alt={b.movieName} className="booking-poster-image" />
                      </div>
                      <div className="booking-info">
                        <div className="booking-movie">{b.movieName}</div>
                        <div className="booking-meta">{b.showDate} – {b.startTime?.slice(0,5)}</div>
                        <div className="booking-meta">{b.theatreCity} – {b.screenName}</div>
                      </div>
                      <div className="booking-status">
                        <span className={`status-pill ${b.bookingStatus === 'CONFIRMED' ? 'status-confirmed' : 'status-past'}`}>
                          {b.bookingStatus}
                        </span>
                        <span className="seat-label">{b.tickets?.length ?? 0} seat{b.tickets?.length !== 1 ? 's' : ''}</span>
                      </div>
                    </div>
                  ))
                )}
              </div>
            </div>

            {/* Membership card */}
            <div className="membership-column">
              <div className="membership-card">
                <span className="membership-tier">{membership?.membershipType}</span>
                <div className="membership-name">{user?.firstName} {user?.lastName}</div>
                <div className="membership-points-value">{membership?.totalLoyaltyPoints ?? 0}</div>
                <span className="membership-points-label">Points Available</span>
                <div className="membership-progress">
                  <div className="membership-progress-label">
                    <span>Progress to Elite</span>
                    <span>{membership?.totalLoyaltyPoints ?? 0} / 3,000 pts</span>
                  </div>
                  <div className="membership-progress-bar">
                    <div className="membership-progress-fill" style={{ width: `${progress}%` }}></div>
                  </div>
                </div>
              </div>
            </div>

          </section>
        </main>
      </div>
      <Footer />
    </>
  )
}