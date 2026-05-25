<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Experience| CinoSphere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contact.css">
</head>

<body>

    <jsp:include page="../components/header.jsp" />
    
    <main class="contact_screen"> 
    
    	<jsp:include page="../components/heroBanner.jsp">
		    <jsp:param name="titleMain" value="Stay" />
		    <jsp:param name="titleEm" value="Connected" />
		    <jsp:param name="subtitle" value="We’d love to hear from you, from booking assistance and partnership enquiries to simply saying hello." />
		</jsp:include>
    
	    <section class="contact_content_wrapper">
		  <div class="contact_page_grid_container">

		 
		  <div class="contact_form_column">
		
		    <div class="contact_form_panel_card">
		      <h3 class="form_panel_title">Send us a <em>Message</em></h3>
		      <p class="form_panel_description">
		        Have a question, feedback, or a corporate booking enquiry? Fill out the form below.
		      </p>
		
		      <div class="form_grid_two_columns">
		        <div class="form_input_group">
		          <label class="form_input_label">First Name</label>
		          <input type="text" class="form_text_field">
		        </div>
		
		        <div class="form_input_group">
		          <label class="form_input_label">Last Name</label>
		          <input type="text" class="form_text_field">
		        </div>
		      </div>
		
		      <div class="form_input_group">
		        <label class="form_input_label">Email</label>
		        <input type="email" class="form_text_field">
		      </div>
		
		      <div class="form_input_group">
		        <label class="form_input_label">Message</label>
		        <textarea class="form_text_area_field"></textarea>
		      </div>
		
		      <button class="form_submit_button_gold">Send Message</button>
		    </div>
		
		  </div>
		
		  
		  <div class="contact_right_column">
		
		   
		    <div class="contact_info_row">
		
		      <div class="info_card_item">
		        <span class="info_card_icon_wrapper icon_theme_gold">
		          <img src="${pageContext.request.contextPath}/icon?name=location">
		        </span>
		        <div>
		          <div class="info_card_label_text">Address</div>
		          <div class="info_card_main_value">Labim Mall, Pulchowk</div>
		        </div>
		      </div>
		
		      <div class="info_card_item">
		        <span class="info_card_icon_wrapper icon_theme_gold">
		          <img src="${pageContext.request.contextPath}/icon?name=phone">
		        </span>
		        <div>
		          <div class="info_card_label_text">Phone</div>
		          <div class="info_card_main_value">+977 01-5555-IMAX</div>
		        </div>
		      </div>
		
		    </div>
		
		   
		    <div class="contact_info_row">
		
		      <div class="info_card_item">
		        <span class="info_card_icon_wrapper icon_theme_red">
		          <img src="${pageContext.request.contextPath}/icon?name=mail">
		        </span>
		        <div>
		          <div class="info_card_label_text">Email</div>
		          <div class="info_card_main_value">hello@cinosphere.com.np</div>
		        </div>
		      </div>
		
		      <div class="info_card_item">
		        <span class="info_card_icon_wrapper icon_theme_gold">
		          <img src="${pageContext.request.contextPath}/icon?name=clock">
		        </span>
		        <div>
		          <div class="info_card_label_text">Box Office</div>
		          <div class="info_card_main_value">10AM – 10:30PM</div>
		        </div>
		      </div>
		
		    </div>
		
		     
		    <div class="faq_glass_container">
		
		      <h3 class="faq_main_title">Frequently Asked <em>Questions</em></h3>
		
		      <details class="faq_item">
		        <summary class="faq_question_row">Can I cancel booking?</summary>
		        <div class="faq_answer_row">
		          Yes, up to 2 hours before showtime.
		        </div>
		      </details>
		
		      <details class="faq_item">
		        <summary class="faq_question_row">What formats are supported?</summary>
		        <div class="faq_answer_row">
		          IMAX Laser, 3D, PLF, Digital.
		        </div>
		      </details>
		
		      <details class="faq_item">
		        <summary class="faq_question_row">Corporate bookings?</summary>
		        <div class="faq_answer_row">
		          Available for 20+ guests with custom packages.
		        </div>
		      </details>
		
		    </div>
		
		  </div>
		
		</div>
		</section>
	</main>
	    
	
	
    <jsp:include page="../components/footer.jsp" />

</body>

</html>