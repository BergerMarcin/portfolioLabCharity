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
</header>

<section class="login-page">
    <h2>Zaloguj się</h2>
    <form method="post" action="/login">
<%--    <form:form action="/login" method="POST" modelAttribute="loginDataDTO">--%>
        <div class="form-section form-section--columns">
            <div class="form-section--column">
<%--                <p class="error"><form:errors path="email"></form:errors></p>--%>
                <div class="form-group form-group--inline">
                    <label>
                        E-mail
                        <input type="email" name="email" placeholder="Email"/>
                    </label>
                        <%--<form:label path="email">
                            E-mail
                            <form:input path="email" type="email"/>
                        </form:label>--%>
                </div>

<%--                <p class="error"><form:errors path="password"></form:errors></p>--%>
                <div class="form-group form-group--inline">
                    <label>Hasło
                        <input type="password" name="password" placeholder="Hasło"/>
                    </label>
                    <%--<form:label path="password">
                        Hasło
                        <form:input path="password" type="password"/>
                    </form:label>--%>
                </div>
<%--TODO: Email password reminder!--%>
                <a href="#" class="btn btn--small btn--without-border reset-password">Przypomnij hasło (! under construction !)</a>
            </div>
        </div>

        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">Zaloguj się</button>
            <a href="/registration/form" class="btn btn--without-border">Załóż konto</a>
        </div>

        <sec:csrfInput/>
    </form>
<%--    </form:form>--%>


    <%-- All Errors --%>
<%--    <c:if test="${not empty errorsMessageMap && errorsMessageMap != null}">
        <div class="errors">
            <h3>Proszę popraw:</h3>
            <c:set var="errorsMessageMapKeys" value="${errorsMessageMap.keySet()}" scope="page"></c:set>
            <c:forEach items="${errorsMessageMapKeys}" var="errorsMessageMapKey" varStatus="stat">
                <p>${stat.count}. W polu ${errorsMessageMapKey} należy: ${errorsMessageMap.get(errorsMessageMapKey)}</p>
            </c:forEach>
        </div>
    </c:if>--%>
</section>


<%--
<section class="login-page">
  <h2>Zaloguj się</h2>
  <form>
    <div class="form-group">
      <input type="email" name="email" placeholder="Email" />
    </div>
    <div class="form-group">
      <input type="password" name="password" placeholder="Hasło" />
      <a href="#" class="btn btn--small btn--without-border reset-password">Przypomnij hasło</a>
    </div>

    <div class="form-group form-group--buttons">
      <a href="#" class="btn btn--without-border">Załóż konto</a>
      <button class="btn" type="submit">Zaloguj się</button>
    </div>
  </form>
</section>
--%>


<%@ include file="footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>