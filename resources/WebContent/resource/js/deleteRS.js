$(document).ready(function() {
    var deleteLink = $("a:contains('Delete')");
    $(deleteLink).click(function(event) {
    	alert("Якщо в базі містяться ресурси даного типу, він видалений не буде")
        var conBox = confirm("Ви впевнені, що хочете видалити цей тип?");
        if(conBox){
        $.ajax({
            url: $(event.target).attr("href"),
            type: "DELETE",

            success: function() {
                var tr = $(event.target).closest("tr");
                tr.css("background-color","#000000");
                tr.fadeIn(1000).fadeOut(200, function(){
                tr.remove();})
            }
        });
        } else {
            event.preventDefault();
        }
        event.preventDefault();
    });
});
