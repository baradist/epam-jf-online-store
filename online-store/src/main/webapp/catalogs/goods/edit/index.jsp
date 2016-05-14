<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="isNew" scope="page" value="${requestScope.isNew}"/>

<c:if test="${!isNew}" >
    <jsp:useBean id="good" type="model.Good" scope="request"/>
</c:if>


<html>
<head>
    <%@ include file="/WEB-INF/import.html"%>
    <c:choose>
        <c:when test="${isNew}" >
            <title>New good</title>
        </c:when>
        <c:otherwise>
            <title>Edit ${good.name}</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
<%@ include file="/WEB-INF/top_line.jsp"%>


<form method="POST" action="/catalogs/goods/edit">
    <table border="0" cellspacing="5">
        <c:if test="${!isNew}">
            <tr>
                <th align="right">Артикул:</th>
                <td align="left"><input type="text" name="id" value="${good.id}" readonly></td>
            </tr>
        </c:if>

        <tr>
            <th align="right">Название:</th>
            <td align="left"><input type="text" name="name"
                <c:if test="${!isNew}">
                    value="${good.name}"
                </c:if>
            ></td>
        </tr>
        <tr>
            <th align="right">Производитель:</th>
            <td align="left"><input type="text" name="producer"
                <c:if test="${!isNew}">
                                    value="${good.producer.name}" readonly
                </c:if>
            >
            </td>
        </tr>
        <tr>
            <th align="right">Описание:</th>
            <td align="left"><input type="text" name="description"
                <c:if test="${!isNew}">
                    value="${good.description}"
                </c:if>
            ></td>
        </tr>
        <tr>
            <td align="right"><input type="submit" value="Save"></td>
            <td align="left"><input type="reset"></td>
        </tr>
    </table>
    <input type="hidden" name="isNew" value="${requestScope.isNew}">
</form>

</body>
</html>
