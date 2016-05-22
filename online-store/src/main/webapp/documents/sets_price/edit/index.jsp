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
<fmt:message bundle="${loc}" key="SetsPrice" var="SetsPrice"/>
<fmt:message bundle="${loc}" key="SetPrice" var="SetPrice"/>
<fmt:message bundle="${loc}" key="Code" var="Code"/>
<fmt:message bundle="${loc}" key="Number" var="Number"/>
<fmt:message bundle="${loc}" key="Date" var="Date"/>
<fmt:message bundle="${loc}" key="Manager" var="Manager"/>

<fmt:message bundle="${loc}" key="Name" var="Name"/>
<fmt:message bundle="${loc}" key="Producer" var="Producer"/>
<fmt:message bundle="${loc}" key="Quantity" var="Quantity"/>
<fmt:message bundle="${loc}" key="PriceSup" var="PriceSup"/>
<fmt:message bundle="${loc}" key="PriceSal" var="PriceSal"/>
<fmt:message bundle="${loc}" key="PriceIncrease" var="PriceIncrease"/>

<c:set var="isNew" scope="page" value="${requestScope.isNew}"/>

<c:if test="${!isNew}">
    <jsp:useBean id="setPrice" type="model.SetPrice" scope="request"/>
</c:if>


<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>
    <c:choose>
        <c:when test="${isNew}">
            <title>${New}</title>
        </c:when>
        <c:otherwise>
            <title>${Editing} ${setPrice.number} / ${setPrice.date}</title>
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
                    <td align="left"><input type="text" name="id" value="${setPrice.id}" readonly></td>
                </tr>
            </c:if>

            <tr>
                <th align="right">${Number}:</th>
                <td align="left"><input type="text" name="number"
                <c:if test="${!isNew}">
                                        value="${setPrice.number}"
                </c:if>
                ></td>
            </tr>
            <tr>
                <th align="right">${Date}:</th>
                <td align="left"><input type="text" name="date"
                <c:if test="${!isNew}">
                                        value="${setPrice.date}" readonly
                </c:if>
                >
                </td>
            </tr>
            <tr>
                <%--// TODO: select - manager--%>
                <th align="right">${Manager}:</th>
                <td align="left"><input type="text" name="manager"
                <c:if test="${!isNew}">
                                        value="${setPrice.manager.firstName} ${setPrice.manager.lastName}"
                </c:if>
                ></td>
            </tr>
            <tr>
                <%--// TODO disabled--%>
                <td align="right"><input type="submit" value="${Save}" disabled></td>
                <td align="left"><input type="${Reset}"></td>
            </tr>
        </table>
        <input type="hidden" name="isNew" value="${requestScope.isNew}">
    </form>

    <table style="border: 1px solid #000;">
        <tr>
            <th style="border: 1px solid #000;">${Code}</th>
            <th style="border: 1px solid #000;">${Name}</th>
            <th style="border: 1px solid #000;">${Producer}</th>
            <th style="border: 1px solid #000;">${PriceSup}</th>
            <th style="border: 1px solid #000;">${PriceIncrease}</th>
            <th style="border: 1px solid #000;">${PriceSal}</th>
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
