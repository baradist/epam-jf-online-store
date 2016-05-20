<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration TO LOCALISE</title>
<body bgcolor="white">

${requestScope.test}
<form method="POST" action="/auth">
    <input type="hidden" name="register" value="${true}">
    <table border="0" cellspacing="5">
        <tr>
            <th align="right">e-mail:</th>
            <td align="left"><input type="text" name="email"></td>
        </tr>
        <tr>
            <th align="right">Password:</th>
            <td align="left"><input type="password" name="password"></td>
        </tr>
        <tr>
            <th align="right">Confirm password:</th>
            <td align="left"><input type="password" name="confirm_password"></td>
        </tr>
        <tr>
            <th align="right">First name:</th>
            <td align="left"><input type="text" name="first_name"></td>
        </tr>
        <tr>
            <th align="right">Last name:</th>
            <td align="left"><input type="text" name="last_name"></td>
        </tr>
        <tr>
            <th align="right">Birthday:</th>
            <td align="left"><input type="date" name="dob">
            </td>
        </tr>
        <tr>
            <th align="right">Phone:</th>
            <td align="left"><input type="text" name="phone"></td>
        </tr>
        <tr>
            <th align="right">Address:</th>
            <td align="left"><input type="text" name="address"></td>
        </tr>
        <tr>
            <td align="right"><input type="submit" value="Send"></td>
            <td align="left"><input type="reset"></td>
        </tr>
    </table>
</form>
</body>
</html>
