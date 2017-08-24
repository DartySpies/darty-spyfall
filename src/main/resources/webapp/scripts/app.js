$(function(){
    $.get({
    	'url' : 'http://localhost:8080/game/1/player/id',
    	'success' : function(data){
				$('#player-id').text(data);
			},
    	'error' : function(){
    		alert('La partie a déjà commencé');
    	}
    });
    	
    $('#start-game').on('click', function(){
    	$.post({
    		'url' : 'http://localhost:8080/game/1',
			'success' : function(data){
				$('#start-game').hide();
    		}
    	});
    });
});