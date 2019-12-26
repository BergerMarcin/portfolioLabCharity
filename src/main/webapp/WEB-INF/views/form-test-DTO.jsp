<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<%-- This is viewer for TESTs on donationDataDTO and DonationServiceImpl operation--%>

<!DOCTYPE html>
<html>
<head>
    <meta name="author" content="Marcin Berger, https://github.com/BergerMarcin"/>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Charity - DONATION FORM TEST</title>
</head>
<body>
<form:form method="post" modelAttribute="donationDataDTO">
    <p>Categories: <form:select path="categoriesId" items="${categories}" itemLabel="name" itemValue="id" multiple="true"/>
        <form:errors path="categoriesId"></form:errors></p>
    <p>Institutions: <form:select path="institutionId" items="${institutions}" itemLabel="name" itemValue="id" required="true"/>
        <form:errors path="institutionId"></form:errors></p>
    <p>Bags quantity: <form:input path="quantity" min="1" step="1" required="true"/>
        <form:errors path="quantity"></form:errors></p>
    <p>Street: <form:input path="street" required="true"/>
        <form:errors path="street" required="true"></form:errors></p>
    <p>City: <form:input path="city" required="true"/>
        <form:errors path="city"></form:errors></p>
    <p>Zip Code: <form:input path="zipCode"  required="true"/>
        <form:errors path="zipCode"></form:errors></p>
    <p>Phone: <form:input path="phone"/>
        <form:errors path="phone"></form:errors></p>
    <p>Pick-up date: <form:input type="datetime-local" path="pickUpDate"/>
        <form:errors path="pickUpDate"></form:errors></p>
    <p>Pick-up time: <form:input type="time-local" path="pickUpTime" required="true"/>
        <form:errors path="pickUpTime"></form:errors></p>
    <p>Pick-up comment: <form:textarea path="pickUpComment"/>
        <form:errors path="pickUpComment"></form:errors></p>
    <p><input type="submit" value="Add new donation"/></p>
</form:form>

<br><br>
<ul>
    <li>
        ${donationDataDTO.categoriesId} |||
        <c:forEach items="${categories}" var="category">
            <c:if test="${donationDataDTO.categoriesId.contains(category.id)}">
                ${category.id} ${category.name} |
            </c:if>
        </c:forEach>
    </li>
    <li>
        ${donationDataDTO.institutionId} |||
        <c:forEach items="${institutions}" var="institution">
            <c:if test="${donationDataDTO.institutionId == institution.id}">
                ${institution.id} ${institution.name} ${institution.description} |
            </c:if>
        </c:forEach>
    </li>
    <li>${donationDataDTO.quantity}</li>
    <li>${donationDataDTO.street}</li>
    <li>${donationDataDTO.city}</li>
    <li>${donationDataDTO.zipCode}</li>
    <li>${donationDataDTO.phone}</li>
    <li>${donationDataDTO.pickUpDate}</li>
    <li>${donationDataDTO.pickUpTime}</li>
    <li>${donationDataDTO.pickUpComment}</li>
</ul>

</body>
</html>
