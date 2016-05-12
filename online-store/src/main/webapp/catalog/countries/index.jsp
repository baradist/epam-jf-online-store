<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
    <%@ include file="/WEB-INF/import.html"%>
    <%--<link rel="stylesheet" href="/css/bootstrap-3.3.6-dist/css/bootstrap.min.css">--%>
    <%--<script src="/js/jquery-1.12.3.min.js"></script>--%>
    <%--<script src="/css/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>--%>
    <%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8">--%>
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
        <th style="border: 1px solid #000;">Страна</th>
    </tr>

    <c:forEach var="country" items="${countries}">
        <tr>
            <td style="border: 1px solid #000;">${country.name}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
