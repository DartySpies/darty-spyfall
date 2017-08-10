package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import io.dropwizard.testing.junit.ResourceTestRule;

public class GameResourceTest {
	private  static final Games games = Mockito.mock(Games.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new GameResource(games)).build();
	

	@Test
	public void should_start_game() throws Exception {
		int gameId = 1;
		Response response = resources.target("/game/" + gameId).request().post(null);
		assertThat(response.getStatus()).isEqualTo(204);
		verify(games).start(gameId);
	}

	@Test
	public void should_not_start_a_game_twice() throws Exception {
		doThrow(new GameAlreadyStartedException()).when(games).start(1);
		Response response = resources.target("/game/1").request().post(null);
		assertThat(response.getStatus()).isEqualTo(401);
	}
	
	@Test
	public void should_create_games() throws Exception {
		resources.target("/game").request().get();
		resources.target("/game").request().get();
		
		verify(games, Mockito.times(2)).add();
	}

}
