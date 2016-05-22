<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="New" var="New"/>
<fmt:message bundle="${loc}" key="Editing" var="Editing"/>
<fmt:message bundle="${loc}" key="Save" var="Save"/>
<fmt:message bundle="${loc}" key="Reset" var="Reset"/>
<fmt:message bundle="${loc}" key="Persons" var="Persons"/>

<fmt:message bundle="${loc}" key="Email" var="Email"/>
<fmt:message bundle="${loc}" key="FirsName" var="FirsName"/>
<fmt:message bundle="${loc}" key="LastName" var="LastName"/>
<fmt:message bundle="${loc}" key="Birthday" var="Birthday"/>
<fmt:message bundle="${loc}" key="Address" var="Address"/>
<fmt:message bundle="${loc}" key="Phone" var="Phone"/>

<c:set var="isNew" scope="page" value="${requestScope.isNew}"/>

<c:if test="${!isNew}">
    <jsp:useBean id="person" type="model.Person" scope="request"/>
</c:if>


<html>
<head>
    <%@ include file="/WEB-INF/import.html" %>
    <c:choose>
        <c:when test="${isNew}">
            <title>${Persons} - ${New} </title>
        </c:when>
        <c:otherwise>
            <title>${Editing}: ${person.name}</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
<%@ include file="/WEB-INF/top_line.jsp" %>


<form method="POST" action="/catalogs/persons/edit/">
    <table border="0" cellspacing="5">
        <c:if test="${!isNew}">
            <tr>
                <th align="right">${Email}:</th>
                <td align="left"><input type="text" name="email" value="${person.email}" readonly></td>
            </tr>
        </c:if>

        <tr>
            <th align="right">${FirsName}:</th>
            <td align="left"><input type="text" name="first_name"
            <c:if test="${!isNew}">
                                    value="${person.firstName}"
            </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">${LastName}:</th>
            <td align="left"><input type="text" name="last_name"
            <c:if test="${!isNew}">
                                    value="${person.lastName}"
            </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">${Birthday}:</th>
            <td align="left"><input type="text" name="dob"
            <c:if test="${!isNew}">
                                    value="${person.dob}"
            </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">${Address}:</th>
            <td align="left"><input type="text" name="address"
            <c:if test="${!isNew}">
                                    value="${person.address}"
            </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">${Phone}:</th>
            <td align="left"><input type="text" name="phone"
            <c:if test="${!isNew}">
                                    value="${person.phone}"
            </c:if>
            ></td>
        </tr>
        <tr>
            <td align="right"><input type="submit" value="${Save}"></td>
            <td align="left"><input type="reset" value="${Reset}"></td>
        </tr>
    </table>
    <input type="hidden" name="isNew" value="${requestScope.isNew}">
</form>

</body>
</html>
