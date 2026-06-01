import { useState, useEffect } from 'react'
import { useSearchParams } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import HeroBanner from '../components/HeroBanner'
import ScheduleCard from '../components/ScheduleCard'
import ErrorBox from '../components/ErrorBox'
import api from '../api'
import './css/schedules.css';
import './css/dateCard.css';
function todayStr() {
  return new Date().toISOString().slice(0, 10)
}

function buildDateStrip() {
  const strip = []
  for (let i = 0; i < 14; i++) {
    const d = new Date(); d.setDate(d.getDate() + i)
    strip.push({
      value:  d.toISOString().slice(0, 10),
      day:    d.toLocaleDateString('en-US', { weekday: 'short' }).toUpperCase(),
      number: d.getDate(),
      month:  d.toLocaleDateString('en-US', { month: 'short' }).toUpperCase(),
    })
  }
  return strip
}

export default function Schedules() {
  const [searchParams, setSearchParams] = useSearchParams()
  const dateStrip = buildDateStrip()

  const [selectedDate, setSelectedDate] = useState(searchParams.get('date') || todayStr())
  const [timeFilter,   setTimeFilter]   = useState(searchParams.get('timeFilter')     || '')
  const [langFilter,   setLangFilter]   = useState(searchParams.get('langFilter')     || '')
  const [locFilter,    setLocFilter]    = useState(searchParams.get('locationFilter') || '')
  const [formatFilter, setFormatFilter] = useState(searchParams.get('format')         || 'all')
  const [movieSearch,  setMovieSearch]  = useState(searchParams.get('movieSearch')    || '')

  const [schedules, setSchedules] = useState([])
  const [loading,   setLoading]   = useState(true)
  const [error,     setError]     = useState('')

  useEffect(() => { fetchSchedules() }, [selectedDate, searchParams])

  function fetchSchedules() {
    setLoading(true); setError('')
    const q = new URLSearchParams({
      selectedDate,
      ...(timeFilter   && { timeFilter }),
      ...(langFilter   && { langFilter }),
      ...(locFilter    && { locationFilter: locFilter }),
      ...(formatFilter && formatFilter !== 'all' && { formatFilter }),
      ...(movieSearch  && { movieSearch }),
    })
    api.get(`/api/schedules?${q.toString()}`)
      .then(data => setSchedules(data?.schedules ?? []))
      .catch(e  => setError(e.message))
      .finally(() => setLoading(false))
  }

  function applyFilters(e) {
    e.preventDefault()
    setSearchParams({ date: selectedDate, timeFilter, langFilter, locationFilter: locFilter, format: formatFilter, movieSearch })
  }

  function selectDate(val) {
    setSelectedDate(val)
    setSearchParams(prev => { const p = new URLSearchParams(prev); p.set('date', val); return p })
  }

  return (
    <>
      <Header />
      <main className="schedules_screen">
        <HeroBanner
          titleMain="What's"
          titleEm="Showing"
          subtitle="Explore available screenings and book the show that fits your time, language, and favourite cinema."
        />

        {/* Date strip */}
        <section className="date_strip_section">
          <div className="schedules_date_strip">
            {dateStrip.map(d => (
              <button
                key={d.value}
                className={`date_card${selectedDate === d.value ? ' active' : ''}`}
                onClick={() => selectDate(d.value)}
                type="button"
              >
                <span className="date_card_day">{d.day}</span>
                <span className="date_card_number">{d.number}</span>
                <span className="date_card_month">{d.month}</span>
              </button>
            ))}
          </div>
        </section>

        {/* Filters */}
        <form onSubmit={applyFilters}>
          <section className="schedules_filter_section">
            <div className="schedules_filter_container">
              <div className="schedules_search_wrapper">
                <span className="schedules_search_icon_container">🔍</span>
                <input
                  type="text"
                  className="schedules_search_input"
                  placeholder="Search movies..."
                  value={movieSearch}
                  onChange={e => setMovieSearch(e.target.value)}
                />
              </div>
              <div className="schedules_filter_dropdown_group">
                <div className="schedules_select_wrapper">
                  <select className="schedules_filter_select" value={timeFilter} onChange={e => setTimeFilter(e.target.value)}>
                    <option value="">All Timings</option>
                    <option value="morning">Morning (Before 12PM)</option>
                    <option value="afternoon">Afternoon (12–5PM)</option>
                    <option value="evening">Evening (5–9PM)</option>
                    <option value="night">Night (After 9PM)</option>
                  </select>
                  <span className="schedules_select_arrow">▾</span>
                </div>
                <div className="schedules_select_wrapper">
                  <select className="schedules_filter_select" value={locFilter} onChange={e => setLocFilter(e.target.value)}>
                    <option value="">All Locations</option>
                    <option value="kathmandu">Kathmandu</option>
                    <option value="pokhara">Pokhara</option>
                    <option value="butwal">Butwal</option>
                  </select>
                  <span className="schedules_select_arrow">▾</span>
                </div>
                <div className="schedules_select_wrapper">
                  <select className="schedules_filter_select" value={langFilter} onChange={e => setLangFilter(e.target.value)}>
                    <option value="">All Languages</option>
                    <option value="english">English</option>
                    <option value="hindi">Hindi</option>
                    <option value="nepali">Nepali</option>
                  </select>
                  <span className="schedules_select_arrow">▾</span>
                </div>
                <div className="schedules_select_wrapper">
                  <select className="schedules_filter_select" value={formatFilter} onChange={e => setFormatFilter(e.target.value)}>
                    <option value="all">All Formats</option>
                    <option value="IMAX">IMAX</option>
                    <option value="3D">Laser Atmos 3D</option>
                    <option value="Standard">Standard</option>
                  </select>
                  <span className="schedules_select_arrow">▾</span>
                </div>
                <div className="schedules_select_wrapper">
                  <button type="submit" className="schedules_filter_pill">APPLY</button>
                </div>
              </div>
            </div>
          </section>
        </form>

        {/* Results */}
        <section className="schedules_list_section">
          <ErrorBox message={error} />
          {loading ? (
            <p style={{ color: 'var(--text-muted)', padding: '2rem' }}>Loading…</p>
          ) : schedules.length === 0 ? (
            <div className="schedules_empty">
              <p>No screenings found for the selected date and filters.</p>
            </div>
          ) : (
            schedules.map(item => (
              <ScheduleCard
                key={item.movieId}
                movie={item}
                halls={item.halls}
                date={selectedDate}
              />
            ))
          )}
        </section>
      </main>
      <Footer />
    </>
  )
}