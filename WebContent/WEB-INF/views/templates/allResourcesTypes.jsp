<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="<c:url value='/resource/js/deleteRS.js'/>"></script>

<div style="text-align: center;">
	<h4>
		<spring:message code="label.restype.pagename" />
	</h4>
</div>
<p>
	<a href="<c:url value='/registrator/resourcetypes/addrestype'/>"
		class="btn btn-success" role="button"><spring:message
			code="label.restype.add" /></a>
</p>
<table id="datatable" class="table table-striped table-bordered table-hover">

	<thead>
		<tr>
			<th><spring:message code="label.restype.typename" /></th>
			<th><spring:message code="label.restype.discreteDescription" /></th>
			<th><spring:message code="label.restype.unitOfMeasurement" /></th>
			<th><spring:message code="label.restype.linearDescription" /></th>
			<th><spring:message code="label.restype.unitOfMeasurement" /></th>
			<th><spring:message code="label.restype.actions" /></th>
		</tr>
	</thead>

	<tbody>
		<c:if test="${not empty listOfResourceType}">
			<c:forEach items="${listOfResourceType}" var="restype">
				<tr>
					<td>${restype.typeName}</td>
					<td><c:forEach items="${restype.discreteParameters}"
							var="dis_param">
							<div class="block">${dis_param.description}</div>
						</c:forEach></td>
					<td><c:forEach items="${restype.discreteParameters}"
							var="dis_param">

							<div class="block">
								<div>${dis_param.unitName}</div>
							</div>
						</c:forEach></td>
					<td><c:forEach items="${restype.linearParameters}"
							var="lin_param">
							<div class="block">${lin_param.description}</div>

						</c:forEach></td>
					<td><c:forEach items="${restype.linearParameters}"
							var="lin_param">
							<div class="block">

								<div>${lin_param.unitName}</div>
							</div>

						</c:forEach></td>
					<td>
						<div class="block">

							<%-- <a href="edit/${restype.typeId}" class="btn btn-primary"
								role="button"><spring:message code="label.restype.edit" /></a> --%>
							<a href="delete/${restype.typeId}" class="btn btn-danger"
								id="deleterestype" role="button"><spring:message
									code="label.restype.delete" /></a>
						</div>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>