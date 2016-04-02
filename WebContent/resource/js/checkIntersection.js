/**
 * author Ann
 */

var Polygons = [];
var N = 10;		 //divide side into N parts.
var tolerance = 0.000000001;

function checkIntersectionAllPolygons(polygon, Polygons){
	var intersect = false;
		
	console.log("polygons.length = " + Polygons.length);
	for (var i = 0; i<Polygons.length; i++)	{
		if (polygon != Polygons[i]) {
			intersect = checkEdgeOfFirstOnSecond(Polygons[i], polygon) || checkEdgeOfFirstOnSecond(polygon, Polygons[i]);
		}
		if (intersect) {
			return intersect;
		}
	}   
}


//check whether boundary of polygon1 intersects polygon2
function checkEdgeOfFirstOnSecond(polygon1, polygon2){
	var intersection = false;
	var vertices = polygon1.getPath();
	var pointBegin;
	var pointEnd;
	console.log("vertices Length = " + vertices.getLength());
	for (var j = 0; j < vertices.getLength()-1; j++){
		//console.log("j = " + j);
		pointBegin = vertices.getAt(j);
		pointEnd = vertices.getAt(j+1);
		intersection = checkInterseptionLinePolygon(pointBegin, pointEnd, polygon2);
		if (intersection){
			console.log("intersection " + intersection);
			return intersection;
		}
	}
	pointBegin = vertices.getAt(vertices.getLength()-1);
	pointEnd = vertices.getAt(0);	
	return checkInterseptionLinePolygon(pointBegin, pointEnd, polygon2);
}

//check whether segment (pointBegin, pointEnd) intersects polygon2
function checkInterseptionLinePolygon(pointBegin, pointEnd, polygon2){
	var pointOnVerticesLat;
	var pointOnVerticesLng;
	var pointOnVertices;
	var isInsidePolygon;
	var isOnEdge;
	for (var k=0; k<N; k++) {
		//take point on the segment
		pointOnVerticesLat = pointBegin.lat() * (N-k)/N + pointEnd.lat()*k/N;
		pointOnVerticesLng = pointBegin.lng() * (N-k)/N + pointEnd.lng()*k/N;
		pointOnVertices = new google.maps.LatLng(pointOnVerticesLat, pointOnVerticesLng);
		//is inside the polygon2
		isInsidePolygon = google.maps.geometry.poly.containsLocation(pointOnVertices, polygon2);
		
		// if yes check if it is on edge
		if (isInsidePolygon) {
			console.log("isInsidePolygon " + isInsidePolygon);
			isOnEdge = google.maps.geometry.poly.isLocationOnEdge(pointOnVertices, polygon2, tolerance);
			console.log("isOnEdge " + isOnEdge);
			if (!isOnEdge) {
				polygon2.setOptions({fillColor: "#FF0000"});
				return (!isOnEdge);
			}
		}		
	}	
return false;
}
