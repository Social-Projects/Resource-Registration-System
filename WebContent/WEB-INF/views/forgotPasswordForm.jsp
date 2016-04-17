<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="signin-container">
  <img src="<c:url value='/resource/img/ukraine_logo.gif'/>"
    class="login_logo col-md-8 col-xs-12">
  <fieldset id="login_fieldset" class="forms col-md-4">

    <c:if test="${msg!= null}">
      <div class="alert alert-success alert-dismissible" role="alert">
        <!-- 						<button type="button" class="close" data-dismiss="alert"  -->
        <!-- 			                                aria-label="Close"> -->
        <!-- 							<span aria-hidden="true">×</span> -->
        <!-- 						</button> -->
        <strong>Повідомлення відравленне на вашу адресу</strong>
      </div>
    </c:if>
    <c:if test="${msg == null}">
      <form:form name='forgotPasswordForm' id="forgotPasswordForm"
        action="${pageContext.request.contextPath}/forgot_password"
        method='POST'>

        <div class="form-group">
          <h5>
            <spring:message code="label.forgotPassword.title" />
          </h5>
          <input class="form-control" id="email" name="email"
            type="text"
            placeholder="<spring:message code="label.user.email" />" 
            size="30" autocomplete="on" autofocus="autofocus" required>

        </div>
        <button type="submit" class="btn btn-primary btn-block">
          <spring:message code="label.resetPassword" />
        </button>
        <div></div>
      </form:form>
    </c:if>
    <div class="form-group">
      <span> <a href="${pageContext.request.contextPath}/login"
        class="forgot-password align-left"> <spring:message
            code="label.signIn" />
      </a>
      </span> <span style="float: right"> 
      <c:if test="${registrationMethod eq ('PERSONAL') || registrationMethod eq ('MIXED')}">
          <a href="${pageContext.request.contextPath}/register"
            class="forgot-password align-right"> <spring:message
              code="label.register" />
          </a>
        </c:if>
      </span>
    </div>
  </fieldset>
</div>