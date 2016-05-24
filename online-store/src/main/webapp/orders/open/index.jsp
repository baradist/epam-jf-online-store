<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="New" var="New"/>
<fmt:message bundle="${loc}" key="Save" var="Save"/>
<fmt:message bundle="${loc}" key="Reset" var="Reset"/>

<fmt:message bundle="${loc}" key="Document" var="Document"/>
<fmt:message bundle="${loc}" key="Orders" var="Orders"/>
<fmt:message bundle="${loc}" key="Order" var="Order"/>
<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Number" var="Number"/>
<fmt:message bundle="${loc}" key="Date" var="Date"/>
<fmt:message bundle="${loc}" key="Buyer" var="Buyer"/>
<fmt:message bundle="${loc}" key="Producer" var="Producer"/>
<fmt:message bundle="${loc}" key="State" var="State"/>
<fmt:message bundle="${loc}" key="Sum" var="Sum"/>
<fmt:message bundle="${loc}" key="Manager" var="Manager"/>

<fmt:message bundle="${loc}" key="Name" var="Name"/>
<fmt:message bundle="${loc}" key="Producer" var="Producer"/>
<fmt:message bundle="${loc}" key="Quantity" var="Quantity"/>
<fmt:message bundle="${loc}" key="Price" var="Price"/>

<jsp:useBean id="order" type="model.Order" scope="request"/>

<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>
    <title>${order.number} / ${order.date}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp" %>

<div class="container">
    <form method="POST" action="/orders/open">
        <table border="0" cellspacing="5">
                <tr>
                    <th align="right">${Code}:</th>
                    <td align="left"><input type="text" name="id" value="${order.id}" readonly></td>
                </tr>
            <tr>
                <th align="right">${Number}:</th>
                <td align="left"><input type="text" name="number" value="${order.number}"></td>
            </tr>
            <tr>
                <th align="right">${Date}:</th>
                <td align="left"><input type="text" name="date" value="${order.date}" readonly>
                </td>
            </tr>
            <tr>
                <th align="right">${Buyer}:</th>
                <td align="left"><input type="text" name="customer" value="${order.customer.email}"></td>
            </tr>
            <tr>
                <th align="right">${State}:</th>
                <td align="left"><input type="text" name="state" value="${order.state}"></td>
            </tr>
            <tr>
                <th align="right">${Sum}:</th>
                <td align="left"><input type="text" name="sum" value="${order.sum}"></td>
            </tr>
            <tr>
                <td align="right"><input type="submit" value="${Save}" disabled></td>
                <td align="left"><input type="reset" value="${Reset}"></td>
            </tr>
        </table>
    </form>

    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Code}</th>
            <th style="border: 1px solid #000;">${Name}</th>
            <th style="border: 1px solid #000;">${Producer}</th>
            <th style="border: 1px solid #000;">${Quantity}</th>
            <th style="border: 1px solid #000;">${Price}</th>
        </tr>

        <c:forEach var="item" items="${items}">
            <tr>
                <td style="border: 1px solid #000;">${item.good.id}</td>
                <td style="border: 1px solid #000;">${item.good.name}</td>
                <td style="border: 1px solid #000;">${item.good.producer.name} / ${item.good.producer.country.name}</td>
                <td style="border: 1px solid #000;">${item.quantity}</td>
                <td style="border: 1px solid #000;">${item.price}</td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>
