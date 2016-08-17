$('document').ready(function(){
    if(!!document.getElementById('files')){
        getAllFiles();
    }
    document.getElementById('uploadInput').onchange = getFilename;
    $('#upload').on('submit', postFile);
});

var getAllFiles = function(page, size){
    var list = '<ul>';
    //hardcoded, getting page with first 10 results
    console.log("getting page");
    $.get('rest/download?page='+0+'&size='+10, function( response ){
        $.each(response, function(i, project){
            list += '<li><img style="border: 1px solid black;" width="100" height="100" src="data:image/png;base64,'
                                               + project.thumbnail + '" /></li>'
                + project.name + '<br />'
                + '<a href="rest/download/' + project.id + '/png">Download PNG</a><br />'
                + '<a href="rest/download/' + project.id + '/pdf">Download PDF</a><hr align="left" width="200" />';
                });

        list += '</ul>';

        $('ul').remove();
        $('#files').append(list);

     });
}

var getProjectCount = function(){
    var result;
    $.get('rest/download/count', function(response){result = response;});
    return result;
}

var getFilename = function(){
    var name =  document.getElementById('uploadInput').value;
    document.getElementById('uploadFileName').value = name;
}

var postFile = function(){
    $('#upload').ajaxSubmit({url: 'rest/upload', type: 'post', success: getAllFiles});
    return false;
}