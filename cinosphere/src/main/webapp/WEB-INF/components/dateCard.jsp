<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dateCard.css">
</head>

<button type="submit" name="selectedDate" value="${param.value}" class="schedules_date_pill ${param.active == 'true' ? 'active' : ''}">
    <span class="schedules_date_pill_day">${param.day}</span>
    <span class="schedules_date_pill_num">${param.number}</span>
    <span class="schedules_date_pill_month">${param.month}</span>
</button>