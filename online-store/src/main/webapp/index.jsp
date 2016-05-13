<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
    <%@ include file="/WEB-INF/import.html"%>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="locale" var="loc" />
    <fmt:message bundle="${loc}" key="main.title" var="title" />
    <fmt:message bundle="${loc}" key="head.listOfLots" var="listOfGoods" />
    <%--<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />--%>
    <title>${title}</title>

</head>
<body>

<%--<jsp:include page="WEB-INF/top_line.jsp" />--%>
<%@ include file="WEB-INF/top_line.jsp"%>

<h1>${listOfGoods}</h1>
<table style="border: 1px solid #000;">
    <tr>
        <th style="border: 1px solid #000;">Артикул</th>
        <th style="border: 1px solid #000;">Название</th>
        <th style="border: 1px solid #000;">Производитель</th>
        <th style="border: 1px solid #000;">Цена</th>
    </tr>

    <c:forEach var="lot" items="${lots}">
        <tr>
            <td style="border: 1px solid #000;">${lot.good.id}</td>
            <td style="border: 1px solid #000;">${lot.good.name}</td>
            <td style="border: 1px solid #000;">${lot.good.producer.name} / ${lot.good.producer.country.name}</td>
            <td style="border: 1px solid #000;">${lot.price_sal}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
