<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movies | CinoSphere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/movies.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/movieCard.css">
</head>

<body>

    <jsp:include page="../components/header.jsp" />
    
    <main class="movie_screen"> 
    
	    <jsp:include page="../components/heroBanner.jsp">
		    <jsp:param name="titleMain" value="What's" />
		    <jsp:param name="titleEm" value="Playing" />
		    <jsp:param name="subtitle" value="Storytelling under spectacle, explore the latest blockbusters now showing." />
		</jsp:include>
	    
	    <section class="movie_filter_section">
	    <form method="get" action="${pageContext.request.contextPath}/movies">
		    <div class="movie_filter_container">
		        <div class="movie_search_wrapper">
		            <span class="movie_search_icon_container">
		                <img src="${pageContext.request.contextPath}/icon?name=search" alt="Search" />
		            </span>
		            <input type="text" placeholder="Search movie names..." name="movieSearch" class="movie_search_input" value="${searchKeyword}">
		        </div>
		        <div class="movie_filter_dropdown_group">
		            <div class="movie_select_wrapper">
		                <select class="movie_filter_select" name="langFilter">
		                    <option value="">All Languages</option>
		                    <option value="english" ${selectedLanguage=="english"?"selected":""}>English</option>
		                    <option value="hindi" ${selectedLanguage=="hindi"?"selected":""}>Hindi</option>
		                    <option value="nepali" ${selectedLanguage=="nepali"?"selected":""}>Nepali</option>
		                </select>
		                <span class="movie_select_arrow">
		                    <img src="${pageContext.request.contextPath}/icon?name=arrowdown" alt="Arrow Down" />
		                </span>
		            </div>
		
		            <div class="movie_select_wrapper">
		                <select class="movie_filter_select" name="genreFilter">
		                    <option value="">All Genres</option>
		                    <option value="action" ${selectedGenre=="action"?"selected":""}>Action</option>
		                    <option value="drama" ${selectedGenre=="drama"?"selected":""}>Drama</option>
		                    <option value="comedy" ${selectedGenre=="comedy"?"selected":""}>Comedy</option>
			                <option value="sci-fi" ${selectedGenre=="sci-fi"?"selected":""}>Sci-Fi</option>
			                <option value="horror" ${selectedGenre=="horror"?"selected":""}>Horror</option>
			                <option value="biography" ${selectedGenre=="biography"?"selected":""}>Biography</option>
		                </select>
		                <span class="movie_select_arrow">
		                    <img src="${pageContext.request.contextPath}/icon?name=arrowdown" alt="Arrow Down" />
		                </span>
		            </div>
		            <div class="movie_select_wrapper">
		                <select class="movie_filter_select" name="status">
		                    <option value="all" ${selectedStatus=="all"|| empty selectedStatus?"selected":""}>All</option>
		                    <option value="NOW_SHOWING" ${selectedStatus=="NOW_SHOWING"?"selected":""}>Now Showing</option>
		                    <option value="COMING_SOON" ${selectedStatus=="COMING_SOON"?"selected":""}>Coming Soon</option>
		                </select>
		                <span class="movie_select_arrow">
		                    <img src="${pageContext.request.contextPath}/icon?name=arrowdown" alt="Arrow Down" />
		                </span>
		            </div>
		             <div class="movie_select_wrapper">
		            <button type="submit" class="movie_filter_pill">APPLY</button>
		            </div>
		        </div>
		    </div>
		    
		  </form>
		</section>
			<section class="movie_section">
				<div class="movie_main_content_container">
						 <c:if test="${not empty error}">
				    <jsp:include page="../components/errorBox.jsp">
				        <jsp:param name="errorMessage" value="${error}" />
				    </jsp:include>
				</c:if>
				<c:if test="${not empty filteredMovies}">
					<div class="movie_cards_presentation_grid">
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
			     </c:if>	
			    </div>
			</section>
		
		<c:if test="${empty filteredMovies && empty error}">
            <section class="movie_section">
                <div class="movie_main_content_container">
                    <p class="movie_description" style="color:red;font-size:1rem;">No movies match your current filters</p>
                </div>
            </section>
        </c:if>    
	</main>
    <jsp:include page="../components/footer.jsp" />

</body>

</html>