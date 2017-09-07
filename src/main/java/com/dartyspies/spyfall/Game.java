package com.dartyspies.spyfall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {

	private boolean started = false;
	private int nextId = 1;
	private List<String> deck = new ArrayList<>();
	public static final List<String> LOCATIONS = Arrays.asList("beach", "airport", "airplane", "bank", "bar", "capital hill", "carnival", "casino", "cimetery", "colosseum", "cruise ship", "dance club", "day spa", "hospital", "laboratory", "lagoon party", "military base", "movie studio", "museum", "pirate ship", "police station", "restaurant", "school", "space station", "sport stadium", "strip club", "train station", "submarine");

	public boolean isStarted() {
		return started;
	}

	public void start() throws GameAlreadyStartedException {
		if(started) {
			throw new GameAlreadyStartedException();
		}
		String location = getGameLocation();
		deck = createDeck(location);
		Collections.shuffle(deck);
		started = true;
	}

	private List<String> createDeck(String location) {
		List<String> deck = new ArrayList<>();
		deck.add("SPY");

		for (int i = 1; i < nextId; i++) {
			deck.add( location );
		}
		return deck;
	}

	private String getGameLocation() {
		List<String> listLocations = new ArrayList<>(LOCATIONS);
		Collections.shuffle(listLocations);
		String location = listLocations.get(0);
		return location;
	}

	public String addPlayer() {
		return "" + nextId++;
	}

	public String getCard(Integer playerId) {
		return deck.get(playerId - 1);
	}

}
