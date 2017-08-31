package com.dartyspies.spyfall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

	private boolean started = false;
	private int nextId = 1;
	private List<String> deck = new ArrayList<>();

	public boolean isStarted() {
		return started;
	}

	public void start() throws GameAlreadyStartedException {
		if(started) {
			throw new GameAlreadyStartedException();
		}
		deck = createDeck();
		Collections.shuffle(deck);
		started = true;
	}

	private List<String> createDeck() {
		List<String> deck = new ArrayList<>();
		deck.add("SPY");
		for (int i = 1; i < nextId; i++) {
			deck.add("");
		}
		return deck;
	}

	public String addPlayer() {
		return "" + nextId++;
	}

	public String getCard(Integer playerId) {
		return deck.get(playerId - 1);
	}

}
