<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--<spring:url--%>
<%--value="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"--%>
<%--var="jqueryJs" />--%>

<script src="<c:url value='/resource/js/changeStatus.js'/>"></script>

<div style="text-align: center;">
	<h4>
		<spring:message code="label.inactive.pagename" />
	</h4>
</div>
<form:form id="editWinodow" modelAttribute="userDTO" method="post"
	action="get-all-inactive-users">
	<table id="datatable"
		class="table table-striped table-bordered table-hover">
		<thead>
			<tr id="myTable">
				<th><spring:message code="label.login" /></th>
				<th><spring:message code="label.user.secondname" /></th>
				<th><spring:message code="label.user.firstname" /></th>
				<th><spring:message code="label.user.middlename" /></th>
				<th><spring:message code="label.user.email" /></th>
				<th><spring:message code="label.user.address" /></th>
				<th><spring:message code="label.user.passport" /></th>
				<th><spring:message code="label.user.changestatus" /></th>
			</tr>
		</thead>
		<c:if test="${not empty unregistatedUsers}">
			<c:forEach items="${unregistatedUsers}" var="userDTO" varStatus="i">
				<tr>
					<td id="login_${i.index}">${userDTO.login}</td>
					<td id="lastName">${userDTO.lastName}</td>
					<td id="firstName">${userDTO.firstName}</td>
					<td id="middleName">${userDTO.middleName}</td>
					<td id="email">${userDTO.email}</td>
					<td id="address">${userDTO.address.city},${user.address.street},
						${userDTO.address.building}, ${user.address.flat}</td>
					<td id="passpoorts">${userDTO.passport.seria},${user.passport.number},
						${userDTO.passport.published_by_data}</td>
					<td><select id="userStatusId_${i.index}" class="changestatus"
						name="status">
							<c:forEach items="${userStatusList}" var="userStatus"
								varStatus="i">
								<c:choose>
									<c:when test="${userDto.status == userStatus}">
										<option id="status" selected value="${userStatus}">${userStatus}</option>
									</c:when>
									<c:otherwise>
										<option id="status" value="${userStatus}">${userStatus}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</form:form>

