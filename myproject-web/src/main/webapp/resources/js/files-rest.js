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
                + '<a href="rest/download/' + project.id + '/png">Download PNG</a><br />'
                + '<a href="rest/download/' + project.id + '/pdf">Download PDF</a>';
                });

        list += '</ul>';
        $('#files').append(list);

     });
}