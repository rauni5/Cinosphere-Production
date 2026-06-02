import './css/errorBox.css';
export default function ErrorBox({ message }) {
  if (!message) return null
  return (
    <div className="error_banner" role = "alert">
    <div className="error_banner_icon_container">
        <img src="null" alt="Warning" />
    </div>
    
    <div className="error_banner_text_group">
        <p className="error_banner_title"> {message}</p>
    </div>
</div>
  )
}