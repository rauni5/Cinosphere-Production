import './css/errorBox.css';
export default function ErrorBox({ message }) {
  if (!message) return null
  return (
    <div class="error_banner" role = "alert">
    <div class="error_banner_icon_container">
        <img src="" alt="Warning" />
    </div>
    
    <div class="error_banner_text_group">
        <p class="error_banner_title"> {message}</p>
    </div>
</div>
  )
}