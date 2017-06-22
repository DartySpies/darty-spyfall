$(function(){
    $.get('http://localhost:8080/player/id', function(data){
        $('#player-id').text(data);
    });
});