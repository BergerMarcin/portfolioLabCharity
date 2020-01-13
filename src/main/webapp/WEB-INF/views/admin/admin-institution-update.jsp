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

</section>


<%--    <form:form action="/admin/start/role_choice" method="POST" modelAttribute="adminRoleChoiceDataDTO">--%>
<%--    <c:url value="/admin/institutions/add" var="addURL"></c:url>--%>
<%--    <a href="${updateURL}"><button type="button" class="btn">Dodaj nową instytucję</button></a>--%>
<%--    <br>--%>

<%--        <table border="2">--%>
<%--            <tr>--%>
<%--                <th></th>--%>
<%--                <th scope="col">lp.</th>--%>
<%--                <th scope="col">ID</th>--%>
<%--                <th scope="col">nazwa</th>--%>
<%--                <th scope="col">opis</th>--%>
<%--                <th scope="col">zaufana</th>--%>
<%--                <th scope="col">edycja</th>--%>
<%--            </tr>--%>
<%--            <c:forEach items="institutions" var="instit" varStatus="stat">--%>
<%--                <tr>--%>
<%--                    <td>${stat.count}</td>--%>
<%--                    <td>${instit.id}</td>--%>
<%--                    <td>${instit.name}</td>--%>
<%--                    <td>${instit.description}</td>--%>
<%--                    <td>${instit.trusted}</td>--%>
<%--                    <td>--%>
<%--                        <c:url value="/admin/institutions/update" var="updateURL">--%>
<%--                            <c:param name="id" value="${instit.id}"/>--%>
<%--                        </c:url>--%>
<%--                        <a href="${updateURL}"><button type="button" class="btn">Edytuj</button></a>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--        </table>--%>
<%--        <sec:csrfInput/>--%>
<%--    </form:form>--%>


<%@ include file="admin-footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>