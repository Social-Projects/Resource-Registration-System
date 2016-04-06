var map;
var markers = [];
var rectangles = [];
var polygons = [];
var boundsArray = [];
var resTypes = [];
var oTable = null;

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

function initialize() {
  // Reading the cookie of start position
  var lastMapSearchLat = getCookie("LastMapSearchLat");
  var lastMapSearchLng = getCookie("LastMapSearchLng");
  var startPoint;
  if ((lastMapSearchLat.length > 0) && (lastMapSearchLng.length > 0)) {
    var startLat = Number(lastMapSearchLat);
    var startLng = Number(lastMapSearchLng);
    startPoint = new google.maps.LatLng(startLat, startLng);
  } else {
    startPoint = new google.maps.LatLng(49.8326679, 23.942196);
  }

  var mapProp = {
    center : startPoint,
    zoom : 13,
    mapTypeId : google.maps.MapTypeId.ROADMAP
  };
  map = new google.maps.Map(document.getElementById("map_canvas"), mapProp);

  var drawingManager = new google.maps.drawing.DrawingManager({
    drawingControl : true,
    drawingControlOptions : {
      position : google.maps.ControlPosition.TOP_CENTER,
      drawingModes : [ google.maps.drawing.OverlayType.MARKER, google.maps.drawing.OverlayType.RECTANGLE ]
    },
    rectangleOptions : {
      fillColor : '#008000',
      fillOpacity : 0,
      strokeColor : "#FF0000",
      strokeWeight : 1,
      strokeOpacity : 0.5,
      clickable : false,
      zIndex : 1
    }
  });

  drawingManager.setMap(map);

  // Create the search box and link it to the UI element.
  var input = document.getElementById('gmaps-input');
  var searchBox = new google.maps.places.SearchBox(input);
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

  // Bias the SearchBox results towards current map's viewport.
  map.addListener('bounds_changed', function() {
    searchBox.setBounds(map.getBounds());
  });

  // Listen for the event fired when the user selects a prediction and retrieve
  // more details for that place.
  searchBox.addListener('places_changed', function() {
    var places = searchBox.getPlaces();

    if (places.length == 0) {
      return;
    }

    // Clear out the old markers.
    markers.forEach(function(marker) {
      marker.setMap(null);
    });
    markers = [];

    // Clear out the old polygons.
    polygons.forEach(function(poligon) {
      poligon.setMap(null);
    });
    polygons = [];

    // Clear out the old rectangles.
    rectangles.forEach(function(rectangle) {
      rectangle.setMap(null);
    });
    rectangles = [];

    // For each place, get the icon, name and location.
    var bounds = new google.maps.LatLngBounds();
    places.forEach(function(place) {
      var icon = {
        url : place.icon,
        size : new google.maps.Size(71, 71),
        origin : new google.maps.Point(0, 0),
        anchor : new google.maps.Point(17, 34),
        scaledSize : new google.maps.Size(25, 25)
      };
      // Create a marker for each place.
      markers.push(new google.maps.Marker({
        map : map,
        icon : icon,
        title : place.name,
        position : place.geometry.location
      }));

      coordinatesCookie(place.geometry.location.lat(), place.geometry.location.lng());

      if (place.geometry.viewport) {
        // Only geocodes have viewport.
        bounds.union(place.geometry.viewport);
      } else {
        bounds.extend(place.geometry.location);
      }
    });

    map.fitBounds(bounds);
  });

  google.maps.event.addListener(drawingManager, 'markercomplete', function(marker) {

    // // Clear out the old markers.
    // markers.forEach(function (marker) {
    // marker.setMap(null);
    // });
    // markers = [];

    markers.push(marker);

    var latitude = marker.getPosition().lat();
    var longitude = marker.getPosition().lng();

    var latitudeDegrees = Math.floor(latitude);
    var latitudeMinutes = Math.floor((latitude - latitudeDegrees) * 60);
    var latitudeSeconds = (((latitude - latitudeDegrees) * 60 - latitudeMinutes) * 60).toFixed(5);
    var longitudeDegrees = Math.floor(longitude);
    var longitudeMinutes = Math.floor((longitude - longitudeDegrees) * 60);
    var longitudeSeconds = (((longitude - longitudeDegrees) * 60 - longitudeMinutes) * 60).toFixed(5);

    $("#search_by_point").find(".latitudeDegrees").val(latitudeDegrees);
    $("#search_by_point").find(".latitudeMinutes").val(latitudeMinutes);
    $("#search_by_point").find(".latitudeSeconds").val(latitudeSeconds);
    $("#search_by_point").find(".longitudeDegrees").val(longitudeDegrees);
    $("#search_by_point").find(".longitudeMinutes").val(longitudeMinutes);
    $("#search_by_point").find(".longitudeSeconds").val(longitudeSeconds);

    searchOnMapByPoint(marker);
  });
  google.maps.event.addListener(drawingManager, 'rectanglecomplete', function(rectangle) {
    // Clear out the old polygons.
    rectangles.forEach(function(rectangle) {
      rectangle.setMap(null);
    });
    rectangles = [];

    var ne = rectangle.getBounds().getNorthEast();
    var sw = rectangle.getBounds().getSouthWest();

    var neLat = ne.lat();
    var neLng = ne.lng();
    var swLat = sw.lat();
    var swLng = sw.lng();

    var neLatDeg = Math.floor(neLat);
    var neLatMin = Math.floor((neLat - neLatDeg) * 60);
    var neLatSec = (((neLat - neLatDeg) * 60 - neLatMin) * 60).toFixed(5);
    var neLngDeg = Math.floor(neLng);
    var neLngMin = Math.floor((neLng - neLngDeg) * 60);
    var neLngSec = (((neLng - neLngDeg) * 60 - neLngMin) * 60).toFixed(5);

    var swLatDeg = Math.floor(swLat);
    var swLatMin = Math.floor((swLat - swLatDeg) * 60);
    var swLatSec = (((swLat - swLatDeg) * 60 - swLatMin) * 60).toFixed(5);
    var swLngDeg = Math.floor(swLng);
    var swLngMin = Math.floor((swLng - swLngDeg) * 60);
    var swLngSec = (((swLng - swLngDeg) * 60 - swLngMin) * 60).toFixed(5);

    $("#first_point").find(".latitudeDegrees").val(neLatDeg);
    $("#first_point").find(".latitudeMinutes").val(neLatMin);
    $("#first_point").find(".latitudeSeconds").val(neLatSec);
    $("#first_point").find(".longitudeDegrees").val(neLngDeg);
    $("#first_point").find(".longitudeMinutes").val(neLngMin);
    $("#first_point").find(".longitudeSeconds").val(neLngSec);

    $("#second_point").find(".latitudeDegrees").val(swLatDeg);
    $("#second_point").find(".latitudeMinutes").val(swLatMin);
    $("#second_point").find(".latitudeSeconds").val(swLatSec);
    $("#second_point").find(".longitudeDegrees").val(swLngDeg);
    $("#second_point").find(".longitudeMinutes").val(swLngMin);
    $("#second_point").find(".longitudeSeconds").val(swLngSec);

    searchOnMapByArea(rectangle);
    drawingManager.setDrawingMode(null);

    rectangles.push(rectangle);
  });
}

function searchOnMapByPoint(marker, page) {
  var latLng = marker.getPosition();

  map.setCenter(latLng);
  map.setZoom(13);

  var lat = latLng.lat();
  var lng = latLng.lng();
  var page = page || 0;

  coordinatesCookie(lat, lng);

  $("#dark_bg").show();
  $.ajax({
    data : {
      "lat" : lat,
      "lng" : lng,
      "page" : page
    },
    type : "POST",
    url : baseUrl.toString() + "/registrator/resource/getResourcesByPoint",
    timeout : 20000,
    contentType : "application/x-www-form-urlencoded;charset=UTF-8",
    dataType : 'json',
    success : function(data) {
      createPolygons(data, page);
      marker.setMap(map);
      markers.push(marker);
      var json = [];
      for (var i = 0; i < polygons.length; i++) {
        var isWithinPolygon = google.maps.geometry.poly.containsLocation(marker.getPosition(), polygons[i]);
        if (!isWithinPolygon) {
          polygons.splice(i, 1);
          boundsArray.splice(i, 1);
          i--;
        } else {
          json.push(data.polygons[i]);
        }
      }
      console.log("polygons: " + polygons.length);
      showPolygons(polygons);
      createDataTable(json);

      $("#dark_bg").hide();
    },
    error : function() {
      $("#dark_bg").hide();
      bootbox.alert(jQuery.i18n.prop('msg.error'));
    }
  });
}

function searchOnMapByArea(rectangle, page) {
  map.fitBounds(rectangle.getBounds());
  var maxLat = rectangle.getBounds().getNorthEast().lat();
  var minLat = rectangle.getBounds().getSouthWest().lat();
  var maxLng = rectangle.getBounds().getNorthEast().lng();
  var minLng = rectangle.getBounds().getSouthWest().lng();
  var page = page || 0;

  if (polygons.length > 0) {
    for (var i = 0; i < polygons.length; i++) {
      polygons[i].setMap(null);
    }
    polygons = [];
  }

  $("#dark_bg").show();
  $.ajax({
    data : {
      "minLat" : minLat,
      "maxLat" : maxLat,
      "minLng" : minLng,
      "maxLng" : maxLng,
      "resType" : "all",
      "page" : page
    },
    type : "POST",
    url : baseUrl.toString() + "/registrator/resource/getResourcesByAreaLimits",
    timeout : 60000,
    contentType : "application/x-www-form-urlencoded;charset=UTF-8",
    dataType : 'json',
    success : function(data) {
      createPolygons(data, page);
      rectangle.setMap(map);
      rectangles.push(rectangle);
      showPolygons(polygons);
      createDataTable(data.polygons);

      // var countResources = data.countPolygons;

      if (resTypes.length > 0) {
        var resTypeFilter = "<p>Фільтр:</p>";
        for (var i = 0; i < resTypes.length; i++) {
          resTypeFilter += '<button class="btn btn-default btn-filter">' + resTypes[i] + '</button>';
        }
      }
      $("#resTypeFilter").html(resTypeFilter);

      $("#dark_bg").hide();
    },
    error : function() {
      $("#dark_bg").hide();
      bootbox.alert(jQuery.i18n.prop('msg.error'));
    }
  });

  coordinatesCookie(rectangle.getBounds().getCenter().lat(), rectangle.getBounds().getCenter().lng());
}

function searchByParameters(page) {

  var json = new Object();
  json.discreteParameters = [];
  json.linearParameters = [];

  json.resourceTypeId = $("#resourcesTypeSelect").val();
  json.page = page || 0;

  $("#dark_bg").show();

  $(".discreteParameter").each(function() {
    var id_param = $(this).attr("param_id");
    var compare_sign = $(this).find(".compare").val();
    var value = $(this).find(".value").val();
    if (value) {
      var node = new Object();
      node.id = id_param;
      node.compare = compare_sign;
      node.value = value;
      json.discreteParameters.push(node);

    }
  });

  var compare_sign = "linear";
  $(".linearParameter").each(function() {
    var id_param = $(this).attr("param_id");
    var value = $(this).find(".value").val();
    if (value) {

      var node = new Object();
      node.id = id_param;
      node.compare = compare_sign;
      node.value = value;
      json.linearParameters.push(node);

    }
  });

  $.ajax({
    type : "POST",
    url : baseUrl.toString() + "/registrator/resource/resourceSearch",
    data : JSON.stringify(json),
    contentType : 'application/json; charset=utf-8',
    timeout : 60000,
    dataType : 'json',
    success : function(data) {
      createDataTable(data.polygons);
      createPolygons(data, page);
      $("#dark_bg").hide();
    },
    error : function() {
      $("#dark_bg").hide();
      bootbox.alert(jQuery.i18n.prop('msg.error'));
    }
  });
}

function createDataTable(json) {
  if (json.length > 0) {
    var url = location.origin + "/registrator/resource/get/";

    var description = jQuery.i18n.prop('msg.description');
    var type = jQuery.i18n.prop('msg.subclass');
    var identifier = jQuery.i18n.prop('msg.identifier');
    var date = jQuery.i18n.prop('msg.date');
    var details = jQuery.i18n.prop('msg.more');

    $("#searchResult").html('<table id="datatable" class="table table-striped table-bordered" cellspacing="0"></table>');
    oTable = $('#datatable').DataTable({
      "aaData" : json,
      "aoColumns" : [ {
        "sTitle" : description,
        "mData" : "resourceDescription"
      }, {
        "sTitle" : type,
        "mData" : "resourceType"
      }, {
        "sTitle" : identifier,
        "mData" : "identifier"
      }, {
        "sTitle" : date,
        "mData" : "date"
      }, {
        "sTitle" : details,
        "mData" : null
      } ],
      "aoColumnDefs" : [ // Adding URL to the last column
      {
        "aTargets" : [ 4 ], // Column to target
        "mRender" : function(json) {
          return '<a href="' + url + json.identifier + '">' + details + '</a>';
        }
      } ]
    });
  } else {
    $('#searchResult').html(jQuery.i18n.prop('msg.resourcesNotFound'));
  }
}

function createPolygons(json, page) {
  var page = page || 0;
  clearMap();
  var polygonFillColors = [ "#ADD8E6", "#008080", "#32cd32", "#ffff00", "#f08080", "#8a2be2", "#00ffff" ];
  var fillColor;
  var fillColorIndex;
  var infowindow = new google.maps.InfoWindow();
  var infoWindowContent = "<table id='infowindow_table'><tr><th>" + jQuery.i18n.prop('msg.description') + "</th><th>" + jQuery.i18n.prop('msg.subclass') + "</th><th></th></tr>";
  var contentString = "";
  resTypes = [];
  boundsArray = [];

  // Remove the old resource type filter buttons
  $("#resTypeFilter").html("");

  if (json.polygons != undefined) {
    for (var i = 0; i < json.polygons.length; i++) {

      var polygonPath = [];
      var points = json.polygons[i].points;
      var bounds = new google.maps.LatLngBounds();

      for (var j = 0; j < points.length; j++) {
        var myLatLng = new google.maps.LatLng(points[j].latitude, points[j].longitude);
        polygonPath.push(myLatLng);
        bounds.extend(myLatLng);
      }

      boundsArray.push(bounds);

      // Changing fill color depending on resource type
      if (resTypes.length <= polygonFillColors.length) {
        if ($.inArray(json.polygons[i].resourceType, resTypes) == (-1)) {
          resTypes.push(json.polygons[i].resourceType);
          fillColorIndex = resTypes.length - 1;
          fillColor = polygonFillColors[fillColorIndex];
        } else {
          fillColorIndex = $.inArray(json.polygons[i].resourceType, resTypes);
          fillColor = polygonFillColors[fillColorIndex];
        }
      } else {
        fillColor = "#ff0000";
      }

      var polygon = new google.maps.Polygon({
        path : polygonPath, // Координаты
        strokeColor : "#FF0000", // Цвет обводки
        strokeOpacity : 0.8, // Прозрачность обводки
        strokeWeight : 2, // Ширина обводки
        fillColor : fillColor, // Цвет заливки
        fillOpacity : 0.4, // Прозрачность заливки
        zIndex : 5,
        resType : json.polygons[i].resourceType,
        resDescription : json.polygons[i].resourceDescription,
        identifier : json.polygons[i].identifier
      });

      google.maps.event.addListener(polygon, 'click', function(event) {
        contentString = "<tr>" + "<td>" + this.resDescription + "</td>" + "<td>" + this.resType + "</td>" + "<td><a href='" + baseUrl.toString() + "/registrator/resource/get/" + this.identifier + "'><i>Детальніше</i></a> </td>" + "</tr>";
        infowindow.setContent(infoWindowContent + contentString);
        infowindow.setPosition(event.latLng);
        infowindow.open(map);
      });

      polygons.push(polygon);
    }
  }

  createPagination(json.countPolygons, page);
}

function showPolygons(polygonsArray) {
  var bounds = new google.maps.LatLngBounds();
  var testBounds = new google.maps.LatLngBounds();
  for (var i = 0; i < polygonsArray.length; i++) {
    polygonsArray[i].setMap(map);
    bounds.union(boundsArray[i]);
  }
  console.log("bounds: " + bounds);
  if (!bounds.equals(testBounds)) {
    map.fitBounds(bounds);
  }
}

function clearMap() {
  // Clear out the old markers.
  markers.forEach(function(marker) {
    marker.setMap(null);
  });
  markers = [];

  // Clear out the old polygons.
  polygons.forEach(function(poligon) {
    poligon.setMap(null);
  });
  polygons = [];

  // Clear out the old rectangles.
  rectangles.forEach(function(rectangle) {
    rectangle.setMap(null);
  });
  rectangles = [];

}

function createPagination(count, page) {
  var pagination = $('<ul class="pagination"></ul>');
  var paginationDiv = $('#paginationDiv');
  var page = page || 0;
  paginationDiv.html("");
  if (count > 200) {
    var pages = Math.floor(count / 200) + 1;
    for (var i = 0; i < pages; i++) {
      var begin = i * 200 + 1;
      var end = (i + 1) * 200;
      if (end > count) {
        end = count;
      }
      var paginationLi = $('<li><a class="pageA" href="#" page="' + i + '">' + begin + ' - ' + end + '</a></li>');
      if (i == page) {
        paginationLi.addClass('active');
      }
      pagination.append(paginationLi);
    }
    paginationDiv.append(pagination);
  }
}

function coordinatesCookie(lat, lng) {
  // Expires date for cookie
  var d = new Date();
  d.setTime(d.getTime() + 7 * 24 * 60 * 60 * 1000);
  // Deleting of the old cookie
  document.cookie = "LastMapSearchLat=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
  document.cookie = "LastMapSearchLng=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
  // Add new cookie
  document.cookie = "LastMapSearchLat=" + lat + ";expires=" + d.toUTCString();
  document.cookie = "LastMapSearchLng=" + lng + ";expires=" + d.toUTCString();

}

$(document).ready(function() {
  $.post(baseUrl.toString() + "/registrator/resource/getResourcesByTypeId", {
    "resourceTypeId" : $("#resourcesTypeSelect").val()
  }, function(data) {
    $("#searchParameters").html(data);
    $("#table").html("");
  });
});

$(document).on("click", "#searchOnMapButton", function() {
  var latitudeDegrees = Number($(".latitudeDegrees").val());
  var latitudeMinutes = Number($(".latitudeMinutes").val());
  var latitudeSeconds = Number($(".latitudeSeconds").val());

  var longitudeDegrees = Number($(".longitudeDegrees").val());
  var longitudeMinutes = Number($(".longitudeMinutes").val());
  var longitudeSeconds = Number($(".longitudeSeconds").val());

  var searchLat = latitudeDegrees + latitudeMinutes / 60 + latitudeSeconds / 3600;
  var searchLng = longitudeDegrees + longitudeMinutes / 60 + longitudeSeconds / 3600;

  var myLatLng = new google.maps.LatLng(searchLat, searchLng);

  // Clear out the old markers.
  markers.forEach(function(marker) {
    marker.setMap(null);
  });
  markers = [];

  var marker = new google.maps.Marker({
    position : myLatLng,
    map : map
  });

  markers.push(marker);

  searchOnMapByPoint(marker);
});

$(document).on("click", "#searchOnMapButton_area", function() {
  var firstPoint = $("#first_point");
  var secondPoint = $("#second_point");

  var firstLatDeg = Number(firstPoint.find(".latitudeDegrees").val());
  var firstLatMin = Number(firstPoint.find(".latitudeMinutes").val());
  var firstLatSec = Number(firstPoint.find(".latitudeSeconds").val());
  var firstLngDeg = Number(firstPoint.find(".longitudeDegrees").val());
  var firstLngMin = Number(firstPoint.find(".longitudeMinutes").val());
  var firstLngSec = Number(firstPoint.find(".longitudeSeconds").val());

  var secondLatDeg = Number(secondPoint.find(".latitudeDegrees").val());
  var secondLatMin = Number(secondPoint.find(".latitudeMinutes").val());
  var secondLatSec = Number(secondPoint.find(".latitudeSeconds").val());
  var secondLngDeg = Number(secondPoint.find(".longitudeDegrees").val());
  var secondLngMin = Number(secondPoint.find(".longitudeMinutes").val());
  var secondLngSec = Number(secondPoint.find(".longitudeSeconds").val());

  var firstLat = firstLatDeg + firstLatMin / 60 + firstLatSec / 3600;
  var firstLng = firstLngDeg + firstLngMin / 60 + firstLngSec / 3600;

  var secondLat = secondLatDeg + secondLatMin / 60 + secondLatSec / 3600;
  var secondLng = secondLngDeg + secondLngMin / 60 + secondLngSec / 3600;

  var north;
  var south;
  var east;
  var west;

  if (firstLat > secondLat) {
    north = firstLat;
    south = secondLat;
  } else {
    north = secondLat;
    south = firstLat;
  }

  if (firstLng > secondLng) {
    east = firstLng;
    west = secondLng;
  } else {
    east = secondLng;
    west = firstLng;
  }

  // Clear out the old rectangles.
  rectangles.forEach(function(rectangle) {
    rectangle.setMap(null);
  });
  rectangles = [];

  var rectangle = new google.maps.Rectangle({
    fillColor : '#008000',
    fillOpacity : 0,
    strokeColor : "#FF0000",
    strokeWeight : 1,
    strokeOpacity : 0.5,
    clickable : false,
    zIndex : 1,
    bounds : {
      north : north,
      south : south,
      east : east,
      west : west
    }
  });

  rectangle.setMap(map);
  rectangles.push(rectangle);

  searchOnMapByArea(rectangle);
});

$(document).on("click", ".toggle-button", function() {
  if (!$(this).hasClass("active")) {
    $(this).siblings().removeClass("active");
    $(this).addClass("active");
    var id = $(this).attr("id");
    id = id.substr(0, id.length - 6);
    $(".searchDiv").hide();
    $("#" + id + "Div").show();
  }
});

$(document).on("click", ".btn-filter", function() {
  if (!$(this).hasClass("active")) {
    $("#dark_bg").show();
    $(this).siblings().removeClass("active");
    $(this).addClass("active");
    var resType = $(this).html();
    for (var i = 0; i < polygons.length; i++) {
      if ((polygons[i].resType == resType) && (polygons[i].map == null)) {
        polygons[i].setMap(map);
      } else if ((polygons[i].map != null) && (polygons[i].resType != resType)) {
        polygons[i].setMap(null);
      }
    }
    $("#dark_bg").hide();
  } else {
    $("#dark_bg").show();
    $(this).removeClass("active");
    for (var i = 0; i < polygons.length; i++) {
      if (polygons[i].map == null) {
        polygons[i].setMap(map);
      }
    }
    $("#dark_bg").hide();
  }
});

$(document).on("change", "#resourcesTypeSelect", function() {
  $("#dark_bg").show();
  $.post(baseUrl.toString() + "/registrator/resource/getResourcesByTypeId", {
    "resourceTypeId" : $("#resourcesTypeSelect").val()
  }, function(data) {
    $("#searchParameters").html(data);
    $("#table").html("");
    $("#dark_bg").hide();
  });
});

$(document).on("click", "#search", function() {
  searchByParameters();
});

$(document).on("click", "#showAllResources", function() {
  $("#dark_bg").show();

  var resType = $("#resourcesTypeSelect").val();

  $.ajax({
    type : "POST",
    url : baseUrl.toString() + "/registrator/resource/showAllResources",
    data : {
      "resType" : resType
    },
    contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
    timeout : 60000,
    dataType : 'json',
    success : function(data) {
      createDataTable(data);
      createPolygons(data);
      $("#dark_bg").hide();
    },
    error : function() {
      $("#dark_bg").hide();
      bootbox.alert(jQuery.i18n.prop('msg.error'));
    }
  });

});

$(document).on("click", "#datatable tbody tr", function() {
  if (!$(this).hasClass("clickedTr")) {
    if ($(".clickedTr").length > 0) {
      var prevPolygonId = $(".clickedTr").attr("id").substr(3);
      $(".clickedTr").removeClass("clickedTr");
      if ((rectangles.length == 0) && (markers.length == 0)) {
        polygons[prevPolygonId].setMap(null);
      }
    }

    var polygonId = $(this).attr("id").substr(3);
    $(this).addClass("clickedTr");
    polygons[polygonId].setMap(map);
    map.fitBounds(boundsArray[polygonId]);
  }
});

$(document).on("click", "#paginationDiv .pageA", function() {
  var page = $(this).attr("page");
  // alert ("Hi!");
  // console.log("rectangle: " + rectangles.length);
  if (markers.length > 0) {
    searchOnMapByPoint(markers[0], page);
  } else if (rectangles.length > 0) {
    searchOnMapByArea(rectangles[0], page);
  } else {
    searchByParameters(page);
  }

});

google.maps.event.addDomListener(window, 'load', initialize);
