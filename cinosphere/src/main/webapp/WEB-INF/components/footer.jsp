<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>

<footer class="footer_main">
    <div class="footer_container">
        <div class="footer_content">
            <div class="footer_brand">
                <img src="${pageContext.request.contextPath}/logo?name=logo" alt="CinoSphere" class="footer_logo">
            </div>
            <div class="footer_navigation_group">
                <h4 class="footer_heading">Legal</h4>
                <ul class="footer_links">
                    <li><a href="#">Terms of Service</a></li>
                    <li><a href="#">Privacy Policy</a></li>
                    <li><a href="#">Cookie Settings</a></li>
                </ul>
            </div>
            <div class="footer_navigation_group">
                <h4 class="footer_heading">Support</h4>
                <ul class="footer_links">
                    <li><a href="#">Help Center</a></li>
                    <li><a href="#">Refund Policy</a></li>
                    <li><a href="#">IMAX Schedules</a></li>
                </ul>
            </div>
            <div class="footer_navigation_group">
                <h4 class="footer_heading">About Us</h4>
                <ul class="footer_links">
                    <li><a href="${pageContext.request.contextPath}/aboutus">IMAX Technology</a></li>
                    <li><a href="${pageContext.request.contextPath}/aboutus">Cinema & Screens</a></li>
                    <li><a href="${pageContext.request.contextPath}/aboutus">Sound Systems</a></li>
                </ul>
            </div>
            <div class="footer_navigation_group last_group">
			    <div class="footer_information_bubble">
			        <ul class="footer_links">
			            <li><a href="${pageContext.request.contextPath}/experience">Contact Us</a></li>
			            <li><a href="${pageContext.request.contextPath}/experience">Corporate Office</a></li>
			        </ul>
	        		<div class="bubble_divider"></div>
			        <div class="footer_social_links">
			            <a href="#" class="social_link"><img src="${pageContext.request.contextPath}/icon?name=mail" alt="Mail"></a>
			            <a href="#" class="social_link"><img src="${pageContext.request.contextPath}/icon?name=instagram" alt="Instagram"></a>
			            <a href="#" class="social_link"><img src="${pageContext.request.contextPath}/icon?name=twitter" alt="Twitter"></a>
			        </div>
    			</div>
			</div>
        </div>
        <div class="footer_copyright">
            <p>&copy; 2026 CINOSPHERE, NEPAL. ALL RIGHTS RESERVED.</p>
        </div>
    </div>
</footer>