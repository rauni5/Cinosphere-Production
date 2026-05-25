<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile | CinoSphere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userPanel.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/updateProfile.css">
    
</head>

<body>

	<jsp:include page="../components/header.jsp" />
	
	<div class="panel_layout_wrapper">
	    <aside class="dashboard_sidebar">
	        <div class="sidebar_nav_container">
	            <nav class="sidebar_nav_menu">
	                <span class="sidebar_section_label">Main</span>
	                <a href="${pageContext.request.contextPath}/profile" 
               			class="sidebar_nav_item ${activeTab eq 'dashboard' ? 'active' : ''}">
	                    <div class="sidebar_icon_box">
	                        <span class="movie_select_arrow">
	                            <img src="${pageContext.request.contextPath}/icon?name=dashboard" alt="Dashboard" />
	                        </span>
	                    </div>
	                    <span class="sidebar_nav_label">Dashboard</span>
	                </a>
	                
	              
	                <span class="sidebar_section_label">Account</span>
	                 <a href="${pageContext.request.contextPath}/updateprofile" 
               			class="sidebar_nav_item ${activeTab eq 'updateprofile' ? 'active' : ''}">
	                    <div class="sidebar_icon_box">
	                        <span class="movie_select_arrow">
	                            <img src="${pageContext.request.contextPath}/icon?name=user" alt="Profile" />
	                        </span>
	                    </div>
	                    <span class="sidebar_nav_label">Profile Settings</span>
	                </a>
	            </nav>

	        </div>
	    </aside>
	
	
		<main class="profile_main_panel"> 
		
			<section class="profile_hero_section">
			    <div class="profile_hero_bg"></div>
			
			    <div class="profile_hero_content">
			        <div class="profile_hero_flex">
			            
			            <div class="profile_heading_block">
			                <h2 class="profile_hero_title">
			                    Update <em>Profile</em>
			                </h2>
			
			                <p class="profile_hero_subtitle">
			                    Personalize your account details, privacy settings and membership preferences
			                </p>
			            </div>
			
			        </div>
			    </div>
			</section>
		<form method="post" enctype="multipart/form-data">	
			<section class="update_profile_content_wrapper">
			    <div class="update_profile_overview_card">
			        <div class="update_profile_avatar_wrapper">
			            <div class="update_profile_avatar_circle">
			            <img id="imagePreview" src="${pageContext.request.contextPath}/profileimage?name=${user.userId}" alt="Preview"> 	                
			            </div>
			        
				        <label for="profileAvatarInput" class="update_profile_avatar_edit_button">
				                    <img src="${pageContext.request.contextPath}/icon?name=camera"
				                         alt="Upload Avatar">
				         </label>
	                	<input type="file" name="profileAvatarInput" id="profileAvatarInput" class="update_profile_avatar_input" accept="image/*">
	                </div>
			        <div class="update_profile_user_details">
			            <h3 class="update_profile_user_name">
			                ${user.firstName} ${user.lastName}
			            </h3>
			            <p class="update_profile_user_email">
			                ${user.email}
			            </p>
			            <div class="update_profile_badge_row">
			                <span class="update_profile_badge update_profile_badge_tier">
			                    ${membership.membershipType }
			                </span>
			                <span class="update_profile_badge update_profile_badge_points">
			                    ${membership.totalLoyaltyPoints}
			                </span>
			            </div>
			        </div>
			        <div class="update_profile_points_panel">
			            <div class="update_profile_points_value">
			                ${membership.totalLoyaltyPoints}
			            </div>
			            <div class="update_profile_points_label">
			                SPHERE POINTS
			            </div>
			            <div class="update_profile_progress_wrapper">
			                <div class="update_profile_progress_bar">
			                <fmt:formatNumber var="progress" value="${membership.totalLoyaltyPoints > 3000 ?100 :(membership.totalLoyaltyPoints*100.0)/3000}" maxFractionDigits="2"/>   
			                    <div class="update_profile_progress_fill" style="width:${progress}%"></div>
			                </div>
			                <div class="update_profile_progress_text" >
			       				  ${progress}% to Elite · ${3000-(membership.totalLoyaltyPoints)} pts needed
			                </div>
			            </div>
			        </div>
			    </div>
			</section>
			
			<section class="update_profile_form_grid">
			    <div class="update_profile_form_card">
			        <div class="update_profile_card_title">
			            <img src="${pageContext.request.contextPath}/icon?name=user"
			                 alt="User Icon">
			            <span>Personal Information</span>
			        </div>
			        <div class="update_profile_name_row">
			            <div class="update_profile_input_group">
			                <label class="update_profile_input_label" for="userFirstName">
			                    First Name
			                </label>
			                <input type="text"
			                       name="userFirstName"
			                       class="update_profile_input_field"
			                       value="${user.firstName}">
			            </div>
			            <div class="update_profile_input_group">
			                <label class="update_profile_input_label" for="userLastName">
			                    Last Name
			                </label>
			                <input type="text"
			                       name="userLastName"
			                       class="update_profile_input_field"
			                       value="${user.lastName}">
			            </div>
			        </div>
			
			        <div class="update_profile_input_group">
			            <label class="update_profile_input_label" for="userEmail">
			                Email Address
			            </label>
			            <div class="update_profile_input_group">
			                <input type="email"
			                       name="userEmail"
			                       class="update_profile_input_field"
			                       value="${user.email}">
			            </div>
			        </div>
			
			        <div class="update_profile_input_group">
			            <label class="update_profile_input_label" for="userDob">
			                Date of Birth
			            </label>
			            <input type="date"
			                   name="userDob"
			                   class="update_profile_input_field"
			                   value="${user.dateOfBirth}">
			        </div>
			    </div>
			    
			    <div class="update_profile_form_card">

				    <div class="update_profile_card_title">
				        <img src="${pageContext.request.contextPath}/icon?name=lock"
				             alt="Lock Icon">
				        <span>Change Password</span>
				    </div>
				
				    
				    <div class="update_profile_input_group">
				        <label class="update_profile_input_label" for="currentPassword">
				            Current Password
				        </label>
				
				        <div class="update_profile_input_wrapper">
				            <input type="password"
				                   name="currentPassword"
				                   class="update_profile_input_field">
				
				            <span class="update_profile_input_icon">
				                <img src="${pageContext.request.contextPath}/icon?name=lock"
				                     alt="Current Password Icon">
				            </span>
				        </div>
				    </div>
				
				   
				    <div class="update_profile_input_group">
				        <label class="update_profile_input_label" for="newPassword">
				            New Password
				        </label>
				
				        <div class="update_profile_input_wrapper">
				            <input type="password"
				                   name="newPassword"
				                   class="update_profile_input_field">
				
				            <span class="update_profile_input_icon">
				                <img src="${pageContext.request.contextPath}/icon?name=edit"
				                     alt="New Password Icon">
				            </span>
				        </div>
				    </div>
				
				   
				    <div class="update_profile_input_group">
				        <label class="update_profile_input_label" for="confirmPassword">
				            Confirm Password
				        </label>
				
				        <div class="update_profile_input_wrapper">
				            <input type="password"
				                   name="confirmPassword"
				                   class="update_profile_input_field">
				
				            <span class="update_profile_input_icon">
				                <img src="${pageContext.request.contextPath}/icon?name=checkmark"
				                     alt="Confirm Password Icon">
				            </span>
				        </div>
				    </div>
				
				    <button class="update_profile_button_primary" type="submit" formaction="${pageContext.request.contextPath}/updatepassword">
				        Update Password
				    </button>
				
				</div>
			</section>
			
			<div class="update_profile_footer_actions">
			    <button class="update_profile_button_primary save_all_btn" type="submit" formaction="${pageContext.request.contextPath}/updateprofile">
			        Save All Changes
			    </button>
			    <a href="${pageContext.request.contextPath}/profile">
			    <button class="update_profile_btn_ghost_cancel">Cancel</button>
			 </a>
			</div>
		</form>
			<section class="update_profile_danger_wrapper">
			    <div class="update_profile_form_card danger_outer_card">
			        
			        
			        <div class="update_profile_danger_zone_box">
			            <div class="update_profile_danger_header">
			                <img src="${pageContext.request.contextPath}/icon?name=warning" alt="Warning">
			                <span>Danger Zone</span>
			            </div>
			
			           
			            <div class="update_profile_danger_row">
			                <div class="update_profile_danger_text">
			                    <div class="update_profile_danger_label">Deactivate Account</div>
			                    <div class="update_profile_danger_desc">Temporarily disable your account. You can reactivate anytime by signing back in.</div>
			                </div>
			                <a href="${pageContext.request.contextPath}/deleteaccount">
			                <button class="update_profile_danger_btn_ghost">Deactivate</button>
			                </a>
			            </div>
			
			            
			            <div class="update_profile_danger_row">
			                <div class="update_profile_danger_text">
			                    <div class="update_profile_danger_label">Delete Account Permanently</div>
			                    <div class="update_profile_danger_desc">This will permanently delete all your data, bookings, and accumulated Sphere points.</div>
			                </div> <a href="${pageContext.request.contextPath}/deleteaccount">
			                <button class="update_profile_danger_btn_solid">Delete Account</button>
			                </a>
			            </div>	
			        </div>
			    </div>
			</section>
					
		</main>
 	</div>
	
     
	<jsp:include page="../components/footer.jsp" />
</body>
</html>
	