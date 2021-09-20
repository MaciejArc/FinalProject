<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 21.07.2021
  Time: 09:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
    <style>
        td {
            text-align: center
        }

    </style>
</head>
<body>
<h3>Rejestracja</h3>
<form:form modelAttribute="user">
    <table border="solid">
        <tr>
            <td>Imię:</td>
            <td><form:input path="firstName"/> <br><form:errors path="firstName"/></td>
        </tr>
        <tr>
            <td>Nazwisko:</td>
            <td><form:input path="lastName"/> <br><form:errors path="lastName"/></td>
        </tr>
        <tr>
            <td>Hasło:</td>
            <td><form:password path="password"/><br> <form:errors path="password"/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td> <form:input path="email"/> <form:errors path="email"/></td>
            ${error}
        </tr>
        <tr>
            <td>Numer telefonu:</td>
            <td><form:input path="phone"/> <form:errors path="email"/></td>
        </tr>

    </table>
    <button type="submit">Wyślij</button>

</form:form>

</body>
</html>
