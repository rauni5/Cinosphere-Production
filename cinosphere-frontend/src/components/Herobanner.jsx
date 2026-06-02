import './css/heroBanner.css'

export default function HeroBanner({
  titleMain,
  titleEm,
  subtitle,
}) {
  return (
    <section className="hero_section">
      <div className="overlay"></div>

      <div className="main_content_container">
        <div className="hero_text_stack">

          <h1 className="hero_primary_title">
            {titleMain} <em>{titleEm}</em>
          </h1>

          <p className="hero_narrative_subtitle">
            {subtitle}
          </p>

        </div>
      </div>
    </section>
  )
}
