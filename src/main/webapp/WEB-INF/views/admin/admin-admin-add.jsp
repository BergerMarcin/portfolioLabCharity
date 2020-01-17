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
    <h2>Dodawanie nowej instytucji lub organizacji</h2>

    <form:form method="post" modelAttribute="userDataDTO">
        <sec:csrfInput/>

        <div class="form-section form-section--columns">
            <div class="form-section--column">

                <form:hidden path="id"/>
                <form:hidden path="userInfoDataDTO.id"/>
                <form:hidden path="roleDataDTOList"/>

                <p class="error"><form:errors path="firstName"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="firstName" for="firstName">
                        Imię <form:input path="firstName" id="firstName" required="true"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="lastName"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="lastName" for="lastName">
                        Nazwisko <form:input path="lastName" id="lastName" required="true"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="email"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="email" for="email">
                        Email <form:input type="email" path="email" id="email"  required="true"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="termsAcceptance"></form:errors></p>
                <div class="form-group form-group--inline">
                </div>
                <%--        <div class="form-group form-group--checkbox">--%>
                <form:label path="termsAcceptance" for="termsAcceptance">
                    <%--
                    TODO: uncomment that checkbox style and add to app.js action on changing box color
                                    <span class="checkbox" data-checked="off" style="background-color: transparent;"></span>
                    --%>
                    <span class="description">Zaznacz zgodę na przetwarzanie danych osobowych</span>
                    <form:checkbox path="termsAcceptance" value="true" id="termsAcceptance"/>
                </form:label>
                <%--        </div>--%>
                <br>
                <p class="error"><form:errors path="password"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="password">
                        Hasło <form:input path="password" type="password"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="rePassword"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="rePassword">
                        Powtórz hasło <form:input path="rePassword" type="password"/>
                    </form:label>
                </div>
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
                    <form:checkbox path="active" value="true" id="active"/>
                </form:label>
                <%--        </div>--%>
                <br>

                <p class="error"><form:errors path="userInfoDataDTO.street"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="userInfoDataDTO.street" for="userInfoDataDTO.street">
                        Ulica <form:input path="userInfoDataDTO.street" id="userInfoDataDTO.street"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="userInfoDataDTO.city"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="userInfoDataDTO.city" for="userInfoDataDTO.city">
                        Miasto <form:input path="userInfoDataDTO.city" id="userInfoDataDTO.city"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="userInfoDataDTO.zipCode"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="userInfoDataDTO.zipCode" for="userInfoDataDTO.zipCode">
                        Kod pocztowy <form:input path="userInfoDataDTO.zipCode" id="userInfoDataDTO.zipCode"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="userInfoDataDTO.phone"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="userInfoDataDTO.phone" for="userInfoDataDTO.phone">
                        Telefon <form:input path="userInfoDataDTO.phone" id="userInfoDataDTO.phone"/>
                    </form:label>
                </div>
                <p class="error"><form:errors path="userInfoDataDTO.pickUpComment"></form:errors></p>
                <div class="form-group form-group--inline">
                    <form:label path="userInfoDataDTO.pickUpComment" for="userInfoDataDTO.pickUpComment">
                        Uwagi do odbioru <form:input path="userInfoDataDTO.pickUpComment" id="userInfoDataDTO.pickUpComment"/>
                    </form:label>
                </div>
                <br>

                <%--        <div class="form-group form-group--checkbox">--%>
<%--                <form:label path="roleUser" for="roleUser">--%>
                    <%--
                    TODO: uncomment that checkbox style and add to app.js action on changing box color
                                    <span class="checkbox" data-checked="off" style="background-color: transparent;"></span>
                    --%>
                    <span class="description">Jednocześnie użytkownik </span>
<%--                    <form:checkbox path="roleUser" value="true" id="roleUser"/>--%>
                    <input type="checkbox" name="roleUser" value="true" checked/>
                    <input type="hidden" name="roleUser" value="false"/>
<%--                </form:label>--%>
                <%--        </div>--%>
                <br>

                <div class="form-group form-group--buttons">
                    <button type="submit" class="btn" name="formButtonChoice" value="1">Dodaj</button>
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