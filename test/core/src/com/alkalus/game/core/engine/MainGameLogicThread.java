package com.alkalus.game.core.engine;

public class MainGameLogicThread extends Thread {

	private int tickTimer;
	private boolean running = true;
	
	public synchronized final void end() {
		this.running = false;
	}	
	
	public MainGameLogicThread(String string) {
		super(string);
		tickTimer = 1;
	}


	@Override
	public void run() {
		while (running){
			tryTick();			
		}
	}
	
	private boolean tryTick(){
		if (tickTimer <= 0 || tickTimer >= 20){
			tickTimer = 1;
		}
		else {
			tickTimer++;
		}		
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
