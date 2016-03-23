var baseUrl;

function positionFooter() {

    var headerHeight = $("#header").parent(".row").height();
    var menuHeight = $("#menu").parent(".row").height();
    var body = $("#body");
    var footerHeight = $("#footer").parent(".row").height();

    var newBodyHeight = ($(window).height()-headerHeight-menuHeight-footerHeight-5);

    if ( (body.height()) < newBodyHeight+5) {
        body.parent(".row").height(newBodyHeight);
    } else {
        body.parent(".row").css({
            height: ""
        })
    }
}

$(document).ready(function () {

    positionFooter();

    baseUrl = $("#baseurl").html();
    baseUrl = baseUrl.substring(0,baseUrl.length-2);

    $(document).ajaxSuccess(function(){
        positionFooter();
    });

    $(window).resize(positionFooter);

    //Dropdown menu
    $(".dropdown").mouseenter(function(){
        $(this).find(".dropdown_menu").slideDown(150);
    });

    $(".dropdown").mouseleave(function(){
        $(this).find(".dropdown_menu").slideUp(150);
    });

});