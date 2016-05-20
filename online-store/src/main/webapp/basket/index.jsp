<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="main.title" var="title"/>
    <fmt:message bundle="${loc}" key="head.listOfLots" var="listOfGoods"/>
    <title>${title}</title>

</head>
<body>

<%--<jsp:include page="WEB-INF/top_line.jsp" />--%>
<%@ include file="/WEB-INF/top_line.jsp" %>

<h1>${listOfGoods}</h1>
<table style="border: 1px solid #000;">
    <tr>
        <th style="border: 1px solid #000;">Артикул</th>
        <th style="border: 1px solid #000;">Название</th>
        <th style="border: 1px solid #000;">Производитель</th>
        <th style="border: 1px solid #000;">Цена</th>
        <th style="border: 1px solid #000;">Количество</th>
    </tr>

    <c:forEach var="item" items="${list}">
        <tr>
            <td style="border: 1px solid #000;">${item.good.id}</td>
            <td style="border: 1px solid #000;">${item.good.name}</td>
            <td style="border: 1px solid #000;">${item.good.producer.name} / ${item.good.producer.country.name}</td>
            <td style="border: 1px solid #000;">${item.price}</td>
                <%--quantity--%>
            <td style="border: 1px solid #000;">
                <form action="/basket/" method="post" class="horizontal">
                    <input type="hidden" name="decrease" value="${true}"/>
                    <input type="hidden" name="orderItemId" value="${item.id}"/>
                    <input type="submit" value="-" class="btn btn-default">
                </form>
                    ${item.getIntQuantity()}
                <form action="/basket/" method="post" class="horizontal">
                    <input type="hidden" name="increase" value="${true}"/>
                    <input type="hidden" name="orderItemId" value="${item.id}"/>
                    <input type="submit" value="+" class="btn btn-default">
                </form>
                <form action="/basket/" method="post" class="horizontal">
                    <input type="hidden" name="delete" value="${true}"/>
                    <input type="hidden" name="orderItemId" value="${item.id}"/>
                    <input type="submit" value="x" class="btn btn-default">
                </form>
                    <%--<ul class="nav navbar-nav navbar-fixed-bottom">--%>
                    <%--<li>--%>
                    <%--<form action="/basket" method="post" class="horizontal">--%>
                    <%--<input type="hidden" name="decrease" value="${true}"/>--%>
                    <%--<input type="submit" value="-" class="btn btn-default">--%>
                    <%--</form>--%>
                    <%--</li>--%>
                    <%--<li>${item.quantity}</li>--%>
                    <%--<li>--%>
                    <%--<form action="/basket" method="post" class="horizontal">--%>
                    <%--<input type="hidden" name="increase" value="${true}"/>--%>
                    <%--<input type="submit" value="+" class="btn btn-default">--%>
                    <%--</form>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                    <%--<form action="/basket" method="post" class="horizontal">--%>
                    <%--<input type="hidden" name="delete" value="${true}"/>--%>
                    <%--<input type="submit" value="+" class="btn btn-default">--%>
                    <%--</form>--%>
                    <%--</li>--%>
                    <%--</ul>--%>
            </td>
        </tr>
    </c:forEach>

</table>

<%--<ul class="nav navbar-nav navbar-fixed-bottom">--%>
<%--<c:set value="${0}" var="n" scope="page"/>--%>
<%--<c:forEach var="i" begin="0" step="${requestScope.rowsOnPage}" end="${requestScope.quantity - 1}">--%>
<%--<c:set value="${n + 1}" var="n" scope="page"/>--%>
<%--<li>--%>
<%--<form action="/" method="get" class="horizontal">--%>
<%--<input type="hidden" name="offset" value="${i}"/>--%>
<%--<input type="hidden" name="pageNumber" value="${n}"/>--%>
<%--<input type="hidden" name="rowsOnPage" value="${requestScope.rowsOnPage}"/>--%>
<%--<input type="submit" value="${n}"--%>
<%--<c:if test="${requestScope.pageNumber eq n}">--%>
<%--disabled="true"--%>
<%--</c:if>--%>
<%--class="btn btn-default">--%>
<%--</form>--%>
<%--</li>--%>
<%--</c:forEach>--%>

<%--<li>--%>
<%--<form action="/" method="get" id="pagesTune" class="horizontal">--%>
<%--<select required name="rowsOnPage">--%>
<%--<option <c:if test="${requestScope.rowsOnPage == 5}"> selected </c:if> value="5">5</option>--%>
<%--<option <c:if test="${requestScope.rowsOnPage == 10}"> selected </c:if> value="10">10</option>--%>
<%--<option <c:if test="${requestScope.rowsOnPage == 20}"> selected </c:if> value="20">20</option>--%>
<%--<option <c:if test="${requestScope.rowsOnPage == 50}"> selected </c:if> value="50">50</option>--%>
<%--</select>--%>
<%--<input form="pagesTune" type="submit" value="Обновить">--%>
<%--</form>--%>
<%--</li>--%>
<%--</ul>--%>

</body>
</html>
