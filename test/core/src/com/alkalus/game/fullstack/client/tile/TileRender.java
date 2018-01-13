package com.alkalus.game.fullstack.client.tile;

import com.alkalus.game.core.engine.objects.TextureSet;
import com.alkalus.game.util.AssetUtils;
import com.badlogic.gdx.graphics.Texture;

public class TileRender {

	private final Texture texture;

	public TileRender(TextureSet path){
		this(path.getPath());
	}
	
	public TileRender(String path){
		AssetUtils.queueAssetToLoad(path, "Tile", "png", Texture.class);
		this.texture = AssetUtils.getPNGTexture(path, "Tile");
	}
	
	public synchronized final Texture getTexture() {
		return texture;
	}
	
	
	
}
