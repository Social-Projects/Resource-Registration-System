<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> -->

		
		<select id="registratorLogin" name="registratorLogin"  class="form-control">
								<option value=""><spring:message
										code="label.resource.registrator.select" />:
								</option>
								<c:forEach items="${registrators}" var="registrator">
									<option value="${registrator.login}">${registrator.lastName} ${registrator.firstName} 
										${registrator.middleName} </option>
								</c:forEach>
		</select>