<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/css/suggestion.css'/>">
<script
	src="<c:url value='/resource/js/lib/jquery.autocomplete.min.js'/>"></script>
<script src="<c:url value='/resource/js/batchUserOps.js'/>"></script>

<div class="container">
	<div class="dataTable_wrapper">
		<div style="text-align: center;">
			<h4>
				<spring:message code="label.registrated.pagename" />
			</h4>
		</div>

		<div class="dropdown" id="actionList"
			style="float: Left; margin-right: 5px;">
			<a id="dLabel" role="button" data-toggle="dropdown"
				class="btn btn-xs btn-primary" data-target="#" href="#"><spring:message
					code="label.modal.actions" /> <span class="caret"></span> </a>
			<ul class="dropdown-menu multi-level" role="menu"
				aria-labelledby="dropdownMenu">
				<li class="dropdown-submenu"><a href="#"><spring:message
							code="label.modal.setRole" /></a>
					<ul class="dropdown-menu">
						<c:forEach items="${roleTypes}" var="role">
							<li><a class="set-role" href="#${role.type}"
								val="${role.type}"><spring:message
										code="label.admin.userlist.role_${role.type}" /></a></li>
						</c:forEach>

					</ul></li>

				<li><a href="#" class="set-community"><spring:message
							code="label.modal.setCommunity" /></a></li>
				<li><a href="#" class="reset-password"><spring:message
							code="label.modal.resetPassword" /></a></li>
			</ul>
		</div>

		<table id="datatable"
			class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th><spring:message code="label.user.secondname" /></th>
					<th><spring:message code="label.user.firstname" /></th>
					<th><spring:message code="label.user.middlename" /></th>
					<th><spring:message code="label.user.role" /></th>
					<th><spring:message code="label.manualregister.community" /></th>
					<th></th>
				</tr>
			</thead>
			<tbody id="user-list">
				<c:if test="${not empty userList}">
					<c:forEach items="${userList}" var="user">
						<tr>
							<td><input class="logindata" type="hidden" name="userLogin"
								value="${user.login}" />${user.lastName}</td>
							<td>${user.firstName}</td>
							<td>${user.middleName}</td>
							<td class="role"><spring:message
									code="label.admin.userlist.role_${user.role}" /></td>
							<td class="community">${user.territorialCommunity}</td>
							<td><a
								href="<c:url value='/administrator/users/edit-registrated-user/?login=${user.login}'/>"
								class="btn btn-sm btn-primary" role="button">Профіль</a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
<div id="modalWindow" class="form-horizontal">
	<div id="userCommunitySelectModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<spring:message code="label.registrator.enterData" />
					</h4>
					<label class="control-label"><spring:message
							code="label.community.title" />: </label> <input id="tc_search"
						class="form-control input-md" type="text">
				</div>
				<div class="control-group error"></div>
				<div class="modal-footer">
					<button class="btn btn-info" data-dismiss="modal">
						<spring:message code="label.modal.cancel" />
					</button>
					<button class="submit btn btn-success">
						<spring:message code="label.modal.confirm" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
