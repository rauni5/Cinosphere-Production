import { Link } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
export default function NotFound() {
  return (
    <>
      <Header />
      <main style={{ minHeight: '60vh', display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', gap: '1.5rem', textAlign: 'center', padding: '4rem 2rem' }}>
        <h1 style={{ fontSize: 'clamp(5rem, 15vw, 10rem)', fontWeight: 900, color: 'var(--gold)', lineHeight: 1 }}>404</h1>
        <h2 style={{ fontSize: '1.75rem', color: 'var(--text-primary)' }}>This screen went dark.</h2>
        <p style={{ color: 'var(--text-muted)', maxWidth: '28rem' }}>
          The page you're looking for doesn't exist or has been moved. Head back to the lobby.
        </p>
        <Link to="/" className="primary_gold_button" style={{ display: 'inline-block', marginTop: '0.5rem' }}>
          Back to Home
        </Link>
      </main>
      <Footer />
    </>
  )
}