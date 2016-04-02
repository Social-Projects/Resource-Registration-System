<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%--<link rel="stylesheet" type="text/css" href="<c:url value='/resource/css/cssload.css'/>">--%>

<div class="container-fluid" id="header">

	<div class="col-md-7">
		<h3>
			<spring:message code="label.name.part1" />
		</h3>
		<h3>
			<spring:message code="label.name.part2" />
		</h3>
	</div>
	<div class="col-md-5">
		<div class="col-md-7">

			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<div class="btn-group" style="float: right">
					<button class="btn btn-primary btn-sm" style="margin: 0;">${pageContext.request.userPrincipal.name}</button>
					<button class="btn btn-primary btn-sm dropdown-toggle"
						style="margin: 0;" data-toggle="dropdown">
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/change_password"
							class="change-password"> <spring:message
									code="label.changePassword" />
						</a></li>
						<li><a
							href="${pageContext.request.contextPath}/reset_password"
							class="reset-my-password"> <spring:message
									code="label.resetPassword" />
						</a></li>
						<li class="divider"></li>
						<li><a href="<c:url value="/logout"/>"><spring:message
									code="label.signOut" /></a></li>
					</ul>
				</div>
			</c:if>
		</div>
		<div class="col-md-5">
			<select id="changeLanguage" class="form-control">
				<option value="ua">українська</option>
				<option value="ru">русский</option>
				<option value="en">english</option>
			</select>
		</div>
	</div>
</div>

<%--AJAX Loader for the dark display--%>
<%--<div id="dark_bg">
    <div class="windows8">
        <div class="wBall" id="wBall_1">
            <div class="wInnerBall"></div>
        </div>
        <div class="wBall" id="wBall_2">
            <div class="wInnerBall"></div>
        </div>
        <div class="wBall" id="wBall_3">
            <div class="wInnerBall"></div>
        </div>
        <div class="wBall" id="wBall_4">
            <div class="wInnerBall"></div>
        </div>
        <div class="wBall" id="wBall_5">
            <div class="wInnerBall"></div>
        </div>
    </div>
</div>--%>

<script
	src="<c:url value='/resource/js/lib/jquery.i18n.properties.js'/>"></script>
<script src="<c:url value='/resource/js/lib/jquery.cookie.js'/>"></script>
<%--<script src="<c:url value='/resource/js/passwordReset.js'/>"></script>--%>

<!-- define the page language and load massages -->
<script>
	var lang = "${pageContext.response.locale}";
	setLocalelanguage(lang);
    getLocaleMassages(lang);
    $("#changeLanguage").change(function() {
        var language = $("#changeLanguage").val()
          $.removeCookie('org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE');
          $.cookie('org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE', language,{ path: '/' });
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

