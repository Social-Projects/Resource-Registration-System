<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="signin-container">
	<img src="<c:url value='/resource/img/ukraine_logo.gif'/>"
		class="login_logo col-md-8">
	<fieldset id="login_fieldset" class="forms col-md-4">
		<c:if test="${msg!= null}">
			<div class="alert alert-success alert-dismissible" role="alert">
				<strong><spring:message
						code="label.confirm_email_ok_message_1" /></strong> <br> <strong><spring:message
						code="label.confirm_email_ok_message_2" /></strong>
			</div>
		</c:if>
		<c:if test="${msg == null}">
			<div class="alert alert-danger" role="alert">
				<strong><spring:message
						code="label.confirm_email_er_message_1" /></strong> <br> <strong><spring:message
						code="label.confirm_email_er_message_2" /></strong>
			</div>
		</c:if>
		<div class="form-group">
			<span> <a href="${pageContext.request.contextPath}/login"
				class="forgot-password align-left"> <spring:message
						code="label.signIn" />
			</a>
			</span>
		</div>

	</fieldset>
</div>