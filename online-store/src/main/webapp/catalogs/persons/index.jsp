<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="Goods" var="Goods"/>
    <fmt:message bundle="${loc}" key="delete" var="delete"/>
    <fmt:message bundle="${loc}" key="edit" var="edit"/>
    <title>${title}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp" %>
<div class="container">
    <h1>${Goods}</h1>
    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">e-mail</th>
            <th style="border: 1px solid #000;">Имя</th>
            <th style="border: 1px solid #000;">Фамилия</th>
            <th style="border: 1px solid #000;">День рождения</th>
            <th style="border: 1px solid #000;">Адрес</th>
            <th style="border: 1px solid #000;">Телефон</th>
            <th style="border: 1px solid #000;">Редактировать</th>
        </tr>

        <c:forEach var="person" items="${items}">
            <tr>
                <td style="border: 1px solid #000;">${person.email}</td>
                <td style="border: 1px solid #000;">${person.firstName}</td>
                <td style="border: 1px solid #000;">${person.lastName}</td>
                <td style="border: 1px solid #000;">${person.dob}</td>
                <td style="border: 1px solid #000;">${person.address}</td>
                <td style="border: 1px solid #000;">${person.phone}</td>
                <td style="border: 1px solid #000;">
                    <form action="/catalogs/persons/edit" method="get" class="horizontal">
                        <input type="hidden" name="id" value="${person.id}"/>
                        <input type="submit" value="${edit}" class="btn btn-default">
                    </form>
                    <form action="/catalogs/persons/edit" method="post" class="horizontal">
                        <input type="hidden" name="id" value="${person.id}"/>
                        <input type="hidden" name="delete" value="${true}"/>
                        <input type="submit" value="${delete}" class="btn btn-default">
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>

    <%@ include file="/WEB-INF/pager.jsp" %>
</div>
</body>
</html>
