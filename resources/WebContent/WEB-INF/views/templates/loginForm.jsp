<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${pageContext.request.userPrincipal.name == null}">
<%--  --%>
	<form:form name='loginForm' id="loginForm" action="${pageContext.request.contextPath}/login" method='POST'>

		<table width="50%" border="0" align="center"
			style="padding-bottom: 50px;">
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" style="color: red;">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" style="color: blue; font-size: 20px;"><b>Login
						Here </b></td>
			</tr>


			<tr>
				<td width="35%">Username</td>
				<td><input id="j_username" name="j_username" type="text"
					placeholder="Username" size="30" autocomplete="on"
					autofocus="autofocus"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input id="j_password" name="j_password" type="password"
					placeholder="Password" size="30" autocomplete="on"></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td style="height: 20px;" class="tdLabelC">
					<button id="subbtn">Sign In</button>
				</td>
			</tr>
		</table>

	</form:form>
	
</c:if>