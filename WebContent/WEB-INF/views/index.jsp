<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Реєстратор</title>
<link rel="stylesheet" type="text/css" href="resource/css/normalize.css">
<link rel="stylesheet" type="text/css" href="resource/css/1.css">

</head>
<body>
	<header class="header">
		<div class="container">
			<h1>
				<spring:message code="label.title" />
			</h1>
			<h2>
				<small><spring:message code="label.subtitle" /></small>
			</h2>

			<div class="languages">
				<a href="?lang=ua">UA</a>| <a href="?lang=en">ENG</a>| <a
					href="?lang=ru">RUS</a>
			</div>


		</div>
	</header>
	<!--     <a  href="www.google.com" role="button">Перейти на нову версію</a>-->




	<nav class="page-navigation">
		<div class="container">
			<ul>
				<li><a href="*">Home</a></li>
				<li><a href="3.html">About</a></li>
				<li><a href="4.html">Contact</a></li>



			</ul>


			<!--LOGIN FORM-->
			<form action="#" class="login">
				<input type="text" placeholder=<spring:message code="label.login" />
					required> <input type="password"
					placeholder=<spring:message code="label.password" /> required>
				<input type="submit" value="Sign in">
			</form>
			<!-- /LOGIN FORM -->
		</div>
	</nav>

	<img src="resource/img/1.jpg" alt="list picture" width="300px"
		height="300px">
	<img src="resource/img/2.jpg" alt="list picture" width="300px"
		height="300px">
	<img src="resource/img/3.jpg" alt="list picture" width="300px"
		height="300px">
	<img src="resource/img/4.jpg" alt="list picture" width="300px"
		height="300px">

	<button>Show more about this resource...</button>
	<footer class="footer">Copyright Softserve 2015</footer>
</body>
</html>