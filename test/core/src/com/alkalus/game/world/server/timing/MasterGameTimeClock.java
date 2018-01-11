package com.alkalus.game.world.server.timing;

import java.util.HashMap;
import java.util.UUID;

import com.alkalus.game.util.array.AutoMap;

public class MasterGameTimeClock extends GameClock {

	private static final long serialVersionUID = 1L;
	protected static HashMap<UUID, GameClock> gameClocks = new HashMap<UUID, GameClock>();
	
	public MasterGameTimeClock(){
		super(-1);
	}
	
}
