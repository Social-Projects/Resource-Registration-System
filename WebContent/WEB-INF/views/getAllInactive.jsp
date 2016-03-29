<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Реєстратор</title>
<link rel="stylesheet" type="text/css" href="resource/css/normalize.css">
<link rel="stylesheet" type="text/css" href="resource/css/1.css">

</head>
<body>
	<div class="container">
		<table id="unregistated" border="1">
			<tr>
				<th>First name</th>
				<th>Last name</th>
				<th>Email</th>
				<th>Middle name</th>
				<th>Address</th>
			</tr>

			<c:if test="${not empty unregistatedUsers}">
				<c:forEach items="${unregistatedUsers}" var="user">
					<tr>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>${user.email}</td>
						<td>${user.middleName}</td>
						<c:forEach items="${user.address}" var="add">
							<td>${add.city}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>
</body>
</html>