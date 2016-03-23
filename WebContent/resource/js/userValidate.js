$(document).ready(function() {
	$("#editWindow").validate({
		rules : {
			firstName : {
				required : true
			},
			lastName : {
				required : true
			},
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			firstName : {
				required : "Поле є обов'язковим для введення"
			},
			lastName : {
				required : "Поле є обов'язковим для введення"
			},
			email : {
				required : "Поле є обов'язковим для введення",
				email : "Це не емейл"
			}
		}
	});
});