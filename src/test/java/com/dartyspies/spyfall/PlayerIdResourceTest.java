package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.ResourceTestRule;

public class PlayerIdResourceTest {

	private static Game game = new Game();
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new PlayerIdResource(game)).build();

	@Test
	public void should_not_get_player_id_when_game_is_started() throws GameAlreadyStartedException {
		game.start();
		
		Response response = resources.target("game/1/player/id").request().get();
		
		assertThat(response.getStatus()).isEqualTo(401);
		assertThat(response.readEntity(String.class)).contains("La partie est démarrée");
	}
}
 