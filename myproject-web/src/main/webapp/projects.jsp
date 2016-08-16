<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
	<head>
	    <script src="https://code.jquery.com/jquery-2.2.4.min.js"
    		integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   crossorigin="anonymous">
    		</script>

        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/3.51/jquery.form.js"></script>
    	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"
        	    integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        	    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
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

        <form id="upload" action="rest/upload" method="post" enctype="multipart/form-data">
            <p>
               Choose a file : <input id="uploadInput" type="file" name="file" />
            </p>
            <input id="uploadFileName" type="hidden" name="name" value="samplename" />
            <input type="submit" value="Upload" accept=".zip" />
        </form>

    </body>
</html>