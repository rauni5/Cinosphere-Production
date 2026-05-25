<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>  

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/heroBanner.css">
</head>

<section class="hero_section">	
	<div class="overlay"></div>
		<div class="main_content_container">
			<div class="hero_text_stack">
				<h1 class="hero_primary_title">${param.titleMain} <em>${param.titleEm}</em>
				<p class="hero_narrative_subtitle"> ${param.subtitle}</p>
	         </div>
	    </div>
</section>