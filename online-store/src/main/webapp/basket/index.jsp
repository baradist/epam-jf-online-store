<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="Basket" var="title"/>
<fmt:message bundle="${loc}" key="ChosenGoods" var="ChosenGoods"/>
<fmt:message bundle="${loc}" key="CheckYourInfo" var="CheckYourInfo"/>
<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Name" var="Name"/>
<fmt:message bundle="${loc}" key="Producer" var="Producer"/>
<fmt:message bundle="${loc}" key="Price" var="Price"/>
<fmt:message bundle="${loc}" key="Quantity" var="Quantity"/>
<fmt:message bundle="${loc}" key="Phone" var="Phone"/>
<fmt:message bundle="${loc}" key="Address" var="Address"/>
<fmt:message bundle="${loc}" key="Send" var="Send"/>
<fmt:message bundle="${loc}" key="Reset" var="Reset"/>

<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <title>${title}</title>
</head>
<body>

<%--<jsp:include page="WEB-INF/top_line.jsp" />--%>
<%@ include file="/WEB-INF/top_line.jsp" %>

<div class="container">
    <h1>${ChosenGoods}</h1>
    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Code}</th>
            <th style="border: 1px solid #000;">${Name}</th>
            <th style="border: 1px solid #000;">${Producer}</th>
            <th style="border: 1px solid #000;">${Price}</th>
            <th style="border: 1px solid #000;">${Quantity}</th>
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
                </td>
            </tr>
        </c:forEach>

    </table>

    ${CheckYourInfo}

    <form method="POST" action="/basket/send/">
        <input type="hidden" name="send" value="${true}">
        <table border="0" cellspacing="5">
            <tr>
                <th align="right">${Phone}:</th>
                <td align="left"><input type="text" name="phone" value="${requestScope.person.phone}"></td>
            </tr>
            <tr>
                <th align="right">${Address}:</th>
                <td align="left"><input type="text" name="address" value="${requestScope.person.address}"></td>
            </tr>
            <tr>
                <td align="right"><input type="submit" value="${Send}"></td>
                <td align="left"><input type="reset" value="${Reset}"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
