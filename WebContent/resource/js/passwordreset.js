jQuery(document).ready(function($) {
    $('.reset-my-password').click(function () {
        $("#dark_bg").show();
        $.ajax({
            type: "GET",
            url: "password-reset",
            //dataType: "text",
            //data: JSON.stringify(json),
            //contentType: 'application/json; charset=utf-8',
            //mimeType: 'application/json',
            success: function (data) {
                bootbox.alert(jQuery.i18n.prop(data));
                $("#dark_bg").hide();
                return false;
            },
            error: function (xhr, status, error) {
                bootbox
                    .alert("<h3>Error performing CUSTOM Password reset operation</h3>"
                        + xhr.responseText);
                return "";
            }
        });
    });


})
