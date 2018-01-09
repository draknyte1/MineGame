package com.alkalus.game.assets;

import com.alkalus.game.core.Constants;
import com.alkalus.game.util.AssetUtils;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {

	public static void initialise(){
		AssetUtils.queueAssetToLoad(Constants.PATH_TEXTURES_MISC, "droplet", "png", Texture.class);
		AssetUtils.queueAssetToLoad(Constants.PATH_TEXTURES_MISC, "bucket", "png", Texture.class);
	}
	
}
