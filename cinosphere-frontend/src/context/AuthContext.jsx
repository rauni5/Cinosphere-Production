import { createContext, useContext, useState } from 'react'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem('token'))
  const [user,  setUser]  = useState(() => {
    try { return JSON.parse(localStorage.getItem('user')) } catch { return null }
  })

  function login(loginResponse) {
    // loginResponse = { token, userId, username, firstName, lastName, userRole }
    localStorage.setItem('token', loginResponse.token)
    localStorage.setItem('user',  JSON.stringify(loginResponse))
    setToken(loginResponse.token)
    setUser(loginResponse)
  }

  function logout() {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    setToken(null)
    setUser(null)
  }

  return (
    <AuthContext.Provider value={{ token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  return useContext(AuthContext)
}