jQuery(document).ready(function($) {
	
	var createTableStatus = false;
		
	var oTable;
	function createTable() {	    	

		oTable = $('#example').DataTable({
    	
		"searching": false,
		"bSort" : false,
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
                       {
                        "sTitle" : "Ім'я",
                        "mData" : "firstName"
                       },
                       {
                        "sTitle" : "Прізвище",
                        "mData" : "lastName"
                       },
                       {
  	                     "sTitle" : "По батькові",
  	                     "mData" : "middleName"
  	                   },
  	                   {
                        "sTitle" : "Електронна пошта",
                        "mData" : "email"
                       },
		               {   "sTitle" : "Дії",
		                   "mData" : "action",
		                   "sClass" : "center",
		                   "sClass": "action",
		                   "bSortable": false,
		                   	"sWidth": "15%",
		                   "sDefaultContent" :   '<button class="btn btn-primary">Edit</button> <button class="btn btn-danger">Delete</button>'
		                }              
                       ],
              "ajax": {
       		        	"url":"getData",
       		        	"type":"POST",
       		        	dataType: "json",
       		        	contentType: 'application/json; charset=utf-8',
       		        	"data": function ( data ) {
       		        	  addSearchValue(data);
       	                  return JSON.stringify(data);
       	            	}
       		        	
               }
    });
    
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
	    for (var i = 0; i < data.columns.length; i++) {
	        column = data.columns[i];
	        column.search.compareSign = "equal";
	        column.search.value = $('#inputIndex'+i).val();
	    }
	}
} );