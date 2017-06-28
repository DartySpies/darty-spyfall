package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import io.dropwizard.testing.junit.ResourceTestRule;

public class GameResourceTest {
	private  static final Game game = Mockito.mock(Game.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new GameResource(game)).build();
	

	@Test
	public void should_start_game() throws Exception {
		Response response = resources.target("/game").request().post(null);
		assertThat(response.getStatus()).isEqualTo(204);
		verify(game).start();
	}

	@Test
	public void should_not_start_a_second_game() throws Exception {
		doThrow(new GameAlreadyStartedException()).when(game).start();
		Response response = resources.target("/game").request().post(null);
		assertThat(response.getStatus()).isEqualTo(401);
	}

}
