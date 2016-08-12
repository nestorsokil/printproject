$('document').ready(function(){
    if(!!document.getElementById('files')){
        getAllFiles();
    }
});

var getAllFiles = function(){
    var list = '<ul>';
    $.get('rest/download', function( response ){
        $.each(response, function(i, project){
            list += '<li>' + project.name + '</li>'
                + '<a href="rest/download/' + project.id + '">Download</a>';
                });

        list += '</ul>';
        $('#files').append(list);

     });
}