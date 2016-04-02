jQuery(document).ready(function($) {
	var owner;
	checkboxControl();
	
	$("#owner_search").keyup(function() {
				    if (!this.value) {
				        clearData();
				        $("#owner_login").val('');
				    }
	});
		
	$('#owner_search').autocomplete({
		serviceUrl: 'owners',
	    paramName: "ownerDesc",
	    delimiter: ",",
	    minChars: 2,
	    autoSelectFirst: true,
	    deferRequestBy: 100,
	    type: "POST",
	    onSearchStart:function() {
	    //	clearData();
	    },
	    transformResult: function(response) {
	    	return {
	    		suggestions: $.map($.parseJSON(response), function(item) {
	    			return { value: item.lastName + " " + item.firstName + " " + item.middleName, data: item.login };
	    			})
	          };
	    },
	    onSelect: function (suggestion) {
	    	$.ajax({
	            url: "getOwnerInfo",
	            data: { ownerLogin : suggestion.data},
	            dataType: "json",
	            type: "GET",
	            success: function(data){
	            	clearData();
	            	reasonInclusionControl(data);
	            	// for input inquiry
	            	$("#owner_login").val(data.login);
	            }
	        });
	    }
	
	
	    });
	

	//to restore the information about the co-owner after unsuccessful validation
	if ($("#owner_login").val() !== ""){
		 $.ajax({
	         url: "getOwnerInfo",
	         data: { ownerLogin : $("#owner_login").val()},
	         dataType: "json",
	         type: "GET",
	         success: function(data){
	         	$("#owner_search").val(data.lastName + " " +data.firstName + " " + data.middleName);
	         }
	     });
	}
	
	function reasonInclusionControl(data) {
		owner = data;
	    if( owner.willDocument !== null) {
	        $('#will').removeAttr('disabled');        
	    }
	    if( owner.passport !== null) {
	        $('#pass').removeAttr('disabled');        
	    } 
	    if( owner.otherDocuments !== null) {
	        $('#otherDocs').removeAttr('disabled');       
	    }     	
	}

	function clearData() {	
	$("#reasonInclusion").val("");
		$(".checkbox").each(function(){
			$( "input" ).prop( "checked", false );
			$( ".disable" ).prop( "disabled", true );
		});
	}
	
	function checkboxControl() {
		   $(".checkbox input").click(function(){
			   var inclusion = "";
		        $(".checkbox :checked").each(function(){
		            var id =  $(this).attr('id');
		             if(id === "pass") {                 
		                 var seria = owner.passport.seria + " " + owner.passport.number;
		                 var name = owner.firstName + " " +owner.middleName + " " + owner.lastName;
		                 var published = owner.passport.published_by_data;
		                 var passportInfo = "паспорт громадянина України " 
		                     + seria +", виданий на ім’я " + name + " " + published;               
		                if(owner.passport.comment !== null) {
		                    var comment =owner.passport.comment;
		                    passportInfo = passportInfo + "; " + comment;
		                }
		                inclusion = inclusion + passportInfo + ";\n";
		            }
		             else if(id === "will") {
		                var date = owner.willDocument.accessionDate;
		                var stringDate = $.datepicker.formatDate('dd.mm.yy', new Date(date));
		                var willDocument = "документ волевиявлення від " + stringDate;
		                if(owner.willDocument.comment !==  null) {
		                    var comment =owner.willDocument.comment;
		                    willDocument = willDocument + "; " + comment;
		                }
		                inclusion = inclusion + willDocument+ ";\n";         
		            }
		             else if(id === "otherDocs") {
		                var docs = owner.otherDocuments;
		                inclusion = inclusion + docs + ";\n";  
		            }
		             else if(id === "tytul"){
		                 var inputString = "титул власності на природні ресурси України від 02 квітня 2015 року;";
		                 inclusion = inclusion + inputString + "\n";
		             }
		             else if(id === "delivery"){
		                 var inputDelivery = "доручення;";
		                 inclusion = inclusion + inputDelivery + "\n";                 
		            }
		             
		        });
		        $("#reasonInclusion").prop("value", inclusion);
		   });    
	}
 
});
