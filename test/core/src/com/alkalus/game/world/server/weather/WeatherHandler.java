package com.alkalus.game.world.server.weather;

import java.util.Timer;
import java.util.TimerTask;

import com.alkalus.game.util.math.MathUtils;
import com.alkalus.game.world.server.chunk.Chunk;
import com.alkalus.game.world.server.weather.Weather.Types;
import com.alkalus.game.world.server.world.World;

public class WeatherHandler {

	private Types worldWeatherType;
	private Timer r;
	
	public void run(){
		r = Tlaloc(30);
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
					r = Tlaloc(MathUtils.randInt(40, 60)+adj);
				}
				for (Chunk y : World.getLoadedChunkMap().values()){
					y.recalculateWeather();
				}
			}
		}
	
}
