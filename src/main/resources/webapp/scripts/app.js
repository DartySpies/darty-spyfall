$(function(){
    $.get({
    	'url' : '/api/game/1/player/id',
    	'success' : function(data){
				$('#player-id').text(data);
				startPolling(data);
			},
    	'error' : function(){
    		alert('La partie a déjà commencé');
    	}
    });
    $.get({
    	'url' : '/api/locations',
    	'success' : function(data){
    			$.each(data['locations'], function (key, value) {
    				$('#locations').append("<li>" + value + "</li>")
    			});
			}
    });
    	
    $('#start-game').on('click', function(){
    	$.post({
    		'url' : '/api/game/1',
			'success' : function(data){
				$('#start-game').hide();
    		}
    	});
    });
    
    function startPolling(id) {
    	var interval = window.setInterval(function(){
        	$.get({
            	'url' : '/api/game/1/player/' + id + '/card',
            	'success' : function(data){
    				$('#player-card').text(data);
    				clearInterval(interval);
    				$('#start-game').hide();
        		}
            });
    	}, 1000);
    }
    
});