<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="<c:url value='/resource/js/changeStatus.js'/>"></script>

<div style="text-align: center;">
	<h4>
		<spring:message code="label.registrated.pagename" />
	</h4>
</div>

<table id="datatable" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th><spring:message code="label.user.secondname" /></th>
			<th><spring:message code="label.user.firstname" /></th>
			<th><spring:message code="label.user.middlename" /></th>
			<th><spring:message code="label.user.email" /></th>
			<th><spring:message code="label.user.address" /></th>
			<th><spring:message code="label.user.passport" /></th>
			<th><spring:message code="label.restype.actions" /></th>
		</tr>
	</thead>
	<c:if test="${not empty userDtoList}">
		<c:forEach items="${userDtoList}" var="user">
			<tr>
				<td>${user.lastName}</td>
				<td>${user.firstName}</td>
				<td>${user.middleName}</td>
				<td>${user.email}</td>
				<td>${user.address.city} ${user.address.street},
					${user.address.building} ${user.address.flat}</td>
				<td>${user.passport.seria} ${user.passport.number},
					${user.passport.published_by_data}</td>
				<td><a
					href="<c:url value='/administrator/users/edit-registrated-user/?login=${user.login}'/>"
					class="btn btn-primary" role="button"><spring:message
							code="label.user.more" /></a></td>
			</tr>
		</c:forEach>
	</c:if>
</table>