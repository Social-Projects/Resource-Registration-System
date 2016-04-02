<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="<c:url value='/resource/js/addResourceType.js'/>"></script>

<h2>
	<spring:message code="label.restype.add" />
</h2>

<form:form method="POST" action="addrestype" modelAttribute="newrestype"
	class="form-horizontal">

	<form:errors path="typeName" cssClass="error" style="color:red" />
	<div class="form-group">
		<label class="control-label col-sm-2"><spring:message
				code="label.restype.title" />:</label>
		<div class="col-sm-2">
			<input class="form-control" name="typeName" value="${typeName}"
				placeholder=<spring:message
                    code="label.restupe.enterName" />
				required>
		</div>

	</div>
	<div class="form-inline">
		<button type="button" id="clickmeshow" class="btn btn-primary">
			<spring:message code="label.restype.addparameter" />
		</button>
		<button type="button" id="clickmehide" class="btn btn-primary">
			<spring:message code="label.restype.hideparameter" />
		</button>
	</div>
	<br />
	<div class="form-inline">
		<div id="input1" class="clonedInput"
			style="display: none; margin: 5px">

			<label for="myparam0"><spring:message
					code="label.restype.desc" />:</label> <input id="myparam0"
				name="parameters[0].description" type="text" value=""
				class="form-control" /> <label for="myparam1"><spring:message
					code="label.restype.unitOfMeasurement" />: </label> <input id="myparam1"
				name="parameters[0].unitName" type="text" value=""
				class="form-control" /> <select id="myparam2"
				name="parameters[0].parametersType" class="form-control">
				<option value=""><spring:message
						code="label.restype.option" /></option>
				<option value="linearParameters"><spring:message
						code="label.restype.linpar" /></option>
				<option value="discreteParameters"><spring:message
						code="label.restype.dispar" /></option>
			</select>


		</div>
	</div>
	<br />
	<div id="mybuttontype">
		<input type="button" id="btnAdd" value="+" class="btn btn-primary" />
		<input type="button" id="btnDel" value="-" class="btn btn-primary" />
	</div>
	<br />
	<div class="button">
		<input type="submit" value=<spring:message code="label.save"/>
			class="btn btn-success" id="valid" />
		<button type="reset" class="btn btn-default">
			<spring:message code="label.clearall" />
		</button>
	</div>

</form:form>