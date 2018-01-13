package com.alkalus.game.fullstack.server.material;

import java.util.HashMap;
import java.util.Map;

import com.alkalus.game.core.engine.objects.TextureSet;

public enum Materials {

	//Air
	AIR(0, 0, false, null),
	//Fluids
	WATER(1, 0, false, null),
	LAVA(2, 0, false, null),
	OIL_LIGHT(3, 0, false, null),
	OIL_NORMAL(4, 0, false, null),
	OIL_HEAVY(5, 0, false, null),
	
	
	
	DIRT_PLAIN(11, 0, true, null),
	DIRT_DRY(12, 0, true, null),
	STONE(20, 0, true, null),
	GRANITE(40, 0, true, null);	
	
	
	//Variables
	private final Material material;
	
	Materials(int id, int toughness, boolean solid, TextureSet textures){
		boolean isAir = false;
		if (id == 0){
			isAir = true;
		}		
		//Set Material
		material = new Material((short) id, (byte) toughness, solid, isAir, textures);		
	}
	
	public synchronized final Material getMaterial() {
		return material;
	}
	
	
	//Special Static list of all materials
	public static final Map<Short, Material> materialRegistry = new HashMap<Short, Material>();
	
}
