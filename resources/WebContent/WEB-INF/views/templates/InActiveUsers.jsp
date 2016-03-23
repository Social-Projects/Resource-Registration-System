<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="text-align: center;">
	<h4>
		Список усіх неактивних користувачів
	</h4>
</div>

<table id="datatable" class="table display">
	<thead>
		<tr>
			<th>Прізвище</th>
			<th>Ім'я</th>
			<th>По-батькові</th>
			<th>Електронна пошта</th>
			<th>Адреса</th>
			<th>Паспорт</th>
			<th>Змінити Роль</th>
			<th>Змінти Статус</th> 
		</tr>
	</thead>
	<c:if test="${not empty unregistatedUsers}">
		<c:forEach items="${unregistatedUsers}" var="user">
			<tr>
				<td>${user.lastName}</td>
				<td>${user.firstName}</td>
				<td>${user.middleName}</td>
				<td>${user.email}</td>
				<td><a
					href="${base}addressWindow/?login=${user.login}"
					rel="0">Адреса</a></td>
				<td><a
					href="${base}passportWindow/?login=${user.login}"
					rel="0">Паспорт</a></td>
				<td><select id="roleId" name="roleName">
						<c:forEach items="${roleList}" var="role">
							<c:choose>
								<c:when test="${user.role.type == role.type}">
									<option selected value="${role.type}">${role.type}</option>
								</c:when>
								<c:otherwise>
									<option value="${role.type}">${role.type}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
				<td><select id="userStatusId" name="userStatusName"
					class="selectpicker">
						<c:forEach items="${userStatusList}" var="userStatus">
							<c:choose>
								<c:when test="${user.status == userStatus}">
									<option selected value="${userStatus}">${userStatus}</option>
								</c:when>
								<c:otherwise>
									<option value="${userStatus}">${userStatus}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
			</tr>
		</c:forEach>
	</c:if>
</table>
