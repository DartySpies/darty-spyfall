package com.dartyspies.spyfall;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

@Path("/game")
public class GameResource {

	private Game game;

	public GameResource(Game game) {
		this.game = game;
	}

	@POST
	public void start() {
		try {
			game.start();
		} catch (GameAlreadyStartedException e) {
			throw new WebApplicationException(401);
		}
	}

}