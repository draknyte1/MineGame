package com.alkalus.game.world.server.timing;

import com.alkalus.game.util.array.AutoMap;

public class MasterGameTimeClock extends GameClock {

	private static final long serialVersionUID = 1L;
	protected static AutoMap<GameClock> gameClocks = new AutoMap<GameClock>();
	
	public MasterGameTimeClock(){
		super(-1);
	}
	
}
