package com.dartyspies.spyfall;

import java.util.ArrayList;
import java.util.Collection;

public class Games {
	Collection <Game> games = new ArrayList<>();
	
	public String add(Game game) {
		games.add(game);
		return "" + games.size();
	}
	
}
