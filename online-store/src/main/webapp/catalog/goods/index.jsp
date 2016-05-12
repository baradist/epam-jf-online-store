<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
    <%@ include file="/WEB-INF/import.html"%>
    <%--<link rel="stylesheet" href="/css/bootstrap-3.3.6-dist/css/bootstrap.min.css">--%>
    <%--<script src="/js/jquery-1.12.3.min.js"></script>--%>
    <%--<script src="/css/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>--%>
    <%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8">--%>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="locale" var="loc" />
    <fmt:message bundle="${loc}" key="Goods" var="Goods" />
    <fmt:message bundle="${loc}" key="delete" var="delete" />
    <fmt:message bundle="${loc}" key="edit" var="edit" />
    <title>${title}</title>
</head>
<body>

<%@ include file="/WEB-INF/top_line.jsp"%>

<h1>${Goods}</h1> <form method="GET" id="add_good">
    <button name="isNew" form="add_good" formaction="/catalog/goods/edit" value=true>
        Add new
    </button>
</form>
<table style="border: 1px solid #000;">
    <tr>
        <th style="border: 1px solid #000;">Артикул</th>
        <th style="border: 1px solid #000;">Название</th>
        <th style="border: 1px solid #000;">Производитель</th>
        <%--<th style="border: 1px solid #000;">Описание</th>--%>
        <th style="border: 1px solid #000;">Редактировать</th>
    </tr>

    <c:forEach var="good" items="${goods}">
        <tr>
            <td style="border: 1px solid #000;">${good.id}</td>
            <td style="border: 1px solid #000;">${good.name}</td>
            <td style="border: 1px solid #000;">${good.producer.name} / ${good.producer.country.name}</td>
        <%--<td style="border: 1px solid #000;">${good.description}</td>--%>
            <td style="border: 1px solid #000;">
                <%--<a href="/catalog/goods/edit?id=${good.id}">edit</a>--%>
                <%--<form method="post" id="delete">--%>
                    <%--<button name="id" form="delete" formaction="/catalog/goods/delete" value="${good.id}">--%>
                        <%--remove--%>
                    <%--</button>--%>
                <%--</form>--%>
                <form action="/catalog/goods/edit" method="get" class="horizontal">
                    <input type="hidden" name="id" value="${good.id}" />
                    <input type="submit" value="${edit}" class="btn btn-default">
                </form>
                <form action="/catalog/goods/edit" method="post" class="horizontal">
                    <input type="hidden" name="id" value="${good.id}" />
                    <input type="hidden" name="delete" value="${true}" />
                    <input type="submit" value="${delete}" class="btn btn-default">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
