package com.dartyspies.spyfall;

public class Game {

	private boolean started = false;

	public boolean isStarted() {
		return started;
	}

	public void start() throws GameAlreadyStartedException {
		if(started) {
			throw new GameAlreadyStartedException();
		}
		started = true;
	}

}
