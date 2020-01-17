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
<h2>Lista administratorów</h2>
    <c:url value="/admin/admin/add" var="addURL"></c:url>
    <a href="${addURL}"><button type="button" class="btn">Dodaj nowego administratora</button></a>
    <br><br>

    <table border="2">
        <tr>
            <th scope="col">Lp.</th>
            <th scope="col">ID</th>
            <th scope="col">imię</th>
            <th scope="col">nazwisko</th>
            <th scope="col">email</th>
            <th scope="col">aktywny</th>
            <th scope="col">role</th>
            <th scope="col">ulica</th>
            <th scope="col">miasto</th>
            <th scope="col">kod</th>
            <th scope="col">telefon</th>
            <th scope="col">edycja</th>
            <th scope="col">usuń</th>
        </tr>
        <c:forEach items="${userDataDTOList}" var="user" varStatus="stat">
            <tr>
                <td>${stat.count}</td>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>
                    <c:if test="${user.active}">tak</c:if>
                    <c:if test="${!user.active}">NIE</c:if>
                </td>
                <td>
                    <c:forEach items="${user.roleDataDTOList}" var="role">
                        ${role.name}<br>
                    </c:forEach>
                </td>
                <td>${user.userInfoDataDTO.street}</td>
                <td>${user.userInfoDataDTO.city}</td>
                <td>${user.userInfoDataDTO.zipCode}</td>
                <td>${user.userInfoDataDTO.phone}</td>
                <td>
                    <c:url value="/admin/admin/update" var="updateURL">
                        <c:param name="id" value="${user.id}"/>
                    </c:url>
                    <a href="${updateURL}"><button type="button" class="btn">Edytuj</button></a>
                </td>
                <td>
                    <c:url value="/admin/admin/delete" var="deleteURL">
                        <c:param name="id" value="${user.id}"/>
                    </c:url>
                    <a href="${deleteURL}"><button type="button" class="btn">Usuń</button></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>

<%@ include file="admin-footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>