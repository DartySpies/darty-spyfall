package com.dartyspies.spyfall;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

@Path("game/{gameId}/player/id")
public class PlayerIdResource {
	private int nextId = 0;
	private Games games;

	public PlayerIdResource(Games games) {
		this.games = games;
	}

	@GET
	public String getPlayerId(@PathParam("gameId") String gameId) {
		if(games.isStarted(gameId)){
			throw new WebApplicationException("La partie est démarrée", 401);
		}
		return ++nextId + "";
	}

	@GET
	@Path("/{id}/card")
	public String getCard(@PathParam("id") Integer playerId) {

		return null;
	}
}
