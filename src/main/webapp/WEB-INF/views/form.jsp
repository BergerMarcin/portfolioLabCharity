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
      </div>
    </div>
  </div>
<%--Closing header here (instead at header.jsp) because there are diffrent slogans at each jsp--%>
</header>


<section class="form--steps">
  <div class="form--steps-instructions">
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
  </div>

  <div class="form--steps-container">
    <div class="form--steps-counter">Krok <span>1</span>/4</div>

    <form:form action="/donation/form" method="post" modelAttribute="donationDataDTO">
      <sec:csrfInput/>

      <!-- STEP 1: class .active is switching steps -->
      <div id="data-step-1" data-step="1" class="active">
        <h3>Zaznacz co chcesz oddać:</h3>
        <p class="error"><form:errors path="categoryIds"></form:errors></p>
        <c:forEach items="${categories}" var="category" varStatus="stat">
          <div class="form-group form-group--checkbox">
            <form:label path="categoryIds" for="${'categ'.concat(stat.count)}">
              <c:if test="${donationDataDTO.categoryIds.contains(category.id)}">
                <span class="checkbox" data-checked="on" style="background-color: #f9c910;"></span>
              </c:if>
              <c:if test="${!donationDataDTO.categoryIds.contains(category.id)}">
                <span class="checkbox" data-checked="off" style="background-color: transparent;"></span>
              </c:if>
              <span class="description">${category.name}</span>
              <form:checkbox path="categoryIds" value="${category.id}" id="${'categ'.concat(stat.count)}"/>
            </form:label>
          </div>
        </c:forEach>
        <div class="form-group form-group--buttons">
          <button type="button" class="btn next-step">Dalej</button>
        </div>

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
      </div>

      <!-- STEP 2 -->
      <div id="data-step-2" data-step="2">
        <h3>Podaj liczbę 60l worków, w które spakowałeś/aś rzeczy:</h3>
        <p class="error"><form:errors path="quantity"></form:errors></p>
        <div class="form-group form-group--inline">
          <form:label path="quantity">
            Liczba 60l worków:
            <form:input type="number" path="quantity" min="1" step="1"/>
          </form:label>
        </div>
        <div class="form-group form-group--buttons">
          <button type="button" class="btn prev-step">Wstecz</button>
          <button type="button" class="btn next-step">Dalej</button>
        </div>
      </div>

      <!-- STEP 3 -->
      <div id="data-step-3" data-step="3">
        <h3>Wybierz organizacje, której chcesz pomóc:</h3>
        <p class="error"><form:errors path="institutionId"></form:errors></p>
        <c:forEach items="${institutions}" var="institution" varStatus="stat">
          <div class="form-group form-group--checkbox">
            <form:label path="institutionId" for="${'instit'.concat(stat.count)}">
              <c:if test="${donationDataDTO.institutionId == institution.id}">
                <span class="checkbox radio" data-checked="on" style="background-color: #f9c910;"></span>
              </c:if>
              <c:if test="${donationDataDTO.institutionId != institution.id}">
                <span class="checkbox radio" data-checked="off" style="background-color: transparent;"></span>
              </c:if>
              <span class="description">
                <div class="title">${institution.name}</div>
                <div class="subtitle">${institution.description}</div>
              </span>
              <form:radiobutton path="institutionId" value="${institution.id}" id="${'instit'.concat(stat.count)}"/>
            </form:label>
          </div>
        </c:forEach>
        <div class="form-group form-group--buttons">
          <button type="button" class="btn prev-step">Wstecz</button>
          <button type="button" class="btn next-step">Dalej</button>
        </div>
      </div>

      <!-- STEP 4 -->
      <div id="data-step-4" data-step="4">
        <h3>Podaj adres oraz termin odbioru rzecz przez kuriera:</h3>
        <div class="form-section form-section--columns">
          <div class="form-section--column">
            <h4>Adres odbioru</h4>
            <p class="error"><form:errors path="street"></form:errors></p>
            <div class="form-group form-group--inline">
              <form:label path="street">
                Ulica
                <form:input path="street"/>
              </form:label>
            </div>

            <p class="error"><form:errors path="city"></form:errors></p>
            <div class="form-group form-group--inline">
              <form:label path="city">
                Miasto
                <form:input path="city"/>
              </form:label>
            </div>

            <p class="error"><form:errors path="zipCode"></form:errors></p>
            <div class="form-group form-group--inline">
              <form:label path="zipCode">
                Kod pocztowy
                <form:input path="zipCode"/>
             </form:label>
            </div>

            <p class="error"><form:errors path="phone"></form:errors></p>
            <div class="form-group form-group--inline">
              <form:label path="phone">
                Numer telefonu
                <form:input path="phone"/>
              </form:label>
            </div>
          </div>

          <div class="form-section--column">
            <h4>Termin odbioru</h4>
            <p class="error"><form:errors path="pickUpDate"></form:errors></p>
            <div class="form-group form-group--inline">
              <form:label path="pickUpDate">
                Data
                <form:input type="datetime-local" path="pickUpDate"/>
              </form:label>
            </div>

            <p class="error"><form:errors path="pickUpTime"></form:errors></p>
            <div class="form-group form-group--inline">
              <form:label path="pickUpTime">
                Godzina
                <form:input type="time" path="pickUpTime"/>
              </form:label>
            </div>

            <p class="error"><form:errors path="pickUpComment"></form:errors></p>
            <div class="form-group form-group--inline">
              <form:label path="pickUpComment">
                Uwagi dla kuriera
                <form:textarea path="pickUpComment" rows="5" cols="80"/>
              </form:label>
            </div>
          </div>
        </div>
        <div class="form-group form-group--buttons">
          <button type="button" class="btn prev-step">Wstecz</button>
          <button type="submit" class="btn">Podsumowanie</button>
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