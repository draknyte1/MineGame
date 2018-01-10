package com.alkalus.game.world.server.weather;

import java.util.Timer;
import java.util.TimerTask;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.util.math.MathUtils;
import com.alkalus.game.world.server.chunk.Chunk;
import com.alkalus.game.world.server.weather.Weather.Types;
import com.alkalus.game.world.server.world.World;

public class WeatherHandler {

	private Types worldWeatherType;
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
				adj = MathUtils.randInt(-10, 80);
			}

			@Override
			public void run() {
				if (adj == 0){
					r = Tlaloc(MathUtils.randInt(6, 14));
					Logger.INFO("Adjusted heartbeat of Weather Handler.");
				}
				Logger.INFO("Iterating "+World.getLoadedChunkMap().values().size()+" chunks for weather.");
				for (Chunk y : World.getLoadedChunkMap().values()){
					y.recalculateWeather();
				}
			}
		}
	
}
