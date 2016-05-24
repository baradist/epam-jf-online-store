<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="Orders" var="Orders"/>

<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Number" var="Number"/>
<fmt:message bundle="${loc}" key="State" var="State"/>
<fmt:message bundle="${loc}" key="Manager" var="Manager"/>
<fmt:message bundle="${loc}" key="Sum" var="Sum"/>

<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <title>${title}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp" %>

<div class="container">
    <h1>${Orders}</h1>
    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Code}</th>
            <th style="border: 1px solid #000;">${Number}</th>
            <th style="border: 1px solid #000;">${State}</th>
            <th style="border: 1px solid #000;">${Manager}</th>
            <th style="border: 1px solid #000;">${Sum}</th>
        </tr>

        <c:forEach var="order" items="${orders}">
            <tr>
                    <%--// TODO format date by tld function --%>
                <td style="border: 1px solid #000;">${order.id}</td>
                <td style="border: 1px solid #000;"><a href="/orders/open?id=${order.id}">${order.number}
                    / ${order.date} </a></td>
                <td style="border: 1px solid #000;">${order.state}</td>
                <td style="border: 1px solid #000;">${order.customer.email}</td>
                <td style="border: 1px solid #000;">${order.sum}</td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>
