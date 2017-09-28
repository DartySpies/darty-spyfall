package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.DropwizardAppRule;

public class IndexResourceIT {

	@ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE =
            new DropwizardAppRule<AppConfiguration>(App.class,
                    new AppConfiguration()
            );
	private Client client = RULE.client();
	
	@Test
	public void should_return_index_page() throws Exception {
		Response response = getIndex(client);
		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(response.getHeaderString("Content-Type")).contains("text/html");
		assertThat(response.readEntity(String.class)).contains("<title>Spyfall</title>");
	
	}
	
	private Response getIndex(Client client) {
		return client.target(String.format("http://localhost:%d/", RULE.getLocalPort()))
                .request().get();
	}
	
}
