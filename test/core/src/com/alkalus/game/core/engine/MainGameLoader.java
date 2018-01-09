package com.alkalus.game.core.engine;

import com.alkalus.game.core.Constants;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.core.interfaces.IInitObject;
import com.alkalus.game.core.loaders.InternalGameLoader;
import com.alkalus.game.core.loaders.TestLoader;
import com.alkalus.game.core.loaders.TestLoader2;
import com.alkalus.game.util.array.AutoMap;

public class MainGameLoader {
	
	public synchronized static final boolean runGameLoad(){
		Logger.INFO("Loading "+Constants.GAME_NAME+" running at "+Constants.GAME_VERSION+".");
		Logger.INFO("This is just a crazy test simulation.");
		//Load Config
		if (loadConfig()){ 
			Logger.INFO("Loaded config.");
		}
		//generate a config
		else {
			Logger.INFO("Created config.");			
		}
		registerInternalLoaders();
		if (preInit()){ //If preinit checks pass, proceed.
			if (init()){ //If init checks pass, proceed.
				if (postInit()){ //If postinit checks pass, proceed.
					Logger.INFO("Game Starting.");
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	public synchronized static boolean loadConfig(){
		//Load Config Here
		return true;
	}

	private synchronized final static void registerInternalLoaders(){
		InternalGameLoader.initialise();
	}
	
	private volatile static AutoMap<IInitObject> preRunObjects = new AutoMap<IInitObject>();
	
	public synchronized static short getFreeLoaderID(){
		return (short) preRunObjects.size();
	}
	
	public synchronized static IInitObject registerLoader(IInitObject loader){
		return preRunObjects.put(loader);
	}
	
	public synchronized static boolean preInit(){
		Logger.INFO(Constants.GAME_NAME+" has found "+preRunObjects.size()+" game object loaders.");
		for (IInitObject r : preRunObjects.values()){
			r.preInit();
		}		
		Logger.INFO("Passed Stage: Pre-Init");
		return true;
	}
	
	public synchronized static boolean init(){
		for (IInitObject r : preRunObjects.values()){
			r.init();
		}		
		Logger.INFO("Passed Stage: Init");	
		return true;
	}
	
	public synchronized static boolean postInit(){
		for (IInitObject r : preRunObjects.values()){
			r.postInit();
		}	
		Logger.INFO("Passed Stage: Post-Init");		
		return true;
	}
	
}
