<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


	<div class="signin-container">
        <img src="<c:url value='/resource/img/ukraine_logo.gif'/>" class="login_logo col-md-8">
		<fieldset id="login_fieldset" class="forms col-md-4">

			<c:if test="${pageContext.request.userPrincipal.name == null}">

				<form:form name='loginForm' id="loginForm"
					action="${pageContext.request.contextPath}/login" method='POST'>

					<div style="color: red;">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>

						<c:if test="${not empty error}">
					<div class="error">${error}</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="msg">${msg}</div>
				</c:if>



					<div class="form-group">
						<label for="inputEmail"><spring:message
                    code="label.login" /></label>
						<input class="form-control"
							id="login" name="login" type="text"
							placeholder=<spring:message
                    code="label.login" /> size="30" autocomplete="on"
							autofocus="autofocus">

					</div>


					<div class="form-group">
						<label for="inputPassword"><spring:message
                    code="label.password" /></label>
						<input
							class="form-control" id="password" name="password"
							type="password" placeholder=<spring:message
                    code="label.password" /> size="30"
							autocomplete="on">
					</div>
					
					<div class="checkbox">
						<label><input type="checkbox" id="_spring_security_remember_me" value="true" name="_spring_security_remember_me">
						 <spring:message code="label.rememberMe" /></label>
					</div>
					
					<div class="form-group" align="center" >
					<button type="submit" class="btn btn-primary"
						style="width: 100px;"><spring:message code="label.signIn" /></button>
					
					
					<c:if test="${registrationMethod eq ('PERSONAL') || registrationMethod eq ('MIXED')}">
					<a href="${pageContext.request.contextPath}/register" class="btn btn-success"
						role="button" ><spring:message code="label.register" /></a>
					</c:if>
						
					</div>
					<a href="${pageContext.request.contextPath}/forgot_password" class="forgot-password">
	                		<spring:message code="label.forgotPassword" />
	            	</a>
				</form:form>
			</c:if>
		</fieldset>
	</div>




