var map;
var polygons = [];
var newPolygons = [];
var polygonsFromCoordinates = [];
var PS = null;
var isInsideUKRAINE = true;

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

function initialize() {
// Reading the cookie of start position
    var lastMapSearchLat = getCookie("LastMapSearchLat");
    var lastMapSearchLng = getCookie("LastMapSearchLng");
    var startPoint;
    if ((lastMapSearchLat.length > 0) && (lastMapSearchLng.length > 0)) {
        var startLat = Number(lastMapSearchLat);
        var startLng = Number(lastMapSearchLng);
        startPoint = new google.maps.LatLng(startLat, startLng);
    }
    else {
        startPoint = new google.maps.LatLng(49.8326679, 23.942196);
    }

    var mapProp = {
        center: startPoint,
        zoom: 13,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("map_canvas"), mapProp);

    //Drawing of the snappable polygon. External plugin
    var polystyle = {
                fillColor: '#008000',
                fillOpacity: 0.4,
                strokeColor: "#006400",
                strokeWeight: 3,
                clickable: false,
                zIndex: 1
    };

    PS = PolySnapper({
        map: map,
        threshold: 20,
        key: 'shift',
        keyRequired: true,
        polygons: polygons,
        polystyle: polystyle,
        //hidePOI: true,
        onEnabled: function(){
            console.log("enabled")
        },
        onDisabled: function(){
            console.log("disabled")
        }
    });

    // show polygons on edit page
    addPointsToMap(true);

    // Create the search box and link it to the UI element.
    var input = document.getElementById('gmaps-input');
    var button = document.getElementById('gmaps-show-res');
    var drawing = document.getElementById('cp-wrap');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(button);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(drawing);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function () {
        searchBox.setBounds(map.getBounds());
    });
    var markers = [];
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function () {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        // Clear out the old markers.
        markers.forEach(function (marker) {
            marker.setMap(null);
        });
        markers = [];

        // For each place, get the icon, name and location.
        var bounds = new google.maps.LatLngBounds();
        places.forEach(function (place) {
            var icon = {
                url: place.icon,
                size: new google.maps.Size(71, 71),
                origin: new google.maps.Point(0, 0),
                anchor: new google.maps.Point(17, 34),
                scaledSize: new google.maps.Size(25, 25)
            };
// Create a marker for each place.
            markers.push(new google.maps.Marker({
                map: map,
                icon: icon,
                title: place.name,
                position: place.geometry.location
            }));

//Expires date for cookie
            var d = new Date();
            d.setTime(d.getTime() + 7 * 24 * 60 * 60 * 1000);
//Deleting of the old cookie
            document.cookie = "LastMapSearchLat=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
            document.cookie = "LastMapSearchLng=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
//Add new cookie
            document.cookie = "LastMapSearchLat=" + place.geometry.location.lat() + ";expires=" + d.toUTCString();
            document.cookie = "LastMapSearchLng=" + place.geometry.location.lng() + ";expires=" + d.toUTCString();

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });



    //google.maps.event.addListener(drawingManager, 'overlaycomplete', function (event) {
    //    drawingManager.setDrawingMode(null);
    //    var polygon = event.overlay;
    //    var vertices = polygon.getPath();
    //    newPolygons.push(polygon);
    //
    //    for (var i = 0; i < vertices.getLength(); i++) {
    //        bounds.extend(vertices.getAt(i));
    //    }
    //
    //});

}

function getResources() {
    var resType = $("#resourcesTypeSelect").val();
    if (map.getBounds() === undefined) return;
    var maxLat = map.getBounds().getNorthEast().lat();
    var minLat = map.getBounds().getSouthWest().lat();
    var maxLng = map.getBounds().getNorthEast().lng();
    var minLng = map.getBounds().getSouthWest().lng();

    if (polygons.length > 0) {
        for (var i = 0; i < polygons.length; i++) {
            polygons[i].setMap(null);
        }
        polygons = [];
    }

    $("#dark_bg").show();
    $.ajax({
        data: {
            "minLat": minLat,
            "maxLat": maxLat,
            "minLng": minLng,
            "maxLng": maxLng,
            "page":0,
            "resType": resType
        },
        type: "POST",
        async: false,
        url: baseUrl.toString() + "/registrator/resource/getResourcesByAreaLimits",
        timeout: 20000,
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        dataType: 'json',
        success: function (data) {
            for (var i = 0; i < data.polygons.length; i++) {
                if (data.polygons[i].identifier === $("#identifier").val()) {
                    continue;
                }
                var polygonPath = [];
                var points = data.polygons[i].points;
                for (var j = 0; j < points.length; j++) {
                    polygonPath.push(new google.maps.LatLng(points[j].latitude, points[j].longitude));
                }

                var polygon = new google.maps.Polygon({
                    path: polygonPath, // Координаты
                    strokeColor: "#FF0000", // Цвет обводки
                    strokeOpacity: 0.8, // Прозрачность обводки
                    strokeWeight: 2, // Ширина обводки
                    fillColor: "#0000FF", // Цвет заливки
                    fillOpacity: 0.3, // Прозрачность заливки
                    snapable: true,
                    map: map
                });
                polygons.push(polygon);
            }
            //console.log("Find " + polygons.length + " polygons");
            //$("#dark_bg").hide();
        },
        error: function () {
            $("#dark_bg").hide();
            bootbox.alert(jQuery.i18n.prop('msg.error'));
        }
    });
    //return dfd.done();
}


//Function which checks does the new polygon intersect other polygons

function intersectionCheck(polygon){
    //console.log("Intersection check started!");

    $("#dark_bg").show();

    var intersection = false;
    var point;
    var i, j, k;
    var isWithinPolygon;
    var vertices;
    var sameVertice = false;
    var verticesPoly;
    var resType = $("#resourcesTypeSelect").val();
    var bounds = new google.maps.LatLngBounds();

    if (resType == "") {
        $("#resourcesTypeSelect").focus();
        bootbox.alert(jQuery.i18n.prop('msg.selectType'));
        $("#dark_bg").hide();
        return true;
    }

    vertices = polygon.getPath();
    for (i = 0; i < vertices.getLength(); i++) {
        bounds.extend(vertices.getAt(i));
    }

    map.fitBounds(bounds);

    getResources();   

    //console.log("Polygons found: "+polygons.length);

   /* for (i = 0; i < vertices.getLength(); i++) {
        point = vertices.getAt(i);
        for (j = 0; j < polygons.length; j++) {
            isWithinPolygon = google.maps.geometry.poly.containsLocation(point, polygons[j]);
            if (isWithinPolygon) {
                //console.log("Possible intersection at point: "+ i);
                sameVertice = false;
                verticesPoly = polygons[j].getPath();
                for (k = 0; k < verticesPoly.getLength(); k++) {
                    //console.log("Point: "+point+" compare: "+ verticesPoly.getAt(k));
                    if ((point.lat() == verticesPoly.getAt(k).lat()) &&
                        (point.lng() == verticesPoly.getAt(k).lng()) &&
                        (!sameVertice)) {
                        //console.log("Same point!");
                        sameVertice = true;
                    }
                }
                if (!sameVertice) {
                    console.log("Intersection found");
                    intersection = true;
                    polygons[j].setOptions({fillColor: "#FF0000"});
                }

            }
        }
    }

    for (i = 0; i < polygons.length; i++) {
        verticesPoly = polygons[i].getPath();
        for (j = 0; j < verticesPoly.getLength(); j++) {
            point = verticesPoly.getAt(j);
            isWithinPolygon = google.maps.geometry.poly.containsLocation(point, polygon);
            if (isWithinPolygon) {
                sameVertice = false;
                for (k = 0; k < vertices.getLength(); k++) {
                    if ((point.lat() == vertices.getAt(k).lat()) &&
                        (point.lng() == vertices.getAt(k).lng()) &&
                        (!sameVertice)) {
                        sameVertice = true;
                    }
                }
                if (!sameVertice) {
                    intersection = true;
                    polygons[i].setOptions({fillColor: "#FF0000"});
                }
            }

        }
    }*/
    
    
    intersection = checkIntersectionAllPolygons(polygon, newPolygons);    
    if (intersection) {
    	bootbox.alert(jQuery.i18n.prop('msg.resoursesIntersect'));
    } else { 
    	intersection = checkIntersectionAllPolygons(polygon, polygons);
    	if (intersection) {
    	bootbox.alert(jQuery.i18n.prop('msg.resoursesIntersect'));
    	} else {
	    	isInsideUKRAINE = isInsideUkraine(polygon);
	    	if (isInsideUKRAINE) {
		    	newPolygons.push(polygon);
		        polygon.setEditable(false);
	    	}
    	}
    }
    
       
    $("#dark_bg").hide();
    return intersection;
}

function cleanPoints() {
    while($('div[id^=polygon_]').length > 1) {
        $('div[id^=polygon_]').last().remove();
    }
    while ($('.clonedAreaInput').length > 1) {
        $('.clonedAreaInput').last().remove();
        if ($('.clonedAreaInput').length == 1) {
            $("#btnAddAreaPoint").removeAttr('disabled');
        }
    }
    $('.clonedAreaInput input:not(#pointNumber)').val(0);
}

function checkWithTolerance(value1, value2, tolerance) {
    if (Math.abs(value1 - value2) < tolerance) {
        return true;
    }
    else {
        return false;
    }
}

function calculateAreaPerimeter(polygon, i) {
    i = i || 0;
    //Calculation of area and perimeter of all new polygons.
    area = Number(google.maps.geometry.spherical.computeArea(polygon.getPath()));
    perimeter = Number(google.maps.geometry.spherical.computeLength(polygon.getPath()));

    //Generate html
    return "<div>" +
        "<label>"+jQuery.i18n.prop('msg.Polygon')+" "+(i+1)+": </label> " +
        "<span>"+jQuery.i18n.prop('msg.Area')+" "
        +(area / 10000).toFixed(5)+" "+jQuery.i18n.prop('msg.Area.units')+"; </span>" +
        "<span>"+jQuery.i18n.prop('msg.Perimeter')+" "
        +(perimeter).toFixed(1)+" "+jQuery.i18n.prop('msg.Perimeter.units')+" </span>"+
        "</div>";
}

$("#gmaps-show-res").click(function () {

    var resType = $("#resourcesTypeSelect").val();
    if (resType == "") {
        $("#resourcesTypeSelect").focus();
        bootbox.alert(jQuery.i18n.prop('msg.selectType'));
        return false;
    }
    //$("#dark_bg").show();
    getResources();
    //$("#dark_bg").hide();
    //console.log("Polygons: " + polygons.length)
});

//Add coordinates from map and verify them
$("#addPointsFromMap").click(function () {

    if ((newPolygons.length!=undefined) && (newPolygons.length > 0)) {
        $("#dark_bg").show();
        var infoBoxMessage = "";

        //If user entered the correct polygon and it doesn't intersect with existing polygons
        //we add points coordinates to inputs and deny to edit entered polygon. Before adding points
        //we clean the points inputs.
        cleanPoints();

        for (var i = 0; i < newPolygons.length; i++) {

            var pointsArray = newPolygons[i].getPath().getArray();
            for (var j = 0; j < pointsArray.length; j++) {
                var delimiter = String(pointsArray[j]).indexOf(",");
                var end = String(pointsArray[j]).length;
                var latitude = Number(String(pointsArray[j]).slice(1, delimiter));
                var longitude = Number(String(pointsArray[j]).slice(delimiter + 1, end - 1));

                var latitudeDegrees = Math.floor(latitude);
                var latitudeMinutes = Math.floor((latitude - latitudeDegrees) * 60);
                var latitudeSeconds = ((latitude - latitudeDegrees) * 60 - latitudeMinutes) * 60;
                var longitudeDegrees = Math.floor(longitude);
                var longitudeMinutes = Math.floor((longitude - longitudeDegrees) * 60);
                var longitudeSeconds = ((longitude - longitudeDegrees) * 60 - longitudeMinutes) * 60;

                addNewPoint(i,
                    latitudeDegrees, latitudeMinutes, latitudeSeconds,
                    longitudeDegrees, longitudeMinutes, longitudeSeconds);
            }
            newPolygons[i].setOptions({fillColor: "#003400"});

            //Calculation of area and perimeter of all new polygons.
            infoBoxMessage += calculateAreaPerimeter(newPolygons[i], i);
        }
        $("#infoBox").html(infoBoxMessage);

        $("#dark_bg").hide();
    }
    else {
        bootbox.alert(jQuery.i18n.prop('msg.enterPolygon'));
    }
});

$(document).on("click", "#addPointsToMap", function () {
    addPointsToMap(false)
});

function addPointsToMap(allowEmptyArea){

    var polygonsDiv = $('div[id^=polygon_]');
    var enoughPoints = true;
    var infoBoxMsg = "";
    var alertMsg = "";

    polygonsDiv.each(function (index) {
        if ($(this).find('.clonedAreaInput').length < 3) {
            if (alertMsg.length > 0) {
                alertMsg += "<br>";
            }
            alertMsg += jQuery.i18n.prop('msg.Polygon') +" "+
                (index+1) + ": " +
                jQuery.i18n.prop('msg.minPoints');
            enoughPoints = false;
        }
    });

    if ((allowEmptyArea) && (!enoughPoints)) {
        return;
    }

    if (alertMsg.length > 0) {
        bootbox.alert(alertMsg);
    }

    if(enoughPoints){
        newPolygons.forEach(function(polygon){
            polygon.setMap(null);
        });
        newPolygons = [];
        $("#infoBox").html(jQuery.i18n.prop('msg.infoBox'));
        polygonsFromCoordinates.forEach(function(polygon){
            polygon.setMap(null);
        });

        polygonsDiv.each(function (index) {
            var polygonPath = [];
            $(this).find('.clonedAreaInput').each(function () {
                var latGrad = Number($(this).find('#myparam1').val());
                var latMin = Number($(this).find('#myparam2').val());
                var latSec = Number($(this).find('#myparam3').val());
                var lngGrad = Number($(this).find('#myparam4').val());
                var lngMin = Number($(this).find('#myparam5').val());
                var lngSec = Number($(this).find('#myparam6').val());

                var lat = latGrad + latMin / 60 + latSec / 3600;
                var lng = Number(lngGrad + lngMin / 60 + lngSec / 3600);

                var newpoint = new google.maps.LatLng(lat, lng);
                polygonPath.push(newpoint);
            });

            var polygonFromCoordinates = new google.maps.Polygon({
                path: polygonPath, // Координаты
                strokeColor: "#FF0000", // Цвет обводки
                strokeOpacity: 0.8, // Прозрачность обводки
                strokeWeight: 2, // Ширина обводки
                fillColor: "#008000", // Цвет заливки
                fillOpacity: 0.3, // Прозрачность заливки
                map: map
            });

            intersectionCheck(polygonFromCoordinates);
            polygonsFromCoordinates.push(polygonFromCoordinates);
            infoBoxMsg += calculateAreaPerimeter(polygonFromCoordinates, index);
        });
        $("#infoBox").html(infoBoxMsg);
    }
}

google.maps.event.addDomListener(window, 'load', initialize);

$("#cp-wrap").on("click", "a", function(){

    var inactive = $(this).hasClass("inactiveLink");

    if(!inactive) {

        var action = $(this).data("action");

        if (action == 'new') {
            PS.enable();
            $(this).closest(".toggle").removeClass("active");
            $(this).closest(".toggle").siblings("span").addClass("active");
        }
        else if (action == 'save') {
            if(PS.polygon().getPath().length > 2) {

                var intersection = intersectionCheck(PS.polygon());
                //if (!intersection) {
                if (!intersection && isInsideUKRAINE) { 	
                    PS.save();
                    $(this).closest(".toggle").removeClass("active");
                    $(this).closest(".toggle").siblings("span").addClass("active");
                }
            }
            else {
                bootbox.alert(jQuery.i18n.prop('msg.enterPolygon'));
            }
        }
        else {
            PS.disable();
            $(this).closest(".toggle").removeClass("active");
            $(this).closest(".toggle").siblings("span").addClass("active");
        }
    }
    else {
        bootbox.alert(jQuery.i18n.prop('msg.forbidden'));
    }

});

$(document).on("click", "#mapManual", function(){
    var spoiler = $(this).siblings(".spoiler");
    var icon = $(this).find(".glyphicon");
    if (!($(this).hasClass("active"))) {
        spoiler.slideDown(200);
        $(this).addClass("active");
        icon.each(function(){
            if($(this).hasClass("hidden")) {
                $(this).removeClass("hidden");
            }
            else {
                $(this).addClass("hidden");
            }
        });
    }
    else {
        spoiler.slideUp(200);
        $(this).removeClass("active");
        icon.each(function(){
            if($(this).hasClass("hidden")) {
                $(this).removeClass("hidden");
            }
            else {
                $(this).addClass("hidden");
            }
        });
    }
});

$(document).on("click", "#allUkraine", function(){
    var isChecked = $(this).is(':checked');
    if(isChecked) {
        cleanPoints();
        newPolygons.forEach(function(polygon){
            polygon.setMap(null);
        });
        newPolygons = [];
        $("#infoBox").html(jQuery.i18n.prop('msg.infoBox'));

        addNewPoint(0,200,0,0,200,0,0);
        $("#latLngAdd").hide();
        //We make the link "Add polygon" inactive
        $(".toggle a").addClass("inactiveLink");
        $("#btnAddAreaPoint").attr('disabled', 'disabled');
        $('#btnDelAreaPoint').attr('disabled', 'disabled');
    }
    else {
        cleanPoints();
        $("#latLngAdd").show();
        $(".inactiveLink").removeClass("inactiveLink");
        $("#btnAddAreaPoint").removeAttr('disabled');
    }
});

$(document).on("click", "#clearAllPoints", function(){
    cleanPoints();
    $(".inactiveLink").removeClass("inactiveLink");
});

$(document).on("click", "#delAllPolygons", function(){
    cleanPoints();
    newPolygons.forEach(function(polygon){
        polygon.setMap(null);
    });
    newPolygons = [];
    $("#infoBox").html(jQuery.i18n.prop('msg.infoBox'));
    $(".inactiveLink").removeClass("inactiveLink");
});

$(document).on("click", "#resetForm", function(){
    cleanPoints();
    //$("input[id*='myparam']").removeAttr("disabled");
    $("#typeParameters").html("");
    $("#reasonInclusion").text("");
    $("#infoBox").html(jQuery.i18n.prop('msg.infoBox'));
    $('#will').attr("disabled","disabled");
    $('#pass').attr("disabled","disabled"); 
    $('#otherDocs').attr("disabled","disabled"); 
    newPolygons.forEach(function (polygon) {
        polygon.setMap(null);
    });
    newPolygons = [];
    $(".inactiveLink").removeClass("inactiveLink");
});

$(document).on("click", "#submitForm", function(){
    var points = $('.clonedAreaInput');
    if(points.length == 2) {
        bootbox.alert(jQuery.i18n.prop('msg.twoPoints'));
        return false;
    }
    if((newPolygons.length > 0)&&(points.length > 1)){
        var latArray = [];
        var lngArray = [];
        var different = false;

        newPolygons.forEach(function(newPolygon) {
            var path = newPolygon.getPath();
            path.forEach(function(point){
                latArray.push(point.lat());
                lngArray.push(point.lng());
            });
        });

        var i=0;
        points.each(function(){
            var latGrad = Number($(this).find('#myparam1').val());
            var latMin = Number($(this).find('#myparam2').val());
            var latSec = Number($(this).find('#myparam3').val());
            var lngGrad = Number($(this).find('#myparam4').val());
            var lngMin = Number($(this).find('#myparam5').val());
            var lngSec = Number($(this).find('#myparam6').val());

            var lat = latGrad + latMin/60 + latSec/3600;
            var lng = lngGrad + lngMin/60 + lngSec/3600;

            //For unknown reasons when we create a new google point LatLng
            //the lng value changes a little bit. That's why we had to create
            //function checkWithTolerance where we calculate the difference
            //between two points and compare it with tolerance.
            var tolerance = 0.0000000001;

            if ((!checkWithTolerance(lat, latArray[i], tolerance)) ||
                (!checkWithTolerance(lng, lngArray[i], tolerance))) {
                console.log("Difference in point "+i+":");
                console.log("lat: "+lat+" lng: "+lng);
                console.log("lat: "+latArray[i]+" lng: "+lngArray[i]);
                $(this).find("input").css("background","rgba(255,0,0,0.4)");
                different = true;
            }
            i++;
        });
        if(different) {
            bootbox.alert(jQuery.i18n.prop('msg.differentPoints'));
            return false;
        }
    }
    else if ((points.length == 1)
        &&(points.find("#myparam1").val()==200)
        &&(points.find("#myparam4").val()==200)) {
        return true;
    }
    else {
        bootbox.alert(jQuery.i18n.prop('msg.enterPolygon'));
        return false;
    }
});

$(document).on("click", ".delPoint", function(){
    var thisDel = $(this);
    var polygonDiv = $(this).closest("div[id^=polygon_]");
    if(polygonDiv.find(".clonedAreaInput").length > 1) {
        var pointNum = $(this).closest("div[id^=areaInput]").find("#pointNumber").val();
        $(this).closest("div[id^=areaInput]").remove();
        //Changing the index of next points
        polygonDiv.find(".clonedAreaInput").each(function(index){
           if (index >= pointNum-1) {
               $(this).find("#pointNumber").val(index+1);
               $(this).attr("id",$(this).attr("id").replace(
                   'areaInput'+(index+2),
                   'areaInput'+(index+1)
               ));
               $(this).find('input').each(function() {
                   $(this).attr( "name",$(this).attr("name").replace(
                       'points[' + (index+1) + ']',
                       'points[' + index + ']'));
               });

           }
        });
    }
    else {
        if ($("div[id^=polygon_]").length > 1) {
            bootbox.confirm(jQuery.i18n.prop('msg.delPolygon'),function(){
                var polygonNum = Number(polygonDiv.find(".polygonIndex").html())-1;
                polygonDiv.remove();
                //Changing the polygon indexes in all input fields
                $("div[id^=polygon_]").each(function(index){
                    if (index >= polygonNum) {
                        $(this).attr("id", $(this).attr("id").replace(
                            'polygon_'+(index+2), 'polygon_'+(index+1))
                        );
                        $(this).find(".polygonIndex").html(index+1);
                        $(this).find('input').each(function(){
                            $(this).attr( "name",$(this).attr("name").replace(
                                'poligons['+(index+1)+']',
                                'poligons[' + index + ']'));
                        });
                    }
                });
            });
        }
        else {
            $(this).closest("div[id^=areaInput]").find('#pointNumber').val(1);
            $(this).closest("div[id^=areaInput]").find('input:not(#pointNumber)').val(0);
            bootbox.alert(jQuery.i18n.prop('msg.lastPoint'));
        }
    }
});

$(document).on("click", "#addPolygon", function(){
    if($('div[id^=polygon_]').last().find('.clonedAreaInput').length >=3 ) {
        var num = $('div[id^=polygon_]').length;
        console.log("Polygons count: " + num);
        addNewPolygon(num);
    }
    else {
        bootbox.alert(jQuery.i18n.prop('msg.minPoints'));
    }
});