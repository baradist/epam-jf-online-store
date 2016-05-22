<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="main.Title" var="Title"/>
<fmt:message bundle="${loc}" key="ListOfGoods" var="ListOfGoods"/>

<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Name" var="Name"/>
<fmt:message bundle="${loc}" key="Producer" var="Producer"/>
<fmt:message bundle="${loc}" key="Price" var="Price"/>
<fmt:message bundle="${loc}" key="Basket" var="Basket"/>
<fmt:message bundle="${loc}" key="BasketPutInto" var="BasketPutInto"/>
<fmt:message bundle="${loc}" key="BasketRemoveFrom" var="BasketRemoveFrom"/>

<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <title>${Title}</title>
</head>
<body>

<%--<jsp:include page="WEB-INF/top_line.jsp" />--%>
<%@ include file="/WEB-INF/top_line.jsp" %>
<div class="container">
<h1>${ListOfGoods}</h1>
<table style="border: 1px solid #000;">
    <tr>
        <th style="border: 1px solid #000;">${Code}</th>
        <th style="border: 1px solid #000;">${Name}</th>
        <th style="border: 1px solid #000;">${Producer}</th>
        <th style="border: 1px solid #000;">${Price}</th>
        <th style="border: 1px solid #000;">${Basket}</th>
    </tr>

    <c:forEach var="item" items="${list}">
        <tr>
            <td style="border: 1px solid #000;">${item.good.id}</td>
            <td style="border: 1px solid #000;">${item.good.name}</td>
            <td style="border: 1px solid #000;">${item.good.producer.name} / ${item.good.producer.country.name}</td>
            <td style="border: 1px solid #000;">${item.price}</td>
            <td style="border: 1px solid #000;">
                <c:choose>
                    <c:when test="${item.quantityOrdered > 0}">
                        <form action="/basket/edit/" method="post" class="horizontal">
                            <input type="hidden" name="good" value="${item.good.id}"/>
                            <input type="hidden" name="delete" value="${true}"/>
                            <input type="submit" value="${BasketRemoveFrom}" class="btn btn-default">
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="/basket/edit/" method="post" class="horizontal">
                            <input type="hidden" name="good" value="${item.good.id}"/>
                            <input type="hidden" name="quantity" value="${1}"/>
                            <input type="hidden" name="price" value="${item.price}"/>
                            <input type="hidden" name="add" value="${true}"/>
                            <input type="submit" value="${BasketPutInto}" class="btn btn-default">
                        </form>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>

</table>

<%@ include file="/WEB-INF/pager.jsp" %>
</div>

</body>

</html>
