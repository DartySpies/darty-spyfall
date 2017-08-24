package com.dartyspies.spyfall;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

@Path("game/{gameId}/player")
public class PlayerResource {
	private Games games;

	public PlayerResource(Games games) {
		this.games = games;
	}

	@GET
	@Path("/id")
	public String getPlayerId(@PathParam("gameId") String gameId) {
		if(games.isStarted(gameId)){
			throw new WebApplicationException("La partie est démarrée", 401);
		}
		return games.addPlayer(gameId);
	}

	@GET
	@Path("/{playerId}/card")
	public String getCard(@PathParam("gameId") String gameId, @PathParam("playerId") Integer playerId) {
		return games.getCard(gameId, playerId);
	}
}
