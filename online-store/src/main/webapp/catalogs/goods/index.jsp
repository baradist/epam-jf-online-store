<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/WEB-INF/import.html" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="Catalog" var="Catalog"/>
<fmt:message bundle="${loc}" key="Goods" var="Goods"/>
<fmt:message bundle="${loc}" key="Delete" var="Delete"/>
<fmt:message bundle="${loc}" key="Edit" var="Edit"/>
<fmt:message bundle="${loc}" key="New" var="New"/>

<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Name" var="Name"/>
<fmt:message bundle="${loc}" key="Producer" var="Producer"/>

<html>
<head>
    <title>${Catalog} - ${Goods}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp" %>

<div class="container">
    <h1>${Catalog} - ${Goods}</h1>
    <form method="GET" id="add_good">
        <button name="isNew" form="add_good" formaction="/catalogs/goods/edit" value=true>
            ${New}
        </button>
    </form>
    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Code}</th>
            <th style="border: 1px solid #000;">${Name}</th>
            <th style="border: 1px solid #000;">${Producer}</th>
            <%--<th style="border: 1px solid #000;">Описание</th>--%>
            <th style="border: 1px solid #000;">${Edit}</th>
        </tr>

        <c:forEach var="good" items="${items}">
            <tr>
                <td style="border: 1px solid #000;">${good.id}</td>
                <td style="border: 1px solid #000;">${good.name}</td>
                <td style="border: 1px solid #000;">${good.producer.name} / ${good.producer.country.name}</td>
                    <%--<td style="border: 1px solid #000;">${good.description}</td>--%>
                <td style="border: 1px solid #000;">
                    <form action="/catalogs/goods/edit" method="get" class="horizontal">
                        <input type="hidden" name="id" value="${good.id}"/>
                        <input type="submit" value="${Edit}" class="btn btn-default">
                    </form>
                    <form action="/catalogs/goods/edit" method="post" class="horizontal">
                        <input type="hidden" name="id" value="${good.id}"/>
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
