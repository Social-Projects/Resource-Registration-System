<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<div class="container-fluid" id="header">

  <div class="col-md-7 col-xs-12">
    <h3>
      <spring:message code="label.name.part1"/>
    </h3>
    <h3>
      <spring:message code="label.name.part2"/>
    </h3>
  </div>
  <div class="col-md-5 col-xs-12 hcontrollers">
    <div class="col-md-7 col-xs-6 hprofile">
      <c:if
        test="${pageContext.request.userPrincipal.name != null}">
        <div class="btn-group">
          <button class="btn btn-primary btn-sm"
                  style="margin: 0;">${pageContext.request.userPrincipal.name}</button>
          <button
            class="btn btn-primary btn-sm dropdown-toggle"
            style="margin: 0;" data-toggle="dropdown">
            <span class="caret"></span>
          </button>
          <ul class="dropdown-menu">
            <li><a
              href="${pageContext.request.contextPath}/change_password"
              class="change-password"> <spring:message
              code="label.changePassword"/>
            </a></li>
            <li><a href="#" class="reset-my-password">
              <spring:message code="label.resetPassword"/>
            </a></li>
            <li class="divider"></li>
            <li><a href="<c:url value="/logout"/>"><spring:message
              code="label.signOut"/></a></li>
          </ul>
        </div>
      </c:if>
    </div>
    <div class="col-md-5 col-xs-6 hlanguage">
      <select id="changeLanguage" class="form-control" style="height: 30px">
        <option value="ua">українська</option>
        <option value="ru">русский</option>
        <option value="en">english</option>
      </select>
    </div>
  </div>
</div>

<script src="<c:url value='/resource/js/lib/jquery.i18n.properties.js'/>"></script>
<script src="<c:url value='/resource/js/lib/jquery.cookie.js'/>"></script>
<script src="<c:url value='/resource/js/resetPassword.js'/>"></script>

<!-- define the page language and load massages -->
<script>
	var lang = "${pageContext.response.locale}";
	setLocalelanguage(lang);
	getLocaleMassages(lang);
	$("#changeLanguage")
			.change(
					function() {
						var language = $("#changeLanguage").val()
						$
								.removeCookie('org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE');
						$
								.cookie(
										'org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE',
										language, {
											path : '/'
										});
						location.reload();
					});
	function getLocaleMassages(lang) {
		jQuery.i18n.properties({
			name : 'Messages',
			path : '${pageContext.request.contextPath}'
					+ '/resource/massages/i18n/',
			mode : 'both',
			language : lang,
		});
	}
	function setLocalelanguage(lang) {
		$("#changeLanguage").val(lang);
	}
</script>

