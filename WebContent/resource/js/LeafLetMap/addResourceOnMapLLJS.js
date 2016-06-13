var map;
var polygons = [];
var newPolygons = [];
var polygonsFromCoordinates = [];
var PS = null;
var activePolygon;
var resType;

function getCookie(cname) {
  var name = cname + "=";
  var ca = document.cookie.split(';');
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ')
      c = c.substring(1);
    if (c.indexOf(name) == 0)
      return c.substring(name.length, c.length);
  }
  return "";
}

function getResources() {
  //var resType = $("#resourcesTypeSelect").val();
  var identifier = $("#identifier").val();	
  resType = $("#resourcesTypeSelect").val();
  if (map.getBounds() === undefined) return;
  var bounds = map.getBounds();
  var maxLat = bounds._northEast.lat;
  var minLat = bounds._southWest.lat;
  var maxLng = bounds._northEast.lng;
  var minLng = bounds._southWest.lng;

  $("#dark_bg").show();
  $.ajax({
    data : {
      "minLat" : minLat,
      "maxLat" : maxLat,
      "minLng" : minLng,
      "maxLng" : maxLng,
      "page" : 0,
      "resType" : resType
    },
    type : "POST",
    async : false,
    url : baseUrl.toString() + "/registrator/resource/getResourcesByAreaLimits",
    timeout : 20000,
    contentType : "application/x-www-form-urlencoded;charset=UTF-8",
    dataType : 'json',
    success : function(data) {
    	
    	//except  polygons which are editing
    	var exceptPoly = data.polygons;
    	for(var i =0; i<data.polygons.length; i++){
    		if(data.polygons[i].identifier==identifier){
    			exceptPoly.splice(i,1);
    			i--;
    		}
    	}
    	
    	drawPolygons(exceptPoly);
    	
      $("#dark_bg").hide();
    },
    error : function() {
      $("#dark_bg").hide();
      bootbox.alert(jQuery.i18n.prop('msg.error'));
    }
  });
}


function cleanPoints() {
  while ($('div[id^=polygon_]').length > 1) {
    $('div[id^=polygon_]').last().remove();
  }
  while ($('.clonedAreaInput').length > 1) {
    $('.clonedAreaInput').last().remove();
    if ($('.clonedAreaInput').length == 1) {
      $("#btnAddAreaPoint").removeAttr('disabled');
    }
  }
  $('.clonedAreaInput input:not(#pointNumber)').val(0);
  
  newPolygons = [];
  polygons = [];
}

function checkWithTolerance(value1, value2, tolerance) {
  if (Math.abs(value1 - value2) < tolerance) {
    return true;
  } else {
    return false;
  }
}

function calculateAreaPerimeter(polygon, i) {
  i = i || 0;
  //Calculation of area and perimeter of all new polygons.
  var area =  L.GeometryUtil.geodesicArea(polygon.getLatLngs());
  area = L.GeometryUtil.readableArea(area);
  perimeter = L.GeometryUtil.polyPerimetr(polygon);
  console.log("area="+area+"  perim="+perimeter);

  //area = Math.round(area * 100) / 100;
  //perimeter = Math.round(perimeter * 100) / 100;

  //Generate html
  var str = "<div>" + "<label>" + jQuery.i18n.prop('msg.Polygon') + " " + (i + 1) + ": </label> " + "<span>" + jQuery.i18n.prop('msg.Area')
  	  + " " + area + " " + jQuery.i18n.prop('msg.Area.units') + "; </span>" + "<span>"
      + jQuery.i18n.prop('msg.Perimeter') + " " + (perimeter).toFixed(1) + " " + jQuery.i18n.prop('msg.Perimeter.units') + " </span>" + "</div>";
  var retrn = new Object();
  retrn.str = str;
  retrn.area = area;
  retrn.perimeter = (perimeter).toFixed(1);
  return retrn;
}




//this function called (by map event listener) every time when any changes(update or create) occurred with polygons on map
//it's copy polygons coordinates to appropriate fields on UI by addNewPoint method
function updatePointOnUI(polygonsFromMap){
	//console.log(polygonsFromMap);
	cleanPoints();
	var infoBoxMessage = "";
	polygons = polygonsFromMap;
	for(var i = 0; i<polygonsFromMap.length; i++){
		isPolygonInsideUkraine(polygonsFromMap[i]);
		infoBoxMessage += calculateAreaPerimeter(polygonsFromMap[i], i).str;
		
		putParameter("PERIMETER", calculateAreaPerimeter(polygonsFromMap[i], i).perimeter, polygonsFromMap.length,i);
        putParameter("AREA", calculateAreaPerimeter(polygonsFromMap[i], i).area, polygonsFromMap.length,i);
		
		var points = polygonsFromMap[i]._latlngs;
		var polygonPath = [];
		for (var j = 0; j<points.length; j++){
			var point = points[j];
			var latitude = point.lat;
		    var longitude = point.lng;
		    polygonPath.push(point);

		    var latitudeDegrees = Math.floor(latitude);
		    var latitudeMinutes = Math.floor((latitude - latitudeDegrees) * 60);
		    var latitudeSeconds = ((latitude - latitudeDegrees) * 60 - latitudeMinutes) * 60;
		    var longitudeDegrees = Math.floor(longitude);
		    var longitudeMinutes = Math.floor((longitude - longitudeDegrees) * 60);
		    var longitudeSeconds = ((longitude - longitudeDegrees) * 60 - longitudeMinutes) * 60;
	
		    addNewPoint(i, latitudeDegrees, latitudeMinutes, latitudeSeconds, longitudeDegrees, longitudeMinutes, longitudeSeconds);
		}
		
		newPolygons.push(polygonPath);
	}
	$("#infoBox").html(infoBoxMessage);
};




// this function adds polygons to map by points typed in text fields
function addPointsToMap(allowEmptyArea) {

  var polygonsDiv = $('div[id^=polygon_]');
  var enoughPoints = true;
  var infoBoxMsg = "";
  var alertMsg = "";

  polygonsDiv.each(function(index) {
    if ($(this).find('.clonedAreaInput').length < 3) {
      if (alertMsg.length > 0) {
        alertMsg += "<br>";
      }
      alertMsg += jQuery.i18n.prop('msg.Polygon') + " " + (index + 1) + ": " + jQuery.i18n.prop('msg.minPoints');
      enoughPoints = false;
    }
  });

  if ((allowEmptyArea) && (!enoughPoints)) {
    return;
  }

  if (alertMsg.length > 0) {
    bootbox.alert(alertMsg);
  }

  if (enoughPoints) {
	  
    newPolygons = [];
    $("#infoBox").html(jQuery.i18n.prop('msg.infoBox'));

    polygonsDiv.each(function(index) {
      var polygonPath = [];
      $(this).find('.clonedAreaInput').each(function() {
        var latGrad = Number($(this).find('#myparam1').val());
        var latMin = Number($(this).find('#myparam2').val());
        var latSec = Number($(this).find('#myparam3').val());
        var lngGrad = Number($(this).find('#myparam4').val());
        var lngMin = Number($(this).find('#myparam5').val());
        var lngSec = Number($(this).find('#myparam6').val());

        var lat = latGrad + latMin / 60 + latSec / 3600;
        var lng = Number(lngGrad + lngMin / 60 + lngSec / 3600);

        //var newpoint = [lat, lng];
        var newpoint = new Object();
        newpoint.lat = lat;
        newpoint.lng = lng;
        
        polygonPath.push(newpoint);
      });

      newPolygons.push(polygonPath);
      deletePolygons(polygons);
      polygons = drawPolygonsfromUI(newPolygons);
      
      for(var i = 0; i<polygons.length; i++){
  		putParameter("PERIMETER", calculateAreaPerimeter(polygons[i], i).perimeter, polygons.length,i);
        putParameter("AREA", calculateAreaPerimeter(polygons[i], i).area, polygons.length,i);
      }
      
      var infoBoxMessage = "";
      polygons.forEach(function(upoly, index){
    	  infoBoxMessage += calculateAreaPerimeter(upoly, index).str;
      });
      
    });
    $("#infoBox").html(infoBoxMsg);
  }
}



function putParameter(name, value, length, inputNumber) {
	  var param = $("div[data-calculated="+ name + "]");

	  if (param.length > 0) {
	    var id = param[0].attributes.getNamedItem("data-calculatedId").value;
	    for (var i = 0; length - param.length  > i; i++) {
	      addDiscreteValue(id, "", "");
	    }
	    if (inputNumber != undefined){
	    	$("input[name='resourceDiscrete[" + id + "].valueDiscretes[" + inputNumber + "].value']").val(value);
	    }else{
	    	$("input[name='resourceDiscrete[" + id + "].valueDiscretes[" + (length - 1) + "].value']").val(value);
	    }
	    
	    
	  }
}

//old version
/*function putParameter(name, value, length) {
	  var param = $("div[data-calculated="+ name + "]");

	  if (param.length > 0) {
	    var id = param[0].attributes.getNamedItem("data-calculatedId").value;
	    for (var i = 0; length - param.length  > i; i++) {
	      addDiscreteValue(id, "", "");
	    }
	    $("input[name='resourceDiscrete[" + id + "].valueDiscretes[" + (length - 1) + "].value']").val(value);
	  }
}*/

function updateMap(){
	resType = $("#resourcesTypeSelect").val();
	if (resType){
		getResources(polygons);
	}
	
	addPointsToMap(true);
}


$(function(){
	
	updateMap();
	
	$( "#resourcesTypeSelect" ).change(function() {
		  resType = $("#resourcesTypeSelect").val();
		  if (resType == undefined) {
		    $("#resourcesTypeSelect").focus();
		    bootbox.alert(jQuery.i18n.prop('msg.selectType'));
		    return false;
		  }
		  getResources();
		  
	});
	
	$(document).on("click", "#addPointsToMap", function() {
		  addPointsToMap(false)
	});
	$(document).on("click", "#submitForm", function() {
		  
		if(checkInterseption(polygons)){
			bootbox.alert(jQuery.i18n.prop('msg.resoursesIntersect'));
			return false;
		}
		if (!isPolygonsInsideUkraine(polygons)) return false;
		var points = $('.clonedAreaInput');
		  if (points.length == 2) {
		    bootbox.alert(jQuery.i18n.prop('msg.twoPoints'));
		    return false;
		  }
		  if ((newPolygons.length > 0) && (points.length > 1)) {
		    var latArray = [];
		    var lngArray = [];
		    var different = false;

		   newPolygons.forEach(function(newPolygon) {
		      var path = newPolygon;
		      path.forEach(function(point) {
		        latArray.push(point.lat);
		        lngArray.push(point.lng);
		      });
		   });

		    var i = 0;
		    points.each(function() {
		      var latGrad = Number($(this).find('#myparam1').val());
		      var latMin = Number($(this).find('#myparam2').val());
		      var latSec = Number($(this).find('#myparam3').val());
		      var lngGrad = Number($(this).find('#myparam4').val());
		      var lngMin = Number($(this).find('#myparam5').val());
		      var lngSec = Number($(this).find('#myparam6').val());

		      var lat = latGrad + latMin / 60 + latSec / 3600;
		      var lng = lngGrad + lngMin / 60 + lngSec / 3600;

		      //For unknown reasons when we create a new google point LatLng
		      //the lng value changes a little bit. That's why we had to create
		      //function checkWithTolerance where we calculate the difference
		      //between two points and compare it with tolerance.
		      var tolerance = 0.0000000001;

		      if ((!checkWithTolerance(lat, latArray[i], tolerance)) || (!checkWithTolerance(lng, lngArray[i], tolerance))) {
		        console.log("Difference in point " + i + ":");
		        console.log("lat: " + lat + " lng: " + lng);
		        console.log("lat: " + latArray[i] + " lng: " + lngArray[i]);
		        $(this).find("input").css("background", "rgba(255,0,0,0.4)");
		        different = true;
		      }
		      i++;
		    });
		    if (different) {
		      bootbox.alert(jQuery.i18n.prop('msg.differentPoints'));
		      return false;
		    }
		  } else if ((points.length == 1) && (points.find("#myparam1").val() == 200) && (points.find("#myparam4").val() == 200)) {
		    return true;
		  } else {
		    bootbox.alert(jQuery.i18n.prop('msg.enterPolygon'));
		    return false;
		  }
		});





	$(document).on("click", "#mapManual", function() {
	  var spoiler = $(this).siblings(".spoiler");
	  var icon = $(this).find(".glyphicon");
	  if (!($(this).hasClass("active"))) {
	    spoiler.slideDown(200);
	    $(this).addClass("active");
	    icon.each(function() {
	      if ($(this).hasClass("hidden")) {
	        $(this).removeClass("hidden");
	      } else {
	        $(this).addClass("hidden");
	      }
	    });
	  } else {
	    spoiler.slideUp(200);
	    $(this).removeClass("active");
	    icon.each(function() {
	      if ($(this).hasClass("hidden")) {
	        $(this).removeClass("hidden");
	      } else {
	        $(this).addClass("hidden");
	      }
	    });
	  }
	});

	$(document).on("click", "#allUkraine", function() {
	  var isChecked = $(this).is(':checked');
	  if (isChecked) {
	    cleanPoints();
	    newPolygons.forEach(function(polygon) {
	      polygon.setMap(null);
	    });
	    newPolygons = [];
	    $("#infoBox").html(jQuery.i18n.prop('msg.infoBox'));

	    addNewPoint(0, 200, 0, 0, 200, 0, 0);
	    $("#latLngAdd").hide();
	    //We make the link "Add polygon" inactive
	    $(".toggle a").addClass("inactiveLink");
	    $("#btnAddAreaPoint").attr('disabled', 'disabled');
	    $('#btnDelAreaPoint').attr('disabled', 'disabled');
	  } else {
	    cleanPoints();
	    $("#latLngAdd").show();
	    $(".inactiveLink").removeClass("inactiveLink");
	    $("#btnAddAreaPoint").removeAttr('disabled');
	  }
	});

	$(document).on("click", "#clearAllPoints", function() {
	  	deletePolygons(polygons);
		cleanPoints();
	  $(".inactiveLink").removeClass("inactiveLink");
	});

	/*$(document).on("click", "#delAllPolygons", function() {
	  cleanPoints();
	  newPolygons.forEach(function(polygon) {
	    polygon.setMap(null);
	  });
	  newPolygons = [];
	  $("#infoBox").html(jQuery.i18n.prop('msg.infoBox'));
	  $(".inactiveLink").removeClass("inactiveLink");
	});*/

	$(document).on("click", "#resetForm", function() {
	  cleanPoints();
	  //$("input[id*='myparam']").removeAttr("disabled");
	  $("#typeParameters").html("");
	  $("#reasonInclusion").text("");
	  $("#infoBox").html(jQuery.i18n.prop('msg.infoBox'));
	  $('#will').attr("disabled", "disabled");
	  $('#pass').attr("disabled", "disabled");
	  $('#otherDocs').attr("disabled", "disabled");
	  newPolygons.forEach(function(polygon) {
	    polygon.setMap(null);
	  });
	  newPolygons = [];
	  $(".inactiveLink").removeClass("inactiveLink");
	});


	$(document).on("click", ".delPoint", function() {
	  var thisDel = $(this);
	  var polygonDiv = $(this).closest("div[id^=polygon_]");
	  if (polygonDiv.find(".clonedAreaInput").length > 1) {
	    var pointNum = $(this).closest("div[id^=areaInput]").find("#pointNumber").val();
	    $(this).closest("div[id^=areaInput]").remove();
	    //Changing the index of next points
	    polygonDiv.find(".clonedAreaInput").each(function(index) {
	      if (index >= pointNum - 1) {
	        $(this).find("#pointNumber").val(index + 1);
	        $(this).attr("id", $(this).attr("id").replace('areaInput' + (index + 2), 'areaInput' + (index + 1)));
	        $(this).find('input').each(function() {
	          $(this).attr("name", $(this).attr("name").replace('points[' + (index + 1) + ']', 'points[' + index + ']'));
	        });

	      }
	    });
	  } else {
	    if ($("div[id^=polygon_]").length > 1) {
	      bootbox.confirm(jQuery.i18n.prop('msg.delPolygon'), function() {
	        var polygonNum = Number(polygonDiv.find(".polygonIndex").html()) - 1;
	        polygonDiv.remove();
	        //Changing the polygon indexes in all input fields
	        $("div[id^=polygon_]").each(function(index) {
	          if (index >= polygonNum) {
	            $(this).attr("id", $(this).attr("id").replace('polygon_' + (index + 2), 'polygon_' + (index + 1)));
	            $(this).find(".polygonIndex").html(index + 1);
	            $(this).find('input').each(function() {
	              $(this).attr("name", $(this).attr("name").replace('poligons[' + (index + 1) + ']', 'poligons[' + index + ']'));
	            });
	          }
	        });
	      });
	    } else {
	      $(this).closest("div[id^=areaInput]").find('#pointNumber').val(1);
	      $(this).closest("div[id^=areaInput]").find('input:not(#pointNumber)').val(0);
	      bootbox.alert(jQuery.i18n.prop('msg.lastPoint'));
	    }
	  }
	});

	$(document).on("click", "#addPolygon", function() {
	  if ($('div[id^=polygon_]').last().find('.clonedAreaInput').length >= 3) {
	    var num = $('div[id^=polygon_]').length;
	    console.log("Polygons count: " + num);
	    addNewPolygon(num);
	  } else {
	    bootbox.alert(jQuery.i18n.prop('msg.minPoints'));
	  }
	});
});


