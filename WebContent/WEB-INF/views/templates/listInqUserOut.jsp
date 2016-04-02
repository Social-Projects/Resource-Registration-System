<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="<c:url value='/resource/js/deleteProcuration.js'/>"></script>

<div style="text-align: center;">
	<h4>
		<spring:message code="label.inquiry.listInquiryUserOut.pagename" />
	</h4>
</div>


<table id="datatable" class="table display">
	<thead>
		<tr>
			<th><spring:message code="label.inquiry.date" /></th>
			<th><spring:message code="label.inquiry.user" /></th>
			<sec:authorize access="hasRole('USER')">
				<th><spring:message code="label.resource.registrator" /></th>
			</sec:authorize>
			<th hidden="true"><spring:message
					code="label.inquiry.inquiryType" /></th>
			<th><spring:message code="label.resource.identifier" /></th>
			<th><spring:message code="label.restype.actions" /></th>
		</tr>
	</thead>

	<tbody>
		<c:if test="${not empty listInquiryUserOut}">

			<c:forEach items="${listInquiryUserOut}" var="inquiryUserOut">
				<tr>
					<fmt:formatDate value="${inquiryUserOut.date}" pattern="dd.MM.yyyy"
						var="Date" />
					<td>${Date}</td>
					<td>${inquiryUserOut.userName}</td>
					<sec:authorize access="hasRole('USER')">
						<td>${inquiryUserOut.registratorName}</td>
					</sec:authorize>
					<td hidden="true">${inquiryUserOut.inquiryType}</td>
					<td><a
						href="<c:url value='/registrator/resource/get/${inquiryUserOut.resourceIdentifier}' />">
							${inquiryUserOut.resourceIdentifier} </a></td>
					<td>
						<div class="block">
							<sec:authorize access="hasRole('REGISTRATOR')">
								<a
									href="<c:url value='/inquiry/add/delete/${inquiryUserOut.inquiryId}' />"
									class="btn btn-danger" role="button" id="deleteInquiry"> <spring:message
										code="label.restype.delete" /></a>
							</sec:authorize>
							<a
								href="<c:url value='/inquiry/add/printOutput/${inquiryUserOut.inquiryId}' />"
								class="btn btn-primary" role="button"> <spring:message
									code="label.inquiry.print" /></a>
							<sec:authorize access="hasRole('REGISTRATOR')">
								<a
									href="<c:url value='/inquiry/add/printExtract/${inquiryUserOut.inquiryId}' />"
									class="btn btn-primary" role="button"> <spring:message
										code="label.inquiry.printExtract" /></a>
							</sec:authorize>

						</div>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>

<script type="text/javascript">
<!--
$("#datatable").DataTable();
//-->
</script>

