<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 30.07.2021
  Time: 19:22
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
<%@ include file="/admin/header.jsp" %>
<form:form modelAttribute="user">
    <table border="solid">
        <tr>
            <td>Imię:</td>
            <td><form:input path="firstName"/> <form:errors path="firstName"/></td>
        </tr>
        <tr>
            <td>Nazwisko:</td>
            <td><form:input path="lastName"/> <form:errors path="lastName"/></td>
        </tr>
        <tr>
            <td>Hasło:</td>
            <td><form:password path="password"/> <form:errors path="password"/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td> <form:input path="email"/> <form:errors path="email"/></td>
        </tr>
        <tr>
            <td>Numer telefonu:</td>
            <td><form:input path="phone"/> <form:errors path="phone"/></td>
        </tr>
        <tr>
            <td>Firma:</td>
            <td><form:select items="${company}" path="company" itemValue="id" itemLabel="name"/> <form:errors path="company"/></td>
        </tr>

    </table>
    <button type="submit">Wyślij</button>
<p>${error}</p>
</form:form>

</body>
</html>
