<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base"
	value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />

<!DOCTYPE html>
<html>
<head>


<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<!-- fav-icon -->
<link rel="Shortcut Icon" href="/resource/img/tree_leaf.ico"
	type="image/x-icon" />
<title><tiles:getAsString name="title" /></title>

<!-- JQuery lib-->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- Bootstrap -->
<link rel="stylesheet" href="${base}resource/css/bootstrap.css">
<script src="${base}resource/js/bootstrap.min.js"></script>

<!-- Our own css -->
<link rel="stylesheet" href="${base}resource/css/system.css">

<!-- DataTables CSS-->
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.10/css/jquery.dataTables.min.css">

<!-- Bootbox -->
<script src="${base}resource/js/bootbox.js"></script>

<%--<link rel="stylesheet" href="${base}resource/css/login.css">--%>

<!-- JQuery lib-->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script 
	src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>

<!-- DataTables lib -->
<script
	src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>

<!-- Our JavaScript-->
<script src="${base}resource/js/scripts.js"></script>


</head>
<body>
		<div class="container">
			<div class="row">
				<div id="header">
					<tiles:insertAttribute name="header" />
				</div>
			</div>
			<div>
				<div id="body">
					<tiles:insertAttribute name="body" />
				</div>
			</div>
		</div>

		<!--<div style="margin-top: 91px">
			<p style="text-align: center">&copy;2016<spring:message code="label.copyright" /></p>
			<p><a href="/registrator/faq">Допомога </a> <a href="/registrator/help"> Зворотній зв'язок</a></p>
		</div>-->
</body>
</html>