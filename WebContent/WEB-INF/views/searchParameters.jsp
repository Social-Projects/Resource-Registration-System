<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:if test="${not empty discreteParameters or not empty linearParameters}">
    <div><h5><i><spring:message code="label.search.message1" />:</i></h5></div>
</c:if>
<c:if test="${not empty discreteParameters}">
    <div id="discreteParameters" class="container">
        <c:forEach items="${discreteParameters}" var="discreteParameter">
            <div class="col-md-6 discreteParameter" style="padding-bottom: 4px;"
                 param_id="${discreteParameter.discreteParameterId}">
                <label class="col-md-3">${discreteParameter.description}</label>
                <span class="col-md-2">
                    <select class="form-control compare">
                        <option value="less"> <</option>
                        <option value="equal" selected> =</option>
                        <option value="greater"> ></option>
                    </select>
                </span>
                <span class="col-md-6">
                    <input type="text" class="form-control value"/>
                </span>
                <label class="col-md-1">${discreteParameter.unitName}</label>
            </div>
        </c:forEach>
    </div>
</c:if>
<c:if test="${not empty linearParameters}">
    <div id="linearParameters" class="container">
        <c:forEach items="${linearParameters}" var="linearParameter">
            <div class="col-md-6 linearParameter" style="padding-bottom: 4px;"
                 param_id="${linearParameter.linearParameterId}">
                <label class="col-md-3">${linearParameter.description}:</label>
                <span class="col-md-6 col-md-offset-2">
                    <input type="text" class="form-control value"/>
                </span>
                <label class="col-md-1">${linearParameter.unitName}</label>
            </div>
        </c:forEach>
    </div>
</c:if>
<div class="container">
<c:if test="${not empty discreteParameters or not empty linearParameters}">
    <button class="search btn btn-success" id="search">
        <span class="glyphicon glyphicon-search"></span>
        <spring:message code="label.menu.search" />
    </button>
</c:if>
    <button class="search btn btn-success" id="showAllResources">
        <span class="glyphicon glyphicon-search"></span>
        <spring:message code="label.search.showAll" />
    </button>
</div>