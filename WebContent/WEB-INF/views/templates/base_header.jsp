<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<div class="site-title">
   <h3><spring:message code="label.name.part1"/></h3>
   <h3><spring:message code="label.name.part2"/></h3>
</div>
<div class="lang-container" id="header">
            <ul class="languages">
                <a href="?lang=ua"><img src="<c:url value='/resource/img/lang/ua.png'/>"></a>
                <a href="?lang=ru"><img src="<c:url value='/resource/img/lang/ru.png'/>"></a>
                <a href="?lang=en"><img src="<c:url value='/resource/img/lang/en.png'/>"></a>
            </ul>
</div>