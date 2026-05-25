<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register | CinoSphere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
</head>

<body>

<jsp:include page="../components/header.jsp" />
        
<main class="registration_screen">
  <div class="authentication_layout_wrapper">
    
    <div class="authentication_left_column">
      <div class="authentication_background_overlay"></div>
      
      <div class="authentication_body_content">
          <div class="registration_information_block">
            <h2 class="authentication_hero_headline">Become a<br><em>Sphere Member</em></h2>
            <p class="authentication_hero_body">Join the Sphere circle to experience priority bookings, exclusive member rewards & unforgettable premiere nights.</p>
            
            <div class="authentication_feature_list">
              <div class="authentication_feature_item">
                <div class="authentication_feature_dot"></div>
                <span class="authentication_feature_text">Exclusive member rewards & special screenings</span>
              </div>
              <div class="authentication_feature_item">
                <div class="authentication_feature_dot"></div>
                <span class="authentication_feature_text">NPR 500 welcome credit on signup</span>
              </div>
              <div class="authentication_feature_item">
                <div class="authentication_feature_dot"></div>
                <span class="authentication_feature_text">Early access to premiere tickets</span>
              </div>
              <div class="authentication_feature_item">
                <div class="authentication_feature_dot"></div>
                <span class="authentication_feature_text">Earn points on every booking</span>
              </div>
            </div>
          </div>
        </div>
     </div>

    <div class="authentication_right_section">
      <div class="authentication_panel_container">
        
        <div class="registration_header_area">
          <h2 class="authentication_panel_title">Create <em>Account</em></h2>
          <p class="authentication_panel_subtitle">Join CinoSphere - Enter your details to get started</p>
        </div>

        <div class="glass_panel_container">
         <c:if test="${not empty error}">
		    <jsp:include page="../components/errorBox.jsp">
		        <jsp:param name="errorMessage" value="${error}" />
		    </jsp:include>
		</c:if>
          <form action="register" method="POST">
              <div class="form_row_double">
                <div class="form_group_wrapper">
                  <label class="form_label_text">First Name</label>
                  <input type="text" name="firstName" class="form_input_field" placeholder="Aditya" value="${firstName}" required>
                </div>
                <div class="form_group_wrapper">
                  <label class="form_label_text">Last Name</label>
                  <input type="text" name="lastName" class="form_input_field" placeholder="Raut" value="${lastName}" required>
                </div>
              </div>

              <div class="form_row_double">
			    <div class="form_group_wrapper">
			        <label class="form_label_text">Username</label>
			        <div class="form_input_wrapper">
			            <input type="text" name="username" class="form_input_field" placeholder="adir" value="${username}" required>
			            <span class="form_input_icon"> 
			                <img src="${pageContext.request.contextPath}/icon?name=user" alt="User" />
			            </span>
			        </div>
			    </div>
			
			    <div class="form_group_wrapper">
			        <label class="form_label_text">Email Address</label>
			        <div class="form_input_wrapper">
			            <input type="email" name="email" class="form_input_field" placeholder="aditya@gmail.com" value="${email}" required>
			            <span class="form_input_icon"> 
			                <img src="${pageContext.request.contextPath}/icon?name=mail" alt="Mail" />
			            </span>
			        </div>
			    </div>
			 </div>

              <div class="form_row_double">
                <div class="form_group_wrapper">
                  <label class="form_label_text">Gender</label>
                  <select name="gender" class="form_select_field" required>
                    <option value="" ${gender==null?'selected':''} disabled>Select</option>
                    <option value="male" ${gender=='male'?'selected':''}>Male</option>
                    <option value="female" ${gender=='female'?'selected':''}>Female</option>
                    <option value="other" ${gender=='other'?'selected':''}>Other</option>
                  </select>
                </div>
                <div class="form_group_wrapper">
                  <label class="form_label_text">Date of Birth</label>
                  <input type="date" name="dob" class="form_input_field" placeholder="DD/MM/YYYY" value="${dob}" required>
                </div>
              </div>

              <div class="form_group_wrapper">
                <label class="form_label_text">Membership Tier</label>
                <div class="membership_selector_row">
                  <label class="membership_option_card">
                  	<input type="radio" name="membership" value="starter" checked hidden>
                    <span class="membership_tier_name">Starter</span>
                    <span class="membership_price_label">Free</span>
                  </label>
                  <label class="membership_option_card">
                  	<input type="radio" name="membership" value="plus" hidden>
                    <span class="membership_tier_name">Plus</span>
                    <span class="membership_price_label">Rs 499</span>
                  </label>
                  <label class="membership_option_card">
                  	<input type="radio" name="membership" value="elite" hidden>
                    <span class="membership_tier_name">Elite</span>
                    <span class="membership_price_label">Rs 999</span>
                  </label>
                </div>
              </div>

              <div class="form_row_double">
                <div class="form_group_wrapper">
                  <label class="form_label_text">Password</label>
                  <div class="form_input_wrapper">
                    <input type="password" name="password" class="form_input_field" placeholder="••••••••" required>
                    <span class="form_input_icon"> 
		              <img src="${pageContext.request.contextPath}/icon?name=lock" alt="Lock" />
		            </span>
                  </div>
                </div>
                <div class="form_group_wrapper">
                  <label class="form_label_text">Confirm</label>
                  <div class="form_input_wrapper">
                    <input type="password" name="confirmPassword" class="form_input_field" placeholder="••••••••" required>
                    <span class="form_input_icon"> 
		              <img src="${pageContext.request.contextPath}/icon?name=checkmark" alt="Checkmark" />
		            </span>
                  </div>
                </div>
              </div>
              <button type="submit" class="primary_form_button_gold">Get Started</button>
          </form>
          <p class="authentication_switch_text">Already have an account? <a href="login.jsp">Sign in</a></p>
        </div>

        
      </div>
    </div>

  </div>
</main>

<jsp:include page="../components/footer.jsp" />

</body>
</html>