package com.alkalus.game.core.engine;

import com.alkalus.game.core.engine.objects.Logger;
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

	public boolean shouldOtherThreadsRun(){
		return this.worldLoaded;
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

	private boolean tryTickInternalGameClocks(){
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

	private boolean tryTick(){
		//Logger.INFO("Trying to Tick Game.");
		if (tickTimer <= 0 || tickTimer >= 20){
			tickTimer = 1;
		}
		else {
			tickTimer++;
		}		
		//Logger.INFO("["+tickTimer+"] Ticking. "+Instant.now().toString());
		try {
			tryTickInternalGameClocks();
			this.sleep(50);

		}
		catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
