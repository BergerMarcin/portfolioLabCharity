<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html lang="pl">
<head>
    <meta name="author" content="Marcin Berger, https://github.com/BergerMarcin"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Charity</title>

    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<header class="header--main-page">
    <nav class="container container--70">
        <ul class="nav--actions">
            <li><a href="" class="btn btn--small btn--without-border">Zaloguj</a></li>
            <li><a href="#" class="btn btn--small btn--highlighted">Załóż konto</a></li>
<%--            <li class="logged-user">--%>
<%--                Witaj Agata--%>
<%--                <ul class="dropdown">--%>
<%--                    <li><a href="#">Profil</a></li>--%>
<%--                    <li><a href="#">Ustawienia</a></li>--%>
<%--                    <li><a href="#">Moje zbiórki</a></li>--%>
<%--                    <li><a href="#">Wyloguj</a></li>--%>
<%--                </ul>--%>
<%--            </li>--%>
        </ul>

        <ul>
            <li><a href="/donation/form" class="btn btn--without-border active">Start</a></li>
            <li><a href="/index/#steps" class="btn btn--without-border">O co chodzi?</a></li>
            <li><a href="/index/#about-us" class="btn btn--without-border">O nas</a></li>
            <li><a href="/index/#help" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="/index/#contact" class="btn btn--without-border">Kontakt</a></li>
        </ul>
    </nav>
