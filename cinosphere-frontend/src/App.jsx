import { Routes, Route, Navigate } from 'react-router-dom'
import { useAuth } from './context/AuthContext'

import Home        from './pages/Home'
import Movies      from './pages/Movies'
import MovieDetail from './pages/MovieDetail'
import Schedules   from './pages/Schedules'
import Booking     from './pages/Booking'
import Login       from './pages/Login'
import Register    from './pages/Register'
import UserPanel   from './pages/UserPanel'
import UpdateProfile from './pages/UpdateProfile'
import AdminPanel  from './pages/AdminPanel'
import UpdateMovie from './pages/UpdateMovie'
import About       from './pages/About'
import Contact     from './pages/Contact'
import NotFound    from './pages/NotFound'
import './App.css'
// Redirect to /login if not authenticated
function PrivateRoute({ children }) {
  const { token } = useAuth()
  return token ? children : <Navigate to="/login" replace />
}

// Redirect to / if not admin
function AdminRoute({ children }) {
  const { user } = useAuth()
  if (!user) return <Navigate to="/login" replace />
  if (user.userRole !== 'ADMIN') return <Navigate to="/" replace />
  return children
}

export default function App() {
  return (
    <Routes>
      {/* Public */}
      <Route path="/"              element={<Home />} />
      <Route path="/movies"        element={<Movies />} />
      <Route path="/movies/:id"    element={<MovieDetail />} />
      <Route path="/schedules"     element={<Schedules />} />
      <Route path="/about"         element={<About />} />
      <Route path="/contact"       element={<Contact />} />
      <Route path="/login"         element={<Login />} />
      <Route path="/register"      element={<Register />} />

      {/* Authenticated users */}
      <Route path="/booking"       element={<PrivateRoute><Booking /></PrivateRoute>} />
      <Route path="/profile"       element={<PrivateRoute><UserPanel /></PrivateRoute>} />
      <Route path="/profile/edit"  element={<PrivateRoute><UpdateProfile /></PrivateRoute>} />

      {/* Admin only */}
      <Route path="/admin"             element={<AdminRoute><AdminPanel /></AdminRoute>} />
      <Route path="/admin/movies/add"  element={<AdminRoute><UpdateMovie mode="add" /></AdminRoute>} />
      <Route path="/admin/movies/:id"  element={<AdminRoute><UpdateMovie mode="update" /></AdminRoute>} />

      {/* Fallback */}
      <Route path="*" element={<NotFound />} />
    </Routes>
  )
}