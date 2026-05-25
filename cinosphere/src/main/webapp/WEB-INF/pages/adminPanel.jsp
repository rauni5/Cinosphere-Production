<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Admin | CinoSphere</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/adminPanel.css">
</head>

<body>
	<c:set var="currentAdminPage" value="dashboard" scope="request" />

	<jsp:include page="../components/header.jsp" />

	<div class="admin_layout_container">
		<aside class="admin_sidebar_panel">
			<div class="admin_sidebar_inner">
				<nav class="admin_navigation_menu">
					<div class="admin_menu_group">
				        <span class="admin_navigation_label"> Main</span> 
				        <a href="${pageContext.request.contextPath}/admin"
				           class="admin_navigation_item ${currentAdminPage eq 'dashboard' ? 'active' : ''}">
				            <div class="admin_navigation_icon_box">
				                <img src="${pageContext.request.contextPath}/icon?name=dashboard" alt="Dashboard">
				            </div> 
				            <span class="admin_navigation_text"> Dashboard </span>
				        </a>
				    </div>

				    <div class="admin_menu_group">
				        <span class="admin_navigation_label"> Actions </span> 
				        <a href="${pageContext.request.contextPath}/addmovie"
				           class="admin_navigation_item ${currentAdminPage eq 'updatemovie' ? 'active' : ''}">
				            <div class="admin_navigation_icon_box">
				                <img src="${pageContext.request.contextPath}/icon?name=monitor" alt="Actions">
				            </div> 
				            <span class="admin_navigation_text"> Movies </span>
				        </a>
				    </div>
				</nav>

				<div class="admin_sidebar_footer">
					<div class="admin_profile_card">
						<div class="admin_profile_avatar">
							<img id="imagePreview"
								src="${pageContext.request.contextPath}/profileimage?name=${user.userId}"
								alt="Preview"> 
						</div>
						<div class="admin_profile_details">
							<span class="admin_profile_name"> ${user.firstName}
								${user.lastName} </span> <span class="admin_profile_role">
								${user.userRole} </span>
						</div>
					</div>
				</div>
			</div>
		</aside>


		<main class="admin_dashboard_panel">

			<section class="admin_dashboard_hero_section">
				<div class="admin_dashboard_hero_background"></div>
				<div class="admin_dashboard_hero_content">
					<div class="admin_dashboard_hero_layout">
						<div class="admin_dashboard_heading_block">
							<h1 class="admin_dashboard_heading_title">
								Welcome,<em>${user.firstName}</em>
							</h1>
							<p class="admin_dashboard_heading_subtitle">
								${today}
							</p>
						</div>
						<div class="admin_dashboard_action_group">
						<a href="${pageContext.request.contextPath}/addmovie">
							<button class="admin_dashboard_primary_button">
							<img src="${pageContext.request.contextPath}/icon?name=plus"
								alt="Add" /> Add Movie</button>
						</a>
						</div>
					</div>
				</div>
			</section>

			<c:if test="${not empty error}">
				    <jsp:include page="../components/errorBox.jsp">
				        <jsp:param name="errorMessage" value="${error}" />
				    </jsp:include>
			</c:if>
			<section class="admin_metrics_section">
				<div class="admin_metrics_grid">
					<div class="admin_metric_card">
						<span class="admin_metric_value admin_metric_gold">Rs ${revenueToday}</span>
						<span class="admin_metric_title">Revenue Today</span> <span
							class="admin_metric_change">${revenueChange} vs yesterday </span>
					</div>
					<div class="admin_metric_card">
						<span class="admin_metric_value admin_metric_gold"> ${ticketsSoldToday} </span> <span
							class="admin_metric_title"> Tickets Sold Today </span> <span
							class="admin_metric_change">${ticketsChange} vs yesterday </span>
					</div>

					<div class="admin_metric_card">
						<span class="admin_metric_value admin_metric_gold">  ${newMembersToday} </span> <span
							class="admin_metric_title"> New Members Today </span> <span
							class="admin_metric_change">${usersChange} vs yesterday </span>
					</div>
					<div class="admin_metric_card">
						<span class="admin_metric_value admin_metric_gold">${totalBooking==null?0:totalBooking}</span> <span
							class="admin_metric_title"> Total Bookings </span> <span
							class="admin_metric_change">
							${ticketsSoldToday==null?0:ticketsSoldToday} new booking</span>
					</div>
				</div>
			</section>


			<section class="admin_movie_management_section">
				<div class="admin_panel_card">
					<div class="admin_panel_header">
						<h3 class="admin_panel_title" id="movie_management">Movie Management</h3>
						<form action="${pageContext.request.contextPath}/admin"
							method="post">
							<div class="admin_panel_actions">
								<div class="admin_search_wrapper">
									<img
										src="${pageContext.request.contextPath}/icon?name=search"
										alt="Search" class="admin_search_icon" /> <input type="text"
										name="searchMovie" class="admin_search_input"
										placeholder="Search movies..." value="${searchmovie}"/>
								</div>
								<button class="admin_add_movie_button" type="submit">
									<img
										src="${pageContext.request.contextPath}/icon?name=search"
										alt="Search" />  Search
								</button>
							</div>
						</form>
					</div>
					<div class="admin_movie_tabs">
						<form action="${pageContext.request.contextPath}/admin"
							method="post" style="display: inline;">
							<input type="hidden" name="movieStatus" value="all">
							<button
								class="admin_movie_tab ${movieStatus=='all'||empty movieStatus?'active':''}"
								type="submit">All</button>
						</form>

						<form action="${pageContext.request.contextPath}/admin"
							method="post" style="display: inline;">
							<input type="hidden" name="movieStatus" value="NOW_SHOWING">
							<button
								class="admin_movie_tab ${movieStatus=='NOW_SHOWING'?'active':''}"
								type="submit">Showing</button>
						</form>

						<form action="${pageContext.request.contextPath}/admin"
							method="post" style="display: inline;">
							<input type="hidden" name="movieStatus" value="COMING_SOON">
							<button
								class="admin_movie_tab ${movieStatus=='COMING_SOON'?'active':''}"
								type="submit">Upcoming</button>
						</form>

						<form action="${pageContext.request.contextPath}/admin"
							method="post" style="display: inline;">
							<input type="hidden" name="movieStatus" value="ARCHIVE">
							<button
								class="admin_movie_tab ${movieStatus=='ARCHIVE'?'active':''}"
								type="submit">Archived</button>
						</form>
					</div>
					<div class="admin_movie_table_wrapper">
						<table class="admin_movie_table">
							<thead>
								<tr>
									<th>Film</th>
									<th>Release Date</th>
									<th>Duration</th>
									<th>Age Rating</th>
									<th>Status</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
							<c:if test="${not empty filteredMovies}">
								<c:forEach var="movie" items="${filteredMovies}">
									<tr>
										<td>
											<div class="admin_movie_info">
												<div class="admin_movie_poster">
													<img
														src = "${pageContext.request.contextPath}/movieposter?name=${movie.movieId}"
														alt="Michael" class="admin_movie_poster_image" />
												</div>
												<span class="admin_movie_name"> ${movie.movieName} </span>
											</div>
										</td>
										<td>${movie.releaseDate}</td>
										<td>${movie.duration}</td>
										<td>${movie.ageRating}</td>
										<td><span
											class="status_pill ${movie.movieStatus=='NOW_SHOWING'?'status_confirmed': movie.movieStatus=='COMING_SOON'?'status_upcoming':'status_archived'}">
												${movie.movieStatus} </span></td>
										<td>
											<div class="admin_movie_action_group">
											<a href="${pageContext.request.contextPath}/updatemovie?movieId=${movie.movieId}">
												<button class="admin_action_button edit">
													<img
														src="${pageContext.request.contextPath}/icon?name=edit"
														alt="Edit" /></button>
										</a>
										<a href="${pageContext.request.contextPath}/archivemovie?movieId=${movie.movieId}">
												<button class="admin_action_button delete">
													<img
														src="${pageContext.request.contextPath}/icon?name=delete"
														alt="del" /></button>
										</a>
											</div>
										</td>
									</tr>
								</c:forEach>
								</c:if>
							
							</tbody>
						</table>
							<c:if test="${empty error && empty filteredMovies }">
								    <jsp:include page="../components/errorBox.jsp">
								        <jsp:param name="errorMessage" value="No movie found" />
								    </jsp:include>
								</c:if>
					</div>
				</div>
			</section>

			<section id="user_management"class="admin_user_management_section">

				<div class="admin_panel_card">
					<div class="admin_panel_header">
						<h3 class="admin_panel_title">User Management</h3>
						<form action="${pageContext.request.contextPath}/admin"
							method="post">
							<div class="admin_panel_actions">

								<div class="admin_search_wrapper">
									<img
										src="${pageContext.request.contextPath}/icon?name=search"
										alt="Search" class="admin_search_icon" /> <input type="text"
										name="searchUser" class="admin_search_input"
										placeholder="Search users..." value="${searchUser }"/>
								</div>
								<button class="admin_add_movie_button" type="submit">
									<img
										src="${pageContext.request.contextPath}/icon?name=search"
										alt="Search" /> Search
								</button>

							</div>
						</form>
					</div>
					<div class="admin_movie_tabs">
						<form action="${pageContext.request.contextPath}/admin"
							method="post" style="display: inline;">
							<input type="hidden" name="userType" value="all">
							<button
								class="admin_movie_tab ${userType=='all'||empty userType?'active':''}"
								type="submit">All Users</button>
						</form>

						<form action="${pageContext.request.contextPath}/admin"
							method="post" style="display: inline;">
							<input type="hidden" name="userType" value="active">
							<button class="admin_movie_tab ${userType=='active'?'active':''}"
								type="submit">Active</button>
						</form>

						<form action="${pageContext.request.contextPath}/admin"
							method="post" style="display: inline;">
							<input type="hidden" name="userType" value="inactive">
							<button
								class="admin_movie_tab ${userType=='inactive'?'active':''}"
								type="submit">InActive</button>
						</form>
					</div>

					<div class="admin_movie_table_wrapper">
						<table class="admin_movie_table">
							<thead>
								<tr>
									<th>Name</th>
									<th>Email</th>
									<th>Role</th>
									<th>Tier</th>
									<th>Bookings</th>
									<th>Points</th>
									<th>Status</th>
									<th>Toggle</th>
								</tr>
							</thead>
							<tbody>

								<c:if test="${not empty userList}">
									<c:forEach var="user" items="${userList}" varStatus="loop">
										<c:set var="membership"
											value="${membershipList[loop.index]}" />
										<c:set var="booking"
											value="${bookingList[loop.index]}" />
										<tr>
											<td>
												<div class="admin_user_identity">
													<div class="admin_user_avatar">
														<img id="imagePreview"
															src="${pageContext.request.contextPath}/profileimage?name=${user.userId}"
															alt="Preview">
													</div>

													<span class="admin_user_name"> ${user.username} </span>
												</div>
											</td>
											<td class="admin_email_cell">${user.email}</td>
											<td class="admin_user_cell">${user.userRole}</td>
											<td><span class="admin_tier_badge elite">
													${membership.membershipType} </span></td>
											<td class="admin_booking_cell">${booking}</td>
											<td class="admin_points_cell">${membership.totalLoyaltyPoints}</td>
											<td><span
												class="status_pill ${user.isActive==true?'status_confirmed':'status_past suspended_status'}">
													${user.isActive==true?"ACTIVE":"INACTIVE"} </span></td>
											<td>
											    <form action="${pageContext.request.contextPath}/adminactivateaccount"
											          method="post">
											
											        <input type="hidden"
											               name="userId"
											               value="${user.userId}">
											
											        <input type="hidden"
											               name="currentStatus"
											               value="${user.isActive}">
											
											        <button type="submit"
											                class="admin_toggle_button">
											
											            <label class="admin_status_toggle">
											
											                <input type="checkbox" ${user.isActive ? "checked" : ""} disabled>
											
											                <span class="admin_toggle_slider"></span>
											
											            </label>
											
											        </button>
											
											    </form>
											</td>
										</tr>
									</c:forEach>
								</c:if>
								
							</tbody>
						</table>
						<c:if test="${empty userList && empty error}">
									<tr><td>
				    				<jsp:include page="../components/errorBox.jsp">
				        			<jsp:param name="errorMessage" value="No User" />
				    				</jsp:include>
									</td></tr>
								</c:if>
					</div>
				</div>
			</section>
			<section id="booking_management" class="admin_user_management_section">
			<div class="admin_panel_card">
				<div class="admin_panel_header">
					<h3 class="admin_panel_title">Booking Management</h3>
												<div class="admin_panel_actions">
								
								<a class="admin_add_movie_button button" href="${pageContext.request.contextPath}/updatebookingstatus">
									<img
										src="${pageContext.request.contextPath}/icon?name=search"
										alt="Search" />  Update
								</a>
							</div>
				</div>
				<div class="admin_movie_table_wrapper">
					<table class="admin_movie_table">
						<thead>
							<tr>
								<th>Id</th>
								<th>Movie</th>
								<th>Username</th>
								<th>Hall</th>
								<th>Date</th>
								<th>Time</th>
								<th>Seats</th>
								<th>Total Amount</th>
								<th>Points Earned</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty bookings}">
								<c:forEach items="${bookings}" varStatus="loop">
									<tr>
										<td>${bookings[loop.index]}</td>
										<td>${movieNames[loop.index]}</td>
										<td>${usernames[loop.index] }</td>
										<td>${screenNames[loop.index]}</td>
										<td>${showDates[loop.index]}</td>
										<td>${startTimes[loop.index]}</td>
										<td class="admin_email_cell">${seatLabels[loop.index]}</td>
										<td>Rs ${totalAmounts[loop.index]}</td>
										<td class="admin_points_cell">${totalPointsEarned[loop.index]}</td>
										<td><span
											class="status_pill ${bookingStatuses[loop.index]=='confirmed'?'status_confirmed':'status_archived'}">
												${bookingStatuses[loop.index]}
										</span></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
					<c:if test="${empty bookings && empty error}">
						<jsp:include page="../components/errorBox.jsp">
							<jsp:param name="errorMessage" value="No bookings found" />
						</jsp:include>
					</c:if>
				</div>
			</div>
		</section>
		</main>
	</div>


	<jsp:include page="../components/footer.jsp" />

</body>

</html>