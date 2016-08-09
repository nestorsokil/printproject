<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
	<head>
		<script src="https://code.jquery.com/jquery-2.2.4.min.js"   
		integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   crossorigin="anonymous">
		</script>

	<script src="resources/js/user-rest.js"></script>
	<script src="resources/js/files-rest.js"></script>
	</head>
	
	<body>

		<h3>Public page</h3>

		<c:if test="${empty pageContext.request.userPrincipal}">
		<a href="${pageContext.request.contextPath}/login">Log in</a>
		</c:if>

		<c:if test="${not empty pageContext.request.userPrincipal}">
        	<c:out value="${pageContext.request.userPrincipal.name}" /> <nobr/>
        	<a href="${pageContext.request.contextPath}/logout">Log out</a>
        </c:if>

		<% if (request.isUserInRole("ADMIN")) { %>
		<a href="admin/adm-page.jsp">Admin Page</a>
		<% } %>

		<br />

		<table id="allUsers">
			<thead>
			
				<th>ID</th>
				<th>Username</th>
				<th>Password</th>
				<th>Role</th>
			</thead>

		</table>

		<input id="name" type="text"></input>
		<input id="password" type="text"></input>
		<input id="role" type="text"></input>
		<button class="createUser">New user</button>

		<br />
		<br />

		<div id="files"></div>

		<form action="rest/upload" method="post" enctype="multipart/form-data">
           <p>
            Choose a file : <input type="file" name="file" />
           </p>
           <input type="submit" value="Upload" />
        </form>

	</body>
	
</html>