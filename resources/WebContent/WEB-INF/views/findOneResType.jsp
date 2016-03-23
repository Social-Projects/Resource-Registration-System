<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<title>Реєстратор</title>

</head>

<body>

<form:form method="POST" action="edit/{typeName}" modelAttribute="oneResType">

	<div class="caption-res-type">
		Назва:
		<form:input path="typeName" placeholder="Введіть назву тут"
			required="required" /> 
	</div><br/>
	

	<!-- <div id="input1" class="clonedInput" style="display:none" >
		Опис параметру: <input id="myparam0" name="parameters[0].description"
			type="text" value="" /> Oдиниці вимірювання: <input id="myparam1"
			name="parameters[0].unitName" type="text" value="" /> <select
			id="myparam2" name="parameters[0].parametersType"
			class="selectpicker">
			<option value="">Виберіть тип параметру</option>
			<option value="linearParameters">Лінійний параметр</option>
			<option value="discreteParameters">Дискретний параметр</option>
		</select>
	</div> -->
	<div id="mybuttontype">
		<input type="button" id="btnAdd" value="+" class="btn btn-primary" />
		<input type="button" id="btnDel" value="-" class="btn btn-primary" />
	</div>
	<br />
	<div class="button">
		<input type="submit" value="Додати" class="btn btn-success">
	</div>
</form:form>

	<%-- <table class="layout">
		<caption>Опис вибраного типу ресурсу:</caption>
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Discrete parameters</th>
				<th>Linear parameters</th>

			</tr>
		</thead>

		<tbody>
	
			<c:out value="${oneResType.typeName}"/>
			<c:forEach items="${oneResType.discreteParameters}" var="dis_param">

				<div class="block">
					<div>опис: ${dis_param.description}</div>
					<div>ом: ${dis_param.unitName}</div>
				</div>

			</c:forEach>

			<c:forEach items="${oneResType.linearParameters}" var="lin_param">
				<div class="block">
					<div>опис: ${lin_param.description}</div>
					<div>ОМ: ${lin_param.unitName}</div>
				</div>

			</c:forEach>

		</tbody>
	</table>  --%>


</body>
</html>