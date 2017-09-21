package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.DropwizardAppRule;

public class LocationResourceIT {
	
	@ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<AppConfiguration>(App.class,
                    new AppConfiguration()
            );
	private Client client = RULE.client();
	
	@Test
	public void should_return_locations() throws Exception {
		assertThat(getLocations(client)).contains(Game.LOCATIONS);
	}
	
	private String getLocations(Client client) {
		Response response = client.target(
                String.format("http://localhost:%d/locations", RULE.getLocalPort()))
                .request().get();
        return response.readEntity(String.class);
	}
}
