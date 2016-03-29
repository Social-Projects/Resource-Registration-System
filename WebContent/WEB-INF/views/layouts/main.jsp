<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- fav-icon -->
    <link rel="Shortcut Icon" href="<c:url value='/resource/img/maple_leaf.ico'/>"
          type="image/x-icon"/>
    <title><tiles:getAsString name="title"/></title>

    <!-- JQuery lib-->
    <script src="<c:url value='/resource/js/lib/jquery-1.12.0.min.js'/>"></script>
    <script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="<c:url value='/resource/css/bootstrap.css'/>">
    <script src="<c:url value='/resource/js/lib/bootstrap.min.js'/>"></script>

    <!-- Bootbox -->
    <script src="<c:url value='/resource/js/lib/bootbox.js'/>"></script>

    <!-- DataTables lib -->
    <script src="<c:url value='/resource/js/lib/jquery.dataTables.min.js'/>"></script>
    <%--<script src="<c:url value='/resource/js/lib/dataTables.bootstrap.js'/>"></script>--%>
    <!-- DataTables CSS-->
    <link rel="stylesheet" href="<c:url value='/resource/css/jquery.dataTables.min.css'/>">
    <%--<link rel="stylesheet" href="<c:url value='/resource/css/dataTables.bootstrap.css'/>">--%>

    <!-- Our JavaScript-->
    <script src="<c:url value='/resource/js/scripts.js'/>"></script>
    <!-- Our own css -->
    <link rel="stylesheet" href="<c:url value='/resource/css/system.css'/>">

</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">

    <div class="container">
        <div class="row">
            <div class="col-md-12" id="header">
                <tiles:insertAttribute name="header"/>
            </div>
        </div>
        <div class="row">
            <div id="menu">
                <div class="col-md-12" id="menuForAdmin">
                    <tiles:insertAttribute name="menuForAdmin" role="ADMIN"/>

                </div>
                <div class="col-md-12" id="menuForRegistrator">
                    <tiles:insertAttribute name="menuForRegistrator"
                                           role="REGISTRATOR"/>

                </div>
                <div class="col-md-12" id="menuForUser">
                    <tiles:insertAttribute name="menuForUser" role="USER"/>

                </div>
                 <div class="col-md-12" id="menuForCommissioner">
                    <tiles:insertAttribute name="menuForCommissioner" role="COMMISSIONER"/>

                </div>
            </div>
        </div>
        <div class="row">
            <div id="body">
                <tiles:insertAttribute name="body"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12" id="footer">
                <tiles:insertAttribute name="footer"/>
            </div>
        </div>

    </div>

<div id="baseurl" class="hidden"><c:url value="/"/> </div>
</c:if>