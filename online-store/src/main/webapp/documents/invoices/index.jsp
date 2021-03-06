<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="Documents" var="Documents"/>
<fmt:message bundle="${loc}" key="Invoices" var="Invoices"/>

<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Number" var="Number"/>
<fmt:message bundle="${loc}" key="Supplier" var="Supplier"/>
<fmt:message bundle="${loc}" key="Store" var="Store"/>
<fmt:message bundle="${loc}" key="Sum" var="Sum"/>
<fmt:message bundle="${loc}" key="Manager" var="Manager"/>

<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <title>${Documents} - ${Invoices}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp" %>
<div class="container">
    <h1>${Documents} - ${Invoices}</h1>
    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Code}</th>
            <th style="border: 1px solid #000;">${Number}</th>
            <th style="border: 1px solid #000;">${Supplier}</th>
            <th style="border: 1px solid #000;">${Store}</th>
            <th style="border: 1px solid #000;">${Sum}</th>
            <th style="border: 1px solid #000;">${Manager}</th>
        </tr>

        <c:forEach var="item" items="${items}">
            <tr>
                    <%--// TODO format date by tld function --%>
                <td style="border: 1px solid #000;">${item.id}</td>
                <td style="border: 1px solid #000;"><a href="/documents/invoices/edit?id=${item.id}">${item.number}
                    / ${item.date} </a></td>
                <td style="border: 1px solid #000;">${item.supplier.name}</td>
                <td style="border: 1px solid #000;">${item.store.name}</td>
                <td style="border: 1px solid #000;">${item.sum}</td>
                <td style="border: 1px solid #000;">${item.manager.firstName} ${item.manager.lastName}</td>
            </tr>
        </c:forEach>

    </table>

    <%@ include file="/WEB-INF/pager.jsp" %>
</div>
</body>
</html>
