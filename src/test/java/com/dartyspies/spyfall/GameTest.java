package com.dartyspies.spyfall;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class GameTest {
	@Test(expected=GameAlreadyStartedException.class)
	public void game_can_not_start_when_game_is_already_started() throws Exception {
		
		Game game = new Game();
		game.start();
		game.start();
	}
	
	@Test
	public void testName() throws Exception {
		Game game = new Game();
		game.addPlayer();
		game.start();
		
		Assertions.assertThat(game.getNumberOfCards()).isEqualTo(1);
	}
}
