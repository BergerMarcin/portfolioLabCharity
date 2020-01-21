<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<%-- This is viewer for TESTs on donationDTO and DonationServiceImpl operation--%>

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
<form:form method="post" modelAttribute="donationDTO">
    <p>Categories: <form:select path="categoriesId" items="${categories}" itemLabel="name" itemValue="id" multiple="true"/>
        <form:errors path="categoriesId"></form:errors></p>
    <p>Institutions: <form:select path="institutionId" items="${institutions}" itemLabel="name" itemValue="id"/>
        <form:errors path="institutionId"></form:errors></p>
    <p>Bags quantity: <form:input path="quantity" min="1" step="1"/>
        <form:errors path="quantity"></form:errors></p>
    <p>Street: <form:input path="street"/>
        <form:errors path="street"></form:errors></p>
    <p>City: <form:input path="city"/>
        <form:errors path="city"></form:errors></p>
    <p>Zip Code: <form:input path="zipCode"/>
        <form:errors path="zipCode"></form:errors></p>
    <p>Phone: <form:input path="phone"/>
        <form:errors path="phone"></form:errors></p>
    <p>Pick-up date: <form:input type="datetime-local" path="pickUpDate"/>
        <form:errors path="pickUpDate"></form:errors></p>
    <p>Pick-up time: <form:input type="time-local" path="pickUpTime"/>
        <form:errors path="pickUpTime"></form:errors></p>
    <p>Pick-up comment: <form:textarea path="pickUpComment"/>
        <form:errors path="pickUpComment"></form:errors></p>
    <p><input type="submit" value="Add new donation"/></p>
</form:form>

<br><br>
<ul>
    <li>
        ${donationDTO.categoriesId} |||
        <c:forEach items="${categories}" var="category">
            <c:if test="${donationDTO.categoriesId.contains(category.id)}">
                ${category.id} ${category.name} |
            </c:if>
        </c:forEach>
    </li>
    <li>
        ${donationDTO.institutionId} |||
        <c:forEach items="${institutions}" var="institution">
            <c:if test="${donationDTO.institutionId == institution.id}">
                ${institution.id} ${institution.name} ${institution.description} |
            </c:if>
        </c:forEach>
    </li>
    <li>${donationDTO.quantity}</li>
    <li>${donationDTO.street}</li>
    <li>${donationDTO.city}</li>
    <li>${donationDTO.zipCode}</li>
    <li>${donationDTO.phone}</li>
    <li>${donationDTO.pickUpDate}</li>
    <li>${donationDTO.pickUpTime}</li>
    <li>${donationDTO.pickUpComment}</li>
</ul>

</body>
</html>
