<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="movie_feature_film_card ${param.status == 'COMING_SOON' ? 'coming_soon_card' : ''}">
	
	<c:if test="${param.status == 'COMING_SOON'}">
		<div class="movie_release_header">
            <span class="release_date_text">${param.releaseDate}</span>
        </div>
    </c:if>

    <div class="movie_poster_visual_wrapper">
        <div class="movie_status_badge_group">
            <span class="movie_certification_badge">${param.ageRating}</span>
        </div>
        	<img src = "${pageContext.request.contextPath}/movieposter?name=${param.movieId}" alt="${param.movieName} poster" class="movie_poster_image_element"/>
        <div class="movie_poster_gradient_overlay"></div>
    </div>

    <div class="movie_information_panel">
        <h3 class="movie_title">${param.movieName}</h3>
        <p class="movie_description"> ${param.movieLanguage} | ${param.genre} <span>${param.duration} min</span> </p>
        <div class="movie_action_button_bar">
        	<c:choose>
                <c:when test="${param.status == 'NOW_SHOWING'}">
               		<a href="${pageContext.request.contextPath}/schedules" class="movie_booking_primary_button button">Book Now</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/#" class="movie_booking_primary_button button">Notify Me</a>
                </c:otherwise>
            </c:choose>

            <a href="${pageContext.request.contextPath}/moviedetail?movieId=${param.movieId}">
                <div class="movie_quick_view_icon_wrapper">
                    <img src="${pageContext.request.contextPath}/icon?name=info" alt="Info" />
                </div>
            </a>
        </div>
    </div>
</div>