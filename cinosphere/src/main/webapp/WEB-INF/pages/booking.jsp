<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Booking | CinoSphere</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/booking.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/seat.css">
	
</head>

<body>

	<jsp:include page="../components/header.jsp" />
	
	<main class ="booking_screen">
	
		<section class="booking_left_section">
			<div class="movie_card">
			    <div class="movie_card_poster">
			    	<img src="${pageContext.request.contextPath}/movieposter?name=${movie.movieId}"  alt="${movie.movieName} Poster" class="movie_poster_image"/>
			    </div>
			
			    <div class="movie_card_information">
			        <div class="movie_card_title">${movie.movieName}</div>
			        <div class="movie_card_description">${time} · ${date} · ${hallName} </div>
			        <div class="movie_card_badge_group">
			            <span class="movie_card_badge movie_card_badge_gold">${movie.ageRating}</span>
			            <span class="movie_card_badge movie_card_badge_neutral">${movie.duration} min</span>
			        </div>
			    </div>
			
			    <a href="${pageContext.request.contextPath}/schedules" class="movie_card_button"> Change Show
			        <img src="${pageContext.request.contextPath}/icon?name=rightarrow" alt="Arrow Right" class="movie_card_button_icon"/>
			    </a>
				</div>
				
				<div class="seat_selection_header">

				    <div class="seat_selection_heading">
				        <span class="section_eyebrow">Select Your Seats</span>
				        <h2 class="seat_selection_title"> ${hallName} </h2>
				    </div>
		
					<div class="seat_indicator_bar">
						<div class="indicator_item">
					        <div class="overlay overlay_available"></div>
					        <span class="label">Available</span>
					    </div>				
					    <div class="indicator_item">
					        <div class="overlay overlay_taken"></div>
					        <span class="label">Taken</span>
					    </div>
					    <div class="indicator_item">
					    	<div class="overlay overlay_reserved"></div>
					    	<span class="label">Selected</span>
					    </div>
					</div>
				</div>
				
				<div class="screen_label_wrap">
				    <div class="screen_bar"></div>
				    <div class="screen_bar_glow"></div>
				    <div class="screen_text">Screen this way</div>
				</div>
				<form method="get" action="${pageContext.request.contextPath}/booking" id="seatForm" class="seat_form">
            	<input type="hidden" name="movieId"     value="${movieId}"/>
            	<input type="hidden" name="showtimeId"  value="${showtimeId}"/>
            	<input type="hidden" name="screenId"    value="${screenId}"/>
            	<input type="hidden" name="useSpherePoints" value="${useSpherePoints}"/>
            <div class="seat_map_wrap">
                <div class="seat_map">
                    <c:set var="stdLabelShown"  value="false"/>
                    <c:set var="premLabelShown" value="false"/>
                    <c:set var="vipLabelShown"  value="false"/>

                    <c:forEach var="seat" items="${seats}">
                        <c:set var="seatNum"    value="${seat.seatNumber}"/>
                        <c:set var="isTaken"    value="${takenSeatIds.contains(seat.seatId)}"/>
                        <c:set var="isReserved" value="${reservedSeatIds.contains(seat.seatId)}"/>
                        <c:set var="isChecked"  value="${checkedSeatIds.contains(seat.seatId)}"/>

                        <c:if test="${stdLabelShown == 'false' and seatNum <= 90}">
                            <div class="section_label_row">
                                <span class="section_label_text">Standard</span>
                                <div class="section_label_line"></div>
                            </div>
                            <c:set var="stdLabelShown" value="true"/>
                        </c:if>
                        <c:if test="${premLabelShown == 'false' and seatNum > 90 and seatNum <= 180}">
                            <c:if test="${prevRow != ''}"></div></c:if>
                            <c:set var="prevRow" value=""/>
                            <div class="section_label_row">
                                <span class="section_label_text">Premium</span>
                                <div class="section_label_line"></div>
                            </div>
                            <c:set var="premLabelShown" value="true"/>
                        </c:if>
                        <c:if test="${vipLabelShown == 'false' and seatNum > 180}">
                            <c:if test="${prevRow != ''}"></div></c:if>
                            <c:set var="prevRow" value=""/>
                            <div class="section_label_row">
                                <span class="section_label_text">VIP</span>
                                <div class="section_label_line"></div>
                            </div>
                            <c:set var="vipLabelShown" value="true"/>
                        </c:if>

                        <c:if test="${seat.rowNumber != prevRow}">
                    <c:if test="${prevRow != ''}"></div></c:if>
                    <div class="seat_row">
                        <span class="seat_row_label">${seat.rowNumber}</span>
                    <c:set var="prevRow"   value="${seat.rowNumber}"/>
                    <c:set var="colInRow"  value="0"/>
                </c:if>
                <c:set var="colInRow" value="${colInRow + 1}"/>
                <c:if test="${colInRow == 4 or colInRow == 14}">
                    <div class="seat_aisle"></div>
                </c:if>
                <c:choose>
                    <c:when test="${isTaken}">
                        <div class="seat seat_taken"
                             title="${seat.rowNumber}${colInRow} — Taken">
                            <span class="seat_visual">
                                <span class="seat_number">${colInRow}</span>
                            </span>
                        </div>
                    </c:when>
                    <c:when test="${isReserved}">
                        <div class="seat seat_reserved"
                             title="${seat.rowNumber}${colInRow} — Reserved">
                            <span class="seat_visual">
                                <span class="seat_number">${colInRow}</span>
                            </span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <label class="seat seat_available ${isChecked ? 'seat_selected' : ''}"
                               title="${seat.rowNumber}${colInRow}">
                            <input type="checkbox"
                                   name="selectedSeats"
                                   value="${seat.seatId}"
                                   class="seat_checkbox"
                                   ${isChecked ? 'checked' : ''}/>
                            <span class="seat_visual">
                                <span class="seat_number">${colInRow}</span>
                            </span>
                        </label>
                    </c:otherwise>
                </c:choose>
                    </c:forEach>
                    <c:if test="${not empty seats}"></div></c:if>
                </div>
            </div>
            <div class="seat_update_bar">
                <button type="submit" class="seat_update_button">Update Selection</button>
            </div>
        </form>
				
		</section>
		
		<aside class="checkout_panel">
		
			<div class="checkout_header">
				<span class="section_eyebrow"> Your Order </span>
				<h3 class="checkout_title"> Booking <em>Summary</em></h3>
				<div class="checkout_line"></div>
			</div>
			
			<div class="checkout_body">	
					    <c:if test="${not empty error}">
				    <jsp:include page="../components/errorBox.jsp">
				        <jsp:param name="errorMessage" value="${error}" />
				    </jsp:include>
				</c:if> 
				<div class="checkout_section_label"> Selected Seats (<span><c:out value="${seatCount != null ? seatCount : 0}" /></span>)</div>
        	
	        	<div class="selected_seats_area">
	        		<c:choose>
	        		
	        			<c:when test="${not empty selectedSeatLabels}">
	        				<c:forEach var="seat" items="${selectedSeatLabels}">
	                        	<div class="selected_seat_chip"><span>${seat}</span></div>
	                    	</c:forEach>
	                	</c:when>
	                	
		                <c:otherwise>
		                    <div class="no_seats_notifier"> Tap seats on the map then click Update Selection</div>
		                </c:otherwise>
	            	
	            	</c:choose>
	        	</div>
        			
        		<div class="checkout_section_label">Sphere Points</div>
				<div class="sphere_row">
				    <div class="sphere_display">
				        <div class="sphere_title">Use Sphere Points</div>
				        <div class="sphere_meta"> ${spherePoints} Pts · ${sphereDiscountPct}% discount </div>
				    </div>
				    <form method="get" action="${pageContext.request.contextPath}/booking" class="sphere_toggle_form">
                        <input type="hidden" name="movieId"         value="${movieId}"/>
            			<input type="hidden" name="showtimeId"      value="${showtimeId}"/>
            			<input type="hidden" name="screenId"    value="${screenId}"/>
                        <c:forEach var="sid" items="${checkedSeatIds}">
                            <input type="hidden" name="selectedSeats" value="${sid}"/>
                        </c:forEach>
                        <input type="hidden" name="useSpherePoints" value="${useSpherePoints ? 'false' : 'true'}"/>
                        <button type="submit" class="sphere_button">${useSpherePoints ? 'Remove' : 'Apply'}</button>
                    </form>
				</div>        	
							
				<div class="checkout_section_label">Checkout Summary</div>
				<div class="order_lines">
                <c:if test="${stdCount > 0}">
                    <div class="order_line">
                        <span class="order_line_label">Standard (${stdCount} x Rs.${stdPrice})</span>
                        <span class="order_line_value">Rs.${stdSub}</span>
                    </div>
                </c:if>
                <c:if test="${premCount > 0}">
                    <div class="order_line">
                        <span class="order_line_label">Premium (${premCount} x Rs.${premPrice})</span>
                        <span class="order_line_value">Rs.${premSub}</span>
                    </div>
                </c:if>
                <c:if test="${vipCount > 0}">
                    <div class="order_line">
                        <span class="order_line_label">VIP (${vipCount} x Rs.${vipPrice})</span>
                        <span class="order_line_value">Rs.${vipSub}</span>
                    </div>
                </c:if>
                <c:if test="${seatCount == 0}">
                    <div class="order_line">
                        <span class="order_line_label">Tickets</span>
                        <span class="order_line_value">Rs.0</span>
                    </div>
                </c:if>
                <c:if test="${useSpherePoints and discountAmt > 0}">
                    <div class="order_line order_line_discount">
                        <span class="order_line_label">Sphere Credits (${discountPct}%)</span>
                        <span class="order_line_value">-Rs.${discountAmt}</span>
                    </div>
                </c:if>
                <div class="order_line order_line_total">
                    <span class="order_line_label">Total</span>
                    <span class="order_line_value">Rs.${totalAmount}</span>
                </div>
            </div>	
				<form method="post" action="${pageContext.request.contextPath}/booking" id="checkoutForm">
                <input type="hidden" name="movieId"         value="${movieId}"/>
            	<input type="hidden" name="showtimeId"      value="${showtimeId}"/>
            	<input type="hidden" name="screenId"    value="${screenId}"/>
            	<input type="hidden" name="useSpherePoints" value="${useSpherePoints}"/>
                <input type="hidden" name="totalAmount"     value="${totalAmount}"/>
                <c:forEach var="sid" items="${checkedSeatIds}">
                    <input type="hidden" name="selectedSeats" value="${sid}"/>
                </c:forEach>

                <div class="payment_section">
                    <div class="checkout_section_label">Payment Method</div>
                    <div class="payment_methods">
                        <label class="payment_method">
                            <input type="radio" name="paymentMethod" value="esewa" class="payment_input"/>
                            <div class="payment_icon">
                                <img src="${pageContext.request.contextPath}/icon?name=esewa" alt="eSewa" class="payment_icon_image"/>
                            </div>
                            <div class="payment_info"><div class="payment_name">eSewa</div></div>
                        </label>
                        <label class="payment_method">
                            <input type="radio" name="paymentMethod" value="khalti" class="payment_input"/>
                            <div class="payment_icon">
                                <img src="${pageContext.request.contextPath}/icon?name=khalti" alt="Khalti" class="payment_icon_image"/>
                            </div>
                            <div class="payment_info"><div class="payment_name">Khalti</div></div>
                        </label>
                        <label class="payment_method">
                            <input type="radio" name="paymentMethod" value="fonepay" class="payment_input"/>
                            <div class="payment_icon">
                                <img src="${pageContext.request.contextPath}/icon?name=fonepay" alt="Fonepay" class="payment_icon_image"/>
                            </div>
                            <div class="payment_info"><div class="payment_name">Fonepay</div></div>
                        </label>
                    </div>
                </div>
                <div class="checkout_footer">
                    <div class="checkout_footer_line"></div>
                    <c:choose>
                        <c:when test="${seatCount > 0}">
                            <button class="checkout_button" type="submit">Proceed to Payment</button>
                        </c:when>
                        <c:otherwise>
                            <button class="checkout_button checkout_button_disabled" type="submit" disabled>
                                Select Seats to Continue
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
			</div>
		
		</aside>
	</main>
	
	<jsp:include page="../components/footer.jsp" />

</body>

</html>