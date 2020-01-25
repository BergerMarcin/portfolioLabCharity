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
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <nav class="container container--70">
            <ul class="nav--actions">
                <li class="logged-user">
                    Witaj ${currentUser.currentUserDTO.firstName} ${currentUser.currentUserDTO.lastName}!
                </li>
                <li>
                    <form method="post" action="/logout">
                        <a href="/logout"><button type="submit" class="btn btn--small btn--without-border">Wyloguj</button></a>
                        <set:csrfInput/>
                    </form>
                </li>
            </ul>

            <ul>
<%--                <li><a href="/admin/contacts" class="btn btn--without-border active">Info z kontakt</a></li>--%>
<%--                <li><a href="/admin/categories" class="btn btn--without-border active">Kategorie</a></li>--%>
                <li><a href="/admin/institution" class="btn btn--without-border active">Fundacje i organizacje</a></li>
<%--                <li><a href="/admin/donations" class="btn btn--without-border active">Datki</a></li>--%>
<%--                <li><a href="/admin/users" class="btn btn--without-border active">Użytkownicy</a></li>--%>
<%--                <li><a href="/admin/roles" class="btn btn--without-border active">Role</a></li>--%>
                <li><a href="/admin/admin" class="btn btn--without-border active">Administratorzy</a></li>
                <li><a href="/admin/bootstrap" class="btn btn--without-border active">Administratorzy<br>bootstrap</a></li>
            </ul>
        </nav>
    </sec:authorize>
<%--
Closing header at target jsp (not here)
</header>
--%>
