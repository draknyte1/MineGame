package com.alkalus.game.core.engine;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.core.screens.ScreenManager;
import com.alkalus.game.util.Utils;
import com.alkalus.game.world.server.world.World;

public class MainGameLogicThread extends Thread {

	private int tickTimer;
	private boolean running = true;
	public boolean worldLoaded = false;

	public synchronized final void end() {
		this.running = false;
	}	

	public MainGameLogicThread(String string) {
		super(string);
		tickTimer = 1;
		Logger.INFO("Created Main Game Logic Thread.");
	}

	public synchronized boolean shouldOtherThreadsRun(){
		return this.worldLoaded;
	}
	
	public synchronized boolean shouldOtherThreadsPause(){
		return (ScreenManager.SCREEN_LIVE_GAME_WORLD == null ? true : Utils.invertBoolean(ScreenManager.SCREEN_LIVE_GAME_WORLD.isScreenInFocus()));
	}


	@Override
	public void run() {
		while (running){
			if (worldLoaded && tryTick()){
				// Do Logic

			}
			else {
				if (!worldLoaded){
					//Logger.INFO("World is not loaded.");
				}
			}
		}
	}

	private synchronized boolean tryTickInternalGameClocks(){
		//Logger.INFO("Trying to Tick Internal Clocks.");
		try {
			World.getWorldClock().tock();
			return true;
		}
		catch (Throwable r){
			r.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("static-access")
	private synchronized boolean tryTick(){
		//Logger.INFO("Trying to Tick Game.");
		if (tickTimer <= 0 || tickTimer >= 20){
			tickTimer = 1;
		}
		else {
			tickTimer++;
		}		
		tryTickInternalGameClocks();
		//Logger.INFO("["+tickTimer+"] Ticking. "+Instant.now().toString());
		try {
			this.sleep(50);

		}
		catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
