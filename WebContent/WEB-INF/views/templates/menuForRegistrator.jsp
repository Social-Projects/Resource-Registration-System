<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<nav class="navbar navbar-default" id="menubarreg">
    <div class="container-fluid">

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value='/'/>" class="glyphicon glyphicon-home"></a></li>
                <li class="dropdown"><a href="<c:url value='/registrator/resource/searchOnMap'/>">
                    <spring:message code="label.menu.resources" /></a></li>
                <li><a href="<c:url value='/registrator/resourcetypes/show-res-types'/>">
                    <spring:message code="label.menu.resourcesTypes" /></a></li>
                
                <li class="dropdown"><a href="#" class="not-active"><spring:message code="label.menu.inquiries" /></a>
                    <ul class="dropdown_menu">
                        <li> <a href="<c:url value='/inquiry/add/listInquiryUserInput'/>">
                            <spring:message code="label.menu.inquiries.input" /></a>
                        </li>
                        <li> <a href="<c:url value='/inquiry/add/listInqUserOut'/>">
                            <spring:message code="label.menu.inquiries.output" /></a>
                        </li>
                    </ul>
                </li>   
                <li><a href="<c:url value='/registrator/resource/addresource'/>"><spring:message code="label.menu.addnewresource"/></a>
            </ul>
        </div>
    </div>
</nav>