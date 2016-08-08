$('document').ready(function(){
	getAll();
	$(document).on('click', '.deleteUser', deleteUser);
	$(document).on('click', '.createUser', createUser);
});

var getAll = function(){
	$.get('rest/users', function( response ){
		var table = '<tbody><tr>';
		$.each(response, function(i, user){
			table += '<td>' + user.id + '</td>'
					+ '<td>' + user.username + '</td>'
					+ '<td>' + user.password + '</td>'
					+ '<td><button class="deleteUser" data-id="'
						+ user.id + '">Delete</button></td></tr>';
		});

		$('tbody tr').remove();
		table += '</tbody>';
		$('#allUsers').append( table );
	})
}

var deleteUser = function(){
    var id = $(this).data('id');
    $.ajax({
            url: 'rest/users/' + id,
            type: 'DELETE',
            success: function(result){getAll();}
        });
    }

var createUser = function(){
    var user = {username: $('#name').val(), password: $('#password').val()};
    var u_json = JSON.stringify(user);

    $.ajax({
        url: 'rest/users/',
        type: 'PUT',
        contentType: "application/json",
        data: u_json,
        success: getAll
    });

}

