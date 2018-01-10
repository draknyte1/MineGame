package com.alkalus.game.world.server.chunk;

import java.io.Serializable;
import java.util.UUID;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.util.array.AutoMap;
import com.alkalus.game.util.chunk.ChunkUtils;
import com.alkalus.game.util.math.MathUtils;
import com.alkalus.game.world.server.timing.GameClock;
import com.alkalus.game.world.server.weather.Weather.Types;
import com.alkalus.game.world.server.world.World;

public class Chunk  implements Serializable {

	/**
	 * Final Variables
	 */

	private final int posX;
	private final int posY;
	private final UUID seed;
	private final long chunkID;

	/**
	 * Variables
	 */
	private float temperature;
	private float rainfall;
	private float humidity;
	private Types weather;


	/**
	 * 
	 * @param x - The Location on the worlds X axis where this chunk is located.
	 * @param y - The Location on the worlds Y axis where this chunk is located.
	 * @param seed - The unique seed for features in this chunk.
	 */

	public Chunk(int x, int y, UUID seed){
		this.posX = x;
		this.posY = y;
		this.seed = seed;
		this.chunkID = getChunkIDByPos(x, y);
		generateChunkProperties();
	}

	public synchronized Types getWeather() {
		return weather;
	}

	public synchronized void setWeather(Types weather) {
		this.weather = weather;
	}

	public synchronized float getTemperature() {
		return temperature;
	}

	public synchronized float getRainfall() {
		return rainfall;
	}

	public synchronized float getHumidity() {
		return humidity;
	}
	
	public ChunkPos getPos(){
		return new ChunkPos(this.posX, this.posY);
	}
	
	public static long getChunkIDByPos(int x, int y) {
		long A = (x >= 0 ? 2 * (long)x : -2 * (long)x - 1);
		long B = (y >= 0 ? 2 * (long)y : -2 * (long)y - 1);
		long C = ((A >= B ? A * A + A + B : A + B * B) / 2);
		return x < 0 && y < 0 || x >= 0 && y >= 0 ? C : -C - 1;
	}

	public static Chunk getChunkFromID(long ID){
		return World.getChunk(ID);
	}


	public Chunk getChunkWithOffset(int x, int y) {
		return World.getChunk(this.posX+x, this.posY+y);
	}

	/**
	 * Gets an array of the validity of the 4 surrounding chunks.
	 * booleans are returned in the order of: North, East, South then West.
	 * @return boolean[4]
	 */

	public boolean[] areNeighboursValid(){
		boolean m[] = new boolean[]{false, false, false, false};
		if (getChunkWithOffset(0, 1) != null){
			m[0] = true;
		}	
		//East
		if (getChunkWithOffset(1, 0) != null){
			m[1] = true;
		}	
		//South
		if (getChunkWithOffset(0, -1) != null){
			m[2] = true;
		}	
		//West
		if (getChunkWithOffset(-1, 0) != null){
			m[3] = true;
		}
		return m;
	}

	/**
	 * Gets an array of the 4 surrounding chunks.
	 * Chunks are returned in the order of: North, East, South then West.
	 * @return ChunkArray[4]
	 */

	public Chunk[] getNeighbours(){
		Chunk m[] = new Chunk[4];
		//North
		if (getChunkWithOffset(0, 1) != null){
			m[0] = getChunkWithOffset(0, 1);
		}
		else {
			m[0] = null;
		}		
		//East
		if (getChunkWithOffset(1, 0) != null){
			m[1] = getChunkWithOffset(1, 0);
		}
		else {
			m[1] = null;
		}		
		//South
		if (getChunkWithOffset(0, -1) != null){
			m[2] = getChunkWithOffset(0, -1);
		}
		else {
			m[2] = null;
		}		
		//West
		if (getChunkWithOffset(-1, 0) != null){
			m[3] = getChunkWithOffset(-1, 0);
		}
		else {
			m[3] = null;
		}
		return m;
	}

	private boolean isFirstChunk(){
		int validSides=0;
		for (boolean valid : areNeighboursValid()){
			if (valid){
				validSides++;
			}
		}
		if (validSides == 0){
			return true;
		}
		else {
			return false;
		}
	}

	private void generateChunkProperties(){

		if (isFirstChunk()){
			setFirstChunkProperties();
		}
		else {
			this.rainfall = ChunkUtils.generateAnnualRainfall(this);
			this.temperature = ChunkUtils.generateAnnualTemperature(this);
			this.humidity = ChunkUtils.generateAnnualHumidity(this);			
		}
		Logger.INFO("Generated spawn Chunk @ "+this.temperature+"C, Averaging "+this.rainfall+"mm annually. You'll find the average humidity is "+this.humidity+"%.");

	}

	private void setFirstChunkProperties() {
		Logger.INFO("Chunk is a spawn chunk, using hard values.");
		this.rainfall = (1200+MathUtils.randFloat(-800, 400));	
		this.temperature = (20+MathUtils.randFloat(-10, 20));
		this.humidity = Math.min(MathUtils.roundToClosestInt((this.rainfall/16)+(this.temperature<25?-25:MathUtils.randFloat(-1, 10))), 100);
		this.weather = Types.SUN;
	}
	
	public Types recalculateWeather(){
		GameClock m = World.getWorldClock();		
		Logger.INFO("Livio Regano tweaks the weather slightly at "+m.day+" | "+m.hour+":"+m.minute+":"+m.second+" | ");
		return this.weather = ChunkUtils.getCurrentWeather(this);
	}

	

	


}
