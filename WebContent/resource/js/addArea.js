function addNewPolygon(polygonNumber) {
  var newPolygon = $('<div id="polygon_' + (polygonNumber + 1) + '"></div>');
  var newLabel = $('<h4>' + jQuery.i18n.prop('msg.Polygon') + ' <span class="polygonIndex">' + (polygonNumber + 1) + '</span>' + ':</h4>');
  newPolygon.append(newLabel);
  newPolygon.append($('#areaInput1').clone());
  //newPolygon.find('#myparam1').val('0');
  newPolygon.find('input:not(#pointNumber)').val(0);
  newPolygon.find('input').each(function() {
    $(this).attr("name", $(this).attr("name").replace('poligons[0]', 'poligons[' + polygonNumber + ']'));
  });
  $('#polygon_' + polygonNumber).after(newPolygon);
}

function addNewPoint(polygonNumber, latitudeDegrees, latitudeMinutes, latitudeSeconds, longitudeDegrees, longitudeMinutes, longitudeSeconds) {
  var polygon = $('#polygon_' + (polygonNumber + 1));
  if (polygon.length == 0) {
    addNewPolygon(polygonNumber);
    polygon = $('#polygon_' + (polygonNumber + 1));
  }
  var num = polygon.find('.clonedAreaInput').length;
  var prevNum = Number(num - 1);
  var newNum = Number(num + 1);

  if (polygon.find('#myparam1').val() == 0) {
    console.log("point num: " + num);
    polygon.find('#myparam1').val(latitudeDegrees);
    polygon.find('#myparam2').val(latitudeMinutes);
    polygon.find('#myparam3').val(latitudeSeconds.toFixed(7));
    polygon.find('#myparam4').val(longitudeDegrees);
    polygon.find('#myparam5').val(longitudeMinutes);
    polygon.find('#myparam6').val(longitudeSeconds.toFixed(7));
  } else {
    var newElem = polygon.find('#areaInput' + num).clone().attr('id', 'areaInput' + newNum);
    newElem.find('input').each(function() {
      $(this).removeAttr('value');
      $(this).attr("name", $(this).attr("name").replace('points[' + prevNum + ']', 'points[' + num + ']'));
    });

    newElem.find('input#pointNumber').val(newNum);
    newElem.find('input#myparam1').val(latitudeDegrees);
    newElem.find('input#myparam2').val(latitudeMinutes);
    newElem.find('input#myparam3').val(latitudeSeconds);
    newElem.find('input#myparam4').val(longitudeDegrees);
    newElem.find('input#myparam5').val(longitudeMinutes);
    newElem.find('input#myparam6').val(longitudeSeconds);

    polygon.find('#areaInput' + num).after(newElem);
    //$('#btnDelAreaPoint').removeAttr('disabled');

  }
  //alert("point added");

}

$(document).ready(function() {

  $.post("getParameters", {
    "resourceTypeName" : $("#resourcesTypeSelect").val()
  }, getParameters);

  $("#editNumber").click(function() {
    $('#identifier').removeAttr('readonly');
  });

  /* Switch off form submit on ENTER keypress from every input field */
  $('form').bind("keypress", function(e) {
    if (e.keyCode == 13) {
      e.preventDefault();
      return false;
    }
  });

  /* load parameters of selected value */
  $("#resourcesTypeSelect").change(function() {
    $("#typeParameters").html("")
    $.post("getParameters", {
      "resourceTypeName" : $("#resourcesTypeSelect").val()
    }, getParameters);
  });

  $("#datepicker").datepicker({
    dateFormat : 'dd.mm.yy'
  });
  if ($("#datepicker").val() === "") {
    $("#datepicker").datepicker("setDate", new Date);
  }

  $('#btnAddAreaPoint').click(function() {
    if ($('div[id^=polygon_]').last().find('#myparam1').val() == 0) {
      bootbox.alert(jQuery.i18n.prop('msg.enterFirstPoint'));
    }
    var num = $('div[id^=polygon_]').length - 1;
    addNewPoint(num, 0, 0, 0.0, 0, 0, 0.0);
  });


});


function getParameters(data) {
  $("#typeParameters").html(data);
  var calcParamsHtml = $('#typeParameters');
  $('#calculatedParams').html(calcParamsHtml);
   if ((calcParamsHtml == undefined) || (!/\S/.test(calcParamsHtml))) {
    $('#calculatedParamsTab').addClass('hidden');
  } else {
    $('#calculatedParamsTab').removeClass('hidden');
  }
}

//old version
/*function getParameters(data) {
	  $("#typeParameters").html(data);
	  var calcParams = $('#calculatedParamsList');
	  var calcParamsHtml = calcParams.html();
	  calcParams.html('');
	  $('#calculatedParams').html(calcParamsHtml);
	  if ((calcParamsHtml == undefined) || (!/\S/.test(calcParamsHtml))) {
	    $('#calculatedParamsTab').addClass('hidden');
	  } else {
	    $('#calculatedParamsTab').removeClass('hidden');
	  }
}*/

