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

<header class="header--main-page">
    <%--Start & closing header here (instead at header.jsp)--%>
    <%@ include file="admin-header.jsp" %>
</header>

<section class="login-page">
    <h2>Edycja istniejącej instytucji lub organizacji</h2>

    <form:form method="post" modelAttribute="institutionDataDTO">
        <sec:csrfInput/>
        <form:hidden path="id"/>

        <div class="form-section form-section--columns">
            <div class="form-section--column">
                <p class="error"><form:errors path="name"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="name" for="name">
                        Nazwa: <form:input path="name" id="name"/>
                    </form:label>
                </div>

                <p class="error"><form:errors path="description"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="description" for="description">
                        Opis:<form:input path="description" id="description"/>
                    </form:label>
                </div>

                <p class="error"><form:errors path="trusted"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="trusted" for="trusted">
                        Zaznacz jeżeli instytucja jest zaufana
                        <form:checkbox path="trusted" value="true" id="trusted"/>
                    </form:label>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="submit" class="btn" name="formButtonChoice" value="1">Zmień</button>
                    <button type="submit" class="btn" name="formButtonChoice" value="0">Anuluj</button>
                </div>
            </div>
        </div>

        <%@ include file="admin-error-messages-list.jsp" %>

    </form:form>
</section>

<%@ include file="admin-footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>