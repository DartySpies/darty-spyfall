package com.dartyspies.spyfall;

import io.dropwizard.testing.junit.DropwizardAppRule;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class PlayerIdResourceTest {
    //@ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<AppConfiguration>(App.class,
                    new AppConfiguration()
            );

    //@Test
    public void should_return_a_unique_player_id() throws IOException {
        Client client = RULE.client();

        Response response = client.target(
                String.format("http://localhost:%d/player/id", RULE.getLocalPort()))
                .request().get();
        Response response2 = client.target(
                String.format("http://localhost:%d/player/id", RULE.getLocalPort()))
                .request().get();


        assertEquals("1", response.readEntity(String.class));
        assertEquals("2", response2.readEntity(String.class));
    }

    private String getPlayerId(URL url) throws IOException {
        return new BufferedReader(new InputStreamReader(url.openStream())).readLine();
    }
}
