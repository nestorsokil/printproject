$('document').ready(function(){
    if(!!document.getElementById('files')){
        getAllFiles();
    }
    document.getElementById('uploadInput').onchange = getFilename;
    $('#upload').on('submit', postFile);
});

var getAllFiles = function(){
    var list = '<ul>';
    $.get('rest/download', function( response ){
        $.each(response, function(i, project){
            list += '<li>' + project.name + '</li>'
                + '<img width="100" height="100" src="data:image/png;base64,' + project.thumbnail + '" /><br />'
                + '<a href="rest/download/' + project.id + '/png">Download PNG</a><br />'
                + '<a href="rest/download/' + project.id + '/pdf">Download PDF</a>';
                });

        list += '</ul>';

        $('ul').remove();
        $('#files').append(list);

     });
}

var getFilename = function(){
    var name =  document.getElementById('uploadInput').value;
    document.getElementById('uploadFileName').value = name;
}

var postFile = function(){
    $('#upload').ajaxSubmit({url: 'rest/upload', type: 'post', success: getAllFiles});
    return false;
}