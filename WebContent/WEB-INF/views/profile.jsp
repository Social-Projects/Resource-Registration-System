<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Profile</title>
</head>
<body>
	<h1>Your Profile</h1>
	<p>HELLO ;)
	<p>YOU'RE SUCCESSFULLY LOGGED IN!!!!</p>
	<c:out value="${spitter.username}" />
	<br />
	<c:out value="${spitter.firstName}" />
	<br />
	<c:out value="${spitter.lastName}" />
</body>
</html>