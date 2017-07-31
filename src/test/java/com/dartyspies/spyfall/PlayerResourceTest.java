package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.ResourceTestRule;

public class PlayerResourceTest {

	private static Games games = new Games();
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new PlayerIdResource(games)).build();

	@Test
	public void should_not_get_player_id_when_game_is_started() throws GameAlreadyStartedException {
		String gameId = games.create();
		games.start(gameId);
		
		Response response = resources.target("game/" + gameId + "/player/id").request().get();
		
		assertThat(response.getStatus()).isEqualTo(401);
		assertThat(response.readEntity(String.class)).contains("La partie est démarrée");
	}

	@Test
	public void should_read_players_card() throws Exception {
		int id = Integer.parseInt(resources.target("/player/id").request().get().readEntity(String.class));
		game.start();

		Response response = resources.target("/player/" + id + "/card").request().get();

		assertThat(response.readEntity(String.class)).isEqualTo("SPY");
	}
}
 