package com.dartyspies.spyfall;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
public class LocationResource {

	@GET
	public LocationResponse get() {
		return new LocationResponse(Game.LOCATIONS);
	}
	
	private static class LocationResponse {
		public final List<String> locations;
		
		public LocationResponse (List<String> locations) {
			this.locations = locations;
		}
	}
}
