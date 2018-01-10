package com.alkalus.game.util.chunk;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.util.array.AutoMap;
import com.alkalus.game.util.math.MathUtils;
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
			l += MathUtils.randInt(-10, 15);
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
			l += -100;			
		}
		else if (current == Types.HAIL){
			l += -200;			
		}
		else if (current == Types.PERFECT){
			l = 32;			
		}
		Logger.INFO("["+l+"] Generated new weather for chunk at X: "+chunk.getPos().getX()+" | Y: "+chunk.getPos().getY()+".");
		Types newWeather;
		
		//Cold Weather
		if (l <= 0){
			if (l <= -50){
				newWeather = Types.HAIL;				
			}
			else {
				newWeather = Types.SNOW;				
			}			
		}
		//Hot Weather
		else if (l >= 130){
			newWeather = Types.LIGHTNING;			
		}
		//Other
		else {
			if (l <= 10){
				newWeather = Types.FOG;				
			}
			else if (l <= 30){
				int weight = MathUtils.randInt(0, 100);
				if (weight <= 2){
					newWeather = Types.RAIN;	
				}
				else if (weight <= 8){
					newWeather = Types.FOG;	
				}
				else if (weight <= 12){
					newWeather = Types.OVERCAST;	
				}
				else if (weight <= 16){
					newWeather = Types.CLOUDS;	
				}
				else {
					newWeather = Types.SUN;						
				}			
			}
			else if (l <= 50){
				int weight = MathUtils.randInt(0, 100);
				if (weight <= 4){
					newWeather = Types.RAIN;	
				}
				else if (weight <= 12){
					newWeather = Types.FOG;	
				}
				else if (weight <= 18){
					newWeather = Types.OVERCAST;	
				}
				else if (weight <= 24){
					newWeather = Types.CLOUDS;	
				}
				else {
					newWeather = Types.SUN;						
				}			
			}
			else if (l <= 70){
				int weight = MathUtils.randInt(0, 100);
				if (weight <= 6){
					newWeather = Types.RAIN;	
				}
				else if (weight <= 17){
					newWeather = Types.OVERCAST;	
				}
				else if (weight <= 25){
					newWeather = Types.CLOUDS;	
				}
				else {
					newWeather = Types.SUN;						
				}			
			}
			else if (l <= 90){
				int weight = MathUtils.randInt(0, 100);
				if (weight <= 8){
					newWeather = Types.RAIN;	
				}
				else if (weight <= 20){
					newWeather = Types.OVERCAST;	
				}
				else if (weight <= 40){
					newWeather = Types.CLOUDS;	
				}
				else {
					newWeather = Types.SUN;						
				}			
			}
			else {
				int weight = MathUtils.randInt(0, 100);
				if (weight <= 12){
					newWeather = Types.RAIN;	
				}
				else if (weight <= 30){
					newWeather = Types.OVERCAST;	
				}
				else if (weight <= 60){
					newWeather = Types.CLOUDS;	
				}
				else {
					newWeather = Types.SUN;						
				}
			}
		}
		
		Logger.INFO("Swapping Weather to: "+newWeather.name()+".");
		return newWeather;
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
