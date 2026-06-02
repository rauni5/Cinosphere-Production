// All API calls go through here.
// The vite.config.js proxy forwards /api/* → http://localhost:8080
// so you never need to change this URL for local dev.
const BASE = ''

function authHeaders() {
  const token = localStorage.getItem('token')
  return token ? { Authorization: `Bearer ${token}` } : {}
}

async function request(method, endpoint, body, isForm = false) {
  const headers = { ...authHeaders() }
  if (body && !isForm) headers['Content-Type'] = 'application/json'

  const res = await fetch(BASE + endpoint, {
    method,
    headers,
    body: isForm ? body : body ? JSON.stringify(body) : undefined,
  })

  const data = await res.json()
  if (!res.ok) throw new Error(data.message || `HTTP ${res.status}`)
  // Unwrap ApiResponse envelope when present
  return data.data !== undefined ? data.data : data
}

const api = {
  get:        (url)              => request('GET',    url),
  post:       (url, body)        => request('POST',   url, body),
  put:        (url, body)        => request('PUT',    url, body),
  delete:     (url)              => request('DELETE', url),
  postForm:   (url, formData)    => request('POST',   url, formData, true),
  putForm:    (url, formData)    => request('PUT',    url, formData, true),
}

export default api