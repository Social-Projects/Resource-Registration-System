$(document).on('change','#roleId',function() {
	$("#modalWindow").validate({
		submitHandler: function(form) {
			return false;
		},
		rules: {
			registrator_number: {
				required: true
			},
			resource_number: {
				required: true
			},
			identifier: {
				required: true
			}
		},
		messages: {
			registrator_number: {
				required: "Поле є обов'язковим для введення"
			},
			resource_number: {
				required: "Поле є обов'язковим для введення"
			},
			identifier: {
				required: "Поле є обов'язковим для введення"
			}
		}
	});
})