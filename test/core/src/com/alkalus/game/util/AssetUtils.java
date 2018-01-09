package com.alkalus.game.util;

import static com.alkalus.game.core.Constants.ASSET_MANAGER;

import com.alkalus.game.core.Constants;
import com.alkalus.game.core.engine.objects.Logger;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetUtils {

	public static AssetManager getAssetManager(){
		return Constants.ASSET_MANAGER;
	}
	
	public static synchronized void queueAssetToLoad(String fileName, String extension, Class assetType){
		queueAssetToLoad("", fileName, extension, assetType);
	}
	
	public static synchronized void queueAssetToLoad(String path, String fileName, String extension, Class assetType){
		Logger.INFO("Queued Asset `"+fileName+"` to load.");
		ASSET_MANAGER.load(path+fileName+"."+extension, assetType);
	}

	
	public static Texture getPNGTexture(String file){
		return getPNGTexture("", file);
	}
	
	public static Texture getPNGTexture(String path, String file){
		Logger.INFO("Getting PNG Asset `"+path+file+"`.");
		return ASSET_MANAGER.get(path+file+".png", Texture.class);
	}
	
	public static Texture getOtherTexture(String file, String extension){
		return getOtherTexture("", file, extension);
	}
	
	public static Texture getOtherTexture(String path, String file, String extension){
		return ASSET_MANAGER.get(path+file+"."+extension, Texture.class);
	}
	
}
