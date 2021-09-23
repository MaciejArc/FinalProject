<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 30.07.2021
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Witaj: ${userName}</h1>
<a href="http://localhost:8080/admin/faultOrderAll">Usterki</a> ||
<a href="http://localhost:8080/admin/users">Lista pracowników</a> ||
<a href="http://localhost:8080/admin/add/user">Dodaj pracownika</a> ||
<a href="http://localhost:8080/company/add">Dodaj firme</a> ||
<a href="http://localhost:8080/logout">Wyloguj</a>
</body>
</html>
