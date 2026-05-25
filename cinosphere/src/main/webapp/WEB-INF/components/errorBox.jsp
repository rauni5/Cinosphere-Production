<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>  

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errorBox.css">
</head>

<div class="error_banner" role = "alert">
    <div class="error_banner_icon_container">
        <img src="${pageContext.request.contextPath}/icon?name=warning" alt="Warning" />
    </div>
    
    <div class="error_banner_text_group">
        <p class="error_banner_title"> ${param.errorMessage}</p>
    </div>
    
    <button class="error_banner_close_button">
        <img src="${pageContext.request.contextPath}/icon?name=close" alt="Close" />
    </button>
</div>