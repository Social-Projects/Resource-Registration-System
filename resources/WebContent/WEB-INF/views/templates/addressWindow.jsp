<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<h2>Адреса ${userDto.firstName}</h2>
	<form class="form-horizontal" role="form">
		<div class="form-group">
			<label class="control-label col-sm-2" for="Область">Область:</label>
			<div class="col-sm-10">
				<p class="form-control-static">${userDto.address.region}</p>
			</div>
			<label class="control-label col-sm-2" for="Mісто">Місто:</label>
			<div class="col-sm-10">
				<p class="form-control-static">${userDto.address.city}</p>
			</div>
			<label class="control-label col-sm-2" for="Район">Район:</label>
			<div class="col-sm-10">
				<p class="form-control-static">${userDto.address.district}</p>
			</div>
			<label class="control-label col-sm-2" for="Вулиця">Вулиця:</label>
			<div class="col-sm-10">
				<p class="form-control-static">${userDto.address.street}</p>
			</div>
			<label class="control-label col-sm-2" for="Будинок">Будинок:</label>
			<div class="col-sm-10">
				<p class="form-control-static">${userDto.address.building}</p>
			</div>
			<label class="control-label col-sm-2" for="Квартира">Квартира:</label>
			<div class="col-sm-10">
				<p class="form-control-static">${userDto.address.flat}</p>
			</div>
			<label class="control-label col-sm-2" for="Індеск">Індекс:</label>
			<div class="col-sm-10">
				<p class="form-control-static">${userDto.address.postcode}</p>
			</div>
		</div>
	</form>
</div>