<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="isNew" scope="page" value="${requestScope.isNew}"/>

<%--<c:if test="${!isNew}" >--%>
    <%--<jsp:useBean id="good" type="model.Good" scope="request"/>--%>
<%--</c:if>--%>


<html>
<head>
    <%@ include file="/WEB-INF/import.html"%>
    <c:choose>
        <c:when test="${isNew}" >
            <title>New good</title>
        </c:when>
        <c:otherwise>
            <title>Edit ${order.number} / ${order.date}</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
<%@ include file="/WEB-INF/top_line.jsp"%>


<form method="POST" action="/documents/orders/edit">
    <table border="0" cellspacing="5">
        <c:if test="${!isNew}">
            <tr>
                <th align="right">Код заказа:</th>
                <td align="left"><input type="text" name="id" value="${order.id}" readonly></td>
            </tr>
        </c:if>

        <tr>
            <th align="right">Номер:</th>
            <td align="left"><input type="text" name="name"
                <c:if test="${!isNew}">
                    value="${order.number}"
                </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">Дата:</th>
            <td align="left"><input type="text" name="producer"
                <c:if test="${!isNew}">
                                    value="${order.date}" readonly
                </c:if>
            >
            </td>
        </tr>
        <tr>
            <th align="right">Покупатель:</th>
            <td align="left"><input type="text" name="description"
                <c:if test="${!isNew}">
                    value="${order.customer}"
                </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">Статус:</th>
            <td align="left"><input type="text" name="description"
            <c:if test="${!isNew}">
                    value="${order.state}"
            </c:if>
            ></td>
        </tr>
        <tr>
            <td align="right"><input type="submit" value="Save"></td>
            <td align="left"><input type="reset"></td>
        </tr>
    </table>
    <input type="hidden" name="isNew" value="${requestScope.isNew}">
</form>

<table style="border: 1px solid #000;">
    <tr>
        <th style="border: 1px solid #000;">Артикул</th>
        <th style="border: 1px solid #000;">Название</th>
        <th style="border: 1px solid #000;">Производитель</th>
        <th style="border: 1px solid #000;">Количество</th>
        <th style="border: 1px solid #000;">Цена</th>
    </tr>

    <c:forEach var="item" items="${items}">
        <tr>
            <td style="border: 1px solid #000;">${item.good.id}</td>
            <td style="border: 1px solid #000;">${item.good.name}</td>
            <td style="border: 1px solid #000;">${item.good.producer.name} / ${item.good.producer.country.name}</td>
            <td style="border: 1px solid #000;">${item.quantity}</td>
            <td style="border: 1px solid #000;">${item.price}</td>
            <%--<td style="border: 1px solid #000;">--%>
                <%--<form action="/documents/orders/edit" method="get" class="horizontal">--%>
                    <%--<input type="hidden" name="order" value="${item.id}" />--%>
                    <%--<input type="submit" value="${edit}" class="btn btn-default">--%>
                <%--</form>--%>
                <%--<form action="/documents/orders/edit" method="post" class="horizontal">--%>
                    <%--<input type="hidden" name="order" value="${item.id}" />--%>
                    <%--<input type="hidden" name="delete" value="${true}" />--%>
                    <%--<input type="submit" value="${delete}" class="btn btn-default">--%>
                <%--</form>--%>
            <%--</td>--%>
        </tr>
    </c:forEach>

</table>

</body>
</html>
