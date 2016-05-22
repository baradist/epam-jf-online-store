<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="isNew" scope="page" value="${requestScope.isNew}"/>

<c:if test="${!isNew}" >
    <jsp:useBean id="invoice" type="model.Invoice" scope="request"/>
</c:if>


<html>
<head>
    <%@ include file="/WEB-INF/import.html"%>
    <c:choose>
        <c:when test="${isNew}" >
            <title>New good</title>
        </c:when>
        <c:otherwise>
            <title>Edit ${invoice.number} / ${invoice.date}</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
<%@ include file="/WEB-INF/top_line.jsp"%>


<form method="POST" action="/documents/invoices/edit/">
    <table border="0" cellspacing="5">
        <c:if test="${!isNew}">
            <tr>
                <th align="right">Код:</th>
                <td align="left"><input type="text" name="id" value="${invoice.id}" readonly></td>
            </tr>
        </c:if>

        <tr>
            <th align="right">Номер:</th>
            <td align="left"><input type="text" name="name"
                <c:if test="${!isNew}">
                    value="${invoice.number}"
                </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">Дата:</th>
            <td align="left"><input type="text" name="producer"
                <c:if test="${!isNew}">
                                    value="${invoice.date}" readonly
                </c:if>
            >
            </td>
        </tr>
        <tr>
            <th align="right">Поставщик:</th>
            <td align="left"><input type="text" name="description"
                <c:if test="${!isNew}">
                    value="${invoice.supplier.name}"
                </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">Склад:</th>
            <td align="left"><input type="text" name="description"
            <c:if test="${!isNew}">
                    value="${invoice.store.name}"
            </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">Сумма:</th>
            <td align="left"><input type="text" name="description"
            <c:if test="${!isNew}">
                    value="${invoice.sum}"
            </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">Менеджер:</th>
            <td align="left"><input type="text" name="description"
            <c:if test="${!isNew}">
                    value="${invoice.manager.firstName} ${invoice.manager.lastName}"
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
        <th style="border: 1px solid #000;">Количество</th>
        <th style="border: 1px solid #000;">Цена</th>
        <th style="border: 1px solid #000;">Сумма</th>
    </tr>

    <c:forEach var="item" items="${items}">
        <tr>
            <td style="border: 1px solid #000;">${item.good.id}</td>
            <td style="border: 1px solid #000;">${item.good.name}</td>
            <td style="border: 1px solid #000;">${item.good.producer.name} / ${item.good.producer.country.name}</td>
            <td style="border: 1px solid #000;">${item.quantity}</td>
            <td style="border: 1px solid #000;">${item.price}</td>
            <td style="border: 1px solid #000;">${item.quantity * item.price}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
