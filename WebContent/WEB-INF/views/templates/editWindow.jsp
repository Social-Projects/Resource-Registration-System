<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="<c:url value='/resource/js/move.js'/>"></script>
<script src="<c:url value='/resource/js/changeRole.js'/>"></script>
<script src="<c:url value='/resource/js/editingUser.js'/>"></script>
<script src="<c:url value='/resource/js/userValidate.js'/>"></script>
<script src="<c:url value='/resource/js/checkResourceNumber.js'/>"></script>

<div class="container">
	<c:url value='/administrator/users/edit-registrated-user'
		var="theAction" />
	<form:form id="editWindow" modelAttribute="userDTO" method="post"
		action="${theAction}" class="form-horizontal">
		<fieldset>
			<div class="row">
				<div class="col-lg-4">
					<legend>
						<spring:message code="label.user.information" />
					</legend>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.firstname" />* </label>
						<div class="col-lg-8">
							<input id="firstName" name="firstName" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.firstName}" readonly>
							<div class="control-group error">
								<form:errors path="firstName" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.secondname" />* </label>
						<div class="col-lg-8">
							<input id="lastname" name="lastName" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.lastName}" readonly>
							<div class="control-group error">
								<form:errors path="lastName" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.middlename" /></label>
						<div class="col-lg-8">
							<input id="middlename" name="middleName" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.middleName}" readonly>
							<div class="control-group error">
								<form:errors path="middleName" cssClass="black"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.login" /></label>
						<div class="col-lg-8">
							<input id="login" name="login" placeholder="" type="text"
								class="form-control input-md" value="${userDto.login}" readonly>
						</div>
					</div>
					<!-- 					<div class="form-group"> -->
					<%-- 						<label class="col-lg-4 control-label" for="textinput"><spring:message --%>
					<%-- 								code="label.password" /></label> --%>
					<!-- 						<div class="col-lg-8"> -->
					<!-- 							<input id="password" name="password" placeholder="" -->
					<!-- 								class="form-control input-md readonly" type="text" -->
					<%-- 								value="${userDto.password}" readonly> --%>
					<!-- 							<div class="control-group error"> -->
					<%-- 								<form:errors path="password" cssClass="error" --%>
					<%-- 									style="color:black" /> --%>
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.email" />* </label>
						<div class="col-lg-8">
							<input id="email" name="email" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.email}" readonly>
							<div class="control-group error">
								<form:errors path="email" cssClass="error" style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.role" /></label>
						<div class="col-lg-8">
							<select id="roleId" name="role" class="form-control"
								style="width: 230px; height: 34px" disabled>
								<c:forEach items="${roleList}" var="role">
									<c:choose>
										<c:when test="${userDto.role == role.type}">
											<option selected value="${role.type}">
                                                <spring:message code="role.${role.type}" />
                                            </option>
										</c:when>
										<c:otherwise>
											<option value="${role.type}">
                                                <spring:message code="role.${role.type}" />
                                            </option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select></a>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.status" /></label>
						<div class="col-lg-8">
							<select id="userStatusId" name="status" class="form-control"
								style="width: 230px; height: 34px" disabled>
								<c:forEach items="${userStatusList}" var="userStatus">
									<c:choose>
										<c:when test="${userDto.status == userStatus}">
											<option selected value="${userStatus}">
                                                <spring:message code="status.${userStatus}" />
                                            </option>
										</c:when>
										<c:otherwise>
											<option value="${userStatus}">
                                                <spring:message code="status.${userStatus}" />
                                            </option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="col-lg-4">
					<legend>
						<spring:message code="label.user.addressinfo" />
					</legend>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.region" /></label>
						<div class="col-lg-8">
							<input id="region" name="address.region" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.address.region}" readonly>
							<div class="control-group error">
								<form:errors path="address.region" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.city" /></label>
						<div class="col-lg-8">
							<input id="city" name="address.city" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.address.city}" readonly>
							<div class="control-group error">
								<form:errors path="address.city" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.district" /></label>
						<div class="col-lg-8">
							<input id="district" name="address.district" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.address.district}" readonly>
							<div class="control-group error">
								<form:errors path="address.district" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.street" /></label>
						<div class="col-lg-8">
							<input id="street" name="address.street" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.address.street}" readonly>
							<div class="control-group error">
								<form:errors path="address.street" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.building" /></label>
						<div class="col-lg-8">
							<input id="building" name="address.building" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.address.building}" readonly>
							<div class="control-group error">
								<form:errors path="address.building" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.flat" /></label>
						<div class="col-lg-8">
							<input id="flat" name="address.flat" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.address.flat}" readonly>
							<div class="control-group error">
								<form:errors path="address.building" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.postcode" /></label>
						<div class="col-lg-8">
							<input id="postcode" name="address.postcode" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.address.postcode}" readonly>
							<div class="control-group error">
								<form:errors path="address.postcode" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-4">
					<legend>
						<spring:message code="label.user.passportinfo" />
					</legend>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.seria" /></label>
						<div class="col-lg-8">
							<input id="seria" name="passport.seria" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.passport.seria}" readonly>
							<div class="control-group error">
								<form:errors path="passport.seria" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.number" /></label>
						<div class="col-lg-8">
							<input id="number" name="passport.number" placeholder=""
								class="form-control input-md readonly" type="text"
								value="${userDto.passport.number}" readonly>
							<div class="control-group error">
								<form:errors path="passport.number" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.published" /></label>
						<div class="col-lg-8">
							<input id="published" name="passport.published_by_data"
								placeholder="" class="form-control input-md readonly"
								type="text" value="${userDto.passport.published_by_data}"
								readonly>
							<div class="control-group error">
								<form:errors path="passport.published_by_data" cssClass="error"
									style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.manualregister.community" />* </label>
						<div class="col-lg-8">
							<select id="community" name="territorialCommunity"
								class="form-control" style="width: 230px; height: 34px" disabled>
								<c:forEach items="${territorialCommunities}" var="communities">
									<c:choose>
										<c:when test="${userDto.territorialCommunity == communities.name}">
											<option selected value="${communities.name}">${communities.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${communities.name}">${communities.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput">Реєстраційний
							номер об'єкту</label>
						<div class="col-lg-8">
							<input id="resourceNumber" class="form-control input-md"
								type="text" name="resourceNumberJson.resource_number"
								value="${userDto.resourceNumberJson.resource_number}"
								readonly>
						</div>
						<div class="control-group error">
							<form:errors path="resourceNumberJson.resource_number"
								cssClass="error" style="color:black" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput">Номер
							реєстратора</label>
						<div class="col-lg-8">
							<input id="registratorNumber" class="form-control input-md"
								type="text" name="resourceNumberJson.registrator_number"
								value="${userDto.resourceNumberJson.registrator_number}"
								readonly>
							<div class="control-group error">
								<form:errors path="resourceNumberJson.registrator_number"
									cssClass="error" style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput">Номер
							тому</label>
						<div class="col-lg-8">
							<input id="identifierNumber" class="form-control input-md"
								type="text" name="resourceNumberJson.identifier"
								value="${userDto.resourceNumberJson.identifier}" readonly>
							<div class="control-group error">
								<form:errors path="resourceNumberJson.identifier"
									cssClass="error" style="color:black" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-8">
							<input id="key" class="form-control input-md" type="text"
								name="resourceNumberJson.login" value="0"
								style="display: none">
						</div>
					</div>
				</div>
			</div>
			<div class="wrapper" style="text-align: center">
				<button type="button" id="edit" class="btn btn-primary">
					<spring:message code="label.restype.edit"></spring:message>
				</button>
				<input type="submit" id="ok"
					value=<spring:message code="label.user.button"/>
					class="btn btn-primary btn-sm" style="display: none">
			</div>
		</fieldset>
		<spring:message code="label.msg.required" />
	</form:form>
</div>

<div class="container">
	<form:form id="modalWindow" modelAttribute="resourceNumberDtoJson"
		method="post" action="modal-window" class="form-horizontal">
		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">
							<spring:message code="label.registrator.enterData" />
						</h4>
						<h5>
							<label class="control-label">Реєстраційни номер -
								804:23:17:026:79000,</label> <label class="control-label">Номер
								ресурсу - :0212,</label> <label class="control-label">Номер тому
								- *79000/**1</label>
						</h5>
					</div>
					<div class="modal-body">

						<label class="control-label"><spring:message
								code="label.registrator.tomeNumber" /></label> <input id="identifier"
							name="identifier" class="form-control input-md" type="text">
						<div class="control-group error">
							<form:errors path="identifier" cssClass="error"
								style="color:black" />
						</div>
						<label class="control-label"><spring:message
								code="label.registrator.registratorNumber" /></label> <input
							id="registrator_number" name="registrator_number"
							class="form-control input-md" type="text">
						<div class="control-group error">
							<form:errors path="registrator_number" cssClass="error"
								style="color:black" />
						</div>
						<label class="control-label"><spring:message
								code="label.registrator.objectIdentifier" /></label> <input
							id="resource_number" name="resource_number"
							class="form-control input-md" type="text">
					</div>
					<div class="control-group error">
						<form:errors path="resource_number" cssClass="error"
							style="color:black" />
					</div>
					<div class="modal-footer">
						<input type="submit" id="submit" class="btn btn-primary"
							value="Ok">
					</div>
				</div>
			</div>
		</div>
	</form:form>
</div>