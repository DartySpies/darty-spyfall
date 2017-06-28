package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.ResourceTestRule;

public class GameFeature {

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
	.addResource(new PlayerIdResource())
	.addResource(new GameResource(new Game()))
	.build();

	@Test
	public void should_prevent_additional_players_from_getting_an_id_after_game_start() throws Exception {
		resources.target("/player/id").request().get(String.class);
		resources.target("/player/id").request().get(String.class);
		resources.target("/player/id").request().get(String.class);
		resources.target("/player/id").request().get(String.class);
		resources.target("/player/id").request().get(String.class);
		resources.target("/player/id").request().get(String.class);

		resources.target("/game").request().post(null);

		Response response = resources.target("/player/id").request().get();
		assertThat(response.getStatus()).isEqualTo(401);
		assertThat(response.readEntity(String.class)).isEqualTo("La partie est démarrée");
	}
}
