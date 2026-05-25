<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 Page Not Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errorPage.css">
</head>

<body>

    <jsp:include page="../components/header.jsp" />
    
    <main class="error_screen"> 
    
    	<div class="error_background"></div> 
        <div class="error_wrapper">
        
          <div class="error_code_wrapper">
            <div class="error_code_glow"></div>
            <span class="error_code">404</span>
          </div>
          
          <div class="error_eyebrow">
            <div class="eyebrow_line eyebrow_line_left"></div>
            <span class="eyebrow_text">Page Not Found</span>
            <div class="eyebrow_line eyebrow_line_right"></div>
          </div>
          
          <h1 class="error_title">This page cannot<em> be found.</em></h1>
          <p class="error_subtitle">The page you're looking for doesn't exist, has been moved, or the URL was mistyped!</p>
    
    	  <div class="error_actions">
            <a href="${pageContext.request.contextPath}/home" class="button_hero_primary">Back to Home</a>
          </div>
          
        </div>
   
   
    	<jsp:include page="../components/footer.jsp" />
    
     </main>

</body>
</html>