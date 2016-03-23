$(function() {
	$(document).on("click","#deleterestype",
					function(event) {
		event.preventDefault();
						bootbox.confirm(jQuery.i18n.prop('msg.confirmDelete'),function(result){
						if (result) {
							$
									.ajax({
										url : $(event.target).attr("href"),
										type : "DELETE",

										success : function() {
											var tr = $(event.target).closest(
													"tr");
											tr.css("background-color",
													"#000000");
											tr.fadeIn(1000).fadeOut(200,
													function() {
														tr.remove();
													})
										},
										error : function() {
											  bootbox.alert(jQuery.i18n.prop('msg.canNotDelete'));
										}

									});
						} else {
							event.preventDefault();
						}
						event.preventDefault();
					});
});})