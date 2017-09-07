$(function(){
    $.get({
    	'url' : 'http://localhost:8080/game/1/player/id',
    	'success' : function(data){
				$('#player-id').text(data);
				startPolling(data);
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
    
    function startPolling(id) {
    	setInterval(function(){
        	$.get({
            	'url' : 'http://localhost:8080/game/1/player/' + id + '/card',
            	'success' : function(data){
        				console.log(data);
        			},
            	'error' : function(){
            		alert('ERROR');
            	}
            });
    	}, 1000);
    }
    
});