<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 29.07.2021
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/header.jsp" %>

<h2>Twoje usterki:</h2>
<table border="solid">
    <tr>
        <td>Nr</td><td>Opis:</td><td>Status</td><td>Adres</td><td>Wykonawca:</td><td>Akcje:</td>
    </tr>

    <c:forEach items="${order}" var="ord" varStatus="count" >


        <tr>
            <td>${count.count}</td>
            <td>${ord.description}</td>
            <td>${ord.status}</td>
            <td><a href="https://www.google.com/maps/place/${ord.address.street}+${ord.address.houseNumber},+${ord.address.zipCode}+${ord.address.city}" target="_blank">${ord.address.fullAddress}</a> </td>
            <td>${ord.user.fullName}</td>
            <td><a href="http://localhost:8080/faultOrder/add?id=${ord.id}">Edytuj</a> </td>
        </tr>

</c:forEach>

</table>



</body>
</html>
