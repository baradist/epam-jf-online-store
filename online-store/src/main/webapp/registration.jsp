<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration TO LOCALISE</title>
<body bgcolor="white">
<div class="container">
    <form method="POST" action="/auth">
        <input type="hidden" name="register" value="${true}">
        <table border="0" cellspacing="5">
            <tr>
                <th align="right">e-mail*:</th>
                <td align="left"><input type="text" name="email" value="${requestScope.email}"></td>
            </tr>
            <tr>
                <th align="right">Password*:</th>
                <td align="left"><input type="password" name="password"></td>
            </tr>
            <tr>
                <th align="right">Confirm password*:</th>
                <td align="left"><input type="password" name="confirm_password"></td>
            </tr>
            <tr>
                <th align="right">First name*:</th>
                <td align="left"><input type="text" name="first_name" value="${requestScope.firstName}"></td>
            </tr>
            <tr>
                <th align="right">Last name*:</th>
                <td align="left"><input type="text" name="last_name" value="${requestScope.lastName}"></td>
            </tr>
            <tr>
                <th align="right">Birthday:</th>
                <td align="left"><input type="date" name="dob" value="${requestScope.dob}">
                </td>
            </tr>
            <tr>
                <th align="right">Phone:</th>
                <td align="left"><input type="text" name="phone" value="${requestScope.phone}"></td>
            </tr>
            <tr>
                <th align="right">Address:</th>
                <td align="left"><input type="text" name="address" value="${requestScope.address}"></td>
            </tr>
            <tr>
                <td align="right"><input type="submit" value="Send"></td>
                <td align="left"><input type="reset"></td>
            </tr>
        </table>
    </form>
    <c:if test="${requestScope.loginIsEmpty || requestScope.passwordIsEmpty || requestScope.firstNameIsEmpty || requestScope.lastNameIsEmpty }">
        Fill the form, please.
    </c:if>
    <c:if test="${requestScope.loginIsBusy}">
        <br>
        login is busy.
    </c:if>
    <c:if test="${requestScope.differentPasswords}">
        <br>
        Passwords are not equals.
    </c:if>
</div>

</body>
</html>
