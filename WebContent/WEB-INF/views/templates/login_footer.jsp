<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div style="margin-top: 10px;">
       <p><span style="text-align:left; left: 30px">&copy;2015 <spring:message code="label.copyright"></spring:message></span></p>
       <p><span style="text-align:right; right: 30px"><a href="${pageContext.request.userPrincipal.name}/faq">Допомога </a> <a href="${pageContext.request.userPrincipal.name}/help"> Зворотній зв'язок</a></span></p>
</div>