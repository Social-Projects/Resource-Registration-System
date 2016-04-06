<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="alert alert-danger">
  <spring:message code= "label.resource.notFound"/><strong> ${exception.identifier}</strong>
</div>



