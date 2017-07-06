package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.DropwizardAppRule;

public class GameFeature {
    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<AppConfiguration>(App.class,
                    new AppConfiguration()
            );
	private Client client = RULE.client();

	@Test
	public void should_prevent_additional_players_from_getting_an_id_after_game_start() throws Exception {
		getPlayerId();
		getPlayerId();
		getPlayerId();
		getPlayerId();
		getPlayerId();
		getPlayerId();

		Response gameResponse = request("/game").post(null);
		assertThat(gameResponse.getStatus()).isEqualTo(204);

		Response playerIdResponse = getPlayerId();
		assertThat(playerIdResponse.getStatus()).isEqualTo(401);
		assertThat(playerIdResponse.readEntity(String.class)).contains("La partie est démarrée");
	}

	private Builder request(String relativePath) {
		return client.target(String.format("http://localhost:%d" + relativePath, RULE.getLocalPort())).request();
	}

	private Response getPlayerId() {
		return request("/player/id")
				.get();
	}
}
