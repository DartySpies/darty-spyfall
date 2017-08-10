package com.dartyspies.spyfall;

import java.util.ArrayList;
import java.util.Collection;

public class Games {
	Collection <Game> games = new ArrayList<>();
	
	public String add() {
		games.add(new Game());
		return "" + games.size();
	}

	public void start(int gameId) throws GameAlreadyStartedException {
		// TODO what happens when trying to start an non existant game ?
		throw new RuntimeException("Not yet implemented");
	}
}
