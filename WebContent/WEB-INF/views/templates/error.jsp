<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="alert alert-danger">
  <h3>
    <spring:message code="label.errorPage.header"/>
  </h3>
  <p>
    <spring:message code="label.errorPage.text"/>
  </p>
</div>

<!--
Failed URL: ${url}
Exception:  ${exception.message}
<c:forEach items="${exception.stackTrace}" var="ste">    ${ste}
</c:forEach>
-->