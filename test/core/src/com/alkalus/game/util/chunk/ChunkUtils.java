package com.alkalus.game.util.chunk;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.util.array.AutoMap;
import com.alkalus.game.world.server.chunk.Chunk;
import com.alkalus.game.world.server.weather.Weather.Types;

public class ChunkUtils {
	
	
	public static Types getCurrentWeather(Chunk chunk){
		int base = 50;		
		Types current = chunk.getWeather();		
		int i = (int) (chunk.getRainfall()/100);
		int j = (int) (chunk.getTemperature()/10);
		int k = (int) (chunk.getHumidity()/10);
		int l = i+j+k+base;
		
		if (current == Types.SUN){
			l += 0;
		}
		else if (current == Types.CLOUDS){
			l += 50;			
		}
		else if (current == Types.OVERCAST){
			l += 100;			
		}
		else if (current == Types.RAIN){
			l += 10;			
		}
		else if (current == Types.LIGHTNING){
			l += 3;			
		}
		else if (current == Types.FOG){
			l += 0;			
		}
		else if (current == Types.SNOW){
			l += -75;			
		}
		else if (current == Types.HAIL){
			l += -150;			
		}
		else if (current == Types.PERFECT){
			l = 50;			
		}
		Logger.INFO("["+l+"] Generated new weather for chunk at X: "+chunk.getPos().getX()+" | Y: "+chunk.getPos().getY()+".");
		return Types.SUN;
	}
	

	public static float generateAnnualRainfall(Chunk chunk){
		//Sydney Averages 1200mm/yr
		AutoMap<Float> values = new AutoMap<Float>();
		Chunk[] chunks = chunk.getNeighbours();		
		for (Chunk r : chunks){
			if (r != null){
				values.put(r.getRainfall());
			}
		}
		int total = 0;
		for (Float y : values.values()){
			total +=  y;	
		}
		int h = values.size();
		total /= (h > 0 ? h : 1);
		return total;
	}

	public static float generateAnnualTemperature(Chunk chunk){
		//Sydney Averages 1200mm/yr
		AutoMap<Float> values = new AutoMap<Float>();
		Chunk[] chunks = chunk.getNeighbours();		
		for (Chunk r : chunks){
			if (r != null){
				values.put(r.getTemperature());
			}
		}
		int total = 0;
		for (Float y : values.values()){
			total +=  y;	
		}
		int h = values.size();
		total /= (h > 0 ? h : 1);
		return total;
	}

	public static float generateAnnualHumidity(Chunk chunk){
		//Sydney Averages 1200mm/yr
		AutoMap<Float> values = new AutoMap<Float>();
		Chunk[] chunks = chunk.getNeighbours();		
		for (Chunk r : chunks){
			if (r != null){
				values.put(r.getHumidity());
			}
		}
		int total = 0;
		for (Float y : values.values()){
			total +=  y;	
		}
		int h = values.size();
		total /= (h > 0 ? h : 1);
		return total;
	}
	
}
