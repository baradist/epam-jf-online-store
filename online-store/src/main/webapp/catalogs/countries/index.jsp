<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
    <%@ include file="/WEB-INF/import.html"%>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="locale" var="loc" />
    <fmt:message bundle="${loc}" key="Countries" var="Countries" />
    <title>${title}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp"%>

<h1>${Countries}</h1>
<table style="border: 1px solid #000;">
    <tr>
        <th style="border: 1px solid #000;">Код</th>
        <th style="border: 1px solid #000;">Страна</th>
    </tr>

    <c:forEach var="country" items="${countries}">
        <tr>
            <td style="border: 1px solid #000;">${country.id}</td>
            <td style="border: 1px solid #000;">${country.name}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
