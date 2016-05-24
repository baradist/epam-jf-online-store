<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="Editing" var="Editing"/>
<fmt:message bundle="${loc}" key="New" var="New"/>
<fmt:message bundle="${loc}" key="Save" var="Save"/>
<fmt:message bundle="${loc}" key="Reset" var="Reset"/>

<fmt:message bundle="${loc}" key="Document" var="Document"/>
<fmt:message bundle="${loc}" key="Invoices" var="Invoices"/>
<fmt:message bundle="${loc}" key="Invoice" var="Invoice"/>
<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Number" var="Number"/>
<fmt:message bundle="${loc}" key="Date" var="Date"/>
<fmt:message bundle="${loc}" key="Supplier" var="Supplier"/>
<fmt:message bundle="${loc}" key="Store" var="Store"/>
<fmt:message bundle="${loc}" key="Sum" var="Sum"/>
<fmt:message bundle="${loc}" key="Manager" var="Manager"/>

<fmt:message bundle="${loc}" key="Name" var="Name"/>
<fmt:message bundle="${loc}" key="Producer" var="Producer"/>
<fmt:message bundle="${loc}" key="Quantity" var="Quantity"/>
<fmt:message bundle="${loc}" key="Price" var="Price"/>

<c:set var="isNew" scope="page" value="${requestScope.isNew}"/>

<c:if test="${!isNew}">
    <jsp:useBean id="invoice" type="model.Invoice" scope="request"/>
</c:if>


<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>
    <c:choose>
        <c:when test="${isNew}">
            <title>${New}</title>
        </c:when>
        <c:otherwise>
            <title>${Editing} ${invoice.number} / ${invoice.date}</title>
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
                    <th align="right">${Code}:</th>
                    <td align="left"><input type="text" name="id" value="${invoice.id}" readonly></td>
                </tr>
            </c:if>

            <tr>
                <th align="right">${Number}:</th>
                <td align="left"><input type="text" name="number"
                <c:if test="${!isNew}">
                                        value="${invoice.number}"
                </c:if>
                ></td>
            </tr>
            <tr>
                <th align="right">Дата:</th>
                <td align="left"><input type="text" name="date"
                <c:if test="${!isNew}">
                                        value="${invoice.date}" readonly
                </c:if>
                >
                </td>
            </tr>
            <tr>
                <%--// TODO: select - supplier--%>
                <th align="right">${Supplier}:</th>
                <td align="left"><input type="text" name="supplier"
                <c:if test="${!isNew}">
                                        value="${invoice.supplier.name}"
                </c:if>
                ></td>
            </tr>
            <tr>
                <%--// TODO: select - store--%>
                <th align="right">${Store}:</th>
                <td align="left"><input type="text" name="store"
                <c:if test="${!isNew}">
                                        value="${invoice.store.name}"
                </c:if>
                ></td>
            </tr>
            <tr>
                <th align="right">${Sum}:</th>
                <td align="left"><input type="text" name="sum"
                <c:if test="${!isNew}">
                                        value="${invoice.sum}"
                </c:if>
                ></td>
            </tr>
            <tr>
                <th align="right">${Manager}:</th>
                <td align="left"><input type="text" name="manager"
                <c:if test="${!isNew}">
                                        value="${invoice.manager.firstName} ${invoice.manager.lastName}"
                </c:if>
                ></td>
            </tr>
            <tr>
                <%--// TODO disabled--%>
                <td align="right"><input type="submit" value="${Save}" disabled></td>
                <td align="left"><input type="reset" value="${Reset}"></td>
            </tr>
        </table>
        <input type="hidden" name="isNew" value="${requestScope.isNew}">
    </form>

    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Code}</th>
            <th style="border: 1px solid #000;">${Name}</th>
            <th style="border: 1px solid #000;">${Producer}</th>
            <th style="border: 1px solid #000;">${Quantity}</th>
            <th style="border: 1px solid #000;">${Price}</th>
            <th style="border: 1px solid #000;">${Sum}</th>
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

    <%@ include file="/WEB-INF/pager.jsp" %>
</div>
</body>
</html>
