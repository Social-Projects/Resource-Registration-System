$(function() {
	$(Document).on("click","#valid",
					function(event) {
						bootbox.alert("checking");
})
.ajax({
	url : $(event.target).attr("href"),

	error : function() {
		bootbox.alert('checking');
	}

});
		
		
		
});
