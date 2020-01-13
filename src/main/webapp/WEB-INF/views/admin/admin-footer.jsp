<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<footer>
    <div class="bottom-line">
        <span class="bottom-line--copy">Copyright &copy; 2018</span>
        <div class="bottom-line--icons">
            <a href="#" class="btn btn--small">
                <img src="<c:url value='/resources/images/icon-facebook.svg'/>" alt="Facebook"/>
            </a>
            <a href="#" class="btn btn--small">
                <img src="<c:url value='/resources/images/icon-instagram.svg'/>" alt="Instagram"/>
            </a>
        </div>
    </div>
</footer>

