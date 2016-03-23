<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty tableSetting.script}">
	<script src="<c:url value='/resource/js/${tableSetting.script}.js'/>"></script>
</c:if>
<div class="container">

	<c:if test="${not empty tableSetting.tableTitle}">
	
		<div style="text-align: center;">
			<h4>
				<spring:message code="${tableSetting.tableTitle}" />
			</h4>
		</div>
	
	</c:if>

	<div class="dataTable_wrapper">
         <table class="table table-striped table-bordered table-hover" id="example">
			<thead>
				<tr>
					<c:forEach items="${tableSetting.columns}" var="entry">
						<th><spring:message code="${entry.value.title}" /></th>
					</c:forEach>
	           </tr>
			</thead>
	        <tfoot style="display: table-header-group">
	        	<tr>      	
	        		<c:forEach items="${tableSetting.columns}" var="entry" varStatus="status">
	        			<c:if test="${entry.value.type eq 'search'}">
	        				<th style="padding-right:0px !important; padding-left:10px !important">

		        				<div class="input-group form-inline " >
		        					<div class="form-group ">
							            <select id="searchTypeIndex${entry.key}" class="form-control col-sm-3" name="category">
							                <c:forEach items="${entry.value.searchType}" var="searchType">
		        							<c:if test="${searchType eq 'equal'}">
												<option value="${searchType}"><spring:message code="label.tableTitle.searchType.equals" /></option>
											</c:if>
											<c:if test="${searchType eq 'like'}">
												<option value="${searchType}"><spring:message code="label.tableTitle.searchType.contains" /></option>
											</c:if>
				                        </c:forEach>
							            </select>           
							        </div>
							        <div class="form-group ">
										<input size="5" maxlength="50" id="inputIndex${entry.key}" class="form-control col-sm-7"  type="text" placeholder="<spring:message code="${entry.value.title}" />" />
							        </div>
							   </div>
	        				</th>
	        			</c:if>
					</c:forEach>		        	
		        	<th><input type="submit" id="bth-search" class="btn  btn-primary" value="Пошук"/></th>
		        </tr>  
	        </tfoot>
         </table>
    </div>
</div>

<script>
var oTable;
jQuery(document).ready(function($) {
	
	var createTableStatus = false;
		
// 	var oTable;
// 	window.myDataTable=oTable;
	function createTable() {	    	

		oTable = $('#example').DataTable({
    	
		"searching": false,
// 		"bSort" : false,
		"bSort" : true,
		"bDestroy": true,
    	"oLanguage": {   
            "sEmptyTable": "В таблиці немає даних",
            "sInfo": "_END_ із _TOTAL_",    
            "sInfoEmpty": "Немає даних для виводу",
            "sInfoFiltered": " - Фільтруються _MAX_ записів",
              "oPaginate": {
                "sFirst"   : "Перша",
                "sLast"    : "Остання",
                "sNext"    : "Наст.",
                "sPrevious": "Попер."
              },
              "sLengthMenu": "_MENU_ елементів на сторінку",
              "sLoadingRecords": "Почекайте будь ласка, йде загрузка...",
              "sProcessing"   : "Почекайте будь ласка, йде загрузка...",
              "sSearch": "Пошук:",
              "sZeroRecords": "Нічого не знайдено"
            },
        "serverSide": true,
        "aoColumns" : [        
						<c:forEach items="${tableSetting.columns}" var="entry" varStatus="status">
							<c:if test="${entry.value.type eq 'search'}">
								{
						            "sTitle" : "<spring:message code="${entry.value.title}" />",
						            "mData" : '${entry.key}'
						        },
							</c:if>
						    <c:if test="${entry.value.type eq 'button'}">
						    {
							       	   "sTitle" : "<spring:message code="${entry.value.title}" />",
					                   "mData" : "action",
					                   "bSortable": false,
					                   "defaultContent": '<button  class="btn btn-primary" id="${entry.value.buttonId}">Редагувати</button>'
					                        ,                	   
					            },
							</c:if>
						</c:forEach>
						
                       ],
              "ajax": {
       		        	"url":"${tableSetting.url}",
       		        	"type":"POST",
       		        	dataType: "json",
       		        	contentType: 'application/json; charset=utf-8',
       		        	"data": function ( data ) {
       		        	  addSearchValue(data);
       	                  return JSON.stringify(data);
       	            	} 	
               }
    });
		
		
		
// 		$('#example tbody').on('click', '#mybutton', function () {
// 			var data = oTable.row( $(this).parents('tr') ).data();
// 		    alert( data[0] +"s salary is:");
// 		} );
	
	}
	
    $("#bth-search").click(function(event) {
    	if(createTableStatus==false){
    		createTable();
    		createTableStatus=true;
    	}else{
    		oTable.ajax.reload(null, false);
    	}
	});
   
    function addSearchValue(data) {
    	data.tableName = '${tableSetting.tableName}';
	    for (var i = 0; i < data.columns.length; i++) {
	        column = data.columns[i];
	        column.search.compareSign = $('#searchTypeIndex'+i).val();
	        column.search.value = $('#inputIndex'+i).val();
	    }
	}
    
// 	$('#example').on('click', '#mybutton', function () {
// 		var data = oTable.row( $(this).parents('tr') ).data();
// 	    alert( data[0] +"s salary is:");
// 	} );
    
//     $('#example').on('click', '#mybutton', function (e) {
//         e.preventDefault();
//         var data = table.row( $(this).parents('tr') ).data();
//        alert( "s salary is:");
//    } );
    

    
//     $('#example tbody').on( 'click', 'button', function () {
//         var data = table.row( $(this).parents('tr') ).data();
//         alert( data[0] +"'s salary is: "+ data[ 0 ] );
//     } );
    
   
//         $('#example .myButton').on('click',function() {
//         	alert( "s salary is:");
//         });
    
} );
</script>