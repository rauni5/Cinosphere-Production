import { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import ErrorBox from '../components/ErrorBox'
import { useAuth } from '../context/AuthContext'
import api from '../api'
import './css/updateProfile.css';
export default function UpdateProfile() {
  const { user, logout } = useAuth()
  const navigate         = useNavigate()

  const [profile,    setProfile]    = useState({ firstName: '', lastName: '', email: '', dateOfBirth: '' })
  const [password,   setPassword]   = useState({ currentPassword: '', newPassword: '', confirmPassword: '' })
  const [membership, setMembership] = useState(null)
  const [error,      setError]      = useState('')
  const [success,    setSuccess]    = useState('')
  const [busy,       setBusy]       = useState(false)
  const [loading,    setLoading]    = useState(true)

  useEffect(() => {
    Promise.all([
      api.get('/api/users/me'),
      api.get('/api/memberships/me'),
    ]).then(([u, m]) => {
      setProfile({
        firstName:   u.firstName   ?? '',
        lastName:    u.lastName    ?? '',
        email:       u.email       ?? '',
        dateOfBirth: u.dateOfBirth ?? '',
      })
      setMembership(m)
    }).catch(() => setError('Failed to load profile'))
      .finally(() => setLoading(false))
  }, [])

  const progress = membership ? Math.min(100, Math.round((membership.totalLoyaltyPoints / 3000) * 100)) : 0

  async function saveProfile(e) {
    e.preventDefault()
    setError(''); setSuccess('')
    setBusy(true)
    try {
      await api.put('/api/users/me', profile)
      setSuccess('Profile updated successfully.')
    } catch (err) {
      setError(err.message)
    } finally {
      setBusy(false)
    }
  }

  async function savePassword(e) {
    e.preventDefault()
    setError(''); setSuccess('')
    if (password.newPassword !== password.confirmPassword) {
      setError('New passwords do not match')
      return
    }
    setBusy(true)
    try {
      await api.put('/api/users/me/password', password)
      setSuccess('Password updated. Please sign in again.')
      setTimeout(() => { logout(); navigate('/login') }, 2000)
    } catch (err) {
      setError(err.message)
    } finally {
      setBusy(false)
    }
  }

  async function deactivateAccount() {
    if (!window.confirm('Deactivate your account? You can reactivate by signing back in.')) return
    try {
      await api.delete('/api/users/me')
      logout()
      navigate('/')
    } catch (err) {
      setError(err.message)
    }
  }

  if (loading) return <><Header /><p style={{ padding: '4rem', color: 'var(--text-muted)' }}>Loading…</p><Footer /></>

  return (
    <>
      <Header />
      <div className="panel_layout_wrapper">

        {/* Sidebar */}
        <aside className="dashboard_sidebar">
          <div className="sidebar_nav_container">
            <nav className="sidebar_nav_menu">
              <span className="sidebar_section_label">Main</span>
              <Link to="/profile" className="sidebar_nav_item">
                <div className="sidebar_icon_box">🏠</div>
                <span className="sidebar_nav_label">Dashboard</span>
              </Link>
              <span className="sidebar_section_label">Account</span>
              <Link to="/profile/edit" className="sidebar_nav_item active">
                <div className="sidebar_icon_box">👤</div>
                <span className="sidebar_nav_label">Profile Settings</span>
              </Link>
            </nav>
          </div>
        </aside>

        <main className="profile_main_panel">

          {/* Hero */}
          <section className="profile_hero_section">
            <div className="profile_hero_bg"></div>
            <div className="profile_hero_content">
              <div className="profile_hero_flex">
                <div className="profile_heading_block">
                  <h2 className="profile_hero_title">Update <em>Profile</em></h2>
                  <p className="profile_hero_subtitle">Personalize your account details and preferences</p>
                </div>
              </div>
            </div>
          </section>

          <ErrorBox message={error} />
          {success && <div className="success_box" style={{ margin: '0 2rem 1rem' }}>{success}</div>}

          {/* Overview card with points */}
          <section className="update_profile_content_wrapper">
            <div className="update_profile_overview_card">
              <div className="update_profile_avatar_wrapper">
                <div className="update_profile_avatar_circle">
                  <img src={`/uploads/profiles/${user?.userId}.jpg`} alt="avatar"
                    onError={e => e.target.style.display = 'none'} />
                </div>
              </div>
              <div className="update_profile_user_details">
                <h3 className="update_profile_user_name">{profile.firstName} {profile.lastName}</h3>
                <p className="update_profile_user_email">{profile.email}</p>
                <div className="update_profile_badge_row">
                  <span className="update_profile_badge update_profile_badge_tier">{membership?.membershipType}</span>
                  <span className="update_profile_badge update_profile_badge_points">{membership?.totalLoyaltyPoints} pts</span>
                </div>
              </div>
              <div className="update_profile_points_panel">
                <div className="update_profile_points_value">{membership?.totalLoyaltyPoints ?? 0}</div>
                <div className="update_profile_points_label">SPHERE POINTS</div>
                <div className="update_profile_progress_wrapper">
                  <div className="update_profile_progress_bar">
                    <div className="update_profile_progress_fill" style={{ width: `${progress}%` }}></div>
                  </div>
                  <div className="update_profile_progress_text">
                    {progress}% to Elite · {3000 - (membership?.totalLoyaltyPoints ?? 0)} pts needed
                  </div>
                </div>
              </div>
            </div>
          </section>

          {/* Forms grid */}
          <section className="update_profile_form_grid">

            {/* Personal info */}
            <form onSubmit={saveProfile} className="update_profile_form_card">
              <div className="update_profile_card_title">👤 <span>Personal Information</span></div>
              <div className="update_profile_name_row">
                <div className="update_profile_input_group">
                  <label className="update_profile_input_label">First Name</label>
                  <input type="text" className="update_profile_input_field"
                    value={profile.firstName} onChange={e => setProfile(p => ({ ...p, firstName: e.target.value }))} />
                </div>
                <div className="update_profile_input_group">
                  <label className="update_profile_input_label">Last Name</label>
                  <input type="text" className="update_profile_input_field"
                    value={profile.lastName} onChange={e => setProfile(p => ({ ...p, lastName: e.target.value }))} />
                </div>
              </div>
              <div className="update_profile_input_group">
                <label className="update_profile_input_label">Email Address</label>
                <input type="email" className="update_profile_input_field"
                  value={profile.email} onChange={e => setProfile(p => ({ ...p, email: e.target.value }))} />
              </div>
              <div className="update_profile_input_group">
                <label className="update_profile_input_label">Date of Birth</label>
                <input type="date" className="update_profile_input_field"
                  value={profile.dateOfBirth} onChange={e => setProfile(p => ({ ...p, dateOfBirth: e.target.value }))} />
              </div>
              <button type="submit" className="update_profile_button_primary" disabled={busy}>Save Changes</button>
            </form>

            {/* Change password */}
            <form onSubmit={savePassword} className="update_profile_form_card">
              <div className="update_profile_card_title">🔒 <span>Change Password</span></div>
              {['currentPassword', 'newPassword', 'confirmPassword'].map(field => (
                <div key={field} className="update_profile_input_group">
                  <label className="update_profile_input_label">
                    {field === 'currentPassword' ? 'Current Password' : field === 'newPassword' ? 'New Password' : 'Confirm Password'}
                  </label>
                  <div className="update_profile_input_wrapper">
                    <input type="password" className="update_profile_input_field"
                      value={password[field]}
                      onChange={e => setPassword(p => ({ ...p, [field]: e.target.value }))} />
                  </div>
                </div>
              ))}
              <button type="submit" className="update_profile_button_primary" disabled={busy}>Update Password</button>
            </form>
          </section>

          {/* Danger zone */}
          <section className="update_profile_danger_wrapper">
            <div className="update_profile_form_card danger_outer_card">
              <div className="update_profile_danger_zone_box">
                <div className="update_profile_danger_header">⚠️ <span>Danger Zone</span></div>
                <div className="update_profile_danger_row">
                  <div className="update_profile_danger_text">
                    <div className="update_profile_danger_label">Deactivate Account</div>
                    <div className="update_profile_danger_desc">Temporarily disable your account. You can reactivate anytime by signing back in.</div>
                  </div>
                  <button type="button" className="update_profile_danger_btn_ghost" onClick={deactivateAccount}>Deactivate</button>
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