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

<%--TODO:
        - add summary&confirmation page --%>

<section class="login-page">
    <h2>Załóż konto</h2>

    <form:form action="/registration/form" method="POST" modelAttribute="registrationDTO">
    <div class="form-section form-section--columns">
        <div class="form-section--column">
            <p class="error"><form:errors path="firstName"></form:errors></p>
            <div class="form-group form-group--inline">
                <form:label path="firstName">
                    Imię
                    <form:input path="firstName" type="text"/>
                </form:label>
            </div>

            <p class="error"><form:errors path="lastName"></form:errors></p>
            <div class="form-group form-group--inline">
                <form:label path="lastName">
                    Nazwisko
                    <form:input path="lastName" type="text"/>
                </form:label>
            </div>

            <p class="error"><form:errors path="email"></form:errors></p>
            <div class="form-group form-group--inline">
                <form:label path="email">
                    E-mail
                    <form:input path="email" type="email"/>
                </form:label>
            </div>

            <p class="error"><form:errors path="password"></form:errors></p>
            <div class="form-group form-group--inline">
                <form:label path="password">
                    Hasło
                    <form:input path="password" type="password"/>
                </form:label>
            </div>

            <p class="error"><form:errors path="rePassword"></form:errors></p>
            <div class="form-group form-group--inline">
                <form:label path="rePassword">
                    Powtórz hasło
                    <form:input path="rePassword" type="password"/>
                </form:label>
            </div>

        </div>
    </div>

        <p class="error"><form:errors path="termsAcceptance"></form:errors></p>
    <div class="form-group form-group--inline">
    </div>
<%--        <div class="form-group form-group--checkbox">--%>
            <form:label path="termsAcceptance" for="terms">
<%--
TODO: uncomment that checkbox style and add to app.js action on changing box color
                <span class="checkbox" data-checked="off" style="background-color: transparent;"></span>
--%>
                <span class="description">Zaznacz zgodę na przetwarzanie danych osobowych</span>
                <form:checkbox path="termsAcceptance" value="true" id="terms"/>
            </form:label>
<%--        </div>--%>

        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">Załóż konto</button>
            <a href="/login" class="btn btn--without-border">Zaloguj się</a>
        </div>

        <sec:csrfInput/>
    </form:form>


    <%-- All Errors --%>
    <c:if test="${not empty errorsMessageMap && errorsMessageMap != null}">
        <div class="errors">
            <h3>Proszę popraw:</h3>
            <c:set var="errorsMessageMapKeys" value="${errorsMessageMap.keySet()}" scope="page"></c:set>
            <c:forEach items="${errorsMessageMapKeys}" var="errorsMessageMapKey" varStatus="stat">
                <p>${stat.count}. W polu ${errorsMessageMapKey} należy: ${errorsMessageMap.get(errorsMessageMapKey)}</p>
            </c:forEach>
        </div>
    </c:if>
</section>

<%@ include file="footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>