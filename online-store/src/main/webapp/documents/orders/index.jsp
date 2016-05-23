<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="Editing" var="Editing"/>
<fmt:message bundle="${loc}" key="New" var="New"/>
<fmt:message bundle="${loc}" key="Edit" var="Edit"/>
<fmt:message bundle="${loc}" key="Delete" var="Delete"/>
<fmt:message bundle="${loc}" key="Documents" var="Documents"/>
<fmt:message bundle="${loc}" key="Orders" var="Orders"/>

<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Number" var="Number"/>
<fmt:message bundle="${loc}" key="State" var="State"/>
<fmt:message bundle="${loc}" key="Sum" var="Sum"/>
<fmt:message bundle="${loc}" key="Buyer" var="Buyer"/>
<fmt:message bundle="${loc}" key="Deleted" var="Deleted"/>

<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <title>${Documents} - ${Orders}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp" %>

<div class="container">
    <h1>${Documents} - ${Orders}</h1>
    <form method="GET" id="add_good">
        <button name="isNew" form="add_good" formaction="/documents/orders/edit" value="true" disabled>
            ${New}
        </button>
    </form>
    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Code}</th>
            <th style="border: 1px solid #000;">${Number}</th>
            <th style="border: 1px solid #000;">${State}</th>
            <th style="border: 1px solid #000;">${Sum}</th>
            <th style="border: 1px solid #000;">${Buyer}</th>
            <th style="border: 1px solid #000;">${Deleted}</th>
            <th style="border: 1px solid #000;">${Editing}</th>
        </tr>

        <c:forEach var="order" items="${orders}">
            <tr>
                    <%--// TODO format date by tld function --%>
                <td style="border: 1px solid #000;">${order.id}</td>
                <td style="border: 1px solid #000;"><a href="/documents/orders/edit?order=${order.id}">${order.number}
                    / ${order.date} </a></td>
                <td style="border: 1px solid #000;">${order.state}</td>
                <td style="border: 1px solid #000;">${order.sum}</td>
                <td style="border: 1px solid #000;">${order.customer.email}</td>
                <td style="border: 1px solid #000;">${order.deleted}</td>
                <td style="border: 1px solid #000;">
                    <form action="/documents/orders/edit" method="get" class="horizontal">
                        <input type="hidden" name="order" value="${order.id}"/>
                        <input type="submit" value="${Edit}" class="btn btn-default">
                    </form>
                    <form action="/documents/orders/edit" method="post" class="horizontal">
                        <input type="hidden" name="order" value="${order.id}"/>
                        <input type="hidden" name="delete" value="${true}"/>
                        <input type="submit" value="${Delete}" class="btn btn-default">
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>
