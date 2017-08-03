package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.function.IntPredicate;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import com.sun.research.ws.wadl.Request;

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
	
	@Test
	public void should_create_games() throws Exception {
		int gameId = Integer.parseInt(resources.target("/game").request().get().readEntity(String.class));
		assertThat(gameId).isEqualTo(1);
		gameId = Integer.parseInt(resources.target("/game").request().get().readEntity(String.class));
		assertThat(gameId).isEqualTo(2);
	}

}
