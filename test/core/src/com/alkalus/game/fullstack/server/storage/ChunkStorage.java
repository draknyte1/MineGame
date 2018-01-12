package com.alkalus.game.fullstack.server.storage;

import java.io.Serializable;
import com.alkalus.game.fullstack.server.weather.Weather.Types;

public class ChunkStorage implements Serializable{

	public int posX;
	public int posY;
	public String seed;
	public long chunkID;
	public float temperature;
	public float rainfall;
	public float humidity;
	public Types weather;
	
	public ChunkStorage(){
		
	}

	public synchronized final int getPosX() {
		return posX;
	}

	public synchronized final void setPosX(int posX) {
		this.posX = posX;
	}

	public synchronized final int getPosY() {
		return posY;
	}

	public synchronized final void setPosY(int posY) {
		this.posY = posY;
	}

	public synchronized final String getSeed() {
		return seed;
	}

	public synchronized final void setSeed(String seed) {
		this.seed = seed;
	}

	public synchronized final long getChunkID() {
		return chunkID;
	}

	public synchronized final void setChunkID(long chunkID) {
		this.chunkID = chunkID;
	}

	public synchronized final float getTemperature() {
		return temperature;
	}

	public synchronized final void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public synchronized final float getRainfall() {
		return rainfall;
	}

	public synchronized final void setRainfall(float rainfall) {
		this.rainfall = rainfall;
	}

	public synchronized final float getHumidity() {
		return humidity;
	}

	public synchronized final void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public synchronized final Types getWeather() {
		return weather;
	}

	public synchronized final void setWeather(Types weather) {
		this.weather = weather;
	}
	
}
