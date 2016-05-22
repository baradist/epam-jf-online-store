<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="Catalog" var="Catalog"/>
<fmt:message bundle="${loc}" key="Persons" var="Persons"/>
<fmt:message bundle="${loc}" key="Email" var="Email"/>
<fmt:message bundle="${loc}" key="FirsName" var="FirsName"/>
<fmt:message bundle="${loc}" key="LastName" var="LastName"/>
<fmt:message bundle="${loc}" key="Birthday" var="Birthday"/>
<fmt:message bundle="${loc}" key="Address" var="Address"/>
<fmt:message bundle="${loc}" key="Phone" var="Phone"/>
<fmt:message bundle="${loc}" key="Edit" var="Edit"/>
<fmt:message bundle="${loc}" key="Delete" var="Delete"/>

<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>

    <title>${Catalog} - ${Persons}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp" %>
<div class="container">
    <h1>${Catalog} - ${Persons}</h1>
    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Email}</th>
            <th style="border: 1px solid #000;">${FirsName}</th>
            <th style="border: 1px solid #000;">${LastName}</th>
            <th style="border: 1px solid #000;">${Birthday}</th>
            <th style="border: 1px solid #000;">${Address}</th>
            <th style="border: 1px solid #000;">${Phone}</th>
            <th style="border: 1px solid #000;">${Edit}</th>
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
                    <form action="/catalogs/persons/edit/" method="get" class="horizontal">
                        <input type="hidden" name="id" value="${person.id}"/>
                        <input type="submit" value="${Edit}" class="btn btn-default">
                    </form>
                    <form action="/catalogs/persons/edit/" method="post" class="horizontal">
                        <input type="hidden" name="id" value="${person.id}"/>
                        <input type="hidden" name="delete" value="${true}"/>
                        <input type="submit" value="${Delete}" class="btn btn-default">
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>

    <%@ include file="/WEB-INF/pager.jsp" %>
</div>
</body>
</html>
