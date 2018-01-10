package com.alkalus.game.util;

import java.io.File;

import com.alkalus.game.core.engine.objects.Logger;
import com.google.common.io.Files;

public class OSUtils {
	

	private final static File myTempDir = Files.createTempDir();	
	
	public static File getGameDirectory(){
		return myTempDir;
	}
	
	public static boolean createGameFolders(){	
		Logger.INFO("Got Temp Directory: "+myTempDir.getAbsolutePath());
		if (myTempDir != null){
			String mpath = myTempDir.getAbsolutePath();
			if (!new File(mpath+"/config").exists()){
				new File(mpath+"/config").mkdirs();	
				Logger.INFO("Created Folder: "+(new File(mpath+"/config").getAbsolutePath()));
			}
			if (!new File(mpath+"/saves").exists()){
				new File(mpath+"/saves").mkdirs();	
				Logger.INFO("Created Folder: "+(new File(mpath+"/saves").getAbsolutePath()));			
			}
			if (!new File(mpath+"/mods").exists()){
				new File(mpath+"/mods").mkdirs();	
				Logger.INFO("Created Folder: "+(new File(mpath+"/mods").getAbsolutePath()));			
			}
			if (!new File(mpath+"/help").exists()){
				new File(mpath+"/help").mkdirs();	
				Logger.INFO("Created Folder: "+(new File(mpath+"/help").getAbsolutePath()));			
			}
			return true;
		}
		else {
			Logger.ERROR("Failed to create game folders.");
			return false;
		}
		
	}
	
}
