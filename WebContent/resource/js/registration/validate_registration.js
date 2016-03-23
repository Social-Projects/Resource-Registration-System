// expand/collapse input fields
$(document).ready(function() {
  $(".content").hide();
  $(".header").click(function()
  {
    $(this).next(".content").slideToggle(500);
  });
});

$().ready(function() {
    // validate signup form on keyup and submit
  $("#register_form").validate({
    rules: {
      firstname: "required",
      lastname: "required",
      middlename: "required",
      username: {
        required: true,
        minlength: 3,
        maxlength: 20
      },
      password: {
        required: true,
        minlength: 5
      },
      confirm_password: {
        required: true,
        minlength: 5,
        equalTo: "#password"
      },
      email: {
        required: true,
        email: true
      },
      phone: {
        required: false,
        number: true
      },
      passport: {
        required: true,
        minlength: 2,
        maxlength: 2
      },
      passport_number: {
        required: true,
        minlength: 6,
        maxlength: 6,
        digits: true
      },
      date_of_birth: {
        required: true,
        dateISO: true
      },
      region: { required: true },
      city: { required: true },
      building: { required: true },
      postcode: { required: true },
      street: { required: true }
    },
    messages: {
      firstname: "Please enter your firstname",
      lastname: "Please enter your lastname",
      middlename: "Please enter your middlename",
      username: {
        required: "Please enter a username",
        minlength: "Username must consist of at least 3 characters",
        maxlength: "Username must not exceed 20 characters"
      },
      password: {
        required: "Please provide a password",
        minlength: "Password must be at least 5 characters long"
      },
      confirm_password: {
        required: "Please provide a password",
        minlength: "Password must be at least 5 characters long",
        equalTo: "Please enter the same password as above"
      },
      email: "Please enter a valid email address",
      agree: "Please accept our policy",
      passport: {
        required: "Please provide a passport data (seria)",
        minlength: "Passport seria must consist of 2 letters",
        maxlength: "Passport seria must consist of 2 letters",
      },
      passport_number: {
        required: "Please provide a passport number",
        minlength: "Passport number must consist of 6 digits",
        maxlength: "Passport number must consist of 6 digits",
      },

      street: "required",
      postcode: "required",
      building: "required",
      region: "required"
      }
  });

// validate each input field on blur and show warning if necessary
$("input").blur(function() {
    var valid = $(this).valid();
    if(valid == false){
        $(this).css("border", "1px solid red");
    }
    else{
      $(this).css("border", "");
    }
  });

// validate input fields on submit the form
function validateInputFields(){
    var valid = $("input").valid();
    if(valid == false){
        $("input").css("border", "1px solid red");
      }
};

$('#submit').click(function () {
    var valid = $("input").valid();
    if(valid == false){
      bootbox.alert('Please, fill in required fields');
        validateInputFields();
    }
});

// check if confirm password is still valid after password changed
$("#password").blur(function() {
    $("#confirm_password").valid();
  });
});
