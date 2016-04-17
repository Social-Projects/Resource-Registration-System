<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="signin-container">
  <img src="<c:url value='/resource/img/ukraine_logo.gif'/>"
    class="col-md-8 col-xs-12">
  <fieldset id="login_fieldset" class="forms col-md-4">
    <p style="text-align: center; margin: 20px 0;">
      <spring:message code="label.register.congratulation" />
    </p>

    <div class="form-group" style="text-align:center;">
      <a href="${pageContext.request.contextPath}/login"
        class="btn btn-success" role="button"><spring:message code="label.register.gotomain" /></a>
    </div>
  </fieldset>
</div>