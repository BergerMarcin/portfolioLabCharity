<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="set" uri="http://www.springframework.org/security/tags" %>


<%--
Start at target jsp (not here) due to starting classes & due to closing slogans
<header class="header--main-page">
--%>
    <nav class="container container--70">
        <ul class="nav--actions">
            <sec:authorize access="isAnonymous()">
                <li><a href="/login" class="btn btn--small btn--without-border">Zaloguj</a></li>
                <li><a href="/registration/form" class="btn btn--small btn--highlighted">Załóż konto</a></li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <li class="logged-user">
                    Witaj ${currentUser.currentUserDTO.firstName} ${currentUser.currentUserDTO.lastName}!
                    <ul class="dropdown">
                        <li><a href="#">Profil</a></li>
                        <li><a href="#">Ustawienia</a></li>
                        <li><a href="#">Moje zbiórki</a></li>
                        <li>
                            <form method="post" action="/logout">
                                <a href="/logout"><button type="submit" class="btn btn--small btn--without-border">Wyloguj</button></a>
                                <set:csrfInput/>
                            </form>
                        </li>
                    </ul>
                </li>
            </sec:authorize>
        </ul>

        <ul>
<%--            <li><a href="/donation/form/#data-step-1" class="btn btn--without-border active">Start</a></li>--%>
            <li><a href="/donation/form" class="btn btn--without-border active">Start</a></li>
            <li><a href="/index/#steps" class="btn btn--without-border">O co chodzi?</a></li>
            <li><a href="/index/#about-us" class="btn btn--without-border">O nas</a></li>
            <li><a href="/index/#help" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="/index/#contact" class="btn btn--without-border">Kontakt</a></li>
        </ul>
    </nav>
<%--
Closing header at target jsp (not here)
</header>
--%>
