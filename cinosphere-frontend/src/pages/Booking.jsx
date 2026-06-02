import { useState, useEffect, useMemo } from 'react'
import { useSearchParams, useNavigate, Link } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import ErrorBox from '../components/ErrorBox'
import { useAuth } from '../context/AuthContext'
import api from '../api'
import './css/booking.css';
import './css/seat.css';
const STANDARD_MAX  = 90
const PREMIUM_MAX   = 180
const POINTS_NEEDED = 300
const DISCOUNT_PCT  = 15

export default function Booking() {
  const [params]   = useSearchParams()
  const navigate   = useNavigate()
  const { user }   = useAuth()

  const movieId    = parseInt(params.get('movieId'))
  const showtimeId = parseInt(params.get('showtimeId'))
  const screenId   = parseInt(params.get('screenId'))

  const [movie,        setMovie]        = useState(null)
  const [showtime,     setShowtime]     = useState(null)
  const [seats,        setSeats]        = useState([])
  const [takenIds,     setTakenIds]     = useState(new Set())
  const [selectedIds,  setSelectedIds]  = useState(new Set())
  const [membership,   setMembership]   = useState(null)
  const [usePoints,    setUsePoints]    = useState(false)
  const [basePrice,    setBasePrice]    = useState(0)
  const [paymentMethod,setPaymentMethod]= useState('')
  const [error,        setError]        = useState('')
  const [busy,         setBusy]         = useState(false)
  const [loading,      setLoading]      = useState(true)

  useEffect(() => {
    if (!movieId || !showtimeId || !screenId) return
    Promise.all([
      api.get(`/api/movies/${movieId}`),
      api.get(`/api/showtimes/${showtimeId}`),
      api.get(`/api/seats?screenId=${screenId}`),
      api.get(`/api/bookings/taken-seats?showtimeId=${showtimeId}`),
      api.get(`/api/screens/${screenId}`),
      user ? api.get('/api/memberships/me') : Promise.resolve(null),
    ]).then(([m, st, s, taken, screen, mem]) => {
      setMovie(m)
      setShowtime(st)
      setSeats(s)
      setTakenIds(new Set(taken))
      setBasePrice(screen.basePrice || 0)
      setMembership(mem)
    }).catch(() => setError('Failed to load booking data'))
      .finally(() => setLoading(false))
  }, [movieId, showtimeId, screenId])

  function toggleSeat(seatId) {
    setSelectedIds(prev => {
      const next = new Set(prev)
      next.has(seatId) ? next.delete(seatId) : next.add(seatId)
      return next
    })
  }

  function priceFor(seatNumber) {
    if (seatNumber > PREMIUM_MAX) return basePrice * 2.0   // VIP
    if (seatNumber > STANDARD_MAX) return basePrice * 1.5  // Premium
    return basePrice                                        // Standard
  }

  const selectedSeats = useMemo(
    () => seats.filter(s => selectedIds.has(s.seatId)),
    [seats, selectedIds]
  )

  const subtotal = useMemo(
    () => selectedSeats.reduce((sum, s) => sum + priceFor(s.seatNumber), 0),
    [selectedSeats, basePrice]
  )

  const canUsePoints  = membership && membership.totalLoyaltyPoints >= POINTS_NEEDED
  const discountAmt   = usePoints && canUsePoints ? Math.round(subtotal * DISCOUNT_PCT / 100) : 0
  const total         = subtotal - discountAmt

  // Group seats by type for the order summary
  const stdSeats  = selectedSeats.filter(s => s.seatNumber <= STANDARD_MAX)
  const premSeats = selectedSeats.filter(s => s.seatNumber > STANDARD_MAX && s.seatNumber <= PREMIUM_MAX)
  const vipSeats  = selectedSeats.filter(s => s.seatNumber > PREMIUM_MAX)

  // Group all seats by row for the seat map
  const seatsByRow = useMemo(() => {
    const map = new Map()
    seats.forEach(s => {
      if (!map.has(s.rowNumber)) map.set(s.rowNumber, [])
      map.get(s.rowNumber).push(s)
    })
    return map
  }, [seats])

  async function handleCheckout(e) {
    e.preventDefault()
    if (selectedIds.size === 0) { setError('Select at least one seat'); return }
    if (!paymentMethod)         { setError('Select a payment method');   return }
    setError('')
    setBusy(true)
    try {
      const result = await api.post('/api/bookings', {
        showtimeId,
        seatIds: [...selectedIds],
        paymentMethod,
        usePoints: usePoints && canUsePoints,
      })
      navigate(`/profile?confirmed=${result.bookingId}`)
    } catch (err) {
      setError(err.message)
    } finally {
      setBusy(false)
    }
  }

  if (loading) return <><Header /><p style={{ padding: '4rem', color: 'var(--text-muted)' }}>Loading…</p><Footer /></>

  return (
    <>
      <Header />
      <main className="booking_screen">

        {/* ── LEFT: seat map ─────────────────────────────── */}
        <section className="booking_left_section">

          {/* Movie info card */}
          <div className="movie_card">
            <div className="movie_card_poster">
              <img src={`/api/uploads/movies/${movieId}`} alt={movie?.movieName} className="movie_poster_image" />
            </div>
            <div className="movie_card_information">
              <div className="movie_card_title">{movie?.movieName}</div>
              <div className="movie_card_description">
                {showtime?.startTime?.slice(0, 5)} · {showtime?.showDate} · Hall {screenId}
              </div>
              <div className="movie_card_badge_group">
                <span className="movie_card_badge movie_card_badge_gold">{movie?.ageRating}</span>
                <span className="movie_card_badge movie_card_badge_neutral">{movie?.duration} min</span>
              </div>
            </div>
            <Link to="/schedules" className="movie_card_button">Change Show →</Link>
          </div>

          {/* Seat map header */}
          <div className="seat_selection_header">
            <div className="seat_selection_heading">
              <span className="section_eyebrow">Select Your Seats</span>
              <h2 className="seat_selection_title">Hall {screenId}</h2>
            </div>
            <div className="seat_indicator_bar">
              <div className="indicator_item"><div className="overlay overlay_available"></div><span className="label">Available</span></div>
              <div className="indicator_item"><div className="overlay overlay_taken"></div><span className="label">Taken</span></div>
              <div className="indicator_item"><div className="overlay overlay_reserved"></div><span className="label">Selected</span></div>
            </div>
          </div>

          <div className="screen_label_wrap">
            <div className="screen_bar"></div>
            <div className="screen_bar_glow"></div>
            <div className="screen_text">Screen this way</div>
          </div>

          {/* Seat map */}
          <div className="seat_map_wrap">
            <div className="seat_map">
              {/* Section labels */}
              {seats.some(s => s.seatNumber <= STANDARD_MAX) && (
                <div className="section_label_row"><span className="section_label_text">Standard</span><div className="section_label_line"></div></div>
              )}
              {[...seatsByRow.entries()].map(([row, rowSeats]) => {
                const firstSeat = rowSeats[0]
                const showPremLabel = firstSeat.seatNumber > STANDARD_MAX && firstSeat.seatNumber <= STANDARD_MAX + rowSeats.length
                const showVipLabel  = firstSeat.seatNumber > PREMIUM_MAX  && firstSeat.seatNumber <= PREMIUM_MAX  + rowSeats.length
                return (
                  <div key={row}>
                    {showPremLabel && <div className="section_label_row"><span className="section_label_text">Premium</span><div className="section_label_line"></div></div>}
                    {showVipLabel  && <div className="section_label_row"><span className="section_label_text">VIP</span><div className="section_label_line"></div></div>}
                    <div className="seat_row">
                      <span className="seat_row_label">{row}</span>
                      {rowSeats.map((seat, colIdx) => {
                        const col    = colIdx + 1
                        const taken  = takenIds.has(seat.seatId)
                        const chosen = selectedIds.has(seat.seatId)
                        const aisle  = col === 4 || col === 14

                        return (
                          <span key={seat.seatId}>
                            {aisle && <div className="seat_aisle"></div>}
                            {taken ? (
                              <div className="seat seat_taken" title={`${row}${col} — Taken`}>
                                <span className="seat_visual"><span className="seat_number">{col}</span></span>
                              </div>
                            ) : (
                              <label
                                className={`seat seat_available ${chosen ? 'seat_selected' : ''}`}
                                title={`${row}${col}`}
                              >
                                <input
                                  type="checkbox"
                                  className="seat_checkbox"
                                  checked={chosen}
                                  onChange={() => toggleSeat(seat.seatId)}
                                />
                                <span className="seat_visual"><span className="seat_number">{col}</span></span>
                              </label>
                            )}
                          </span>
                        )
                      })}
                    </div>
                  </div>
                )
              })}
            </div>
          </div>

        </section>

        {/* ── RIGHT: checkout panel ──────────────────────── */}
        <aside className="checkout_panel">
          <div className="checkout_header">
            <span className="section_eyebrow">Your Order</span>
            <h3 className="checkout_title">Booking <em>Summary</em></h3>
            <div className="checkout_line"></div>
          </div>

          <div className="checkout_body">
            <ErrorBox message={error} />

            {/* Selected seats */}
            <div className="checkout_section_label">Selected Seats ({selectedIds.size})</div>
            <div className="selected_seats_area">
              {selectedSeats.length === 0
                ? <div className="no_seats_notifier">Click seats on the map to select</div>
                : selectedSeats.map(s => (
                    <div key={s.seatId} className="selected_seat_chip">
                      <span>{s.rowNumber}{seats.indexOf(s) % 17 + 1}</span>
                    </div>
                  ))
              }
            </div>

            {/* Sphere points toggle */}
            {membership && (
              <>
                <div className="checkout_section_label">Sphere Points</div>
                <div className="sphere_row">
                  <div className="sphere_display">
                    <div className="sphere_title">Use Sphere Points</div>
                    <div className="sphere_meta">
                      {membership.totalLoyaltyPoints} Pts · {DISCOUNT_PCT}% discount
                      {!canUsePoints && <span style={{ color: 'var(--text-muted)' }}> (need {POINTS_NEEDED})</span>}
                    </div>
                  </div>
                  <button
                    type="button"
                    className="sphere_button"
                    disabled={!canUsePoints}
                    onClick={() => setUsePoints(v => !v)}
                  >
                    {usePoints ? 'Remove' : 'Apply'}
                  </button>
                </div>
              </>
            )}

            {/* Order lines */}
            <div className="checkout_section_label">Checkout Summary</div>
            <div className="order_lines">
              {stdSeats.length  > 0 && <div className="order_line"><span className="order_line_label">Standard ({stdSeats.length} × Rs.{basePrice})</span><span className="order_line_value">Rs.{stdSeats.length * basePrice}</span></div>}
              {premSeats.length > 0 && <div className="order_line"><span className="order_line_label">Premium ({premSeats.length} × Rs.{Math.round(basePrice * 1.5)})</span><span className="order_line_value">Rs.{Math.round(premSeats.length * basePrice * 1.5)}</span></div>}
              {vipSeats.length  > 0 && <div className="order_line"><span className="order_line_label">VIP ({vipSeats.length} × Rs.{Math.round(basePrice * 2)})</span><span className="order_line_value">Rs.{Math.round(vipSeats.length * basePrice * 2)}</span></div>}
              {selectedSeats.length === 0 && <div className="order_line"><span className="order_line_label">Tickets</span><span className="order_line_value">Rs.0</span></div>}
              {discountAmt > 0 && <div className="order_line order_line_discount"><span className="order_line_label">Sphere Credits ({DISCOUNT_PCT}%)</span><span className="order_line_value">-Rs.{discountAmt}</span></div>}
              <div className="order_line order_line_total"><span className="order_line_label">Total</span><span className="order_line_value">Rs.{total}</span></div>
            </div>

            {/* Payment method */}
            <form onSubmit={handleCheckout}>
              <div className="payment_section">
                <div className="checkout_section_label">Payment Method</div>
                <div className="payment_methods">
                  {['esewa', 'khalti', 'fonepay'].map(pm => (
                    <label key={pm} className={`payment_method ${paymentMethod === pm ? 'selected' : ''}`}>
                      <input type="radio" name="paymentMethod" value={pm} className="payment_input"
                        checked={paymentMethod === pm} onChange={() => setPaymentMethod(pm)} />
                      <div className="payment_icon"></div>
                      <div className="payment_info">
                        <div className="payment_name" style={{ textTransform: 'capitalize' }}>{pm}</div>
                      </div>
                    </label>
                  ))}
                </div>
              </div>

              <div className="checkout_footer">
                <div className="checkout_footer_line"></div>
                <button
                  type="submit"
                  className={`checkout_button ${selectedIds.size === 0 ? 'checkout_button_disabled' : ''}`}
                  disabled={busy || selectedIds.size === 0}
                >
                  {busy ? 'Processing…' : selectedIds.size === 0 ? 'Select Seats to Continue' : 'Proceed to Payment'}
                </button>
              </div>
            </form>
          </div>
        </aside>

      </main>
      <Footer />
    </>
  )
}