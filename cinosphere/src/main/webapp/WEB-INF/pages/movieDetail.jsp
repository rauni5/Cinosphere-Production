<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>${movie.movieName} | CinoSphere</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/movieDetail.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/movieCard.css">
</head>

<body>

	<jsp:include page="../components/header.jsp" />
	
	<main class ="movieDetail_screen">
	
		<div class="detail_hero">
		
			<div class="detail_hero_background">
		       <img src="${pageContext.request.contextPath}/background?name=${movie.movieId}"  alt="${movie.movieName} Poster" class="detail_hero_background_poster"/>
		 	</div>

		  <div class="detail_hero_content">
		    <div class="detail_poster">
		      <img src="${pageContext.request.contextPath}/movieposter?name=${movie.movieId}"  alt="${movie.movieName} Poster" class="detail_poster_image"/>
		      <span class="detail_certification">${movie.ageRating}</span>
		    </div>
		
		    <div class="detail_info">
		      <div class="detail_description_row">
		      	<span class="detail_pill">${movie.genre}</span>
		        <span class="detail_pill">${movie.movieLanguage}</span>
		        <span class="detail_pill">${movie.duration} min</span>
		      </div>
		
		      <h1 class="detail_title"> ${movie.movieName}</h1>
		    </div>
		  </div>
		
		</div>
		
		<div class="detail_body">
			<div class="detail_about_section">
				<h2 class="detail_section_title"> About this <em>Movie</em> </h2>
				<p class="detail_description"> ${movie.description}</p>
				
		        <div class="detail_meta_section">
		            <div class="detail_meta_grid">
		                <div class="detail_meta_item">
		                    <div class="detail_meta_label">Director</div>
		
		                    <div class="detail_meta_value">${movie.director}</div>
		                </div>
		                <div class="detail_meta_item">
		                    <div class="detail_meta_label"> Release Date </div>
		                    <div class="detail_meta_value"> ${movie.releaseDate} </div>
		                </div>
		            </div>
		        </div>
		        
    		</div>
    	</div>
    	
    	<section class="detail_related_section">

		    <div class="detail_related_header">
		
		        <div class="detail_related_header_left">
		
		            <span class="detail_section_eyebrow"> You May Also Like </span>
		            <h2 class="detail_section_title"> More <em>Films</em> </h2>
		
		        </div>
		
		        <a href="${pageContext.request.contextPath}/movies" class="detail_view_all_link">
		            <span class="detail_view_all_text"> View All</span>
		
		            <div class="detail_view_all_icon_wrapper">
		                <img src="${pageContext.request.contextPath}/icon?name=rightarrow" alt="View All" class="detail_view_all_icon"/>
		            </div>
		
		        </a>
		
		    </div>
		    <div class="detail_related_grid">
		        <c:forEach var="movie" items="${filteredMovies}">
		
		            <jsp:include page="../components/movieCard.jsp">
		                <jsp:param name="movieId" value="${movie.movieId}" />
		                <jsp:param name="movieName" value="${movie.movieName}" />
		                <jsp:param name="movieLanguage" value="${movie.movieLanguage}" />
		                <jsp:param name="genre" value="${movie.genre}" />
		                <jsp:param name="duration" value="${movie.duration}" />
		                <jsp:param name="ageRating" value="${movie.ageRating}" />
		                <jsp:param name="releaseDate" value="${movie.releaseDate}" />
		                <jsp:param name="status" value="${movie.movieStatus}" />
		            </jsp:include>
		
		        </c:forEach>
		
		    </div>
		
		</section>
	
	</main>
	
	<jsp:include page="../components/footer.jsp" />

</body>

</html>