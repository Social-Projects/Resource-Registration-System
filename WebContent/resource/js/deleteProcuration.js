/**
 * 
 */

$(function() {
	$(document).on("click","#deleteInquiry",
					function(event) {
		event.preventDefault();
		bootbox.confirm(jQuery.i18n.prop('msg.confirmDeleteProcuration'),function(result){
			if (result) {
				window.location = $(event.target).attr("href");
					
			}
		})
		
	})
})

/*В IE8

 element.onclick = function(event) {
  event = event || window.event;

  if (event.preventDefault) { // если метод существует
    event.preventDefault(); // то вызвать его
  } else { // иначе вариант IE8-:
    event.returnValue = false;
  }
} */