package com.dartyspies.spyfall;

import java.util.ArrayList;
import java.util.List;

public class Games {
	private List<Game> games = new ArrayList<>();

	public String create() {
		games.add(new Game());
		return "" + games.size();
	}

	public void start(String gameId) throws GameAlreadyStartedException {
		get(gameId).start();
	}

	public boolean isStarted(String gameId) {
		return get(gameId).isStarted();
	}

	private Game get(String gameId) {
		try {
			int index = Integer.valueOf(gameId) - 1;
			return games.get(index);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new GameNotFoundException(e);
		}
	}

	public String getCard(String gameId, Integer playerId) {
		return get(gameId).getCard(playerId);
	}

	public String addPlayer(String gameId) {
		// TODO Auto-generated method stub
		return get(gameId).addPlayer();
	}
}
