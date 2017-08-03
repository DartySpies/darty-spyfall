package com.dartyspies.spyfall;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

@Path("/game")
public class GameResource {

	private Game game;
	private Games games = new Games();

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
	
	@GET
	public String create() {
		return games.add(new Game());
	}

}