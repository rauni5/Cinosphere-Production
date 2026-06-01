import Header from '../components/Header'
import Footer from '../components/Footer'
import HeroBanner from '../components/HeroBanner'
import './css/aboutus.css'

const TECH_ITEMS = [
  { icon: '🎥', title: 'IMAX Laser Projection', desc: 'The world\'s most advanced film technology. Proprietary dual 4K laser projection system delivering images with unprecedented brightness, clarity and detail, filling screens up to 18,000 sq. ft.' },
  { icon: '🔊', title: '44.1 kHz IMAX Sound', desc: 'Twelve-channel IMAX digital audio system with custom-tuned speakers — more than 40,000 watts of power designed to fill every seat with uncompromised, crystal-clear sound.' },
  { icon: '💺', title: 'Ergonomic Seating', desc: 'Custom IMAX-designed stadium seating ensures every audience member has a perfect sightline. Generous spacing, lumbar support, and reclining options for a 3–4 hour experience without fatigue.' },
]

const SCREENS = [
  { city: 'Kathmandu', tag: 'FLAGSHIP', desc: 'Our original and largest venue — 18,000 sq. ft. IMAX screen, 44.1 kHz audio, 350+ seats across Standard, Premium & VIP tiers.', status: 'Open' },
  { city: 'Pokhara',   tag: 'BRANCH',   desc: 'Lakeside cinema overlooking the Annapurna range. Full IMAX experience in a boutique 200-seat auditorium.', status: 'Open' },
  { city: 'Butwal',    tag: 'COMING',   desc: 'Our newest development — bringing the full CinoSphere IMAX experience to western Nepal for the first time.', status: 'Opening Soon' },
]

export default function About() {
  return (
    <>
      <Header />
      <main>
        <HeroBanner
          titleMain="Cinema"
          titleEm="Reimagined"
          subtitle="We didn't build a cinema. We engineered an obsession."
        />

        {/* Technology section */}
        <section className="about_tech_section">
          <div className="about_section_inner">
            <div className="about_section_header">
              <span className="section_eyebrow">The Technology</span>
              <h2 className="section_display_title">Built for <em>Perfection</em></h2>
              <p className="about_section_lead">
                Every component of the CinoSphere experience has been obsessively engineered.
                From screen geometry to seat foam density — nothing is accidental.
              </p>
            </div>
            <div className="about_tech_grid">
              {TECH_ITEMS.map(item => (
                <div key={item.title} className="about_tech_card">
                  <div className="about_tech_icon">{item.icon}</div>
                  <h3 className="about_tech_title">{item.title}</h3>
                  <p className="about_tech_desc">{item.desc}</p>
                </div>
              ))}
            </div>
          </div>
        </section>

        {/* Screens section */}
        <section className="about_screens_section">
          <div className="about_section_inner">
            <div className="about_section_header">
              <span className="section_eyebrow">Our Locations</span>
              <h2 className="section_display_title">The <em>Spheres</em></h2>
            </div>
            <div className="about_screens_grid">
              {SCREENS.map(screen => (
                <div key={screen.city} className="about_screen_card">
                  <div className="about_screen_card_top">
                    <div className="about_screen_city">{screen.city}</div>
                    <span className={`about_screen_tag ${screen.status === 'Open' ? 'tag_open' : 'tag_soon'}`}>
                      {screen.status}
                    </span>
                  </div>
                  <span className="about_screen_badge">{screen.tag}</span>
                  <p className="about_screen_desc">{screen.desc}</p>
                </div>
              ))}
            </div>
          </div>
        </section>

        {/* Stats strip */}
        <section className="about_stats_section">
          <div className="about_stats_grid">
            <div className="about_stat"><span className="about_stat_value">18K</span><span className="about_stat_label">sq. ft. Screen</span></div>
            <div className="about_stat"><span className="about_stat_value">44K</span><span className="about_stat_label">Watts of Sound</span></div>
            <div className="about_stat"><span className="about_stat_value">350+</span><span className="about_stat_label">Seats</span></div>
            <div className="about_stat"><span className="about_stat_value">3</span><span className="about_stat_label">Locations</span></div>
          </div>
        </section>

        {/* Mission */}
        <section className="about_mission_section">
          <div className="about_section_inner about_mission_inner">
            <span className="section_eyebrow">Our Mission</span>
            <h2 className="section_display_title">Why We <em>Exist</em></h2>
            <p className="about_mission_body">
              Cinema in Nepal deserves the same experience as anywhere in the world. We started CinoSphere
              because we believe stories told at scale, with perfect sound and image, change the way people
              feel about film — and about each other. Every design decision we make starts with a single
              question: does this make the story better?
            </p>
          </div>
        </section>

      </main>
      <Footer />
    </>
  )
}