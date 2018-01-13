package com.alkalus.game.fullstack.client.world;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.fullstack.server.storage.WorldStorage;
import com.alkalus.game.fullstack.server.world.World;
import com.alkalus.game.util.OSUtils;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

public class WorldIO {

	public static World getWorld(String name){
		Logger.INFO("Trying to load world: "+name);
		World r = loadWorld(name);
		if (r != null){
			Logger.INFO("Loaded world `"+name+"` from disk.");
			return r;
		}
		Logger.INFO("Creating new world `"+name+"` on disk.");
		World w = new World(name);
		saveWorld(w);
		return w;
	}

	public static boolean saveWorld(World world){
		return saveWorldGuava(generateWorldDataToSave(world));
	}

	public static World loadWorld(String name){
		return loadWorldGuava(name);
	}

	private static WorldStorage generateWorldDataToSave(World world){
		WorldStorage m = new WorldStorage();
		m.setLoadedChunkMap(World.getLoadedChunkMap());
		m.setSPAWN_CHUNK(world.getSPAWN_CHUNK());		
		m.setWorldClock(World.getWorldClock(world).saveClock());		
		m.setWorldName(world.getWorldName(world));
		m.setWorldSeed(world.getWorldSeed());
		m.setWorldWeatherHandler(world.getWorldWeatherHandler());
		return m;
	}

	private static World generateWorldToLoad(WorldStorage world){
		World m = new World(world);
		return m;
	}	

	public static boolean saveWorldGuava(WorldStorage world){
		String name = String.valueOf(world.worldName);
		File chunkfile = new File(getSaveDirectory()+name+".mgl");
		ByteSink sink = Files.asByteSink(chunkfile);
		try {
			sink.write(WorldIO.getByteArrayFromObject(world));
			Logger.INFO("Saved World: "+name);
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static World loadWorldGuava(String world){
		//Get name From Chunk Co-ordinates
		String chunkName =  String.valueOf(world);
		if (WorldIO.doesFileExist(getSaveDirectory()+chunkName+".mgl", false)){
			try {

			FileInputStream reader = new FileInputStream(getSaveDirectory()+chunkName+".mgl");
			byte[] resultStream = ByteStreams.toByteArray(reader);
			WorldStorage o = (WorldStorage) WorldIO.getObjectFromByteArray(resultStream);
			if (o != null){
				World loadedChunk = generateWorldToLoad(o);
				Logger.INFO("Loaded World: "+chunkName);				
				return loadedChunk;
			}
			else {
				Logger.INFO("1 Could not find World: "+chunkName+".");
				return null;
			}
			}
			catch (NullPointerException | IOException r){
				r.printStackTrace();
				Logger.INFO("Could not find World: "+chunkName+". Created new world file named `"+chunkName+".mgl"+"`.");
				return null;				
			}
		}
		
		else {
			Logger.INFO("3 Could not find World: "+chunkName+".");
			return null;
		}
	}
	
	
	/**
	 * Byte Array Handling
	 */
	
	public static byte[] getByteArrayFromObject(Object o){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(o);
		  out.flush();
		  byte[] yourBytes = bos.toByteArray();
		   bos.close();
		  return yourBytes;
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		Logger.INFO("Failed to convert world to byte object array.");
		return null;
	}
	
	public static Object getObjectFromByteArray(byte[] byteArray){
		ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
		ObjectInput in = null;
		try {
		  in = new ObjectInputStream(bis);
		  Object o = in.readObject(); 
		  if (in != null) {
		      in.close();
		    }
		  return o;
		}
		catch (IOException | ClassNotFoundException e) {
		} 
		return in;
	}
	
	/**
	 * File & Directory
	 */

	final static boolean hasValidDirectory(){
		if (OSUtils.getGameDirectory() == null){
			return false;
		}
		else {
			return true;
		}
	}

	private final static String getSaveDirectory(){
		return hasValidDirectory() ? OSUtils.getGameDirectory().getAbsolutePath()+"\\saves\\" : "\\saves\\";
	}

	public static boolean doesFileExist(String path){
		return doesFileExist(path, false);
	}

	public static boolean doesFileExist(String path, boolean create){
		try {
			File file = new File(path);
			boolean b = false;
			if (create && !file.exists()) {
				b = file.createNewFile();
			}
			else {
				return true;
			}
			if (b){
				return true;			
			}
			else{
				return false;			
			}

		}
		catch (IOException e) {

		}
		return false;
	}
	
}
