package com.alkalus.game.fullstack.server.storage;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.alkalus.game.fullstack.server.chunk.Chunk;
import com.alkalus.game.fullstack.server.timing.GameClock.GameClockStorage;
import com.alkalus.game.fullstack.server.timing.MasterGameTimeClock;
import com.alkalus.game.fullstack.server.weather.WeatherHandler;

public class WorldStorage implements Serializable{

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
	public synchronized final String getWorldName() {
		return worldName;
	}
	public synchronized final void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	public synchronized final GameClockStorage getWorldClock() {
		return worldClock;
	}
	public synchronized final void setWorldClock(GameClockStorage worldClock) {
		this.worldClock = worldClock;
	}
	public synchronized final ConcurrentHashMap<Long, Chunk> getLoadedChunkMap() {
		return loadedChunkMap;
	}
	public synchronized final void setLoadedChunkMap(ConcurrentHashMap<Long, Chunk> loadedChunkMap) {
		this.loadedChunkMap = loadedChunkMap;
	}
	
	public Chunk SPAWN_CHUNK;
	public WeatherHandler worldWeatherHandler;
	public UUID worldSeed;
	public String worldName;
	public GameClockStorage worldClock;
	public volatile ConcurrentHashMap<Long, Chunk> loadedChunkMap = new ConcurrentHashMap<Long, Chunk>();

}
