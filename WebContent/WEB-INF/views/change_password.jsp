<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="change_password_container">
    <fieldset id="change_password_fieldset">

        <c:if test="${msg!= null}">
            <div class="alert alert-success alert-dismissible col-md-4 col-md-offset-4" role="alert">
                <strong><spring:message code="label.password.updated"/></strong>
            </div>
        </c:if>
        <c:if test="${msg == null}">
            <form:form class="form-horizontal" name='passwordChangeDTO' id="passwordChangeDTO" modelAttribute="passwordChangeDTO"
                       action="${pageContext.request.contextPath}/change_password" method='POST'>

                <!-- Form Name -->
                <legend><spring:message code="label.recoverPassword.title"/></legend>

                <!-- Password input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="password"><spring:message
                            code="label.password"/></label>
                    <div class="col-md-4">
                        <input id="password" name="password" type="password" placeholder=""
                               class="form-control input-md" required="">
                        <form:errors path="password" class="error" cssStyle="color:red" />
                    </div>
                </div>

                <!-- Password input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="newPassword"><spring:message
                            code="label.newPassword"/></label>
                    <div class="col-md-4">
                        <input id="newPassword" name="newPassword" type="password" placeholder=""
                               class="form-control input-md" required="">
                        <form:errors path="newPassword" class="error" cssStyle="color:red" />
                    </div>
                </div>

                <!-- Password input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="confirmNewPassword"><spring:message
                            code="label.confirmNewPassword"/></label>
                    <div class="col-md-4">
                        <input id="confirmNewPassword" name="confirmNewPassword" type="password" placeholder=""
                               class="form-control input-md" required="">
                        <form:errors path="confirmNewPassword" class="error" cssStyle="color:red" />
                    </div>
                </div>

                <!-- Button (Double) -->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="cancel"></label>
                    <div class="col-md-8">
                        <button id="update" name="update" class="btn btn-success"><spring:message
                                code="label.updatePassword"/></button>
                        <button id="cancel" name="cancel" class="btn btn-danger" formnovalidate="true"><spring:message
                                code="label.cancelPolygon"/></button>
                    </div>
                </div>

            </form:form>
        </c:if>
    </fieldset>
</div>