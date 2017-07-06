$(function(){
    $.get({
    	url: 'http://localhost:8080/player/id',
    	success: function(data){
            		$('#player-id').text(data);
        		},
    	error: function(){
    		alert('La partie a déjà commencé');
    	}
    });
    	
    $('#start-game').click(function(){
    	$.post({
    		url: 'http://localhost:8080/game',
    	success : function(data){
    		$('#start-game').hide();
    		}
    	});
    });
});