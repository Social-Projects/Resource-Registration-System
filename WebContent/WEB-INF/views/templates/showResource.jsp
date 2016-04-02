<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<fmt:formatDate value="${resource.date}" pattern="dd.MM.yyyy" var="Date" />

<c:url value="/inquiry/add/addOutputInquiry" var="actionUrl" />

<%--Check if the resource acts on all Ukraine--%>
<c:if test="${resource.resourceArea.poligons[0].points.size() == 1}">
	<c:set var="allUrkaine" value="true" />
</c:if>

<div>
	<form:form modelAttribute="resource" id="form" action="${actionUrl}">
		<c:if test="${empty resource}">
            Ресурс із вказаним ідентифікатором не знайдено
        </c:if>
		<c:if test="${!empty resource}">
			<h1>Ресурс</h1>
			<c:if test="${resource.noEdit}">
				<div class="alert alert-danger">
					<spring:message code="label.resource.noEdit" />
				</div>
			</c:if>

			<table id="datatable" class="table display">
				<tr>
					<td><spring:message code="label.resource.description" /> :</td>
					<td>${resource.description}</td>
				</tr>
				<tr>
					<td><spring:message code="label.resource.type" />:</td>
					<td>${resource.resourceType}</td>
				</tr>
				<tr>
					<td><spring:message code="label.resource.identifier" />:</td>
					<td id="resourceIdentifier">${resource.identifier}</td>
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
					<td colspan="2"><span id="geoCoords"> <span
							class="glyphicon glyphicon-triangle-right"></span> <span
							class="glyphicon glyphicon-triangle-bottom"
							style="display: none;"></span> <spring:message
								code="label.resource.coordinates" />: <c:if test="${allUrkaine}">
								<spring:message code="label.resource.allUkraine" />
							</c:if>
					</span></td>
				</tr>
				<c:if test="${!allUrkaine}">
					<tr class="coordinates">
						<td colspan="2"><c:forEach var="poligon"
								items="${resource.resourceArea.poligons}"
								varStatus="polygonIndex">
								<div class="col-md-12">
									<spring:message code="label.resource.Polygon" />
									<c:out value="${polygonIndex.count}" />
									:
								</div>
								<div class="col-sm-12 polygon">
									<c:forEach var="point" items="${poligon.points}">
										<div class="coordinatesPoint">
											<span class="pointLat"> <span class="latitudeDegrees">${point.latitudeDegrees}</span>°
												<span class="latitudeMinutes">${point.latitudeMinutes}</span>'
												<span class="latitudeSeconds"> <fmt:formatNumber
														type="number" maxFractionDigits="2"
														value="${point.latitudeSeconds}" />
											</span>"
											</span> <span class="pointLng"> <span
												class="longitudeDegrees">${point.longitudeDegrees}</span>° <span
												class="longitudeMinutes">${point.longitudeMinutes}</span>' <span
												class="longitudeSeconds"> <fmt:formatNumber
														type="number" maxFractionDigits="2"
														value="${point.longitudeSeconds}" />
											</span>"
											</span>
										</div>
									</c:forEach>
								</div>
							</c:forEach></td>
					</tr>
				</c:if>
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
						<c:forEach var="valueDiscrete" items="${discrete.valueDiscretes}">
							<td><span class="col-sm-6">${valueDiscrete.value}</span> <span
								class="col-sm-6">${valueDiscrete.comment}</span></td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>

			<!-- Procuration for an extract from register -->

			<script type="text/javascript"
				src="<c:url value='/resource/js/inquiryAddOutput.js'/>"></script>
			<p>
			<div class="form-group">
				<div class="col-sm-5">
					<sec:authorize access="hasRole('USER')">
						<div id="outputInquiry" class="btn btn-success" role="button"
							style="margin-bottom: 10px">
							<spring:message code="label.inquiry.output.pagename" />
						</div>
					</sec:authorize>
				</div>
				<div id="target" class="col-sm-5"></div>
				<div class="col-sm-5">
					<sec:authorize access="hasRole('REGISTRATOR')">
						<div id="editResource" class="btn btn-primary" role="button"
							style="margin-bottom: 10px">
							<spring:message code="label.resource.edit" />
						</div>
						<div id="deleteResource" class="btn btn-danger" role="button"
							style="margin-bottom: 10px">
							<spring:message code="label.restype.delete" />
						</div>
					</sec:authorize>
				</div>

			</div>

			<p>
				<input type="text" hidden="true" id="resourceIdentifierCopy"
					name="resourceIdentifier" value="${resource.identifier}" />
			</p>

		</c:if>
	</form:form>
	<br />
	<p>

		<%--Connect Google Maps--%>


		<c:if test="${!allUrkaine}">
			<div id="map_canvas" class="container" style="height: 500px;"
				centerLat="<c:out value="${
            resource.resourceArea.poligons[0].points[0].latitudeDegrees +
            resource.resourceArea.poligons[0].points[0].latitudeMinutes/60 +
            resource.resourceArea.poligons[0].points[0].latitudeSeconds/3600}"/>"
				centerLng="<c:out value="${
            resource.resourceArea.poligons[0].points[0].longitudeDegrees +
            resource.resourceArea.poligons[0].points[0].longitudeMinutes/60 +
            resource.resourceArea.poligons[0].points[0].longitudeSeconds/3600}"/>">
			</div>
			<script src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
			<script type="text/javascript"
				src="<c:url value='/resource/js/showResourceMap.js'/>"></script>
		</c:if>
</div>