jQuery(document).ready(function($) {
	
	var createTableStatus = false;
    
    createSearchLine();
		
	var oTable;
	function createTable() {	    	

		oTable = $('#example').DataTable({
    	
//      "searching": false,
		"bSort" : false,
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
		                   	sWidth: "15%",
		                   "sDefaultContent" :   '<button class="btn btn-primary">Edit</button> <button class="btn btn-danger">Delete</button>'
		                }              
                       ],
              "ajax": {
       		        	"url":"${home}search/getData",
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
    		oTable.ajax.reload();
    	}
	});
    
    
    
    function createSearchLine() {
    	var searchInput='';
	    for (var i = 0; i < 4; i++) {
// 	    		$('#example tfoot tr').append( '<th><input id="inputIndex'+i+'" type="text" placeholder="Пошук" /></th>' );
	    		searchInput+='<th><input id="inputIndex'+i+'" type="text" placeholder="Пошук" /></th>';
	    }
	    searchInput+='<th><input type="submit" id="bth-search" class="btn btn-primary btn-lg" value="Пошук"/>';
	    searchInput='<tr>'+searchInput+'</tr>';
	    console.log(searchInput);
	    $('#example tfoot').append(searchInput);
	}
    
    function createSearchButton() {	    	
	    		$('#example tfoot th').append( '<button type="submit" id="bth-search" class="btn btn-primary btn-lg" >Search</button>' );
	}
    
    function addSearchValue(data) {
	    for (var i = 0; i < data.columns.length; i++) {
	        column = data.columns[i];
	        column.search.value = $('#inputIndex'+i).val();
	    }
	}

} );