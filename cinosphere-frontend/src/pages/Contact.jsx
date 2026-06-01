import { useState } from 'react'
import Header from '../components/Header'
import Footer from '../components/Footer'
import HeroBanner from '../components/HeroBanner'
import './css/contact.css'
const LOCATIONS = [
  {
    city: 'Kathmandu',
    tag: 'FLAGSHIP',
    address: 'CinoSphere Tower, New Baneshwor, Kathmandu 44600',
    hours: 'Mon–Sun: 9:00 AM – 11:30 PM',
    phone: '+977-1-5901234',
    email: 'ktm@cinosphere.com.np',
  },
  {
    city: 'Pokhara',
    tag: 'BRANCH',
    address: 'Lakeside Road, Ward 6, Pokhara 33700',
    hours: 'Mon–Sun: 10:00 AM – 10:30 PM',
    phone: '+977-61-521234',
    email: 'pkr@cinosphere.com.np',
  },
]

export default function Contact() {
  const [form,    setForm]    = useState({ name: '', email: '', subject: '', message: '' })
  const [success, setSuccess] = useState('')
  const [busy,    setBusy]    = useState(false)

  function onChange(e) {
    setForm(f => ({ ...f, [e.target.name]: e.target.value }))
  }

  async function onSubmit(e) {
    e.preventDefault()
    setBusy(true)
    // No backend endpoint needed for now — just simulate success
    await new Promise(r => setTimeout(r, 800))
    setSuccess('Message sent! We\'ll get back to you within 24 hours.')
    setForm({ name: '', email: '', subject: '', message: '' })
    setBusy(false)
  }

  return (
    <>
      <Header />
      <main>
        <HeroBanner
          titleMain="Get in"
          titleEm="Touch"
          subtitle="Questions, feedback, or just want to talk cinema — we're listening."
        />

        <section className="contact_section">
          <div className="contact_inner">

            {/* Contact form */}
            <div className="contact_form_column">
              <span className="section_eyebrow">Send a Message</span>
              <h2 className="section_display_title">We'd love to <em>hear from you</em></h2>

              {success ? (
                <div className="success_box" style={{ marginTop: '1.5rem' }}>{success}</div>
              ) : (
                <form onSubmit={onSubmit} className="contact_form">
                  <div className="contact_form_row">
                    <div className="form_group">
                      <label className="field_label">Your Name</label>
                      <input type="text" name="name" className="input_control"
                        placeholder="Aditya Raut" value={form.name} onChange={onChange} required />
                    </div>
                    <div className="form_group">
                      <label className="field_label">Email Address</label>
                      <input type="email" name="email" className="input_control"
                        placeholder="aditya@email.com" value={form.email} onChange={onChange} required />
                    </div>
                  </div>
                  <div className="form_group">
                    <label className="field_label">Subject</label>
                    <input type="text" name="subject" className="input_control"
                      placeholder="Booking issue, feedback, partnership…" value={form.subject} onChange={onChange} required />
                  </div>
                  <div className="form_group">
                    <label className="field_label">Message</label>
                    <textarea name="message" className="input_control contact_textarea"
                      placeholder="Tell us more…" rows={5}
                      value={form.message} onChange={onChange} required />
                  </div>
                  <button type="submit" className="primary_gold_button" disabled={busy}>
                    {busy ? 'Sending…' : 'Send Message'}
                  </button>
                </form>
              )}
            </div>

            {/* Location cards */}
            <div className="contact_locations_column">
              <span className="section_eyebrow">Our Locations</span>
              <h2 className="section_display_title">Find <em>Us</em></h2>
              <div className="contact_locations_list">
                {LOCATIONS.map(loc => (
                  <div key={loc.city} className="contact_location_card">
                    <div className="contact_location_header">
                      <span className="contact_location_city">{loc.city}</span>
                      <span className="contact_location_tag">{loc.tag}</span>
                    </div>
                    <p className="contact_location_address">📍 {loc.address}</p>
                    <p className="contact_location_hours">🕐 {loc.hours}</p>
                    <p className="contact_location_phone">📞 {loc.phone}</p>
                    <p className="contact_location_email">✉ {loc.email}</p>
                  </div>
                ))}
              </div>
            </div>

          </div>
        </section>

      </main>
      <Footer />
    </>
  )
}