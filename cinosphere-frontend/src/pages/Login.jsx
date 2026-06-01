import { useState } from 'react'
import { Link, useNavigate, useLocation } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import ErrorBox from '../components/ErrorBox'
import { useAuth } from '../context/AuthContext'
import api from '../api'
import './css/login.css';
export default function Login() {
  const { login } = useAuth()
  const navigate  = useNavigate()
  const location  = useLocation()
  const from      = new URLSearchParams(location.search).get('redirect') || '/'

  const [form, setForm]   = useState({ username: '', password: '' })
  const [error, setError] = useState('')
  const [busy,  setBusy]  = useState(false)

  function onChange(e) {
    setForm(f => ({ ...f, [e.target.name]: e.target.value }))
  }

  async function onSubmit(e) {
    e.preventDefault()
    setError('')
    setBusy(true)
    try {
      const response = await api.post('/api/auth/login', form)
      login(response)
      navigate(response.userRole === 'ADMIN' ? '/admin' : from, { replace: true })
    } catch (err) {
      setError(err.message)
    } finally {
      setBusy(false)
    }
  }

  return (
    <>
      <Header />
      <main className="login_screen">
        <div className="authentication_container">
          <div className="authentication_split_layout">

            <section className="authentication_visual_side">
              <div className="visual_overlay"></div>
              <div className="visual_content_wrapper">
                <div className="brand_narrative">
                  <h2 className="hero_display_title">The screen<br /><em>missed you</em></h2>
                  <p className="hero_display_subtitle">
                    Sign back in to manage your bookings, enjoy membership perks &amp; never miss a premiere.
                  </p>
                  <div className="feature_perk_list">
                    <div className="perk_item"><span className="perk_dot"></span><span className="perk_label">Upcoming booking reminders</span></div>
                    <div className="perk_item"><span className="perk_dot"></span><span className="perk_label">Loyalty points &amp; Sphere credits</span></div>
                    <div className="perk_item"><span className="perk_dot"></span><span className="perk_label">Priority IMAX seat selection</span></div>
                  </div>
                </div>
              </div>
            </section>

            <section className="authentication_form_side">
              <div className="control_panel">
                <header className="panel_header">
                  <h2 className="panel_title">Log <em>In</em></h2>
                  <p className="panel_subtitle">Enter your credentials to continue</p>
                </header>
                <div className="glass_card">
                  <ErrorBox message={error} />
                  <form onSubmit={onSubmit} className="form_standard">
                    <div className="form_group">
                      <label className="field_label">Username</label>
                      <div className="input_field_wrapper">
                        <input type="text" name="username" className="input_control" placeholder="Emilio"
                          value={form.username} onChange={onChange} required />
                        <span className="input_icon">👤</span>
                      </div>
                    </div>
                    <div className="form_group">
                      <label className="field_label">Password</label>
                      <div className="input_field_wrapper">
                        <input type="password" name="password" className="input_control" placeholder="••••••••"
                          value={form.password} onChange={onChange} required />
                        <span className="input_icon">🔒</span>
                      </div>
                      <a href="#" className="link_muted_small forgot_link">Forgot Password?</a>
                    </div>
                    <button type="submit" className="primary_gold_button" disabled={busy}>
                      {busy ? 'Signing in…' : 'Sign In'}
                    </button>
                  </form>
                  <div className="panel_footer">
                    <p className="switch_context_text">
                      New to CinoSphere? <Link to="/register" className="gold_link">Create an account</Link>
                    </p>
                  </div>
                </div>
              </div>
            </section>

          </div>
        </div>
      </main>
      <Footer />
    </>
  )
}