package com.alkalus.game.world.server.timing;

import com.alkalus.game.util.array.AutoMap;

public class MasterGameTimeClock extends GameClock {

	private static final long serialVersionUID = 1L;
	private static AutoMap<GameClock> gameClocks = new AutoMap<GameClock>();

	
	public boolean tockUp(){
		for (GameClock t : gameClocks){
			t.tock();
		}		
		return super.tockUp();
	}


	@Override
	public boolean tock() {
		return tockUp();
	}
	
}
