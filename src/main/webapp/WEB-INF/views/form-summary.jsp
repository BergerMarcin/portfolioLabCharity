<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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

<header class="header--form-page">
    <%--Start & closing header here (instead at header.jsp)--%>
    <%@ include file="header.jsp" %>

    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                Oddaj rzeczy, których już nie chcesz<br />
                <span class="uppercase">potrzebującym</span>
            </h1>

            <div class="slogan--steps">
                <div class="slogan--steps-title">Wystarczą 4 proste kroki:</div>
                <ul class="slogan--steps-boxes">
                    <li>
                        <div><em>1</em><span>Wybierz rzeczy</span></div>
                    </li>
                    <li>
                        <div><em>2</em><span>Spakuj je w worki</span></div>
                    </li>
                    <li>
                        <div><em>3</em><span>Wybierz fundację</span></div>
                    </li>
                    <li>
                        <div><em>4</em><span>Zamów kuriera</span></div>
                    </li>
                </ul>
                <div class="slogan--steps-title"><h3>Podsumowanie poniżej</h3></div>
            </div>
        </div>
    </div>
</header>


<section class="form--steps">
    <%--<div class="form--steps-instructions">
        <div class="form--steps-container">
            <h3>Ważne!</h3>
            <p data-step="1" class="active">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="2">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="3">
                Wybierz jedną, do
                której trafi Twoja przesyłka.
            </p>
            <p data-step="4">Podaj adres oraz termin odbioru rzeczy.</p>
        </div>
    </div>--%>

    <div class="form--steps-container">
        <div class="form--steps-counter"><span></span></div>
<%--        <div class="form--steps-counter">Krok <span>1</span>/4--%>

        <form:form action="/donation/form-summary" method="post" modelAttribute="donationDataDTO">
            <sec:csrfInput/>
            <form:hidden path="categoryIds"/>
            <form:hidden path="institutionId"/>
            <form:hidden path="quantity"/>
            <form:hidden path="street"/>
            <form:hidden path="city"/>
            <form:hidden path="zipCode"/>
            <form:hidden path="phone"/>
            <form:hidden path="pickUpDate"/>
            <form:hidden path="pickUpTime"/>
            <form:hidden path="pickUpComment"/>

            <!-- STEP 5 -->
            <div id="data-step-1" data-step="1">
                <h3>Podsumowanie Twojej darowizny</h3>

                <div class="summary">
                    <div class="form-section">
                        <h4>Oddajesz:</h4>
                        <ul>
                            <li>
                                <span class="icon icon-bag"></span>
                                <span class="summary--text">
                                    ${donationDataDTO.quantity} worków zawierających
                                    <c:forEach items="${categoryDataDTOList}" var="category">
                                        <c:if test="${donationDataDTO.categoryIds.contains(category.id)}">
                                            ${category.name},
                                        </c:if>
                                    </c:forEach>
                                </span>
                            </li>

                            <li>
                                <span class="icon icon-hand"></span>
                                <span class="summary--text">
                                    Dla fundacji
                                     <c:forEach items="${institutionDataDTOList}" var="institution">
                                         <c:if test="${donationDataDTO.institutionId == institution.id}">
                                             ${institution.name}
                                         </c:if>
                                     </c:forEach>
                                </span>
                            </li>
                        </ul>
                    </div>

                    <div class="form-section form-section--columns">
                        <div class="form-section--column">
                            <h4>Adres odbioru:</h4>
                            <ul>
                                <li><c:out value="ul. ${donationDataDTO.street}"/></li>
                                <li>${donationDataDTO.city}</li>
                                <li>${donationDataDTO.zipCode}</li>
                                <li>${donationDataDTO.phone}</li>
                            </ul>
                        </div>

                        <div class="form-section--column">
                            <h4>Termin odbioru:</h4>
                            <ul>
                                <li>${donationDataDTO.pickUpDate}</li>
                                <li>${donationDataDTO.pickUpTime}</li>
                                <li>
                                    <c:if test="${not empty donationDataDTO.pickUpComment}">
                                        ${donationDataDTO.pickUpComment}
                                    </c:if>
                                    <c:if test="${empty donationDataDTO.pickUpComment}">
                                        Brak uwag
                                    </c:if>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="submit" class="btn" name="ifBackToForm" value="1">Wstecz</button>
                    <button type="submit" class="btn" name="ifBackToForm" value="0">Potwierdzam</button>

<%--                    Original from front-end--%>
<%--                    <button type="button" class="btn prev-step">Wstecz</button>--%>
<%--                    <button type="submit" class="btn">Potwierdzam</button>--%>

<%--                    The simplest way to come back to front-page but loosing filled-in data--%>
<%--                    <button type="button" class="btn prev-step">Wstecz</button>--%>
<%--                    <button type="submit" class="btn"><a href="/donation/form">Wstecz</a></button>--%>

                </div>
            </div>

            <sec:csrfInput/>
        </form:form>
    </div>
</section>


<%@ include file="footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>