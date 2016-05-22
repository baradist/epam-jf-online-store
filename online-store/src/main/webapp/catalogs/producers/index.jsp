<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="Catalog" var="Catalog"/>
<fmt:message bundle="${loc}" key="Producers" var="Producers"/>
<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Name" var="Name"/>
<fmt:message bundle="${loc}" key="Country" var="Country"/>

<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <title>${Catalog} - ${Producers}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp" %>
<div class="container">
    <h1>${Catalog} - ${Producers}</h1>
    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Code}</th>
            <th style="border: 1px solid #000;">${Producers}</th>
            <th style="border: 1px solid #000;">${Country}</th>
        </tr>

        <c:forEach var="Producer" items="${producers}">
            <tr>
                <td style="border: 1px solid #000;">${Producer.id}</td>
                <td style="border: 1px solid #000;">${Producer.name}</td>
                <td style="border: 1px solid #000;">${Producer.country.name}</td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>
