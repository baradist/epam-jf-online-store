<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="locale" var="loc" />
    <fmt:message bundle="${loc}" key="local.message" var="message" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru"
                 var="ru_button" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.en"
                 var="en_button" />
</head>
<body>
<form action="localizator" method="post">
    <input type="hidden" name="local" value="ru" /> <input type="submit" value="${ru_button}" /><br />
</form>
<form action="localizator" method="post">
    <input type="hidden" name="local" value="en" /> <input type="submit" value="${en_button}" /><br />
</form>
    <c:out value="${message}" />
</body>
</html>