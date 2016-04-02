<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<h2>Паспорт ${userDto.firstName} ${userDto.lastName}</h2>
	<label class="control-label col-sm-2" for="Серія">Серія:</label>
	<div class="col-sm-10">
		<p class="form-control-static">${userDto.passport.seria}</p>
	</div>
	<label class="control-label col-sm-2" for="Номер">Номер:</label>
	<div class="col-sm-10">
		<p class="form-control-static">${userDto.passport.number}</p>
	</div>
	<label class="control-label col-sm-2" for="Видано">Видано:</label>
	<div class="col-sm-10">
		<p class="form-control-static">${userDto.passport.published_by_data}</p>
	</div>
</div>