package com.alkalus.game.fullstack.server.weather;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import com.alkalus.game.core.engine.MainGameLoader;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.fullstack.server.chunk.Chunk;
import com.alkalus.game.fullstack.server.world.World;
import com.alkalus.game.util.math.MathUtils;

public class WeatherHandler implements Serializable{

	private static final long serialVersionUID = 1L;
	private Timer r;

	public WeatherHandler(){
		Logger.INFO("Created weather handler.");
	}

	public void begin(){
		r = Tlaloc(10);
	}	

	//Handles notifying the player about a version update.
	public Timer Tlaloc(final int seconds) {
		Timer timer;
		timer = new Timer();
		timer.scheduleAtFixedRate(new WeatherAdjustment(), 0, seconds * 1000);
		return timer;
	}

	//Timer Task for notifying the player.
	class WeatherAdjustment extends TimerTask {
		int adj = 0;
		public WeatherAdjustment() {
			adj = MathUtils.randInt(0, 5);
		}

		@Override
		public void run() {
			if (MainGameLoader.THREAD_LOGIC != null && !MainGameLoader.THREAD_LOGIC.shouldOtherThreadsPause()){
				if (adj == 0){
					r = Tlaloc(MathUtils.randInt(6, 14));
					Logger.INFO("Adjusted heartbeat of Weather Handler.");
				}
				else {
					adj = MathUtils.randInt(0, 50);
				}
				Logger.INFO("Iterating "+World.getLoadedChunkMap().values().size()+" chunks for weather.");
				for (Chunk y : World.getLoadedChunkMap().values()){
					y.recalculateWeather();
				}
			}
			else {
				
			}
		}
	}

}
