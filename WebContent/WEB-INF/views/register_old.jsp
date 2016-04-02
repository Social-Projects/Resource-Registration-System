<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit User Profile</title>
<link rel="stylesheet" href="${base}resource/css/register.css">
<link rel="stylesheet" href="${base}resource/css/bootstrap.css">
<script src="${base}resource/js/registration/validate_registration.js"
	type="text/javascript"></script>
<script type="text/javascript">
// AJAX request
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
                if(data == "fail"){
                  console.log(username + " is already in use");
                  //divElem.text(username + " is already in use");
                  //divElem.css('color', 'red');
                }
            },
            error: function(){
                console.log("Cannot process request");
            }
        })}})
});
</script>

</head>
<body>
	<div id="register_container" class="col-md-4">
		<legend>
			<strong>Зареєструвати нового користувача</strong>
		</legend>
		<form class="form-horizontal" id="register_form" role="form"
			method="GET" action="register2">
			<div class="personal_header header">
				<span><b> ► Особисті дані</b></span>
			</div>
			<div class="personal_content content">
				<p>
					<label for="first_name">Ім'я *</label> <input name="firstName"
						type="text" class="form-control login" id="first_name"
						tabindex="100" />
				</p>
				<p>
					<label for="lastname">Прізвище *</label> <input name="lastName"
						type="text" class="form-control login" id="lastname"
						tabindex="100" />
				</p>
				<p>
					<label for="middle_name">По батькові *</label> <input
						name="middleName" type="text" class="form-control login"
						id="middlename" tabindex="100" />
				</p>
				<p>
					<label for="login">Логін *</label> <input name="login" type="text"
						class="form-control login" id="login" tabindex="100" />
				</p>
				<p>
					<label for="password">Password *</label> <input id="password"
						name="password" type="password" class="form-control login"
						tabindex="100" />
				</p>
				<p>
					<label for="confirm_password">Confirm password *</label> <input
						id="confirm_password" name="confirm_password" type="password"
						class="form-control login" tabindex="100" />
				</p>
				<p>
					<label for="email">Електронна адреса *</label> <input name="email"
						type="text" class="form-control login" id="email" tabindex="100" />
				</p>
				<p>
					<label for="date_of_birth">Дата народження (у форматі
						рррр-мм-дд)</label> <input name="date_of_birth" type="text"
						class="form-control login" id="date_of_birth" tabindex="100" />
				</p>
			</div>
			<p>
			<div class="passport_header header">
				<span><b> ► Паспортні дані</b></span>

			</div>
			<div class="passport_content content">
				<p>
					<label for="passport_seria">Паспорт (серія) *</label> <input
						name="seria" type="text" class="form-control login"
						id="passport_seria" tabindex="100" />
				</p>
				<p>
					<label for="passport_number">Паспорт (номер) *</label> <input
						name="number" type="text" class="form-control login"
						id="passport_number" tabindex="100" />
				</p>
				<p>
					<label for="published_by_data">Ким і коли виданий *</label> <input
						name="publishedByData" type="text" class="form-control login"
						id="published_by_data" tabindex="100" />
				</p>
			</div>
			<p>
			<div class="address_header header">
				<span><b> ► Адреса</b></span>
			</div>
			<div class="address_content content">
				<label for="city">Місто *</label> <input name="city" type="text"
					class="form-control login" id="city" tabindex="100" />
				</p>
				<p>
					<label for="region">Область *</label> <input name="region"
						type="text" class="form-control login" id="region" tabindex="100" />
				</p>
				<p>
					<label for="district">Район</label> <input name="district"
						type="text" class="form-control login" id="district"
						tabindex="100" />
				</p>
				<p>
					<label for="street">Вулиця *</label> <input name="street"
						type="text" class="form-control login" id="street" tabindex="100" />
				</p>
				<p>
					<label for="building">Будинок *</label> <input name="building"
						type="text" class="form-control login" id="building"
						tabindex="100" />
				</p>
				<p>
					<label for="flat">Номер квартири</label> <input name="flat"
						type="text" class="form-control login" id="flat" tabindex="100" />
				</p>
				<p>
					<label for="postcode">Поштовий код *</label> <input name="postCode"
						type="text" class="form-control login" id="postcode"
						tabindex="100" />
				</p>
			</div>
			<!--             <div id="captcha">
            <p><label>Введіть символи</label>
            </div> -->
			<p>
			<p>
				<button class="btn btn-success" type="submit" id="submit">Надіслати</button>
				<button class="btn btn-success" type="reset"
					onclick="location.reload();">Очистити форму</button>
		</form>
		<p>Поля, позначені зірочкою (*), є обов'язковими для заповнення
</body>

<!--
ru:

register.required_fields = Поля отмеченные * обязательны к заполнению
register.form.title = РЕГИСТРАЦИОННАЯ ИНФОРМАЦИЯ
register.user.firstname = Имя *
register.user.lastname = Фамилия *
register.user.middlename = Отчество *
register.user.username = Логин (only Latin letters are accepted)*
register.user.password = Пароль *
register.user.confirm_password = Подтвержение пароля *
register.user.email = Email *
register.user.date_of_birth = Дата рождения (гггг-мм-дд)
register.user.passport = Серия паспорта *
register.user.passport_number = Номер паспорта *
register.user.city = Город (посёлок) *
register.user.street = Улица *
register.user.flat = Квартира
register.user.district = Район
register.user.postcode = Почтовый код *
register.user.building = Дом *
register.user.region = Область *
register.submit = Отправить
register.reset = Очистить форму
register.alert.required_fields_missed = Пожалуйста заполните обязательные поля
register.user.



en:
register.required_fields = Fields, marked with (*), are mandatory
register.form.title = REGISTRATION FORM
register.user.firstname = First Name *
register.user.lastname = Last Name *
register.user.middlename = Middle Name *
register.user.username = Login (username) *
register.user.password = Password *
register.user.confirm_password = Confirm password *
register.user.email = Email *
register.user.date_of_birth = Data of birth (yyyy-mm-dd)
register.user.passport = Passport () *
register.user.passport_number = Passport number *
register.user.city = City (town, village) *
register.user.street = Street *
register.user.flat = Appartment
register.user.district = District
register.user.postcode = Postcode *
register.user.building = Building number *
register.user.region = Region *
register.submit = Submit
register.reset = Reset
register.alert.required_fields_missed = Please fill in required fields

ua:
register.required_fields = Поля, позначені зірочкою (*), є обов'язковими для заповнення
register.form.title = РЕЄСТРАЦІЙНА ФОРМА
register.user.firstname = Ім'я *
register.user.lastname = Прізвище *
register.user.middlename = По батькові *
register.user.username = Логін (дозволяється використовувати лише латинські символи) *
register.user.password = Пароль *
register.user.confirm_password = Підтвердити пароль *
register.user.email = Електронна адреса *
register.user.date_of_birth = Дата народження (у форматі рррр-мм-дд)
register.user.passport = Серія паспорта *
register.user.passport_number = Номер паспорта *
register.user.city = Місто (селище, село) *
register.user.street = Вулиця *
register.user.flat = Квартира
register.user.district = Район ("-" якщо немає)
register.user.postcode = Поштовий код *
register.user.building = Номер будинку *
register.user.region = Область *
register.submit = Надіслати
register.reset = Очистити форму
register.alert.required_fields_missed = Будь ласка заповніть обов'язкові поля
-->