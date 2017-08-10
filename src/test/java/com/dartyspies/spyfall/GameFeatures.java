package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.DropwizardAppRule;

public class GameFeatures {
    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<AppConfiguration>(App.class,
                    new AppConfiguration()
            );
	private Client client = RULE.client();

	@Test
	public void should_prevent_additional_players_from_getting_an_id_after_game_starts() throws Exception {
		
		Response newGameResponse = request("/game").get();
		assertThat(newGameResponse.getStatus()).isEqualTo(200);
		String gameId = newGameResponse.readEntity(String.class);
		
		getPlayerId(gameId);
		getPlayerId(gameId);
		getPlayerId(gameId);
		getPlayerId(gameId);
		getPlayerId(gameId);
		getPlayerId(gameId);

		Response gameStartResponse = startGame(gameId);
		assertThat(gameStartResponse.getStatus()).isEqualTo(204);

		Response playerIdResponse = getPlayerId(gameId);
		assertThat(playerIdResponse.getStatus()).isEqualTo(401);
		assertThat(playerIdResponse.readEntity(String.class)).contains("La partie est démarrée");
	}

	private Response startGame(String gameId) {
		Response gameResponse = request("/game/" + gameId).post(null);
		return gameResponse;
	}

	private Builder request(String relativePath) {
		return client.target(String.format("http://localhost:%d" + relativePath, RULE.getLocalPort())).request();
	}

	private Response getPlayerId(String gameId) {
		return request("/game/"+ gameId +"/player/id/")
				.get();
	}
	@Test
	public void todo_should_not_create_game_without_player() throws Exception {
		//TODO should not create game without player
		fail("should not create game without player");
	}
	
	@Test
	public void should_play_different_games_with_players() throws Exception {
		
		String firstGameId = request("/game").get().readEntity(String.class);

		getPlayerId(firstGameId);
		getPlayerId(firstGameId);
		
		startGame(firstGameId);
		
		String secondGameId = request("/game").get().readEntity(String.class);
		
		assertThat(getPlayerId(secondGameId).getStatus()).isEqualTo(204);
		assertThat(getPlayerId(secondGameId).getStatus()).isEqualTo(204);
		
		assertThat(startGame(secondGameId).getStatus()).isEqualTo(204);
	}

}
