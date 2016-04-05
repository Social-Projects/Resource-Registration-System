jQuery(document).ready(function($) {
  $('.reset-my-password').click(function() {
    $("#dark_bg").show();
    $.ajax({
      type : "GET",
      url : "/reset_password",
      success : function(data) {
        bootbox.alert(jQuery.i18n.prop(data));
        $("#dark_bg").hide();
        return false;
      },
      error : function(xhr, status, error) {
        $("#dark_bg").hide();
        bootbox.alert("<h3>Error performing CUSTOM Password reset operation</h3>" + xhr.responseText);
        return "";
      }
    });
  });

})
