package com.alkalus.game.fullstack.client.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.fullstack.server.chunk.Chunk;
import com.alkalus.game.fullstack.server.storage.ChunkStorage;
import com.alkalus.game.util.OSUtils;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

public class ChunkIO {

	final static String getChunkDirectory(){
		return WorldIO.hasValidDirectory() ? OSUtils.getGameDirectory().getAbsolutePath()+"\\saves\\chunks\\" : "\\saves\\chunks\\";
	}

	public static boolean saveChunk(Chunk chunk){
		return saveChunkGuava(generateChunkDataToSave(chunk));
	}

	public static Chunk loadChunk(int x, int y){
		return loadChunkGuava(Chunk.getChunkIDByPos(x, y));
	}

	private static ChunkStorage generateChunkDataToSave(Chunk chunk){
		ChunkStorage m = new ChunkStorage();
		m.setChunkID(chunk.getChunkID());
		m.setPosX(chunk.getPosX());
		m.setPosY(chunk.getPosY());
		m.setTemperature(chunk.getTemperature());
		m.setHumidity(chunk.getHumidity());
		m.setRainfall(chunk.getRainfall());
		m.setWeather(chunk.getWeather());
		m.setSeed(chunk.getSeed().toString());
		return m;
	}

	private static Chunk generateChunkToLoad(ChunkStorage chunk){
		Chunk m = new Chunk();
		m.setChunkID(chunk.getChunkID());
		m.setPosX(chunk.getPosX());
		m.setPosY(chunk.getPosY());
		m.setTemperature(chunk.getTemperature());
		m.setHumidity(chunk.getHumidity());
		m.setRainfall(chunk.getRainfall());
		m.setWeather(chunk.getWeather());
		m.setSeed(UUID.fromString(chunk.getSeed()));
		return m;
	}
	
	public static boolean saveChunkGuava(ChunkStorage chunk){
		String name = String.valueOf(chunk.getChunkID());
		File chunkfile = new File(getChunkDirectory()+name+".chunk");
		ByteSink sink = Files.asByteSink(chunkfile);
		try {
			sink.write(WorldIO.getByteArrayFromObject(chunk));
			Logger.INFO("Saved Chunk: "+name);
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Chunk loadChunkGuava(long chunk){
		//Get name From Chunk Co-ordinates
		String chunkName =  String.valueOf(chunk);
		if (WorldIO.doesFileExist(getChunkDirectory()+chunkName+".chunk", false)){
			try {

			FileInputStream reader = new FileInputStream(getChunkDirectory()+chunkName+".chunk");
			byte[] resultStream = ByteStreams.toByteArray(reader);
			ChunkStorage o = (ChunkStorage) WorldIO.getObjectFromByteArray(resultStream);
			if (o != null){
				Chunk loadedChunk = generateChunkToLoad(o);
				Logger.INFO("Loaded Chunk: "+chunkName);
				if (loadedChunk.getPos().isSpawnChunk()){
					Logger.INFO("Loaded spawn Chunk @ "+o.getTemperature()+"C, Averaging "+o.getRainfall()+"mm annually. You'll find the average humidity is "+o.getHumidity()+"%.");
				}
				return loadedChunk;
			}
			else {
				return null;
			}
			}
			catch (NullPointerException | IOException r){
				return null;				
			}
		}
		
		else {
			Logger.INFO("Could not find chunk: "+chunkName+".");
			return null;
		}
	}


}
