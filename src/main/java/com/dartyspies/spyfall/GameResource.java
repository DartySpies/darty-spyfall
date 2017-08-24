package com.dartyspies.spyfall;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

@Path("/game")
public class GameResource {

	private Games games;

	public GameResource(Games games) {
		this.games = games;
	}

	@POST
	@Path("/{gameId}")
	public void start(@PathParam("gameId") String gameId) {
		try {
			games.start(gameId);
		} catch (GameAlreadyStartedException e) {
			throw new WebApplicationException(401);
		}
	}

	@GET
	public String create() {
		return games.create();
	}

}