<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2>
	Стрес-тестування
</h2>

 <form:form method="POST" action="post" modelAttribute="newrestype"
	class="form-horizontal">


 <button type="submit">Почати</button>
</form:form> 