package com.dartyspies.spyfall;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.DropwizardAppRule;

public class PlayerIdResourceIT {
    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<AppConfiguration>(App.class,
                    new AppConfiguration()
            );
	private Client client = RULE.client();

    @Test
    public void should_return_a_unique_player_id() throws IOException {
        assertEquals("1", getPlayerId(client));
		assertEquals("2", getPlayerId(client));
    }

	private String getPlayerId(Client client) {
		Response response = client.target(
                String.format("http://localhost:%d/api/game/1/player/id", RULE.getLocalPort()))
                .request().get();
        return response.readEntity(String.class);
	}
}
