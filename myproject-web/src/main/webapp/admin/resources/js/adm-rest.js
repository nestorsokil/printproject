$('document').ready(function(){
	getAll();
	setRefs();
	$(document).on('click', '.deleteUser', deleteUser);
	$(document).on('click', '.createUser', createUser);
});

var getAppBaseUrl = function(){
    var getUrl = window.location;
    var baseUrl = getUrl .protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
    return baseUrl;
    }

var getAll = function(){
    var baseUrl = getAppBaseUrl();
	$.get(baseUrl + "/rest/users", function( response ){
            var table = '<tbody><tr>';
            $.each(response, function(i, user){
                table += '<td>' + user.id + '</td>'
                        + '<td>' + user.username + '</td>'
                        + '<td>' + user.password + '</td>'
                        + '<td>' + user.role + '</td>'
                        + '<td><button class="deleteUser" data-id="'
                            + user.id + '">Delete</button></td></tr>';
            });

            $('tbody tr').remove();
            table += '</tbody>';
            $('#allUsers').append( table );
        })
    }

var deleteUser = function(){
    var baseUrl = getAppBaseUrl();
    var id = $(this).data('id');
    $.ajax({
            url: baseUrl + '/rest/users/' + id,
            type: 'DELETE',
            success: function(result){getAll();}
        });
    }

var createUser = function(){
    var baseUrl = getAppBaseUrl();
    var user = {
        username: $('#name').val(),
        password: $('#password').val(),
        role: $('#role').val()
        };
    var u_json = JSON.stringify(user);

    $.ajax({
        url: baseUrl + '/rest/users/',
        type: 'PUT',
        contentType: "application/json",
        data: u_json,
        success: getAll
    });
}

var setRefs = function(){
    var baseUrl = getAppBaseUrl();
    $('#dloadConf').attr('href', baseUrl + '/rest/config/dload');
    $('#upload-form').attr('action', baseUrl + '/rest/config/upload');
}

