<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
    <%@ include file="/WEB-INF/import.html"%>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="locale" var="loc" />
    <fmt:message bundle="${loc}" key="Orders" var="Orders" />
    <fmt:message bundle="${loc}" key="delete" var="delete" />
    <fmt:message bundle="${loc}" key="edit" var="edit" />
    <title>${title}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp"%>

<h1>${Orders}</h1> <form method="GET" id="add_good">
    <button name="isNew" form="add_good" formaction="/documents/orders/edit" value=true>
        Add new
    </button>
</form>
<table style="border: 1px solid #000;">
    <tr>
        <th style="border: 1px solid #000;">Код</th>
        <th style="border: 1px solid #000;">Номер</th>
        <th style="border: 1px solid #000;">Статус</th>
        <th style="border: 1px solid #000;">Пользователь</th>
        <th style="border: 1px solid #000;">Удален</th>
        <%--<th style="border: 1px solid #000;">Производитель</th>--%>
        <%--<th style="border: 1px solid #000;">Описание</th>--%>
        <th style="border: 1px solid #000;">Редактировать</th>
    </tr>

    <c:forEach var="order" items="${orders}">
        <tr>
            <%--// TODO format date by tld function --%>
            <td style="border: 1px solid #000;">${order.id}</td>
            <td style="border: 1px solid #000;"><a href="/documents/orders/edit?order=${order.id}">${order.number} / ${order.date} </a></td>
            <td style="border: 1px solid #000;">${order.state}</td>
            <td style="border: 1px solid #000;">${order.customer.email}</td>
            <td style="border: 1px solid #000;">${order.deleted}</td>
            <td style="border: 1px solid #000;">
                <form action="/documents/orders/edit" method="get" class="horizontal">
                    <input type="hidden" name="order" value="${order.id}" />
                    <input type="submit" value="${edit}" class="btn btn-default">
                </form>
                <form action="/documents/orders/edit" method="post" class="horizontal">
                    <input type="hidden" name="order" value="${order.id}" />
                    <input type="hidden" name="delete" value="${true}" />
                    <input type="submit" value="${delete}" class="btn btn-default">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
