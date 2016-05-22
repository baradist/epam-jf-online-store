<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="Orders" var="Orders"/>
    <fmt:message bundle="${loc}" key="delete" var="delete"/>
    <fmt:message bundle="${loc}" key="edit" var="edit"/>
    <title>${title}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp" %>
<div class="container">
    <h1>${Orders}</h1>
    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">Код</th>
            <th style="border: 1px solid #000;">Номер</th>
            <th style="border: 1px solid #000;">Менеджер</th>
        </tr>

        <c:forEach var="item" items="${items}">
            <tr>
                    <%--// TODO format date by tld function --%>
                <td style="border: 1px solid #000;">${item.id}</td>
                <td style="border: 1px solid #000;"><a href="/documents/sets_price/edit?id=${item.id}">${item.number}
                    / ${item.date} </a></td>
                <td style="border: 1px solid #000;">${item.manager.firstName} ${item.manager.lastName}</td>
            </tr>
        </c:forEach>

    </table>

    <%@ include file="/WEB-INF/pager.jsp" %>
</div>
</body>
</html>
