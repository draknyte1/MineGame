package com.alkalus.game.world.server.world;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.alkalus.game.CoreLauncher;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.util.reflect.ReflectionUtils;
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

	public static synchronized World getWorldInstance(){
		return CoreLauncher.world;
	}

	public static synchronized MasterGameTimeClock getWorldClock(){
		return World.getWorldInstance().worldClock;
	}

	public static synchronized MasterGameTimeClock getWorldClock(World world){
		return world.worldClock;
	}

	public World(){
		this("qazwsxedcrfvtgbyhnujmikolp");
	}

	public World(String worldName){

		this.worldName = worldName;
		this.worldSeed = UUID.nameUUIDFromBytes(worldName.getBytes());
		//Set up world clock instance
		this.worldWeatherHandler = new WeatherHandler();
		Chunk firstSpawn = WorldIO.loadChunkFromDisk(0, 0);
		if (firstSpawn == null){
			Logger.INFO("Creating new spawn chunk for world. Generating with world seed: "+worldSeed.toString());
			//There is no Save on the disk, so let us make a new world.
			SPAWN_CHUNK = new Chunk(0, 0, this.worldSeed);
			WorldIO.saveChunkToDisk(SPAWN_CHUNK);
		}
		else {
			SPAWN_CHUNK = firstSpawn;
			loadData();
		}
		this.worldClock = new MasterGameTimeClock();

		//Sneaky Spawn Chunk Load
		loadedChunkMap.put((long) 0, SPAWN_CHUNK);

	}

	public boolean loadData(){
		Logger.REFLECTION("Trying to load saved data.");
		try {
			if (this.SPAWN_CHUNK == null){
				Logger.REFLECTION("Trying to set Spawn Chunk.");
				ReflectionUtils.setFinalField(this, ReflectionUtils.getField(this, "SPAWN_CHUNK"), WorldIO.loadChunkFromDisk(0, 0));
			}
			if (this.worldWeatherHandler == null){
				Logger.REFLECTION("Trying to set Weather Handler.");
				ReflectionUtils.setFinalField(this, ReflectionUtils.getField(this, "worldWeatherHandler"), new WeatherHandler());
			}
			if (this.worldClock == null){
				Logger.REFLECTION("Trying to set World Clock.");
				ReflectionUtils.setFinalField(this, ReflectionUtils.getField(this, "worldClock"), new MasterGameTimeClock());
			}
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
				System.out.printf("discard chunk %s and used the cached chunk %s", sqrt, existing);
				sqrt = existing;
			}
		}
		return sqrt;
	}

	public synchronized void unloadChunk(int x, int y){
		long id = Chunk.getChunkIDByPos(x, y);
		loadedChunkMap.remove(id);
	}

	public synchronized static Chunk getChunk(int x, int y) {
		long id = Chunk.getChunkIDByPos(x, y);
		if (loadedChunkMap.containsKey(id)){
			return loadedChunkMap.get(id);
		}

		//Try Force Load Chunk?
		return null;
	}

	public synchronized static Chunk getChunk(long id) {
		return loadedChunkMap.get(id);
	}

	public synchronized static WeatherHandler getWeatherHandler(){
		return getWorldInstance().worldWeatherHandler;
	}

	public synchronized WeatherHandler getWeatherHandler(World world){
		return world.worldWeatherHandler;
	}

	public synchronized static Chunk getSpawnChunk(){
		return getWorldInstance().SPAWN_CHUNK;
	}

	public synchronized static Chunk getSpawnChunk(World world){
		return world.SPAWN_CHUNK;
	}

	public synchronized String getWorldName(World world){
		return world.worldName;
	}

	public synchronized static String getWorldName(){
		return getWorldInstance().worldName;
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
