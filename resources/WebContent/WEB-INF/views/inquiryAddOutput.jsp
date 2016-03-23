<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Реєстратор</title>
<link rel="stylesheet" type="text/css" href="resource/css/normalize.css">
<link rel="stylesheet" type="text/css" href="resource/css/1.css">

</head>
<body>
    <header class = "header">
		<div class="container">
		<h1><spring:message code="label.title" /></h1>
		<h2>
		<small><spring:message code="label.subtitle" /></small></h2>
		
		<div class="languages">
		<a href="?lang=ua">UA</a>|
        <a href="?lang=en">ENG</a>|
        <a href="?lang=ru">RUS</a> 
     </div>
     
     
		</div> </header>
         <!--     <a  href="www.google.com" role="button">Перейти на нову версію</a>-->
         
       
          
           
	<nav class="page-navigation">
		<div class="container">
		<ul>
			<li><a href="http://rada.gov.ua/">Home</a></li>
			<li><a href="3.html">About</a></li>
			<li><a href="4.html">Contact</a></li>

		</ul>
		
		<!--FORM FOR ADDING OUTPUT INQUIRY-->
		
		
		
		<form action="/inquiry/add/output" name="inquiryAddOutput" class="INQUIRY" method="get">
		<pre>
		користувач              <input type="text" name="user" value="ivan"   >  <!--hidden -->	<br/> 
		реєстратор              <select name="registratorList" form="inquiryAddOutput">
									<option value="oleks">Архилюк Олександр</option>
									<option value="petro">Петренко Петро</option>						  
							    </select> 	<br/> 
		ідентифікатор ресурсу   <input type="text" name="resourceIdentifier" value="123567"> 	<br/> 
						
		<input type="submit" />
		</pre>
		</form>
		
		
		<br/> <br/>
		
		<!-- /FORM FOR ADDING OUTPUT INQUIRY -->
		</div>
	</nav> 
 <main></main>
                            <img src="resource/img/1.jpg" alt="list picture" width="300px" height="300px">
                            <img src="resource/img/2.jpg" alt="list picture" width="300px" height="300px">
                            <img src="resource/img/3.jpg" alt="list picture" width="300px" height="300px">
                            <img src="resource/img/4.jpg" alt="list picture" width="300px" height="300px">
                            <h3>Fauna</h3>
                            <p>Stork and others
    </p>
                            <button>Show more about this resource...</button>
                       


<footer class="footer">Copyright Softserve 2015 Koroliuk</footer>
</body>
</html>