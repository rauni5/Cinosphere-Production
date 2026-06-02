import Header from '../components/Header'
import Footer from '../components/Footer'
import './css/aboutus.css'

export default function About() {
  return (
    <>
      <Header />

      <main className="about_screen">

        {/* Hero */}
        <section className="about_hero_section">
          <div className="about_hero_overlay"></div>

          <div className="about_hero_container">
            <div className="about_hero_text_content">
              <h1 className="about_hero_primary_title">
                The <em>Vision</em>
                <br />
                Behind <em>the Screen</em>
              </h1>

              <p className="about_hero_subtitle">
                Born from a vision of world class cinema, experience
                entertainment beyond the ordinary.
              </p>

              <div className="about_hero_action_group">
                <a href="/movies" className="about_hero_button about_hero_button_primary">
                  Explore Movies
                </a>

                <a href="#story" className="about_hero_button about_hero_button_secondary">
                  Our Story
                </a>
              </div>
            </div>
          </div>
        </section>

        {/* Statistics */}
        <section className="about_statistics_section">
          <div className="accent_line top"></div>

          <div className="about_statistics_header">
            <span className="about_section_eyebrow">
              Our Numbers
            </span>

            <h2 className="about_section_title">
              The scale of <em>ambition</em>
            </h2>
          </div>

          <div className="about_statistics_grid">

            <div className="about_statistics_card">
              <span className="about_statistics_value">18,000</span>
              <span className="about_statistics_label">Square Feet</span>
              <span className="about_statistics_description">
                The largest screen surface within South Asia.
              </span>
            </div>

            <div className="about_statistics_card">
              <span className="about_statistics_value">44K</span>
              <span className="about_statistics_label">Watts of Audio</span>
              <span className="about_statistics_description">
                12 channels IMAX certified speaker array installed throughout the halls.
              </span>
            </div>

            <div className="about_statistics_card">
              <span className="about_statistics_value">1400+</span>
              <span className="about_statistics_label">Seats Across Halls</span>
              <span className="about_statistics_description">
                Every seat made for comfort with identical sight lines and acoustic balance.
              </span>
            </div>

            <div className="about_statistics_card">
              <span className="about_statistics_value">3</span>
              <span className="about_statistics_label">Cities by 2027</span>
              <span className="about_statistics_description">
                Kathmandu · Pokhara · Butwal
              </span>
            </div>

          </div>

          <div className="accent_line bottom"></div>
        </section>

        {/* Story */}
        <section className="about_story_section" id="story">

          <div className="about_story_grid">

            <div className="about_story_content">

              <span className="about_section_eyebrow">
                The Journey
              </span>

              <h2 className="about_section_title">
                From <em>an idea</em>
                <br />
                to reality
              </h2>

              <p className="about_story_description">
                It all started with a conversation in New York after an IMAX
                screening of Spider-Man: No Way Home. We were blown away and
                left with a question — why can't we have this in Kathmandu?
              </p>

              <p className="about_story_description about_story_description_spacing">
                Two years later, that question became a company. Then a
                construction site. Then a screen. Then a world class
                experience.
              </p>

              <div className="about_story_statistics">

                <div className="about_story_statistic_item">
                  <span className="about_story_statistic_value">2.4B+</span>
                  <div className="about_story_statistic_label">
                    TOTAL INVESTMENT
                  </div>
                </div>

                <div className="about_story_statistic_item">
                  <span className="about_story_statistic_value">500+</span>
                  <div className="about_story_statistic_label">
                    TEAM MEMBERS
                  </div>
                </div>

                <div className="about_story_statistic_item">
                  <span className="about_story_statistic_value">3+</span>
                  <div className="about_story_statistic_label">
                    OPERATIONAL VENUES
                  </div>
                </div>

              </div>
            </div>

            <div className="about_story_timeline">

              <div className="about_story_timeline_item">
                <span className="about_story_timeline_year">
                  2023: The Spark
                </span>

                <div className="about_story_timeline_title">
                  The Idea
                </div>

                <p className="about_story_timeline_description">
                  Founder Aditya Raut and co-founder Raunit Giri watch
                  Spider-Man NWH in IMAX in New York. On the flight back to
                  Kathmandu they begin sketching what Nepal's first IMAX
                  could look like.
                </p>
              </div>

              <div className="about_story_timeline_item">
                <span className="about_story_timeline_year">
                  The CinoSphere Regime
                </span>

                <div className="about_story_timeline_title">
                  CinoSphere Nepal Pvt. Ltd. Incorporated
                </div>

                <p className="about_story_timeline_description">
                  The company is registered. The core leadership team is
                  assembled from cinema operations in Nepal, Singapore and
                  the UK.
                </p>
              </div>

              <div className="about_story_timeline_item">
                <span className="about_story_timeline_year">
                  Early 2025: Build
                </span>

                <div className="about_story_timeline_title">
                  Construction Begins in Kathmandu
                </div>

                <p className="about_story_timeline_description">
                  Ground breaks at Labim Mall, Lalitpur. The auditorium is
                  engineered to IMAX specifications.
                </p>
              </div>

              <div className="about_story_timeline_item">
                <span className="about_story_timeline_year">
                  Late 2025: Calibration
                </span>

                <div className="about_story_timeline_title">
                  Laser System Commissioned
                </div>

                <p className="about_story_timeline_description">
                  Nepal's first certified IMAX screen passes all benchmarks.
                </p>
              </div>

              <div className="about_story_timeline_item">
                <span className="about_story_timeline_year">
                  2026: A Grand Opening
                </span>

                <div className="about_story_timeline_title">
                  CinoSphere Kathmandu Opens Its Doors
                </div>

                <p className="about_story_timeline_description">
                  The first public screening takes place and thousands of
                  guests experience Nepal's first IMAX cinema.
                </p>
              </div>

            </div>
          </div>
        </section>

        {/* Leadership Team */}
        <section className="leadership_team_section">
          <div className="accent_line top"></div>

          <div className="leadership_team_header">
            <span className="about_section_eyebrow">
              Our Team
            </span>

            <h2 className="about_section_title">
              Foundational Pillars of <em>CinoSphere</em>
            </h2>
          </div>

          <div className="leadership_team_grid">

            <div className="leadership_team_card">
              <div className="leadership_team_avatar">
                <img src="/api/uploads/profiles/aditya" alt="Aditya Raut" />
              </div>

              <div className="leadership_team_name">
                Aditya Raut
              </div>

              <span className="leadership_team_role">
                Full Stack Developer
              </span>

              <p className="leadership_team_bio">
                CinoSphere database, UI/UX, frontend and technical documentation engineer.
              </p>
            </div>
            <div className="leadership_team_card">
              <div className="leadership_team_avatar">
                <img src="/api/uploads/profiles/5" alt="Raunit Giri" />
              </div>
              <div className="leadership_team_name">
                Raunit Giri
              </div>

              <span className="leadership_team_role">
                Backend Developer
              </span>

              <p className="leadership_team_bio">
                CinoSphere database, backend engineer.
              </p>
            </div>
            <div className="leadership_team_card">
              <div className="leadership_team_avatar">
                <img src="/api/uploads/profiles/milan" alt="Milan Lama" />
              </div>

              <div className="leadership_team_name">
                Milan lama
              </div>

              <span className="leadership_team_role">
                juinor Developer
              </span>

              <p className="leadership_team_bio">
                
              </p>
            </div>

          </div>

          <div className="accent_line bottom"></div>
        </section>

        {/* Locations */}
        <section className="locations_showcase_section">

          <div className="accent_line top"></div>

          <div className="locations_showcase_header">
            <span className="about_section_eyebrow">
              Where we are
            </span>

            <h2 className="about_section_title">
              Our <em>Locations</em>
            </h2>
          </div>

          <div className="locations_showcase_grid">

            <div className="location_card">
              <div className="location_card_background">
                <img src="/images/hall1.jpg" alt="Kathmandu" />
              </div>

              <div className="location_card_overlay"></div>

              <div className="location_card_content">

                <div className="location_status location_status_open">
                  Operational
                </div>

                <div className="location_name">
                  Kathmandu
                </div>

                <div className="location_address">
                  Labim Mall, 3rd Floor, Lalitpur
                </div>

                <div className="location_chips">
                  <span className="location_chip">4 Halls</span>
                  <span className="location_chip">IMAX Laser</span>
                  <span className="location_chip">800 Seats</span>
                  <span className="location_chip">Exclusive Lounge Access</span>
                </div>

              </div>
            </div>

          </div>

          <div className="accent_line bottom"></div>
        </section>

      </main>

      <Footer />
    </>
  )
}
