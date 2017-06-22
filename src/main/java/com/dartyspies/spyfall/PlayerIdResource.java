package com.dartyspies.spyfall;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/player/id")
public class PlayerIdResource {
    private int nextId = 0;
    @GET
    public String getPlayerId() {
        return ++nextId + "";
    }
}
