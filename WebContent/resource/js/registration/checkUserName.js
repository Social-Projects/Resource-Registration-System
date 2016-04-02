$(document).ready(function() {
  $('#username').keyup(function (){
      var username = $(this).val();
        console.log(username);

        if(username.length >= 3){
        $.ajax({
            url: 'http://localhost:8080/registrator/check-username-is-available',
            method: 'GET',
            data: {login: username},
            dataType: 'json',
            success: function(data){
                var divElem = $();
            },
        })
        }})
});