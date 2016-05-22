<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="Catalog" var="Catalog"/>
<fmt:message bundle="${loc}" key="Stores" var="Stores"/>
<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Store" var="Store"/>
<fmt:message bundle="${loc}" key="Address" var="Address"/>

<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <title>${Catalog} - ${Stores}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp" %>
<div class="container">
    <h1>${Catalog} - ${Stores}</h1>
    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Code}</th>
            <th style="border: 1px solid #000;">${Store}</th>
            <th style="border: 1px solid #000;">${Address}</th>
        </tr>

        <c:forEach var="store" items="${stores}">
            <tr>
                <td style="border: 1px solid #000;">${store.id}</td>
                <td style="border: 1px solid #000;">${store.name}</td>
                <td style="border: 1px solid #000;">${store.address}</td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>
