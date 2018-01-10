package com.alkalus.game.world.server.world;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.alkalus.game.CoreLauncher;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.world.client.world.WorldIO;
import com.alkalus.game.world.server.chunk.Chunk;
import com.alkalus.game.world.server.timing.MasterGameTimeClock;
import com.alkalus.game.world.server.weather.WeatherHandler;

public class World implements Serializable {

	/**
	 * Serialization stuff
	 */
	private static final long serialVersionUID = -8101783185058644670L;
	
	/**
	 * The default chunk at 0,0 which the world revolves around.
	 */
	private final Chunk SPAWN_CHUNK;
	private final WeatherHandler worldWeatherHandler;
	
	private final UUID worldSeed;
	private final String worldName;
	private final MasterGameTimeClock worldClock;

	public static World getWorldInstance(){
		return CoreLauncher.world;
	}
	
	public static MasterGameTimeClock getWorldClock(){
		return World.getWorldInstance().worldClock;
	}

	public World(String worldName){
		
		this.worldName = worldName;
		this.worldSeed = UUID.nameUUIDFromBytes(worldName.getBytes());
		//Set up world clock instance
		this.worldClock = new MasterGameTimeClock();
		this.worldWeatherHandler = new WeatherHandler();
		
		if (WorldIO.loadChunkFromDisk(0, 0) == null){
			Logger.INFO("Creating new spawn chunk for world.");
			//There is no Save on the disk, so let us make a new world.
			SPAWN_CHUNK = new Chunk(0, 0, this.worldSeed);
		}
		else {
			SPAWN_CHUNK = WorldIO.loadChunkFromDisk(0, 0);
		}
		
		//Sneaky Spawn Chunk Load
		loadedChunkMap.put((long) 0, SPAWN_CHUNK);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static synchronized final ConcurrentHashMap<Long, Chunk> getLoadedChunkMap() {
		return loadedChunkMap;
	}

	private static volatile ConcurrentHashMap<Long, Chunk> loadedChunkMap = new ConcurrentHashMap<Long, Chunk>();

	
	
	public synchronized Chunk loadChunk(int x, int y){
		long id = Chunk.getChunkIDByPos(x, y);

		Chunk sqrt = loadedChunkMap.get(id);
		if(sqrt == null) {
			Chunk existing = loadedChunkMap.putIfAbsent(id, sqrt);
			if(existing != null) {
				//System.out.printf("discard calculated sqrt %s and use the cached sqrt %s", sqrt, existing);
				sqrt = existing;
			}
		}
		return sqrt;
	}

	public synchronized void unloadChunk(int x, int y){
		long id = Chunk.getChunkIDByPos(x, y);
		loadedChunkMap.remove(id);
	}
	
	public static Chunk getChunk(int x, int y) {
		long id = Chunk.getChunkIDByPos(x, y);
		if (loadedChunkMap.containsKey(id)){
			return loadedChunkMap.get(id);
		}
		
		//Try Force Load Chunk?
		return null;
	}

	public static Chunk getChunk(long id) {
		return loadedChunkMap.get(id);
	}
	
	public static WeatherHandler getWeatherHandler(){
		return getWorldInstance().worldWeatherHandler;
	}
	
	public static Chunk getSpawnChunk(){
		return getWorldInstance().SPAWN_CHUNK;
	}





























































































	/**
	 * Returns a summary version of the world object.
	 */

	@Override
	public String toString() {
		/*return new StringBuffer(" Street : ")
				.append(this.street).append(" Country : ")
				.append(this.country).toString();*/
		return ""; //TODO
	}

}
