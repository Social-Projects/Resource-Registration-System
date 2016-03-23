<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<title>Access Denied</title>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">
</head>
<body>

	<div class="row" >
		<div class="col s5 m5 l5">		
			<img src="http://cdn.ttgtmedia.com/rms/computerweekly/45421_security-access-denied-padlock-key.jpg" 
				style="margin-left: 100px; margin-top: 150px;  height: 270px;"/>		
		</div>
	
		
		<div class="col s6 m6 l6 center-align" style="margin-top: 140px;">
			<h1 style="color:#bdbdbd; text-align: center;"><spring:message code="access.denied.header" /></h1>
			<p style=":background-color: #bdbdbd; color : grey; text-align: center"><spring:message code="access.denied.main" /></p>
			<a href="${pageContext.request.contextPath}/home" class="btn " ><spring:message code="access.denied.button" /></a>
		</div>
		
	</div>




</body>
</html>