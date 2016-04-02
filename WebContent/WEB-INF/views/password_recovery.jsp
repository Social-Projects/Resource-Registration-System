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
				<!-- 						<button type="button" class="close" data-dismiss="alert"  -->
				<!-- 			                                aria-label="Close"> -->
				<!-- 							<span aria-hidden="true">×</span> -->
				<!-- 						</button> -->
				<strong>Ви успішно змінили пароль</strong>
			</div>
		</c:if>
		<c:if test="${msg == null}">
			<form:form name='passwordRecoveryDTO' id="passwordRecoveryDTO"
				modelAttribute="passwordRecoveryDTO"
				action="${pageContext.request.contextPath}/password_recovery"
				method='POST'>
				<h5>
					<spring:message code="label.recoverPassword.title" />
				</h5>
				<div class="form-group">
					<label for="inputPassword"><spring:message
							code="label.newPassword" /></label> <input class="form-control"
						id="password" name="password" type="password"
						placeholder=<spring:message
	                    code="label.password" />
						size="30" autocomplete="on">
					<form:errors path="password" class="control-label" />
				</div>

				<div class="form-group">
					<label for="inputPassword"><spring:message
							code="label.confirmNewPassword" /></label> <input class="form-control"
						id="confirmPassword" name="confirmPassword" type="password"
						placeholder=<spring:message
	                    code="label.password" />
						size="30" autocomplete="on">
					<form:errors path="confirmPassword" class="control-label" />
				</div>
				<input type="hidden" name="hash" value="${hash}">
				<form:errors path="hash" class="control-label" />
				<button type="submit" class="btn btn-primary btn-block">
					<spring:message code="label.changePassword" />
				</button>
			</form:form>
		</c:if>
		<div class="form-group">
			<span> <a href="${pageContext.request.contextPath}/login"
				class="forgot-password align-left"> <spring:message
						code="label.signIn" />
			</a>
			</span> <span style="float: right"> <a
				href="${pageContext.request.contextPath}/registration"
				class="forgot-password align-right"> <spring:message
						code="label.register" />
			</a>
			</span>
		</div>
	</fieldset>
</div>