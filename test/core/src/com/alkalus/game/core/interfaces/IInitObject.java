package com.alkalus.game.core.interfaces;

public interface IInitObject {
		
	public abstract IInitObject register();
	
	public abstract short getLoaderID();

	public abstract boolean preInit();
	
	public abstract boolean init();
	
	public abstract boolean postInit();
	
}
