<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
    <%@ include file="/WEB-INF/import.html"%>

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
    <button name="isNew" form="add_good" formaction="/catalogs/goods/edit" value=true>
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

    <c:forEach var="good" items="${items}">
        <tr>
            <td style="border: 1px solid #000;">${good.id}</td>
            <td style="border: 1px solid #000;">${good.name}</td>
            <td style="border: 1px solid #000;">${good.producer.name} / ${good.producer.country.name}</td>
        <%--<td style="border: 1px solid #000;">${good.description}</td>--%>
            <td style="border: 1px solid #000;">
                <form action="/catalogs/goods/edit" method="get" class="horizontal">
                    <input type="hidden" name="id" value="${good.id}" />
                    <input type="submit" value="${edit}" class="btn btn-default">
                </form>
                <form action="/catalogs/goods/edit" method="post" class="horizontal">
                    <input type="hidden" name="id" value="${good.id}" />
                    <input type="hidden" name="delete" value="${true}" />
                    <input type="submit" value="${delete}" class="btn btn-default">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>


<ul class="nav navbar-nav navbar-fixed-bottom">
    <c:set value="${0}" var="n" scope="page"/>
    <c:forEach var="i" begin="0" step="${requestScope.rowsOnPage}" end="${requestScope.quantity - 1}">
        <c:set value="${n + 1}" var="n" scope="page"/>
        <li>
            <form action="/catalogs/goods/" method="get" class="horizontal">
                <input type="hidden" name="offset" value="${i}"/>
                <input type="hidden" name="pageNumber" value="${n}"/>
                <input type="submit" value="${n}"
                <c:if test="${requestScope.pageNumber eq n}">
                       disabled="true"
                </c:if>
                       class="btn btn-default">
            </form>
        </li>
    </c:forEach>
    <li>Записей на странице: <b>${requestScope.rowsOnPage}</b>  </li>

    <li>
        <form action="/catalogs/goods/" method="get" id="pagesTune" class="horizontal">
            <input form="pagesTune" type="submit" value="Установить:">
            <select required name="rowsOnPage">
                <%--<option <c:if test="${requestScope.rowsOnPage ==  5}"> selected disabled </c:if> value= "5" >5</option>--%>
                <%--<option <c:if test="${requestScope.rowsOnPage == 10}"> selected disabled </c:if> value="10">10</option>--%>
                <%--<option <c:if test="${requestScope.rowsOnPage == 20}"> selected disabled </c:if> value="20">20</option>--%>
                <%--<option <c:if test="${requestScope.rowsOnPage == 50}"> selected disabled </c:if> value="50">50</option>--%>
                <c:if test="${requestScope.rowsOnPage !=  5}"> <option value= "5"> 5</option> </c:if>
                <c:if test="${requestScope.rowsOnPage != 10}"> <option value="10">10</option> </c:if>
                <c:if test="${requestScope.rowsOnPage != 20}"> <option value="20">20</option> </c:if>
                <c:if test="${requestScope.rowsOnPage != 50}"> <option value="50">50</option> </c:if>
            </select>
        </form>
    </li>
</ul>

</body>
</html>
