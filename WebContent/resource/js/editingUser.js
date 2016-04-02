$(document).on('click','#edit',function(){
	$('.readonly').removeAttr('readonly');
	$('.form-control').removeAttr('disabled');
	$('#edit').hide();
	$('#ok').show();
})