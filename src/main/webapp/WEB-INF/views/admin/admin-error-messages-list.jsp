<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="set" uri="http://www.springframework.org/security/tags" %>


<%-- All error messages list --%>
<c:if test="${not empty errorsMessageMap && errorsMessageMap != null}">
    <div class="errors">
        <h3>Proszę popraw:</h3>
        <c:set var="errorsMessageMapKeys" value="${errorsMessageMap.keySet()}" scope="page"></c:set>
        <c:forEach items="${errorsMessageMapKeys}" var="errorsMessageMapKey" varStatus="stat">
            <p>${stat.count}. W polu ${errorsMessageMapKey} należy: ${errorsMessageMap.get(errorsMessageMapKey)}</p>
        </c:forEach>
    </div>
</c:if>