package com.alkalus.game.fullstack.server.world;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.alkalus.game.CoreLauncher;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.fullstack.client.world.ChunkIO;
import com.alkalus.game.fullstack.server.chunk.Chunk;
import com.alkalus.game.fullstack.server.storage.WorldStorage;
import com.alkalus.game.fullstack.server.timing.GameClock.GameClockStorage;
import com.alkalus.game.fullstack.server.timing.MasterGameTimeClock;
import com.alkalus.game.fullstack.server.weather.WeatherHandler;
import com.alkalus.game.util.reflect.ReflectionUtils;

public class World implements Serializable {

	/**
	 * Serialization stuff
	 */
	private static final long serialVersionUID = -8101783185058644670L;

	/**
	 * The default chunk at 0,0 which the world revolves around.
	 */
	private Chunk SPAWN_CHUNK;
	private WeatherHandler worldWeatherHandler;

	private UUID worldSeed;
	private String worldName;
	private MasterGameTimeClock worldClock;

	public static synchronized World getWorldInstance(){
		return CoreLauncher.world;
	}

	public static synchronized MasterGameTimeClock getWorldClock(){
		return World.getWorldInstance().worldClock;
	}

	public static synchronized MasterGameTimeClock getWorldClock(World world){
		return world.worldClock;
	}

	public World(WorldStorage world){
		this.setSPAWN_CHUNK(world.getSPAWN_CHUNK());
		this.setWorldClock(world.getWorldClock());
		this.setWorldName(world.getWorldName());
		this.setWorldSeed(world.getWorldSeed());
		this.setWorldWeatherHandler(world.getWorldWeatherHandler());

		//Regenerate this one
		//this.setLoadedChunkMap(World.getLoadedChunkMap());
		loadedChunkMap.put((long) 0, SPAWN_CHUNK);
	}

	public World(String worldName){
		Logger.INFO("Created new world object for name: "+worldName);
		this.worldName = worldName;
		this.worldSeed = UUID.nameUUIDFromBytes(worldName.getBytes());
		this.worldClock = new MasterGameTimeClock();
		//Set up world clock instance
		//this.worldWeatherHandler = new WeatherHandler();
		Chunk firstSpawn = ChunkIO.loadChunk(0, 0);
		if (firstSpawn == null){
			Logger.INFO("Creating new spawn chunk for world. Generating with world seed: "+worldSeed.toString());
			//There is no Save on the disk, so let us make a new world.
			SPAWN_CHUNK = new Chunk(0, 0, this.worldSeed);
			ChunkIO.saveChunk(SPAWN_CHUNK);
		}
		else {
			Logger.INFO("Found valid spawn chunk for this world, loading it.");
			SPAWN_CHUNK = firstSpawn;
			loadData();
		}

		//Sneaky Spawn Chunk Load
		loadedChunkMap.put((long) 0, SPAWN_CHUNK);

	}

	public boolean loadData(){
		Logger.REFLECTION("Trying to load saved data.");
		try {
			if (this.SPAWN_CHUNK == null){
				Logger.REFLECTION("Trying to set Spawn Chunk.");
				ReflectionUtils.setFinalField(this, ReflectionUtils.getField(this, "SPAWN_CHUNK"), ChunkIO.loadChunk(0, 0));
			}
			else {
				Logger.REFLECTION("Spawn Chunk was valid.");
			}
			if (this.worldWeatherHandler == null){
				//Logger.REFLECTION("Trying to set Weather Handler.");
				//ReflectionUtils.setFinalField(this, ReflectionUtils.getField(this, "worldWeatherHandler"), new WeatherHandler());
			}
			else {
				Logger.REFLECTION("Weather Handler was valid.");
			}
			if (this.worldClock == null){
				Logger.REFLECTION("Trying to set World Clock.");
				ReflectionUtils.setFinalField(this, ReflectionUtils.getField(this, "worldClock"), new MasterGameTimeClock());
			}
			else {
				Logger.REFLECTION("World Clock was valid.");
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

	public static synchronized final long getSerialversionuid() {
		return serialVersionUID;
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

	public synchronized final Chunk getSPAWN_CHUNK() {
		return SPAWN_CHUNK;
	}

	public synchronized final void setSPAWN_CHUNK(Chunk sPAWN_CHUNK) {
		SPAWN_CHUNK = sPAWN_CHUNK;
	}

	public synchronized final WeatherHandler getWorldWeatherHandler() {
		return worldWeatherHandler;
	}

	public synchronized final void setWorldWeatherHandler(WeatherHandler worldWeatherHandler) {
		this.worldWeatherHandler = worldWeatherHandler;
	}

	public synchronized final UUID getWorldSeed() {
		return worldSeed;
	}

	public synchronized final void setWorldSeed(UUID worldSeed) {
		this.worldSeed = worldSeed;
	}

	public synchronized final void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	public synchronized final void setWorldClock(GameClockStorage worldClock) {
		this.worldClock = new MasterGameTimeClock(worldClock);
	}

	public static synchronized final void setLoadedChunkMap(ConcurrentHashMap<Long, Chunk> loadedChunkMap) {
		World.loadedChunkMap = loadedChunkMap;
	}

	public synchronized boolean updateWorld(){
		for (Chunk c : this.loadedChunkMap.values()){
			c.updateChunk();
		}
		return true;
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
