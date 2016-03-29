<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div style="text-align: center;">
	<h4>
		<spring:message code="label.inquiry.input.pagename" />
	</h4>
</div>

			<table id="datatable" class="table display"> 
			
				<thead>
					<tr>
						<th hidden="true"><spring:message code="label.inquiry.inquiryId" /></th>
						<th><spring:message code="label.inquiry.date" /></th>
						<th><spring:message code="label.inquiry.user" /></th>
						<th><spring:message code="label.resource.registrator" /></th>
						<th hidden="true"><spring:message code="label.inquiry.inquiryType" /></th>
						<th><spring:message code="label.resource.identifier" /></th>
						<th><spring:message code="label.resource.status" /></th>
						<th><spring:message code="label.restype.actions" /></th>
					</tr>
				</thead>
				
				<tbody>
					 <c:if test="${not empty listInquiryUser}"> 
					
						<c:forEach items="${listInquiryUser}" var="inquiryUser">						
							<tr>								
								<td hidden="true"> ${inquiryUser.inquiryId}	</td>
								<fmt:formatDate value="${inquiryUser.date}" pattern="dd.MM.yyyy" var="Date" />
								<td> ${Date}	</td>
								<td> ${inquiryUser.userName}	</td>
								<td> ${inquiryUser.registratorName}	</td>
								<td hidden="true"> ${inquiryUser.inquiryType}	</td>
								<td><a href="<c:url value='/registrator/resource/get/${inquiryUser.resourceIdentifier}' />"> 
										${inquiryUser.resourceIdentifier}	</a></td>	
								<td> ${inquiryUser.resourceStatus}	</td>
								<td> <div class="block">										
										<a href="<c:url value='/inquiry/add/printdata/${inquiryUser.inquiryId}' />"
											class="btn btn-primary" role="button"> 
											<spring:message code="label.inquiry.print" /></a>  	
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


		