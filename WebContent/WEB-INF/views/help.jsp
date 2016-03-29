<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Thanks for registration</title>
<style>
body {
  background-color: #e3e3e3;
  font: "Verdana";
}
#support{
    position: relative;
}
</style>
</head>
<img src="<c:url value='/resource/img/contactus-banner.png'/>" id="support">
<body>
<h4>Анкета для зворотнього зв'язку</h4>
<p>Якщо у вас виникли будь-які питання або побажання стосовно роботи сайту, прохання звернутися до адміністратора.
Ми будемо раді домопогти у вирішенні ваших проблем
<form id="adminContactForm" action="post">
<textarea rows="10" cols="80">

</textarea>
<p><input type="button" name="submit" value="Send">
</body>
