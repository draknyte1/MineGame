package com.alkalus.game.core.loaders.template;

import com.alkalus.game.core.engine.MainGameLoader;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.core.interfaces.IInitObject;

public class BaseLoader implements IInitObject{

	private final short loaderID;
	public final BaseLoader instance;
	
	static{
		
	}
	
	public BaseLoader(String loaderRegistrationName){
		instance = this;	
		loaderID = MainGameLoader.getFreeLoaderID();
		instance.register();
		Logger.INFO("Created & Registered Loader for: "+loaderRegistrationName+". Loader has ID: "+loaderID+".");
	}
	
	@Override
	public synchronized boolean preInit() {
		return true;
	}

	@Override
	public synchronized boolean init() {
		return true;
	}

	@Override
	public synchronized boolean postInit() {
		return true;
	}

	@Override
	public synchronized IInitObject register() {
		return MainGameLoader.registerLoader(this);
	}

	@Override
	public synchronized short getLoaderID() {
		return this.loaderID;
	}

}
