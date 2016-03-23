<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base" value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/"/>


<script type="text/javascript" src="${base}/resource/js/showAllResources.js"></script>

<div>
    <c:if test="${not empty resourceTypes}">
        <div style="padding-bottom: 15px;" >
            <label class="">Оберіть підклас об'єкту: </label>
            <select id="resourcesTypeSelect" class="form-control" style="width:auto; display: inline">
                <c:forEach items="${resourceTypes}" var="resourceType">
                    <option value="${resourceType.typeId}">${resourceType.typeName}</option>
                </c:forEach>
            </select>
        </div>
        <table id="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Identifier</th>
                <th>Description</th>
                <th>Date</th>
                <th>Status</th>
                    <%--<th>Status</th>--%>
                    <%--<th>Status</th>--%>
                    <%--<th>Status</th>--%>
                    <%--<th>Status</th>--%>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                    <%--<td></td>--%>
                    <%--<td></td>--%>
                    <%--<td></td>--%>
                    <%--<td></td>--%>
            </tr>
            </tbody>
        </table>
    </c:if>

</div>