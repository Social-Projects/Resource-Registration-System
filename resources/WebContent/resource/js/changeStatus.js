$(document).on('change','select',function() {
	var option=$(this).val();
	alert(option);
//	$.ajax({
//		type : "POST",
//		url: "${base}administrator/users/change-User-Role",
//		data: JSON.stringify(option),
//		dataType : 'json',
//		timeout : 100000,
//		success: function(data) {
//			console.log('Success',data);
//		}
//	});
});
