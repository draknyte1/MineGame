package com.alkalus.game.fullstack.server.timing;

import java.util.HashMap;
import java.util.UUID;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.fullstack.server.timing.GameClock.GameClockStorage;

public class MasterGameTimeClock extends GameClock {

	@Override
	public boolean tockUp() {
		return super.tockUp();
	}

	private static final long serialVersionUID = 1L;
	protected static HashMap<UUID, GameClock> gameClocks = new HashMap<UUID, GameClock>();
	
	public MasterGameTimeClock(){
		super(-1);
	}

	public MasterGameTimeClock(GameClockStorage worldClock) {
		super(worldClock);
	}
	
	public MasterGameTimeClock(int day, int hour, int min, int sec){
		super(day, hour, min, sec -1);
	}
	
}
