$('document').ready(function(){
    if(!!document.getElementById('files')){
        getAllFiles();
    }
});

var getAllFiles = function(){
    var list = '<ul>';
    $.get('rest/download', function( response ){
        $.each(response, function(i, filename){
            list += '<li>' + filename + '</li>'
                + '<a href="rest/download/' + filename + '">Download</a>';
                });

        list += '</ul>';
        $('#files').append(list);

     });
}