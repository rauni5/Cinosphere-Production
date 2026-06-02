import { useState } from 'react'
import Header from '../components/Header'
import Footer from '../components/Footer'
import HeroBanner from '../components/HeroBanner'
import './css/contact.css'

const FAQS = [
  {
    question: 'Can I cancel booking?',
    answer: 'Yes, up to 2 hours before showtime.',
  },
  {
    question: 'What formats are supported?',
    answer: 'IMAX Laser, 3D, PLF, Digital.',
  },
  {
    question: 'Corporate bookings?',
    answer: 'Available for 20+ guests with custom packages.',
  },
]

const CONTACT_INFO_ROWS = [
  [
    {
      icon: '📍',
      theme: 'icon_theme_gold',
      label: 'Address',
      value: 'Labim Mall, Pulchowk',
    },
    {
      icon: '📞',
      theme: 'icon_theme_gold',
      label: 'Phone',
      value: '+977 01-5555-IMAX',
    },
  ],
  [
    {
      icon: '✉️',
      theme: 'icon_theme_red',
      label: 'Email',
      value: 'hello@cinosphere.com.np',
    },
    {
      icon: '🕒',
      theme: 'icon_theme_gold',
      label: 'Box Office',
      value: '10AM – 10:30PM',
    },
  ],
]

export default function Contact() {
  const [form, setForm] = useState({
    firstName: '',
    lastName: '',
    email: '',
    message: '',
  })

  const [success, setSuccess] = useState('')
  const [busy, setBusy] = useState(false)

  function onChange(e) {
    setForm(f => ({
      ...f,
      [e.target.name]: e.target.value,
    }))
  }

  async function onSubmit(e) {
    e.preventDefault()

    setBusy(true)

    await new Promise(resolve => setTimeout(resolve, 800))

    setSuccess("Message sent! We'll get back to you within 24 hours.")

    setForm({
      firstName: '',
      lastName: '',
      email: '',
      message: '',
    })

    setBusy(false)
  }

  return (
    <>
      <Header />

      <main className="contact_screen">

        <HeroBanner
          titleMain="Stay"
          titleEm="Connected"
          subtitle="We’d love to hear from you, from booking assistance and partnership enquiries to simply saying hello."
        />

        <section className="contact_content_wrapper">

          <div className="contact_page_grid_container">

            {/* Left Column */}
            <div className="contact_form_column">

              <div className="contact_form_panel_card">

                <h3 className="form_panel_title">
                  Send us a <em>Message</em>
                </h3>

                <p className="form_panel_description">
                  Have a question, feedback, or a corporate booking enquiry?
                  Fill out the form below.
                </p>

                {success ? (
                  <div className="success_box">
                    {success}
                  </div>
                ) : (
                  <form onSubmit={onSubmit}>

                    <div className="form_grid_two_columns">

                      <div className="form_input_group">
                        <label className="form_input_label">
                          First Name
                        </label>

                        <input
                          type="text"
                          name="firstName"
                          value={form.firstName}
                          onChange={onChange}
                          className="form_text_field"
                          required
                        />
                      </div>

                      <div className="form_input_group">
                        <label className="form_input_label">
                          Last Name
                        </label>

                        <input
                          type="text"
                          name="lastName"
                          value={form.lastName}
                          onChange={onChange}
                          className="form_text_field"
                          required
                        />
                      </div>

                    </div>

                    <div className="form_input_group">
                      <label className="form_input_label">
                        Email
                      </label>

                      <input
                        type="email"
                        name="email"
                        value={form.email}
                        onChange={onChange}
                        className="form_text_field"
                        required
                      />
                    </div>

                    <div className="form_input_group">
                      <label className="form_input_label">
                        Message
                      </label>

                      <textarea
                        name="message"
                        value={form.message}
                        onChange={onChange}
                        className="form_text_area_field"
                        required
                      />
                    </div>

                    <button
                      type="submit"
                      disabled={busy}
                      className="form_submit_button_gold"
                    >
                      {busy ? 'Sending...' : 'Send Message'}
                    </button>

                  </form>
                )}

              </div>

            </div>

            {/* Right Column */}
            <div className="contact_right_column">

              {CONTACT_INFO_ROWS.map((row, rowIndex) => (
                <div
                  key={rowIndex}
                  className="contact_info_row"
                >
                  {row.map(item => (
                    <div
                      key={item.label}
                      className="info_card_item"
                    >
                      <span
                        className={`info_card_icon_wrapper ${item.theme}`}
                      >
                        {item.icon}
                      </span>

                      <div>
                        <div className="info_card_label_text">
                          {item.label}
                        </div>

                        <div className="info_card_main_value">
                          {item.value}
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              ))}

              <div className="faq_glass_container">

                <h3 className="faq_main_title">
                  Frequently Asked <em>Questions</em>
                </h3>

                {FAQS.map(faq => (
                  <details
                    key={faq.question}
                    className="faq_item"
                  >
                    <summary className="faq_question_row">
                      {faq.question}
                    </summary>

                    <div className="faq_answer_row">
                      {faq.answer}
                    </div>
                  </details>
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
