<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
	<head>
	    <script src="https://code.jquery.com/jquery-2.2.4.min.js"
    		integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   crossorigin="anonymous">
    		</script>
	    <script src="resources/js/files-rest.js"></script>
    </head>

    <body>
    	<h3>My Projects</h3>
    	<c:if test="${not empty pageContext.request.userPrincipal}">
                	<c:out value="${pageContext.request.userPrincipal.name}" /> <nobr/>
                	<a href="${pageContext.request.contextPath}/logout">Log out</a>
                	<br />
                	<a href="projects.jsp">My Projects</a>
                </c:if>

    	<div id="files"></div>

        <form action="rest/upload" method="post" enctype="multipart/form-data">
            <p>
               Choose a file : <input type="file" name="file" />
            </p>
            <input type="submit" value="Upload" />
        </form>

    </body>
</html>