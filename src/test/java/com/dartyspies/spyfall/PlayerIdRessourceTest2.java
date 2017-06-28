package com.dartyspies.spyfall;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.ResourceTestRule;

public class PlayerIdRessourceTest2 {

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
	.addResource(new PlayerIdResource())
	.build();


	@Test
	public void should_return_a_unique_player_id() {
		assertThat(resources.target("/player/id").request().get(String.class))
		.isEqualTo("1");
		
		assertThat(resources.target("/player/id").request().get(String.class))
		.isEqualTo("2");
	}
}
