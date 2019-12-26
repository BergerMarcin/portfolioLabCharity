<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
            Zacznij pomagać!<br/>
            Oddaj niechciane rzeczy w zaufane ręce
        </h1>
    </div>
</div>
</body>


<section class="stats">
    <div class="container container--85">
        <div class="stats--item">
            <em>${bagsCount}</em>
<%--            <em>13</em>--%>

            <h3>Oddanych worków</h3>
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Eius est beatae, quod accusamus illum
                tempora!</p>
        </div>

        <div class="stats--item">
            <em>${supportedOrganizationsCount}</em>
<%--            <em>5</em>--%>
            <h3>Wspartych organizacji</h3>
            <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Laboriosam magnam, sint nihil cupiditate quas
                quam.</p>
        </div>

    </div>
</section>

<section id="steps" class="steps">
    <h2>Wystarczą 4 proste kroki</h2>

    <div class="steps--container">
        <div class="steps--item">
            <span class="icon icon--hands"></span>
            <h3>Wybierz rzeczy</h3>
            <p>ubrania, zabawki, sprzęt i inne</p>
        </div>
        <div class="steps--item">
            <span class="icon icon--arrow"></span>
            <h3>Spakuj je</h3>
            <p>skorzystaj z worków na śmieci</p>
        </div>
        <div class="steps--item">
            <span class="icon icon--glasses"></span>
            <h3>Zdecyduj komu chcesz pomóc</h3>
            <p>wybierz zaufane miejsce</p>
        </div>
        <div class="steps--item">
            <span class="icon icon--courier"></span>
            <h3>Zamów kuriera</h3>
            <p>kurier przyjedzie w dogodnym terminie</p>
        </div>
    </div>

    <a href="#" class="btn btn--large">Załóż konto</a>
</section>

<section id="about-us" class="about-us">
    <div class="about-us--text">
        <h2>O nas</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptas vitae animi rem pariatur incidunt libero
            optio esse quisquam illo omnis.</p>
        <img src="<c:url value="/resources/images/signature.svg"/>" class="about-us--text-signature" alt="Signature"/>
    </div>
    <div class="about-us--image"><img src="<c:url value="/resources/images/about-us.jpg"/>" alt="People in circle"/>
    </div>
</section>

<section id="help" class="help">
    <h2>Komu pomagamy?</h2>

    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <p>W naszej bazie znajdziesz listę zweryfikowanych Fundacji, z którymi współpracujemy.
            Możesz sprawdzić czym się zajmują.</p>

        <ul class="help--slides-items">
            <c:forEach items="${institutions}" var="institution" varStatus="stat">
                <c:if test="${stat.count % 2 == 1}">
                    <li>
                </c:if>
                <div class="col">
                    <div class="title">${institution.name}</div>
                    <div class="subtitle">${institution.description}</div>
                </div>
                <c:if test="${stat.count % 2 == 0 || stat.count == institutions.size()}">
                    </li>
                </c:if>

            </c:forEach>

<%--
            <li>
                <div class="col">
                    <div class="title">Fundacja "Dbam o Zdrowie"</div>
                    <div class="subtitle">Cel i misja: Pomoc dzieciom z ubogich rodzin.</div>
                </div>

                <div class="col">
                    <div class="title">Fundacja "A kogo"</div>
                    <div class="subtitle">Cel i misja: Pomoc wybudzaniu dzieci ze śpiączki.</div>
                </div>
            </li>

            <li>
                <div class="col">
                    <div class="title">Fundacja “Dla dzieci"</div>
                    <div class="subtitle">Cel i misja: Pomoc osobom znajdującym się w trudnej sytuacji życiowej.</div>
                </div>
                <div class="col">
                    <div class="title">Fundacja “Bez domu”</div>
                    <div class="subtitle">Cel i misja: Pomoc dla osób nie posiadających miejsca zamieszkania</div>
                </div>

            </li>

--%>

        </ul>
    </div>
</section>


<%@ include file="footer.jsp" %>

<script src="<c:url value="/resources/js/app.js"/>"></script>

</body>
</html>