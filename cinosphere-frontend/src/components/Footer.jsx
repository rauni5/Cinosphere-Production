import { Link } from 'react-router-dom'
import './css/footer.css';
export default function Footer() {
  return (
    <footer className="footer_main">
      <div className="footer_container">
        <div className="footer_content">

          <div className="footer_brand">
            <span className="footer_logo_text">CinoSphere</span>
          </div>

          <div className="footer_navigation_group">
            <h4 className="footer_heading">Legal</h4>
            <ul className="footer_links">
              <li><a href="#">Terms of Service</a></li>
              <li><a href="#">Privacy Policy</a></li>
              <li><a href="#">Cookie Settings</a></li>
            </ul>
          </div>

          <div className="footer_navigation_group">
            <h4 className="footer_heading">Support</h4>
            <ul className="footer_links">
              <li><a href="#">Help Center</a></li>
              <li><a href="#">Refund Policy</a></li>
              <li><a href="#">IMAX Schedules</a></li>
            </ul>
          </div>

          <div className="footer_navigation_group">
            <h4 className="footer_heading">About Us</h4>
            <ul className="footer_links">
              <li><Link to="/about">IMAX Technology</Link></li>
              <li><Link to="/about">Cinema &amp; Screens</Link></li>
              <li><Link to="/about">Sound Systems</Link></li>
            </ul>
          </div>

          <div className="footer_navigation_group last_group">
            <div className="footer_information_bubble">
              <ul className="footer_links">
                <li><Link to="/contact">Contact Us</Link></li>
                <li><Link to="/contact">Corporate Office</Link></li>
              </ul>
              <div className="bubble_divider"></div>
              <div className="footer_social_links">
                <a href="#" className="social_link">✉</a>
                <a href="#" className="social_link">📷</a>
                <a href="#" className="social_link">🐦</a>
              </div>
            </div>
          </div>

        </div>
        <div className="footer_copyright">
          <p>&copy; 2026 CINOSPHERE, NEPAL. ALL RIGHTS RESERVED.</p>
        </div>
      </div>
    </footer>
  )
}