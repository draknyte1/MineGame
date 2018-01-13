package com.alkalus.game.core.engine.objects;

import com.alkalus.game.core.Constants;

public enum TextureSet {

	DIRT("Dirt"),
	WATER("Water"),
	STONE("Stone"),
	STONE_2("Stone2");
	
	final String path;
	
	TextureSet(String texturePath){
		this.path = Constants.PATH_TEXTURES+"textureset\\"+texturePath;
	}

	public synchronized final String getPath() {
		return this.path;
	}
	
}
