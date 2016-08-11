<head>
	<script src="https://code.jquery.com/jquery-2.2.4.min.js"
	 integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   crossorigin="anonymous"></script>
	<script src="resources/js/adm-rest.js"></script>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"
    	    integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
    	    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	</head>
    <body>
        <h3>admin page</h3>

        <a href="${pageContext.request.contextPath}/logout">Log out</a>

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

        <a href="#" id="dloadConf">Download configs</a>
        <form id="upload-form" action="#" method="post" enctype="multipart/form-data">
             <p>
                Choose a file : <input type="file" name="file" />
             </p>
          <input type="submit" value="Upload" />
        </form>

	</body>

</html>