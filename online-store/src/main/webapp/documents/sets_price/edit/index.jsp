<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="isNew" scope="page" value="${requestScope.isNew}"/>

<c:if test="${!isNew}">
    <jsp:useBean id="setPrice" type="model.SetPrice" scope="request"/>
</c:if>


<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>
    <c:choose>
        <c:when test="${isNew}">
            <title>New good</title>
        </c:when>
        <c:otherwise>
            <title>Edit ${setPrice.number} / ${setPrice.date}</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
<%@ include file="/WEB-INF/top_line.jsp" %>
<div class="container">

    <form method="POST" action="/documents/invoices/edit/">
        <table border="0" cellspacing="5">
            <c:if test="${!isNew}">
                <tr>
                    <th align="right">Код:</th>
                    <td align="left"><input type="text" name="id" value="${setPrice.id}" readonly></td>
                </tr>
            </c:if>

            <tr>
                <th align="right">Номер:</th>
                <td align="left"><input type="text" name="name"
                <c:if test="${!isNew}">
                                        value="${setPrice.number}"
                </c:if>
                ></td>
            </tr>
            <tr>
                <th align="right">Дата:</th>
                <td align="left"><input type="text" name="producer"
                <c:if test="${!isNew}">
                                        value="${setPrice.date}" readonly
                </c:if>
                >
                </td>
            </tr>
            <tr>
                <th align="right">Менеджер:</th>
                <td align="left"><input type="text" name="description"
                <c:if test="${!isNew}">
                                        value="${setPrice.manager.firstName} ${setPrice.manager.lastName}"
                </c:if>
                ></td>
            </tr>
            <tr>
                <%--// TODO disabled--%>
                <td align="right"><input type="submit" value="Save" disabled></td>
                <td align="left"><input type="reset"></td>
            </tr>
        </table>
        <input type="hidden" name="isNew" value="${requestScope.isNew}">
    </form>

    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">Артикул</th>
            <th style="border: 1px solid #000;">Название</th>
            <th style="border: 1px solid #000;">Производитель</th>
            <th style="border: 1px solid #000;">Цена закупки</th>
            <th style="border: 1px solid #000;">Наценка</th>
            <th style="border: 1px solid #000;">Цена продажи</th>
        </tr>

        <c:forEach var="item" items="${items}">
            <tr>
                <td style="border: 1px solid #000;">${item.lot.good.id}</td>
                <td style="border: 1px solid #000;">${item.lot.good.name}</td>
                <td style="border: 1px solid #000;">${item.lot.good.producer.name} / ${item.lot.good.producer.country.name}</td>
                <td style="border: 1px solid #000;">${item.lot.priceSup}</td>
                <td style="border: 1px solid #000;">${item.increase}</td>
                <td style="border: 1px solid #000;">${item.priceSal}</td>
            </tr>
        </c:forEach>

    </table>

    <%@include file="/WEB-INF/pager.jsp"%>
</div>
</body>
</html>
