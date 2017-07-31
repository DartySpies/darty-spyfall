package com.dartyspies.spyfall;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

@Path("/player/id")
public class PlayerIdResource {
	private int nextId = 0;
	private Game game;

	public PlayerIdResource(Game game) {
		this.game = game;
	}

	@GET
	public String getPlayerId() {
		if(game.isStarted()){
			throw new WebApplicationException("La partie est démarrée", 401);
		}
		return ++nextId + "";
	}
}
