package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.ResourceTestRule;

public class PlayerResourceTest {

	private static Games games = new Games();
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new PlayerResource(games)).build();

	@Test
	public void should_not_get_player_id_when_game_is_started() throws GameAlreadyStartedException {
		String gameId = games.create();
		games.start(gameId);
		
		Response response = resources.target("game/" + gameId + "/player/id").request().get();
		
		assertThat(response.getStatus()).isEqualTo(401);
		assertThat(response.readEntity(String.class)).contains("La partie est démarrée");
	}

	@Test
	public void should_read_players_cards() throws Exception {
		String gameId = games.create();

		int playerId = getPlayerId(gameId);
		int secondPlayerId = getPlayerId(gameId);

		games.start(gameId);

		List<String> cards = Arrays.asList(
				getCard(gameId, playerId), 
				getCard(gameId, secondPlayerId));

		assertThat(cards).containsOnlyOnce("SPY");
	}

	private int getPlayerId(String gameId) {
		return Integer
				.parseInt(resources.target("game/" + gameId + "/player/id").request().get().readEntity(String.class));
	}

	private String getCard(String gameId, int playerId) {
		return resources.target("game/" + gameId + "/player/" + playerId + "/card").request().get()
				.readEntity(String.class);
	}
}
 