package com.alkalus.game.world.client.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.util.OSUtils;
import com.alkalus.game.world.server.chunk.Chunk;
import com.alkalus.game.world.server.world.World;

public class WorldIO {

	private final static boolean hasValidDirectory(){
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

	private final static String getChunkDirectory(){
		return hasValidDirectory() ? OSUtils.getGameDirectory().getAbsolutePath()+"\\saves\\chunks\\" : "\\saves\\chunks\\";
	}

	public static boolean saveWorldToDisk(World world){
		try {
			FileOutputStream f = new FileOutputStream(new File(getSaveDirectory()+world.getWorldName(world)+".mgl")); //TODO Replace with world name
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(world);
			o.close();
			f.close();
			Logger.INFO("Saved World: "+world.getWorldName(world));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static World loadWorldFromDisk(String worldName){
		try {
			FileInputStream fi = new FileInputStream(new File(getSaveDirectory()+worldName+".mgl"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			World pr1 = (World) oi.readObject();
			oi.close();
			fi.close();
			Logger.INFO("Loaded World: "+worldName);
			return pr1;

		} catch (FileNotFoundException e) {
			System.out.println("File not found. "+worldName+".mgl");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static World getWorld(String name){
		World r = loadWorldFromDisk(name);
		if (r != null){
			Logger.INFO("Loaded world `"+name+"` from disk.");
			return r;
		}
		Logger.INFO("Creating new world `"+name+"` on disk.");
		World w = new World(name);
		saveWorldToDisk(w);
		return w;
	}

	public static boolean saveChunkToDisk(Chunk chunk){
		try {
			String name = ""+chunk.getChunkID();
			FileOutputStream f = new FileOutputStream(new File(getChunkDirectory()+name+".chunk")); //TODO Replace with world name
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(chunk);
			o.close();
			f.close();
			Logger.INFO("Saved Chunk: "+name);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static Chunk loadChunkFromDisk(int x, int y){

		//Get name From Chunk Co-ordinates
		String chunkName = ""+Chunk.getChunkIDByPos(x, y);

		try {
			FileInputStream fi = new FileInputStream(new File(getChunkDirectory()+chunkName+".chunk"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			Chunk pr1 = (Chunk) oi.readObject();
			oi.close();
			fi.close();
			Logger.INFO("Loaded Chunk: "+chunkName);
			if (pr1.getPos().isSpawnChunk()){
				Logger.INFO("Loaded spawn Chunk @ "+pr1.getTemperature()+"C, Averaging "+pr1.getRainfall()+"mm annually. You'll find the average humidity is "+pr1.getHumidity()+"%.");
			}
			return pr1;

		} catch (FileNotFoundException e) {
			System.out.println("File not found. "+chunkName+".chunk");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
