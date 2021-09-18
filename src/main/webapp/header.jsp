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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <title>Title</title>
</head>
<body>
<h1>Witaj: ${userName}</h1>
<a href="http://localhost:8080/user/start">Moje usterki</a> ||
<a href="http://localhost:8080/faultOrder/add?id=">Zgłoś usterkę</a> ||
<a href="http://localhost:8080/addresses/add">Dodaj adres</a> ||
<a href="http://localhost:8080/logout">Wyloguj</a>
</body>
</html>
