package com.alkalus.game.core.interfaces;

import com.alkalus.game.core.engine.objects.TextureSet;
import com.alkalus.game.fullstack.server.material.Material;

public interface IMaterial {
	
	public short getMaterialID();
	public short setMaterialID(short id);

	public byte getToughness();
	public byte setToughness(byte i);
	
	public boolean isSolid();
	public boolean setSolid(boolean bool);
	
	public boolean isAir();
	public boolean setIsAirType(boolean bool);
	
	public Material getMaterial();
	
	public TextureSet getTextureSet();
	public TextureSet setTextureSet(TextureSet textures);
	
}
