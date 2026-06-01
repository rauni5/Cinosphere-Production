import './css/heroBanner.css';
export default function HeroBanner({ titleMain, titleEm, subtitle }) {
  return (
    <section className="hero_banner_section">
      <div className="hero_banner_overlay"></div>
      <div className="hero_banner_content">
        <h1 className="hero_banner_title">
          {titleMain} <em>{titleEm}</em>
        </h1>
        <p className="hero_banner_subtitle">{subtitle}</p>
      </div>
    </section>
  )
}