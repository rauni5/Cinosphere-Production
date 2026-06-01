import { Link, NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import './css/header.css';
export default function Header() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  function handleLogout() {
    logout()
    navigate('/')
  }

  return (
    <header className="navigation_header" id="mainHeader">
      <nav className="navigation_container">

        <Link to="/" className="navigation_brand">
          <span className="navigation_logo_text">CinoSphere</span>
        </Link>

        <ul className="navigation_menu">
          <li><NavLink to="/movies"    className={({ isActive }) => isActive ? 'active' : ''}>Movies</NavLink></li>
          <li><NavLink to="/schedules" className={({ isActive }) => isActive ? 'active' : ''}>Schedules</NavLink></li>
          <li><NavLink to="/about"     className={({ isActive }) => isActive ? 'active' : ''}>About Us</NavLink></li>
          <li><NavLink to="/contact"   className={({ isActive }) => isActive ? 'active' : ''}>Experience</NavLink></li>
        </ul>

        <div className="navigation_actions">
          {!user ? (
            <Link to="/login" className="button outline_button">
              <span className="button_text">Sign In</span>
            </Link>
          ) : (
            <div className="navigation_user_group">
              {user.userRole === 'ADMIN'? (
                <Link to="/admin" className="button outline_button" style={{ marginRight: '0.5rem' }}>
                  Admin
                </Link>
              ):<Link to="/profile" className="button outline_button">
                 Profile
              </Link>
              }
              <button onClick={handleLogout} className="button outline_button" style={{ marginLeft: '0.5rem' }}>
                Sign Out
              </button>
            </div>
          )}
        </div>

      </nav>
    </header>
  )
}