<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:formatDate value="${resource.date}" pattern="dd.MM.yyyy" var="Date" />

<div>	
	<form:form modelAttribute="resource">
	<c:if test="${empty resource}">
	 Ресурс із вказаним ідентифікатором не знайдено
	</c:if>
		<c:if test="${!empty resource}">
		<h1>Ресурс</h1>
			<table>
				<tr>
					<td><spring:message code="label.resource.description" /> :</td>
					<td>${resource.description}</td>
				</tr>
				<tr>
					<td><spring:message code="label.resource.type" />:</td>
					<td>${resource.resourceType.typeName}</td>
				</tr>
				<tr>
					<td><spring:message code="label.resource.identifier" />:</td>
					<td>${resource.identifier}</td>
				</tr>
				<tr>
					<td><spring:message code="label.resource.tome" />:</td>
					<td>${resource.tomeIdentifier}</td>
				</tr>
				<tr>
					<td><spring:message code="label.resource.registrator" />:</td>
					<td>${resource.registratorName}</td>
				</tr>
				<tr>
					<td><spring:message code="label.resource.reasonInclusion" />:</td>
					<td>${resource.reasonInclusion}</td>
				</tr>
				<tr>
					<td><spring:message code="label.resource.date" />:</td>
					<td>${Date}</td>
				</tr>
				<tr>
					<td><spring:message code="label.resource.coordinates" />:</td>
				</tr>
				<c:forEach var="poligon" items="${resource.resourceArea.poligons}">
					<c:forEach var="point" items="${poligon.points}">
						<tr>
							<td>${point.latitudeDegrees}°${point.latitudeMinutes}'
								${point.latitudeSeconds}"</td>
							<td>${point.longitudeDegrees}°${point.longitudeMinutes}'
								${point.longitudeSeconds}"</td>
						</tr>
					</c:forEach>
				</c:forEach>
				<c:forEach var="linear" items="${resource.resourceLinear}">
					<tr>
						<td>${linear.linearParameterDescription},${linear.linearParameterUnit}</td>
						<c:forEach var="segment" items="${linear.segments}">
							<td>${segment.begin}-${segment.end}</td>
						</c:forEach>
					</tr>
				</c:forEach>
				<c:forEach var="discrete" items="${resource.resourceDiscrete}">
					<tr>
						<td>${discrete.discreteParameterDescription},${discrete.discreteParameterUnit}</td>
						<c:forEach var="value" items="${discrete.values}">
							<td>${value}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</form:form>
</div>