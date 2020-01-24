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

<sec:authorize access="!hasRole('ROLE_SUPERADMIN')">
    <h2>Nieuprawnione wywołanie strony usuwania administratora</h2>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_SUPERADMIN')">
<section class="login-page">
    <h2>Usunięcie istniejącego administratora <br><br> (Uwaga! Będzie to całkowite usunięcie z bazy)</h2>
    <br>

<%--    TODO: when add User field to Donation entity uncomment below--%>
<%--
    <h3>Wraz z usinięciem administratora nastąpi usunięcie darowizn administratora</h3>
    <c:if test="${donationDTOList != null && not empty donationDTOList}">
        <h3 class="errors">Uwaga! Dla wybranej do usunięcia instytucji zostaną również usunięte następujące jej datki:</h3>
        <table border="2">
            <tr>
                <th scope="col">Lp.</th>
&lt;%&ndash;                <th scope="col">ID</th>&ndash;%&gt;
                <th scope="col">kategorie</th>
                <th scope="col">ilość<br>worków</th>
                <th scope="col">instytucja</th>
                <th scope="col">data odbioru</th>
                <th scope="col">godzina<br>odbioru</th>
            </tr>
            <c:forEach items="${donationDTOList}" var="user" varStatus="stat">
                <tr>
                    <td>${stat.count}</td>
&lt;%&ndash;                    <td>${donation.id}</td>&ndash;%&gt;
                    <td>${user.categoryIds}</td>
                    <td>${user.quantity}</td>
                    <td>${user.institutionId}</td>
                    <td>${user.pickUpDate}</td>
                    <td>${user.pickUpTime}</td>
                </tr>
            </c:forEach>
        </table>
        <br><br>
    </c:if>
--%>

    <form:form method="post" modelAttribute="userDTO">
        <sec:csrfInput/>

        <form:hidden path="id"/>
        <form:hidden path="firstName"/>
        <form:hidden path="lastName"/>
        <form:hidden path="email"/>
        <form:hidden path="active"/>
        <form:hidden path="termsAcceptance"/>
        <form:hidden path="userInfoDTO.id"/>
        <form:hidden path="userInfoDTO.street"/>
        <form:hidden path="userInfoDTO.city"/>
        <form:hidden path="userInfoDTO.zipCode"/>
        <form:hidden path="userInfoDTO.phone"/>
        <form:hidden path="userInfoDTO.pickUpComment"/>
<%--        <c:forEach items="${roleDTOList}" var="roleDTO" varStatus="stat">--%>
<%--            <c:set var="index" value="${stat.count-1}"/>--%>
<%--            <p>Index: ${index}</p>--%>
<%--            <form:hidden path="roleDTOList[index].id"/>--%>
<%--            <form:hidden path="roleDTOList[index].name"/>--%>
<%--        </c:forEach>--%>

        <div class="form-section form-section--columns">

            <h2>Dane usuwanego administratora:</h2>
            <table border="2">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">imię</th>
                    <th scope="col">nazwisko</th>
<%--                    <th scope="col">email</th>--%>
                    <th scope="col">aktywny</th>
                    <th scope="col">role</th>
                    <th scope="col">ulica</th>
                    <th scope="col">miasto</th>
                    <th scope="col">kod</th>
                    <th scope="col">telefon</th>
                </tr>
                <tr>
                    <td>${userDTO.id}</td>
                    <td>${userDTO.firstName}</td>
                    <td>${userDTO.lastName}</td>
<%--                    <td>${userDTO.email}</td>--%>
                    <td>
                        <c:if test="${user.active}">tak</c:if>
                        <c:if test="${!user.active}">NIE</c:if>
                    </td>
                    <td>
                        <c:forEach items="${userDTO.roleDTOList}" var="role">
                            ${role.name}<br>
                        </c:forEach>
                    </td>
                    <td>${userDTO.userInfoDTO.street}</td>
                    <td>${userDTO.userInfoDTO.city}</td>
                    <td>${userDTO.userInfoDTO.zipCode}</td>
                    <td>${userDTO.userInfoDTO.phone}</td>
                </tr>
            </table>

            <div class="form-section--column">
                <h4>Potwierdź całkowite usunięcie administratora hasłem zalogowanego super-administratora</h4>

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
                        Powtórz hasło <form:input path="rePassword" type="password"/>
                    </form:label>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="submit" class="btn" name="formButtonChoice" value="1">Potwierdź Usunięcie</button>
                    <button type="submit" class="btn" name="formButtonChoice" value="0">Anuluj</button>
                </div>

                <%@ include file="admin-error-messages-list.jsp" %>

            </div>
        </div>
    </form:form>
</section>
</sec:authorize>>

<%@ include file="admin-footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>