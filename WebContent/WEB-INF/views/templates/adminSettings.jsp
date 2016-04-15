<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
  .panel30 {
    max-width: 45%;
  }
</style>

<div class="container">
  <form:form id="сhangeReg" method="post" modelAttribute="settings"
    action="settings">
    <c:if test="${settings.success}">
      <div class="alert alert-success">
        <span><spring:message code="label.admin.settings.save"/></span>
      </div>
    </c:if>
    <div class="panel panel-default" max>
      <div class="panel-heading">
        <h3 class="panel-title"><spring:message code="label.admin.settings.method" /></h3>
      </div>
      <div class="panel-body panel30">
        <p>
          <spring:message code="label.admin.settings.chooseoption" />
        </p>
        <div class="radio">
          <label><input type="radio" name="registrationMethod"
                        value="PERSONAL"
              <c:if test="${settings.registrationMethod  == 'PERSONAL'}"> checked="checked" </c:if> />
            <spring:message code="label.admin.settings.personal" /> </label>
        </div>

        <div class="radio">
          <label><input type="radio" name="registrationMethod" value="MANUAL"
                        <c:if test="${ settings.registrationMethod  == 'MANUAL'}">checked="checked"</c:if> />
            <spring:message code="label.admin.settings.manual" /> </label>
        </div>
        <div class="radio">
          <label><input type="radio" name="registrationMethod" value="MIXED"
              <c:if test="${ settings.registrationMethod  == 'MIXED'}"> checked="checked" </c:if> />
            <spring:message code="label.admin.settings.mixed" /> </label>
        </div>
      </div>
    </div>

    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title"><spring:message code="label.admin.settings.timeZone" /></h3>
      </div>
      <div class="panel-body panel30">
        <p>
          <spring:message code="label.admin.settings.timeZoneInput" />
          <input id = "time_id" class="form-control col-md-4" name="timeZone"
                 value="${settings.timeZone}">
            <%--<form:errors path="timeZone" cssClass="error"--%>
            <%--style="color:red" />--%>
          <c:if test="${settings.error}">
            <span class="error" style="color:red"><spring:message code="msg.settings.timeZone.error"/></span>
          </c:if>
        </p>
      </div>
    </div>

    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title"><spring:message code="label.admin.settings.smtp" /></h3>
      </div>
      <div class="panel-body">
        <div class="container">
          <div class="row">
            <div class="col-md-4">
              <p><spring:message code="label.admin.settings.smtp.host" /></p>
              <input id = "smtpHost" class="form-control" name="smtpParameters.host" value="${settings.smtpParameters.host}">
            </div>
            <div class="col-md-2">
              <p><spring:message code="label.admin.settings.smtp.port" /></p>
              <input id = "smtpPort" class="form-control" name="smtpParameters.port" value="${settings.smtpParameters.port}">
            </div>
            <div class="col-md-6">

              <span class="checkbox"><form:checkbox path="smtpParameters.tlsEnabled" value="${settings.smtpParameters.tlsEnabled}"/>
                <label for="smtpParameters.tlsEnabled1"><spring:message code="label.admin.settings.smtp.tls" /> </label>
              </span>

            </div>
          </div>
          <div class="row">
            <div class="col-md-4">
              <p><spring:message code="label.admin.settings.smtp.username" /></p>
              <input id = "smtpUsername" class="form-control" name="smtpParameters.username" value="${settings.smtpParameters.username}">
            </div>
            <div class="col-md-4">
              <p><spring:message code="label.admin.settings.smtp.password" /></p>
              <input type="password" id = "smtpPassword" class="form-control" name="smtpParameters.password" value="${settings.smtpParameters.password}">
            </div>
          </div>

        </div>
      </div>
    </div>

    <input type="submit" id="confirmRegistrationMethod"
      value="<spring:message code="label.admin.settings.confirm" />"
      class="btn btn-primary" />
  </form:form>
</div>

<link rel="stylesheet" type="text/css"
      href="<c:url value='/resource/css/suggestion.css'/>">

<%--<script src="<c:url value='/resource/js/adminSettings.js'/>"></script>--%>
<script src="<c:url value='/resource/js/lib/jquery.autocomplete.min.js'/>"></script>
<script src="<c:url value='/resource/js/timeZoneSuggestions.js'/>"></script>