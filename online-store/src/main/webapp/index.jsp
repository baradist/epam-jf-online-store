<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<html>
<head>
    <title>List</title>
</head>
<body>


<h1>List!</h1>
<table style="border: 1px solid #000;">
    <tr>
        <th style="border: 1px solid #000;">Артикул</th>
        <th style="border: 1px solid #000;">Название</th>
        <th style="border: 1px solid #000;">Производитель</th>
        <th style="border: 1px solid #000;">Цена</th>
    </tr>

    <c:forEach var="lot" items="${lots}">
        <tr>
            <td style="border: 1px solid #000;">${lot.good.id}</td>
            <td style="border: 1px solid #000;">${lot.good.name}</td>
            <td style="border: 1px solid #000;">${lot.good.producer.name} / ${lot.good.producer.name}</td>
            <td style="border: 1px solid #000;">${lot.price_sal}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
