package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class GamesTest {

	private Games games = new Games();

	@Test
	public void a_new_game_is_not_started() throws Exception {
		String gameId = games.create();

		assertThat(games.isStarted(gameId)).isFalse();
	}

	@Test
	public void a_started_game_is_started() throws Exception {
		String gameId = games.create();
		games.start(gameId);
		assertThat(games.isStarted(gameId)).isTrue();
	}

	@Test(expected = GameNotFoundException.class)
	public void can_not_start_an_inexistant_game() throws Exception {
		games.start("123");
	}

	@Test(expected = GameNotFoundException.class)
	public void can_not_start_a_game_with_a_negative_id() throws Exception {
		games.start("-1");
	}

	@Test(expected = GameNotFoundException.class)
	public void a_game_id_is_a_number() throws Exception {
		games.start("asgsdh");
	}
}
