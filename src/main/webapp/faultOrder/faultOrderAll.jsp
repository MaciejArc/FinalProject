<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 30.07.2021
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/admin/header.jsp" %>
<h2>Lista uterek:</h2>
<table border="solid">
    <tr>
        <td>ID:</td>
        <td>Klient</td>
        <td>Adres</td>
        <td>Opis:</td>
        <td>Status</td>
        <td>Wykonawca:</td>
        <td>Akcje:</td>
    </tr>
    <c:forEach items="${faultOrder}" var="ord">

        <tr>
            <td>${ord.id}</td>
            <td><a href="http://localhost:8080/user/info?id=${ord.client.id}">${ord.client.fullName}</a></td>
            <td>
                <a href="https://www.google.com/maps/place/${ord.address.street}+${ord.address.houseNumber},+${ord.address.zipCode}+${ord.address.city}"
                   target="_blank">${ord.address.fullAddress}</a></td>
            <td>${ord.description}</td>
            <td>${ord.status}</td>
            <td>${ord.user.fullName}</td>
            <td><a href="http://localhost:8080/faultOrder/update?id=${ord.id}">Edytuj</a> <a
                    href="http://localhost:8080/faultOrder/delete?id=${ord.id}">Usuń</a></td>
        </tr>

    </c:forEach>
</table>
</body>
</html>
