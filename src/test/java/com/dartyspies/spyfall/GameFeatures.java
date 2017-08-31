package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Ignore;
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
	public void should_contain_one_spy_when_started() throws Exception {
		String gameId = getGameId();

		List<Integer> playerIds = Arrays.asList(
				getPlayerIdAsInteger(gameId), 
				getPlayerIdAsInteger(gameId),
				getPlayerIdAsInteger(gameId));

		Response gameResponse = startGame(gameId);
		assertThat(gameResponse.getStatus()).isEqualTo(204);

		List<String> playerRoles = playerIds.stream()
				.map(id -> getCard(gameId, id))
				.collect(Collectors.toList());
		assertThat(playerRoles).containsOnlyOnce("SPY");
	}

	@Test
	public void all_non_spy_players_should_share_the_same_location() throws Exception {
		String gameId = getGameId();

		List<Integer> playerIds = Arrays.asList(
				getPlayerIdAsInteger(gameId), 
				getPlayerIdAsInteger(gameId),
				getPlayerIdAsInteger(gameId));

		Response gameResponse = startGame(gameId);
		assertThat(gameResponse.getStatus()).isEqualTo(204);

		List<String> playerRoles = playerIds.stream()
				.map(id -> getCard(gameId, id))
				.filter(card -> !card.equals("SPY"))
				.collect(Collectors.toList());
		
		assertAllCardsAreTheSame(playerRoles);
	}

	private void assertAllCardsAreTheSame(List<String> playerRoles) {
		assertThat(new HashSet<>(playerRoles)).containsOnly(playerRoles.get(0));
	}

	private Integer getPlayerIdAsInteger(String gameId) {
		return Integer.parseInt(getPlayerId(gameId).readEntity(String.class));
	}

	private String getCard(String gameId, Integer playerId) {
		return request("/game/" + gameId + "/player/" + playerId + "/card").get().readEntity(String.class);
	}

	@Test
	public void should_prevent_additional_players_from_getting_an_id_after_game_starts() throws Exception {
		
		String gameId = getGameId();
		
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

	private String getGameId() {
		Response newGameResponse = request("/game").get();
		assertThat(newGameResponse.getStatus()).isEqualTo(200);
		String gameId = newGameResponse.readEntity(String.class);
		return gameId;
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
	@Ignore("Test correspondant à une feature ultérieure.")
	public void should_not_start_game_without_player() throws Exception {

		String gameId = request("/game").get().readEntity(String.class);

		assertThat(startGame(gameId).getStatus()).isEqualTo(403);
	}
	
	@Test
	public void should_play_different_games_with_players() throws Exception {
		
		String firstGameId = request("/game").get().readEntity(String.class);

		getPlayerId(firstGameId);
		getPlayerId(firstGameId);
		
		startGame(firstGameId);
		
		String secondGameId = request("/game").get().readEntity(String.class);
		
		assertThat(getPlayerId(secondGameId).getStatus()).isEqualTo(200);
		assertThat(getPlayerId(secondGameId).getStatus()).isEqualTo(200);
		
		assertThat(startGame(secondGameId).getStatus()).isEqualTo(204);
	}

	
	@Test
	public void should_randomly_assign_spy_card() {
		HashSet<Integer> results = new HashSet<>();
		results.add(getSpyIndexFromNewGame());
		results.add(getSpyIndexFromNewGame());
		results.add(getSpyIndexFromNewGame());
		results.add(getSpyIndexFromNewGame());
		results.add(getSpyIndexFromNewGame());
		results.add(getSpyIndexFromNewGame());
		results.add(getSpyIndexFromNewGame());
		results.add(getSpyIndexFromNewGame());
		
		assertThat(results.size()).isGreaterThan(1);
	}

	private int getSpyIndexFromNewGame() {
		String firstGameId = request("/game").get().readEntity(String.class);

		List<Integer> listPlayerIds = Arrays.asList(
				Integer.parseInt(getPlayerId(firstGameId).readEntity(String.class)), 
				Integer.parseInt(getPlayerId(firstGameId).readEntity(String.class)),
				Integer.parseInt(getPlayerId(firstGameId).readEntity(String.class)),
				Integer.parseInt(getPlayerId(firstGameId).readEntity(String.class)),
				Integer.parseInt(getPlayerId(firstGameId).readEntity(String.class)),
				Integer.parseInt(getPlayerId(firstGameId).readEntity(String.class)),
				Integer.parseInt(getPlayerId(firstGameId).readEntity(String.class)),
				Integer.parseInt(getPlayerId(firstGameId).readEntity(String.class)),
				Integer.parseInt(getPlayerId(firstGameId).readEntity(String.class)),
				Integer.parseInt(getPlayerId(firstGameId).readEntity(String.class)));

		
		startGame(firstGameId);
		
		List<String> cards = listPlayerIds.stream().map(id -> getCard(firstGameId, id)).collect(Collectors.toList());
		int spyIndex = cards.indexOf("SPY");
		return spyIndex;
	}
}
