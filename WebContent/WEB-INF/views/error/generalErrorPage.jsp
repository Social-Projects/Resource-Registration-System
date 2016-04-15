<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message>label.errorPage.title</spring:message></title>

<link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">
</head>
<body>
	<div class="alert alert-danger">
		<h3>
			<spring:message code="label.errorPage.header" />
		</h3>
		<p>
			<spring:message code="label.errorPage.text" />
		</p>
	</div>
	<a href="${pageContext.request.contextPath}/" class="btn ">
	   <spring:message code="label.errorPage.homeButton" />
	</a>
</body>
</html>

<!--
Failed URI: ${requestUri}
Exception:  ${errorMessage}
<c:forEach items="${exception.stackTrace}" var="ste">    ${ste}
</c:forEach>
-->