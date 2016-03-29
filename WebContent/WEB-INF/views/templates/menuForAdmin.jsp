<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<nav class="navbar navbar-default" id="menubaradmin">
    <div class="container-fluid">

        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value='/'/>" class="glyphicon glyphicon-home"></a></li>
                 <li class="dropdown"><a href="#" class="not-active"><spring:message code="label.user.pagename" /></a>
                    <ul class="dropdown_menu">
                         <li><a href="<c:url value='/administrator/users/get-all-users'/>">
                             <spring:message code="label.registrated.pagename" /></a></li>
                         <li><a href="<c:url value='/administrator/users/get-all-inactive-users'/>">
                              <spring:message code="label.inactive.pagename" /></a></li>
                    </ul>
                </li>
                <li><a href="<c:url value='/administrator/settings'/>">
                    <spring:message code="label.admin.settings"/></a></li>
                <li><a href="<c:url value='/administrator/communities/show-all-communities'/>"><spring:message code="label.community.showall"/></a></li>
                <li><a href="<c:url value='/manualregistration'/>"><spring:message
                                code="label.manualregister" /></a></li>
                                
                <li><a href="#" id="unlockUsers" ><spring:message code="label.security.unblockall"/></a></li>                                   
                <li><a href="<c:url value='/administrator/users/search'/>" class="glyphicon glyphicon-search"></a></li>
            </ul>
        </div>
    </div>
</nav>


<script>
        $("#unlockUsers").click(function(e){
            e.preventDefault();
             $.get("${pageContext.request.contextPath}/administrator/users/unlockusers", function(){
                });
             bootbox.alert(jQuery.i18n.prop('msg.unblockallusers'));
        });
</script>

