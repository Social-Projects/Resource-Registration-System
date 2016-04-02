<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h2>
	<spring:message code="label.community.editTitle" />
</h2>

<form:form method="POST" action="editCommunity"
	modelAttribute="communityDTO" class="form-horizontal">

	<form:errors path="name" cssClass="error" style="color:red" />
	<div class="form-group">
		<label class="control-label col-sm-2"><spring:message
				code="label.community.title" />:</label>
		<div class="col-sm-3">
			<input class="form-control" name="name" value="${community.name}"
				required> <input type="hidden" class="form-control"
				name="territorialCommunityId"
				value="${community.territorialCommunityId}">
		</div>
	</div>
	<form:errors path="registrationNumber" cssClass="error"
		style="color:red" />
	<div class="form-group">
		<label class="control-label col-sm-2"><spring:message
				code="label.community.titleNumber" />:</label>
		<div class="col-sm-3">
			<c:choose>
				<c:when test="${empty community.registrationNumber}">
					<input class="form-control" name="registrationNumber"
						placeholder=<spring:message code="label.community.enterNumber"/>>
				</c:when>
				<c:otherwise>
					<input class="form-control" name="registrationNumber"
						value="${community.registrationNumber}">
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<br />
	<div class="button">
		<input type="submit"
			value=<spring:message code="label.community.save"/>
			class="btn btn-success" />
	</div>

</form:form>