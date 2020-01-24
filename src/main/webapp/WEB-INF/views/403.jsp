<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


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

<header>
    <%--Start & closing header here (instead at header.jsp)--%>
    <%@ include file="header.jsp" %>

    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                Dostęp zabroniony. Proszę zaloguj się<br><br>
                <a href="/index" class="btn btn--without-border active">Strona główna</a>
            </h1>
        </div>
    </div>
</header>

<%--<section class="login-page">--%>
<%--    <h2>Dostęp zabroniony</h2>--%>
<%--    <a href="/index" class="btn btn--without-border active">Strona główna</a>--%>
<%--</section>--%>

<%@ include file="footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>