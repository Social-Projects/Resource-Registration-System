<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="<c:url value='/resource/js/adminSettings.js'/>"></script>

<div class="container">
	<form:form id="сhangeReg" method="post" modelAttribute="settings"
		action="settings">
		<h4>
			<spring:message code="label.admin.settings.method" />
		</h4>
		<p>
			<spring:message code="label.admin.settings.chooseoption" />
		</p>
		<div class="radio">
			<label><input type="radio" name="optradio" value="PERSONAL"
				<c:if test="${ REGISTRATION_METHOD  == 'PERSONAL'}"> checked="checked" </c:if> />
				<spring:message code="label.admin.settings.personal" /> </label>
		</div>

		<div class="radio">
			<label><input type="radio" name="optradio" value="MANUAL"
				<c:if test="${ REGISTRATION_METHOD  == 'MANUAL'}">checked="checked"</c:if> />
				<spring:message code="label.admin.settings.manual" /> </label>
		</div>
		<div class="radio">
			<label><input type="radio" name="optradio" value="MIXED"
				<c:if test="${ REGISTRATION_METHOD  == 'MIXED'}"> checked="checked" </c:if> />
				<spring:message code="label.admin.settings.mixed" /> </label>
		</div>
		<div class="container">
			<div class="row">
				<h4>
					<spring:message code="label.admin.settings.timeZone" />
				</h4>
			</div>
			<div class="row control-group">
				<div class="col-md-3">
					<input class="form-control col-md-3" name="timeZone"
						value="${TIME_ZONE}">
				</div>
			</div>
			<%--smelly workaround to add vertical blank space--%>
			<div class="row">
				<h4></h4>
			</div>
		</div>
		<input type="submit" id="confirmRegistrationMethod"
			value="<spring:message code="label.admin.settings.confirm" />"
			class="btn btn-primary" />
	</form:form>
</div>