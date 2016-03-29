<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="<c:url value='/resource/js/adminSettings.js'/>"></script>

<div class="container">
	<h4>
		<spring:message code="label.admin.settings.method" />
	</h4>
	<p>
		<spring:message code="label.admin.settings.chooseoption" />
	</p>
	<form:form id="сhangeReg" method="post" modelAttribute="regMethod"
		action="settings">
		<div class="radio">
			<label><input type="radio" name="optradio" value="personal"
				<c:if test="${ regMethod  == 'PERSONAL'}"> checked="checked" </c:if> />
				<spring:message code="label.admin.settings.personal" /> </label>
		</div>

		<div class="radio">
			<label><input type="radio" name="optradio" value="manual"
				<c:if test="${ regMethod  == 'MANUAL'}">checked="checked"</c:if> />
				<spring:message code="label.admin.settings.manual" /> </label>
		</div>
		<div class="radio">
			<label><input type="radio" name="optradio" value="mixed"
				<c:if test="${ regMethod  == 'MIXED'}"> checked="checked" </c:if> />
				<spring:message code="label.admin.settings.mixed" /> </label>
		</div>
		<input type="submit" id="confirmRegistrationMethod"
			value="<spring:message code="label.admin.settings.confirm" />"
			class="btn btn-primary" />
	</form:form>
</div>