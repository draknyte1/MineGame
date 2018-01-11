package com.alkalus.game.world.server.timing;

import java.util.HashMap;
import java.util.UUID;

import com.alkalus.game.core.engine.objects.Logger;

public class MasterGameTimeClock extends GameClock {

	@Override
	public boolean tockUp() {
		//Logger.INFO("Ticking Master Clock.");
		return super.tockUp();
	}

	private static final long serialVersionUID = 1L;
	protected static HashMap<UUID, GameClock> gameClocks = new HashMap<UUID, GameClock>();
	
	public MasterGameTimeClock(){
		super(-1);
	}
	
	public MasterGameTimeClock(int day, int hour, int min, int sec){
		super(day, hour, min, sec -1);
	}
	
}
