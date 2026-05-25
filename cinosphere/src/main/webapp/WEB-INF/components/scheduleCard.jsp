<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/scheduleCard.css">
</head>


<div class="schedules_row">
    <div class="schedules_row_movie">
        <div class="schedules_row_poster">
        	<img src="${pageContext.request.contextPath}/movieposter?name=${param.movieId}" alt="${param.movieName} poster" class="movie_poster_image_element"/>
        </div>
        <div class="schedules_row__information">
            <div class="schedules_row_title">${param.movieName}</div>
            <div class = "description_group">
	            <div class="schedules_row_description"> ${param.language} · ${param.genre}</div>
            </div>
            <div class="schedule_row_badges">
                <span class="age_rating_badge">${param.ageRating}</span>
                <span class="format_badge">${param.duration} min</span>
            </div>
        </div>
    </div>

	<div class="schedules_row_times">
	    <c:forEach var="hallBlock" items="${fn:split(param.halls, ';')}">
	        <c:set var="cleanBlock" value="${fn:trim(hallBlock)}" />
	        <c:set var="parts"    value="${fn:split(cleanBlock, '|')}" />
	        <c:set var="hallName" value="${parts[0]}" />
	        <c:set var="times" value="${parts[1]}" />
	        <c:set var="screenId" value="${parts[2]}" />
	        <div class="time_hall_block">
	            <div class="time_hall_label">${hallName}</div>
	                <div class="times_slots">
	                    <c:forEach var="slot" items="${fn:split(times, ',')}">
                    	<c:set var="slotParts"   value="${fn:split(slot, '.')}" />
                    	<c:set var="time" value="${slotParts[0]}" />
                    	<c:set var="showtimeId"  value="${slotParts[1]}" />
                    	<form method="get" action="${pageContext.request.contextPath}/booking">
                        <input type="hidden" name="movieId"    value="${param.movieId}"/>
                        <input type="hidden" name="showtimeId" value="${showtimeId}"/>
                        <input type="hidden" name="screenId"   value="${screenId}"/>
                        <input type="hidden" name="selectedDate" value="${param.date}"/>
	                        <button type="submit" name="selectedTime" value="${time}" class="time_slot">
	                            <div class="time_slot_times">${time}</div>
	                        </button>
	                     </form>
	                     </c:forEach>
	           		</div>
	        </div>
	    </c:forEach>
	</div>
</div>