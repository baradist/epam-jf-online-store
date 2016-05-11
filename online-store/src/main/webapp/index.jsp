<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
    <link rel="stylesheet" href="css/bootstrap-3.3.6-dist/css/bootstrap.min.css">
    <script src="js/jquery-1.12.3.min.js"></script>
    <script src="css/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="locale" var="loc" />
    <fmt:message bundle="${loc}" key="main.title" var="title" />
    <fmt:message bundle="${loc}" key="head.listOfGoods" var="listOfGoods" />
    <%--<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />--%>
    <title>${title}</title>
</head>
<body>

<%--<jsp:include page="WEB-INF/top_line.jsp" />--%>
<%@ include file="WEB-INF/top_line.jsp"%>

<%--<form action="localizator" method="post">--%>
    <%--<input type="hidden" name="local" value="ru" /> <input type="submit" value="${ru_button}" /><br />--%>
<%--</form>--%>
<%--<form action="localizator" method="post">--%>
    <%--<input type="hidden" name="local" value="en" /> <input type="submit" value="${en_button}" /><br />--%>
<%--</form>--%>

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
