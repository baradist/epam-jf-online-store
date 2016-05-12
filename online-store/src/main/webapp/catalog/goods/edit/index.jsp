<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="good" type="model.Good" scope="request"/>

<html>
<head>
    <title>Edit ${good.name}</title>
</head>
<body>

<%--<%--%>
    <%--Boolean isNew = (boolean) request.getAttribute("isNew");--%>
    <%--pageContext.setAttribute("isNew", isNew);--%>

<%--%>--%>
<%--<c:out value="${requestScope.get('new')}" />--%>

<%--<c:set var="salary" scope="request" value="${40}" />--%>
<%----%>
<%--<c:out value="${requestScope.salary}" />--%>
<%--<c:out value="${requestScope.new}" />--%>
${requestScope.isNew}
<%--${pageScope.isNew}--%>

<form method="POST" action="/catalog/goods/edit">
    <table border="0" cellspacing="5">
        <tr>
            <th align="right">Артикул:</th>
            <td align="left"><input type="text" name="id" value="${good.id}" readonly></td>
        </tr>
        <tr>
            <th align="right">Название:</th>
            <td align="left"><input type="text" name="name" value="${good.name}"></td>
        </tr>
        <tr>
            <th align="right">Производитель:</th>
            <td align="left"><input type="text" name="producer" value="${good.producer.name}" readonly></td>
        </tr>
        <tr>
            <th align="right">Описание:</th>
            <td align="left"><input type="text" name="description" value="${good.description}"></td>
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
