<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>

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
    <h2>Zmiana danych istniejącego administratora</h2>

    <form:form method="post" modelAttribute="userDTO">
        <sec:csrfInput/>

        <div class="form-section form-section--columns">
            <div class="form-section--column">

                <form:hidden path="id"/>
                <form:hidden path="email"/>
                <form:hidden path="termsAcceptance"/>
                <form:hidden path="userInfoDTO.id"/>

                <h4>Główne dane konta</h4>
                <p class="error"><form:errors path="firstName"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="firstName" for="firstName">
                        Imię <form:input path="firstName" id="firstName"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="lastName"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="lastName" for="lastName">
                        Nazwisko <form:input path="lastName" id="lastName"/>
                    </form:label>
                </div>
<%--
                <p class="error"><form:errors path="email"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="email" for="email">
                        Email <form:input path="email" type="email" id="email"/>
                    </form:label>
                </div>
--%>
                <p class="error"><form:errors path="active"></form:errors></p>
                <div class="form-group form-group--inline">
                </div>
                <%--        <div class="form-group form-group--checkbox">--%>
                <form:label path="active" for="active">
                    <%--
                    TODO: uncomment that checkbox style and add to app.js action on changing box color
                                    <span class="checkbox" data-checked="off" style="background-color: transparent;"></span>
                    --%>
                    <span class="description">Aktywny status</span>
                    <form:checkbox path="active" id="active"/>
                </form:label>
                <%--        </div>--%>

                <h4>Wybierz poziom dostępu</h4>
                <p>Dostęp:
<%--                    TODO: improve below select form--%>
                    <form:select path="roleDTOList" items="${roleDTOList}" itemLabel="name"
                                 itemValue="id" multiple="true">
                    </form:select>
                </p>
                <%--<c:forEach items="${roleDTOListAll}" var="role" varStatus="stat">
                    <div class="form-group form-group--checkbox">
                        <form:label path="roleDTOList" for="${'role'.concat(stat.count)}">
                            <c:if test="${userDTO.roleDTOList.contains(role)}">
                                <span class="checkbox" data-checked="on" style="background-color: #f9c910;"></span>
                            </c:if>
                            <c:if test="${!userDTO.roleDTOList.contains(role)}">
                                <span class="checkbox" data-checked="off" style="background-color: transparent;"></span>
                            </c:if>
                            <span class="description">${substringAfter(role.name, 'ROLE_')}</span>
                            <form:checkbox path="roleDTOList" value="${role}" id="${'role'.concat(stat.count)}"/>
                        </form:label>
                    </div>
                </c:forEach>--%>
            </div>>

            <div class="form-section--column">
                <h4>Adres odbioru</h4>

                <p class="error"><form:errors path="userInfoDTO.street"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="userInfoDTO.street" for="userInfoDTO.street">
                        Ulica <form:input path="userInfoDTO.street" id="userInfoDTO.street"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="userInfoDTO.city"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="userInfoDTO.city" for="userInfoDTO.city">
                        Miasto <form:input path="userInfoDTO.city" id="userInfoDTO.city"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="userInfoDTO.zipCode"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="userInfoDTO.zipCode" for="userInfoDTO.zipCode">
                        Kod pocztowy <form:input path="userInfoDTO.zipCode" id="userInfoDTO.zipCode"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="userInfoDTO.phone"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="userInfoDTO.phone" for="userInfoDTO.phone">
                        Telefon <form:input path="userInfoDTO.phone" id="userInfoDTO.phone"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="userInfoDTO.pickUpComment"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="userInfoDTO.pickUpComment" for="userInfoDTO.pickUpComment">
                        Uwagi do odbioru <form:input path="userInfoDTO.pickUpComment" id="userInfoDTO.pickUpComment"/>
                    </form:label>
                </div>
            </div>

            <h4>Potwierdź powyższe zmiany hasłem danego administratora</h4>
            <p class="error"><form:errors path="password"></form:errors></p>
            <div class="form-group form-group--inline">
                <form:label path="password">
                    Hasło
                    <form:input path="password" type="password"/>
<%--                    <input type="password" name="password" th:value="${userDTO.password}"/>--%>
                </form:label>
            </div>
            <p class="error"><form:errors path="rePassword"></form:errors></p>
            <div class="form-group form-group--inline">
                <form:label path="rePassword">
                    Powtórz hasło <form:input path="rePassword" type="password"/>
                </form:label>
            </div>

            <div class="form-group form-group--buttons">
                <button type="submit" class="btn" name="formButtonChoice" value="1">Wprowadź zmiany</button>
                <button type="submit" class="btn" name="formButtonChoice" value="0">Anuluj</button>
            </div>
        </div>

        <%@ include file="admin-error-messages-list.jsp" %>

    </form:form>

</section>

<%@ include file="admin-footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>