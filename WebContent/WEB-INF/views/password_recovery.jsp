<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="signin-container">
  <img src="<c:url value='/resource/img/ukraine_logo.gif'/>"
    class="col-md-8 col-xs-12">
  <fieldset id="login_fieldset" class="forms col-md-4">

    <c:if test="${msg != null}">
      <div class="alert alert-success alert-dismissible" role="alert">
        <!-- 						<button type="button" class="close" data-dismiss="alert"  -->
        <!-- 			                                aria-label="Close"> -->
        <!-- 							<span aria-hidden="true">×</span> -->
        <!-- 						</button> -->
        <strong><spring:message code="label.recoverPassword.success"/></strong>
      </div>
    </c:if>
    <c:if test="${msg == null}">
      <form:form name='passwordRecoveryDTO' id="passwordRecoveryDTO"
        modelAttribute="passwordRecoveryDTO"
        action="${pageContext.request.contextPath}/password_recovery"
        method='POST'>
        <h4 style="text-align: center;">
          <spring:message code="label.recoverPassword.title" />
        </h4>
        <div class="form-group">
          <label for="login"><spring:message code="label.login" /></label>
          <select id="login" name="login" class="form-control" required>
            <%-- If single user in current email --%>
            <c:choose>
              <c:when test="${loginListSize < 2}">
                <c:forEach items="${loginList}" var="login">
                  <option value="${login}" selected>${login}</option>
                </c:forEach>
              </c:when>
              <%-- If multiple users in current email --%>
              <c:otherwise>
                <option value="" disabled selected><spring:message
                    code="label.recoverPassword.selectlogin" /></option>
                <c:forEach items="${loginList}" var="login">
                  <option value="${login}">${login}</option>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </select>
        </div>
        <div class="form-group">
          <label for="password"><spring:message
              code="label.newPassword" /></label> <input class="form-control"
            id="password" name="password" type="password"
            placeholder=<spring:message
	                    code="label.password" />
            size="30" autocomplete="on" required>
          <form:errors path="password" class="error" cssStyle="color:red" />
        </div>

        <div class="form-group">
          <label for="confirmPassword"><spring:message
              code="label.confirmNewPassword" /></label> <input
            class="form-control" id="confirmPassword"
            name="confirmPassword" type="password"
            placeholder=<spring:message
	                    code="label.password" />
            size="30" autocomplete="on" required>
          <form:errors path="confirmPassword" class="error" cssStyle="color:red" />
        </div>
        <input type="hidden" name="hash" value="${hash}">
        <form:errors path="hash" class="error" cssStyle="color:red" />
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
        href="${pageContext.request.contextPath}/register"
        class="forgot-password align-right"> <spring:message
            code="label.register" />
      </a>
      </span>
    </div>
  </fieldset>
</div>