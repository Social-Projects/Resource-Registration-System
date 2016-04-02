<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/css/cssload.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/css/searchOnMap.css'/>">

<div class="container"
	style="margin-bottom: 25px; line-height: 28px; vertical-align: top;">
	<div class="col-sm-12 controls">
		<span id="searchByPointButton"
			class="btn btn-primary toggle-button col-sm-3">
			<h4>
				<spring:message code="label.search.byCoordinate" />
			</h4>
		</span> <span id="searchByAreaButton"
			class="btn btn-primary toggle-button col-sm-3">
			<h4>
				<spring:message code="label.search.byRegion" />
			</h4>
		</span> <span id="searchByParameterButton"
			class="btn btn-primary toggle-button col-sm-3">
			<h4>
				<spring:message code="label.search.byParameter" />
			</h4>
		</span>
	</div>
	<div id="searchByPointDiv" class="col-md-12 searchDiv">
		<p>
			<spring:message code="label.search.byCoordinate.massage" />
			:
		</p>

		<div class="col-sm-5" style="padding-bottom: 5px;">
			<label class="col-sm-3" style="padding-top: 6px; margin: 0;">
				<spring:message code="label.resource.latitude" />:
			</label> <input type="text" class="latitudeDegrees form-control"
				placeholder="<spring:message
                    code="label.resource.coordinates.degrees" />"
				style="width: 90px; display: inline-block;"> ° <input
				type="text" class="latitudeMinutes form-control"
				placeholder="<spring:message
                    code="label.resource.coordinates.minutes" />"
				style="width: 75px; display: inline-block;"> ' <input
				type="text" class="latitudeSeconds form-control"
				placeholder="<spring:message
                    code="label.resource.coordinates.seconds" />"
				style="width: 125px; display: inline-block;"> "
		</div>
		<div class="col-sm-5" style="padding-bottom: 5px;">
			<label class="col-sm-3" style="padding-top: 6px; margin: 0;"><spring:message
					code="label.resource.longitude" />: </label> <input type="text"
				class="longitudeDegrees form-control"
				placeholder="<spring:message
                    code="label.resource.coordinates.degrees" />"
				style="width: 90px; display: inline-block;"> ° <input
				type="text" class="longitudeMinutes form-control"
				placeholder="<spring:message
                    code="label.resource.coordinates.minutes" />"
				style="width: 75px; display: inline-block;"> ' <input
				type="text" class="longitudeSeconds form-control"
				placeholder="<spring:message
                    code="label.resource.coordinates.seconds" />"
				style="width: 125px; display: inline-block;"> "
		</div>
		<div class="col-sm-2">
			<button id="searchOnMapButton" class="btn btn-success">
				<spring:message code="label.menu.search" />
			</button>
		</div>
	</div>
	<div id="searchByAreaDiv" class="col-md-12 searchDiv">
		<p>
			<spring:message code="label.search.byRegion.massage" />
			:
		</p>

		<div id="first_point">
			<div class="col-sm-5" style="padding-bottom: 5px;">
				<label class="col-sm-3" style="padding-top: 6px; margin: 0;"><spring:message
						code="label.resource.latitude" />: </label> <input type="text"
					class="latitudeDegrees form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.degrees" />"
					style="width: 90px; display: inline-block;"> ° <input
					type="text" class="latitudeMinutes form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.minutes" />"
					style="width: 75px; display: inline-block;"> ' <input
					type="text" class="latitudeSeconds form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.seconds" />"
					style="width: 125px; display: inline-block;"> "
			</div>
			<div class="col-sm-5" style="padding-bottom: 5px;">
				<label class="col-sm-3" style="padding-top: 6px; margin: 0;"><spring:message
						code="label.resource.longitude" />: </label> <input type="text"
					class="longitudeDegrees form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.degrees" />"
					style="width: 90px; display: inline-block;"> ° <input
					type="text" class="longitudeMinutes form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.minutes" />"
					style="width: 75px; display: inline-block;"> ' <input
					type="text" class="longitudeSeconds form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.seconds" />"
					style="width: 125px; display: inline-block;"> "
			</div>
		</div>
		<div id="second_point">
			<div class="col-sm-5" style="padding-bottom: 5px;">
				<label class="col-sm-3" style="padding-top: 6px; margin: 0;"><spring:message
						code="label.resource.latitude" />: </label> <input type="text"
					class="latitudeDegrees form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.degrees" />"
					style="width: 90px; display: inline-block;"> ° <input
					type="text" class="latitudeMinutes form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.minutes" />"
					style="width: 75px; display: inline-block;"> ' <input
					type="text" class="latitudeSeconds form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.seconds" />"
					style="width: 125px; display: inline-block;"> "
			</div>
			<div class="col-sm-5" style="padding-bottom: 5px;">
				<label class="col-sm-3" style="padding-top: 6px; margin: 0;"><spring:message
						code="label.resource.longitude" />: </label> <input type="text"
					class="longitudeDegrees form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.degrees" />"
					style="width: 90px; display: inline-block;"> ° <input
					type="text" class="longitudeMinutes form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.minutes" />"
					style="width: 75px; display: inline-block;"> ' <input
					type="text" class="longitudeSeconds form-control"
					placeholder="<spring:message
                    code="label.resource.coordinates.seconds" />"
					style="width: 125px; display: inline-block;"> "
			</div>
		</div>
		<div class="col-sm-2">
			<button id="searchOnMapButton_area" class="btn btn-success">
				<spring:message code="label.menu.search" />
			</button>
		</div>
	</div>
	<div id="searchByParameterDiv" class="col-md-12 searchDiv">
		<c:if test="${not empty resourceTypes}">
			<div style="padding-bottom: 15px;">
				<label class=""><spring:message
						code="label.resource.selectType" />:</label> <select
					id="resourcesTypeSelect" class="form-control"
					style="width: auto; display: inline">
					<c:forEach items="${resourceTypes}" var="resourceType">
						<option value="${resourceType.typeId}">${resourceType.typeName}</option>
					</c:forEach>
				</select>
			</div>
			<div id="searchParameters" class="container"></div>
		</c:if>
	</div>
</div>
<div class="container" id="searchResult"></div>
<div id="paginationDiv" class="col-md-12"></div>
<div id="resTypeFilter" class="col-md-12"></div>
<div id="map_canvas" class="container" style="height: 700px;"></div>

<%--Search fild for Google Map--%>
<p>
	<input id="gmaps-input" class="controls gmap-input"
		style="width: 300px;" type="text"
		placeholder=<spring:message code="label.menu.searchOnMap"/>>
</p>

<%--AJAX Loader for the dark display--%>
<div id="dark_bg">
	<div class="windows8">
		<div class="wBall" id="wBall_1">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_2">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_3">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_4">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_5">
			<div class="wInnerBall"></div>
		</div>
	</div>
</div>

<script
	src="http://maps.googleapis.com/maps/api/js?libraries=drawing,places,geometry&sensor=false"></script>
<script type="text/javascript"
	src="<c:url value='/resource/js/searchOnMap.js'/>"></script>