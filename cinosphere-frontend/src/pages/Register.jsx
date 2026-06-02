import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import ErrorBox from '../components/ErrorBox'
import api from '../api'
import './css/register.css'
const INITIAL = {
  firstName: '', lastName: '', username: '', email: '',
  password: '', confirmPassword: '', dateOfBirth: '', gender: '',
}

export default function Register() {
  const navigate = useNavigate()
  const [form,    setForm]    = useState(INITIAL)
  const [error,   setError]   = useState('')
  const [success, setSuccess] = useState('')
  const [busy,    setBusy]    = useState(false)

  function onChange(e) {
    setForm(f => ({ ...f, [e.target.name]: e.target.value }))
  }

  async function onSubmit(e) {
    e.preventDefault()
    setError('')
    setSuccess('')
    if (form.password !== form.confirmPassword) {
      setError('Passwords do not match')
      return
    }
    setBusy(true)
    try {
      await api.post('/api/auth/register', form)
      setSuccess('Registration successful! Wait for admin approval, then sign in.')
      setTimeout(() => navigate('/login'), 3000)
    } catch (err) {
      setError(err.message)
    } finally {
      setBusy(false)
    }
  }

  return (
    <>
      <Header />
      <main className="registration_screen">
        <div className="authentication_layout_wrapper">

          {/* Left visual */}
          <div className="authentication_left_column">
            <div className="authentication_background_overlay"></div>
            <div className="authentication_body_content">
              <div className="registration_information_block">
                <h2 className="authentication_hero_headline">Become a<br /><em>Sphere Member</em></h2>
                <p className="authentication_hero_body">
                  Join the Sphere circle to experience priority bookings, exclusive member rewards &amp; unforgettable premiere nights.
                </p>
                <div className="authentication_feature_list">
                  <div className="authentication_feature_item"><div className="authentication_feature_dot"></div><span className="authentication_feature_text">Exclusive member rewards &amp; special screenings</span></div>
                  <div className="authentication_feature_item"><div className="authentication_feature_dot"></div><span className="authentication_feature_text">NPR 500 welcome credit on signup</span></div>
                  <div className="authentication_feature_item"><div className="authentication_feature_dot"></div><span className="authentication_feature_text">Early access to premiere tickets</span></div>
                  <div className="authentication_feature_item"><div className="authentication_feature_dot"></div><span className="authentication_feature_text">Earn points on every booking</span></div>
                </div>
              </div>
            </div>
          </div>

          {/* Right form */}
          <div className="authentication_right_section">
            <div className="authentication_panel_container">
              <div className="registration_header_area">
                <h2 className="authentication_panel_title">Create <em>Account</em></h2>
                <p className="authentication_panel_subtitle">Join CinoSphere — Enter your details to get started</p>
              </div>

              <div className="glass_panel_container">
                <ErrorBox message={error} />
                {success && <div className="success_box">{success}</div>}

                <form onSubmit={onSubmit}>
                  <div className="form_row_double">
                    <div className="form_group_wrapper">
                      <label className="form_label_text">First Name</label>
                      <input type="text" name="firstName" className="form_input_field"
                        placeholder="Aditya" value={form.firstName} onChange={onChange} required />
                    </div>
                    <div className="form_group_wrapper">
                      <label className="form_label_text">Last Name</label>
                      <input type="text" name="lastName" className="form_input_field"
                        placeholder="Raut" value={form.lastName} onChange={onChange} required />
                    </div>
                  </div>

                  <div className="form_row_double">
                    <div className="form_group_wrapper">
                      <label className="form_label_text">Username</label>
                      <div className="form_input_wrapper">
                        <input type="text" name="username" className="form_input_field"
                          placeholder="adir" value={form.username} onChange={onChange} required />
                        <span className="form_input_icon">👤</span>
                      </div>
                    </div>
                    <div className="form_group_wrapper">
                      <label className="form_label_text">Email Address</label>
                      <div className="form_input_wrapper">
                        <input type="email" name="email" className="form_input_field"
                          placeholder="aditya@gmail.com" value={form.email} onChange={onChange} required />
                        <span className="form_input_icon">✉</span>
                      </div>
                    </div>
                  </div>

                  <div className="form_row_double">
                    <div className="form_group_wrapper">
                      <label className="form_label_text">Gender</label>
                      <select name="gender" className="form_select_field" value={form.gender} onChange={onChange} required>
                        <option value="" disabled>Select</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                        <option value="other">Other</option>
                      </select>
                    </div>
                    <div className="form_group_wrapper">
                      <label className="form_label_text">Date of Birth</label>
                      <input type="date" name="dateOfBirth" className="form_input_field"
                        value={form.dateOfBirth} onChange={onChange} required />
                    </div>
                  </div>

                  <div className="form_row_double">
                    <div className="form_group_wrapper">
                      <label className="form_label_text">Password</label>
                      <div className="form_input_wrapper">
                        <input type="password" name="password" className="form_input_field"
                          placeholder="••••••••" value={form.password} onChange={onChange} required />
                        <span className="form_input_icon">🔒</span>
                      </div>
                    </div>
                    <div className="form_group_wrapper">
                      <label className="form_label_text">Confirm</label>
                      <div className="form_input_wrapper">
                        <input type="password" name="confirmPassword" className="form_input_field"
                          placeholder="••••••••" value={form.confirmPassword} onChange={onChange} required />
                        <span className="form_input_icon">✔</span>
                      </div>
                    </div>
                  </div>

                  <button type="submit" className="primary_form_button_gold" disabled={busy}>
                    {busy ? 'Creating account…' : 'Get Started'}
                  </button>
                </form>

                <p className="authentication_switch_text">
                  Already have an account? <Link to="/login">Sign in</Link>
                </p>
              </div>
            </div>
          </div>

        </div>
      </main>
      <Footer />
    </>
  )
}
