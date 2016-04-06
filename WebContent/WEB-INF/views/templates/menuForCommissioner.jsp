<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<nav class="navbar navbar-default" id="menubarcom">
  <div class="container-fluid">

    <div class="collapse navbar-collapse"
      id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="<c:url value='/'/>"
          class="glyphicon glyphicon-home"></a></li>
        <li class="dropdown"><a href="#" class="not-active"> <spring:message
              code="label.menu.users" /></a>
          <ul class="dropdown_menu">
            <li><a
              href="<c:url value='/administrator/users/get-all-users'/>"><spring:message
                  code="label.registrated.pagename" /></a></li>
            <li><a
              href="<c:url value='/administrator/users/get-all-inactive-users'/>"><spring:message
                  code="label.inactive.pagename" /></a></li>
          </ul></li>
        <c:if
          test="${(registrationMethod eq ('MANUAL')) || (registrationMethod eq ('MIXED'))}">
          <li><a href="<c:url value='/manualregistration'/>"><spring:message
                code="label.manualregister" /></a></li>
        </c:if>
<!--         <li><a -->
<%--           href="<c:url value='/administrator/users/search" class="glyphicon glyphicon-search'/>"></a> --%>
      </ul>
    </div>
  </div>
</nav>