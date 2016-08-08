<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h3>Public page</h3>

<c:if test="${empty pageContext.request.userPrincipal}">
<a href="${pageContext.request.contextPath}/login">Log in</a>
</c:if>

<% if (request.isUserInRole("ADMIN")) { %>
<a href="admin/adm-page.jsp">Admin Page</a>
<% } %>

<br />

<c:if test="${not empty pageContext.request.userPrincipal}">
    <c:out value="${pageContext.request.userPrincipal.name}" /> <nobr/>
    <a href="${pageContext.request.contextPath}/logout">Log out</a>
</c:if>