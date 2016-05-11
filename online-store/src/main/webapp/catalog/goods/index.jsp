<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap-3.3.6-dist/css/bootstrap.min.css">
    <script src="/js/jquery-1.12.3.min.js"></script>
    <script src="/css/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="locale" var="loc" />
    <fmt:message bundle="${loc}" key="Goods" var="Goods" />
    <title>${title}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp"%>

<h1>${Goods}</h1>
<table style="border: 1px solid #000;">
    <tr>
        <th style="border: 1px solid #000;">Артикул</th>
        <th style="border: 1px solid #000;">Название</th>
        <th style="border: 1px solid #000;">Производитель</th>
        <th style="border: 1px solid #000;">Описание</th>
    </tr>

    <c:forEach var="good" items="${goods}">
        <tr>
            <td style="border: 1px solid #000;">${good.id}</td>
            <td style="border: 1px solid #000;">${good.name}</td>
            <td style="border: 1px solid #000;">${good.producer.name} / ${good.producer.country.name}</td>
            <td style="border: 1px solid #000;">${good.description}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>