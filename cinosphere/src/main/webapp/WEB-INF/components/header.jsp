<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
</head>

<header class="navigation_header" id="mainHeader">
  <nav class="navigation_container">
    
    <a href="${pageContext.request.contextPath}/home" class="navigation_brand">
      <img src="${pageContext.request.contextPath}/logo?name=logo" alt="Logo" class="navigation_logo"/>
    </a>

    <ul class="navigation_menu">
      <li>
        <a href="${pageContext.request.contextPath}/movies" class="${activePage == 'movies' ? 'active' : ''}">Movies</a>
      </li>

      <li>
      	<a href="${pageContext.request.contextPath}/schedules" class="${activePage == 'schedules' ? 'active' : ''}">Schedules</a>
      </li>

      <li>
        <a href="${pageContext.request.contextPath}/aboutus" class="${activePage == 'about' ? 'active' : ''}">About Us</a>
      </li>

      <li>
        <a href="${pageContext.request.contextPath}/experience" class="${activePage == 'experience' ? 'active' : ''}">Experience</a>
      </li>
    </ul>
  
    <div class="navigation_actions">
    <c:if test="${empty user}">
      <a href="${pageContext.request.contextPath}/login" class="button outline_button">
        <img src="${pageContext.request.contextPath}/icon?name=user" alt="" class="navigation_icon" />
        <span class="button_text">Sign In</span>
      </a>
      <a href="${pageContext.request.contextPath}/register" class="button primary_button">
        <span class="button_text">Get Started</span>
        <img src="${pageContext.request.contextPath}/icon?name=rightarrow" alt="" class="navigation_icon" />
      </a>
      </c:if>
      <c:if test="${not empty user}">
      <a href="${pageContext.request.contextPath}/profile" class="button outline_button">
        <img src="${pageContext.request.contextPath}/icon?name=user" class="navigation_icon" />
        <span class="button_text">Profile</span>
      </a>
      <a href="${pageContext.request.contextPath}/logout" class="button primary_button">
        <span class="button_text">Logout</span>
        <img src="${pageContext.request.contextPath}/icon?name=rightarrow" class="navigation_icon" />
      </a>
      </c:if>
    </div>
  </nav>
</header>