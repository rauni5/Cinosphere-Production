<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | CinoSphere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>

<body>

<jsp:include page="../components/header.jsp" />
    
<main class="login_screen">
    <div class="authentication_container">
      <div class="authentication_split_layout">
        
        <section class="authentication_visual_side">
          <div class="visual_overlay"></div>
          
          <div class="visual_content_wrapper">
            <div class="brand_narrative">
              <h2 class="hero_display_title">The screen<br /><em>missed you</em></h2>
              <p class="hero_display_subtitle">Sign back in back to manage your bookings, enjoy membership perks & never miss a premiere.</p>
              <div class="feature_perk_list">
                <div class="perk_item">
                  <span class="perk_dot"></span>
                  <span class="perk_label">Upcoming booking reminders</span>
                </div>
                <div class="perk_item">
                  <span class="perk_dot"></span>
                  <span class="perk_label">Loyalty points & Sphere credits</span>
                </div>
                <div class="perk_item">
                  <span class="perk_dot"></span>
                  <span class="perk_label">Priority IMAX seat selection</span>
                </div>
              </div>
            </div>
          </div>
        </section>

        <section class="authentication_form_side">
		  <div class="control_panel">
		    
		    <header class="panel_header">
		      <h2 class="panel_title">Log <em>In</em></h2>
		      <p class="panel_subtitle">Enter your credentials to continue</p>
		    </header>
		    <div class="glass_card">
		    <c:if test="${not empty error}">
				    <jsp:include page="../components/errorBox.jsp">
				        <jsp:param name="errorMessage" value="${error}" />
				    </jsp:include>
				</c:if> 
		      <form action="${pageContext.request.contextPath}/login" method="POST" class="form_standard">
		        
		        <div class="form_group">
		        
		          <label class="field_label">Username</label>
		          <div class="input_field_wrapper">
		            <input type="username" name="username" class="input_control" placeholder="Emilio" value="${typedUser}" required />
		            <span class="input_icon"> 
		              <img src="${pageContext.request.contextPath}/icon?name=user" alt="User" />
		            </span>
		          </div>
		        </div>
		        
		        <div class="form_group">
		            <label class="field_label">Password</label>
		          <div class="input_field_wrapper">
		            <input type="password" name="password" class="input_control" placeholder="••••••••" required />
		            <span class="input_icon"> 
		              <img src="${pageContext.request.contextPath}/icon?name=lock" alt="Lock" />
		            </span>
		          </div>
		           <a href="#" class="link_muted_small forgot_link">Forgot Password?</a>
		        </div>
		        
		        <button type="submit" class="primary_gold_button">Sign In</button>
		      </form>
		    
		      <div class="panel_footer">
		        <p class="switch_context_text"> New to CinoSphere?
		          <a href="${pageContext.request.contextPath}/register" class="gold_link">Create an account</a>
		        </p>
		      </div>
		    </div>
		  </div>
		</section>

      </div>
    </div>
</main>

<jsp:include page="../components/footer.jsp" />

</body>
</html>