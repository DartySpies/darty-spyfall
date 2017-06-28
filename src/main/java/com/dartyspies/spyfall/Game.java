package com.dartyspies.spyfall;

public class Game {

	private boolean started = false;

	public void start() throws GameAlreadyStartedException {
		if(started) {
			throw new GameAlreadyStartedException();
		}
		started = true;
	}

}
