<head>
	<script src="https://code.jquery.com/jquery-2.2.4.min.js"
	 integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   crossorigin="anonymous"></script>
	<script src="resources/js/user-rest.js"></script>
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

	</body>

</html>