/**
 * author Pavlo Antentyk
 */
// create map
function createMap(){
	map = L.map('openMap',  {editable: true}).setView(startPoint, 12);
	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
		maxZoom: 25,
		attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
	}).addTo(map);
}

function drawPolygons(polygons) {
	clearMap(map);
	var i;
	var infoWindowContent = "<table id='infowindow_table'><tr><th>" + jQuery.i18n.prop('msg.description') + "</th><th>" + jQuery.i18n.prop('msg.subclass') + "</th><th></th></tr>";
	var contentString = "";
	if (polygons && polygons.length>0) {
		for (i = 0; i < polygons.length; i++) {
			
			var polygon = polygons[i];
			var points = polygon.points;
			var polygonPath = [];
			
			for (var j = 0; j < points.length; j++) {
		        var myLatLng = [points[j].latitude, points[j].longitude];
		        polygonPath.push(myLatLng);
		       
		      }
			
			var drawedPolygon = L.polygon(polygonPath, {color:'red', clickable:'true', weight:'3'}).addTo(map);
			
			drawedPolygon.on('click',function(e){
				console.log(e.target);
			});
			
			contentString = "<tr>" + "<td>" + polygon.resourceDescription + "</td>" + "<td>" + polygon.resourceType
			+ "</td>" + "<td><a href='" + baseUrl.toString() + "/registrator/resource/get/?id="
			+ polygon.identifier + "'><i>Детальніше</i></a> </td>" + "</tr>";
			drawedPolygon.bindPopup(infoWindowContent+contentString);
			
		}
	}
}

function clearMap(m) {
    for(i in m._layers) {
        if(m._layers[i]._path != undefined && m._layers[i].exeptClear!= true) {
            try {
                m.removeLayer(m._layers[i]);
            }
            catch(e) {
                console.log("problem with " + e + m._layers[i]);
            }
        }
    }
}
