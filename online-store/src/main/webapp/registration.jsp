<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<%--<fmt:message bundle="${loc}" key="New" var="New"/>--%>
<%--<fmt:message bundle="${loc}" key="Editing" var="Editing"/>--%>
<fmt:message bundle="${loc}" key="Save" var="Save"/>
<fmt:message bundle="${loc}" key="Reset" var="Reset"/>
<fmt:message bundle="${loc}" key="Persons" var="Persons"/>

<fmt:message bundle="${loc}" key="Email" var="Email"/>
<fmt:message bundle="${loc}" key="Password" var="Password"/>
<fmt:message bundle="${loc}" key="PasswordConfirm" var="PasswordConfirm"/>
<fmt:message bundle="${loc}" key="FirsName" var="FirsName"/>
<fmt:message bundle="${loc}" key="LastName" var="LastName"/>
<fmt:message bundle="${loc}" key="Birthday" var="Birthday"/>
<fmt:message bundle="${loc}" key="Address" var="Address"/>
<fmt:message bundle="${loc}" key="Phone" var="Phone"/>
<fmt:message bundle="${loc}" key="FillTheFormPlease" var="FillTheFormPlease"/>
<fmt:message bundle="${loc}" key="LoginIsBusy" var="LoginIsBusy"/>
<fmt:message bundle="${loc}" key="PasswordsAreNotEquals" var="PasswordsAreNotEquals"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Registration TO LOCALISE</title>
<body bgcolor="white">
<div class="container">
    <div class="container">
        <form method="POST" action="/auth">
            <input type="hidden" name="register" value="${true}">
            <table border="0" cellspacing="5">
                <tr>
                    <th align="right">${Email}*:</th>
                    <td align="left"><input type="text" name="email" value="${requestScope.email}"></td>
                </tr>
                <tr>
                    <th align="right">${Password}*:</th>
                    <td align="left"><input type="password" name="password"></td>
                </tr>
                <tr>
                    <th align="right">${PasswordConfirm}*:</th>
                    <td align="left"><input type="password" name="confirm_password"></td>
                </tr>
                <tr>
                    <th align="right">${FirsName}*:</th>
                    <td align="left"><input type="text" name="first_name" value="${requestScope.firstName}"></td>
                </tr>
                <tr>
                    <th align="right">${LastName}*:</th>
                    <td align="left"><input type="text" name="last_name" value="${requestScope.lastName}"></td>
                </tr>
                <tr>
                    <th align="right">${Birthday}:</th>
                    <td align="left"><input type="date" name="dob" value="${requestScope.dob}">
                    </td>
                </tr>
                <tr>
                    <th align="right">${Phone}:</th>
                    <td align="left"><input type="text" name="phone" value="${requestScope.phone}"></td>
                </tr>
                <tr>
                    <th align="right">${Address}:</th>
                    <td align="left"><input type="text" name="address" value="${requestScope.address}"></td>
                </tr>
                <tr>
                    <td align="right"><input type="submit" value="${Save}"></td>
                    <td align="left"><input type="reset" value="${Reset}"></td>
                </tr>
            </table>
        </form>
        <c:if test="${requestScope.loginIsEmpty || requestScope.passwordIsEmpty || requestScope.firstNameIsEmpty || requestScope.lastNameIsEmpty }">
            ${FillTheFormPlease}
        </c:if>
        <c:if test="${requestScope.loginIsBusy}">
            <br>
            ${LoginIsBusy}
        </c:if>
        <c:if test="${requestScope.differentPasswords}">
            <br>
            ${PasswordsAreNotEquals}
        </c:if>
    </div>
</div>
</body>
</html>
