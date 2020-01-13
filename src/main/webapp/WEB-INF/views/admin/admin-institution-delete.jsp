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
    <h2>Usunięcie istniejącej instytucji lub organizacji</h2>

    <form:form method="post" modelAttribute="institutionDataDTO">
        <sec:csrfInput/>
        <form:hidden path="id"/>
        <form:hidden path="name"/>
        <form:hidden path="description"/>
        <form:hidden path="trusted"/>

        <div class="form-section form-section--columns">
            <div class="form-section--column">
                <table border="2">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">nazwa</th>
                        <th scope="col">opis</th>
                        <th scope="col">zaufana</th>
                    </tr>
                    <tr>
                        <td>${institutionDataDTO.id}</td>
                        <td>${institutionDataDTO.name}</td>
                        <td>${institutionDataDTO.description}</td>
                        <td>
                            <c:if test="${institutionDataDTO.trusted}">tak</c:if>
                            <c:if test="${!institutionDataDTO.trusted}">NIE</c:if>
                        </td>
                    </tr>
                </table>

                <div class="form-group form-group--buttons">
                    <button type="submit" class="btn" name="formButtonChoice" value="1">Potwierdź Usunięcie</button>
                    <button type="submit" class="btn" name="formButtonChoice" value="0">Anuluj</button>
                </div>

                </div>
        </div>
    </form:form>
</section>

<%@ include file="admin-footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>