<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty Resources}">
    <h4>За Вашим запитом нічого не знайдено.</h4>
</c:if>
<c:if test="${not empty Resources}">
    <table id="datatable">
        <thead>
        <tr>
            <th>Підклас об'єкту</th>
            <th>Ідентифікатор</th>
            <th>Опис</th>
            <th>Дата</th>
            <%--<c:forEach items="${Resources[0].resourceDiscrete}" var="resourceDiscrete">--%>
                <%--<th>${resourceDiscrete.discreteParameterDescription}, ${resourceDiscrete.discreteParameterUnit}</th>--%>
            <%--</c:forEach>--%>
            <%--<c:forEach items="${Resources[0].resourceLinear}" var="resourceLinear">--%>
                <%--<th>${resourceLinear.linearParameterDescription}, ${resourceLinear.linearParameterUnit}</th>--%>
            <%--</c:forEach>--%>
            <th>Детальніше</th>
        </tr>
        </thead>
        <c:forEach items="${Resources}" var="resource">
            <tr <c:if test="${resource.status}"/>>
                <td>${resource.identifier}</td>
                <td>${resource.description}</td>
                <td><fmt:formatDate pattern="dd-MM-yyyy" value="${resource.date}" /></td>
                <c:forEach items="${resource.resourceDiscrete}" var="resourceDiscrete">
                    <td>
                        <c:forEach items="${resourceDiscrete.valueDiscretes}" var="valueDiscrete">
                                ${valueDiscrete.value} <br/>
                        </c:forEach>
                    </td>
                </c:forEach>
                <c:forEach items="${resource.resourceLinear}" var="resourceLinear">
                    <td>
                        <c:forEach items="${resourceLinear.segments}" var="segment">
                            ${segment.begin} - ${segment.end} <br/>
                        </c:forEach>
                    </td>
                </c:forEach>
                <td>
                    <a href="get/${resource.identifier}" class="btn btn-success">Детальніше</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>