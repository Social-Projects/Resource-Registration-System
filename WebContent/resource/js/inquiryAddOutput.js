
$(document).on("click","#outputInquiry",function(){
	$.ajax({
		url: 	baseUrl.toString() +'/inquiry/add/outputInquiry',
		type: 	'GET',
		success: function(response){
			$('#target').html(response);
			
			$('#registratorLogin').change(function(){
				$('#form').submit();								
			})			
		}
	})	
})

$(document).on("click","#editResource",function(){
    window.location = baseUrl.toString() +'/registrator/resource/addresource?mode=edit&id='+ $('#resourceIdentifierCopy').val();
})

$(document).on("click","#deleteResource",function(){
	bootbox.confirm(jQuery.i18n.prop('msg.confirmDeleteResource'),function(result){
		if (result) {
			window.location = baseUrl.toString() +'/registrator/resource/delete/'+ $('#resourceIdentifierCopy').val();			
		}
	})
})



