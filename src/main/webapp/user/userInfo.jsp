<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 10.08.2021
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Informacje o kliencie.</h2>
<input type="button" value="Wstecz" onClick="history.back();"/>
<table border="solid">
    <tr>
        <td>Imię:</td>
        <td>${user.firstName}</td>
    </tr>
    <tr>
        <td>Nazwisko:</td>
        <td>${user.lastName}</td>
    </tr>
    <tr>
        <td>Email:</td>
        <td>${user.email}</td>
    </tr>
    <tr>
        <td>Numer telefonu:</td>
        <td>${user.phone}</td>
    </tr>
    <c:forEach items="${user.addresses}" var="address">
        <tr>
            <td>Adres:</td>

            <td>${address.fullAddress}</td>

        </tr>
    </c:forEach>
</table>
</body>
</html>
