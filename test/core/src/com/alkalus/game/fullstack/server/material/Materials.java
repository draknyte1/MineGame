package com.alkalus.game.fullstack.server.material;

import com.alkalus.game.core.engine.objects.TextureSet;

public enum Materials {

	//Technical
	AIR(0, "Air", 0, false, null),
	BEDROCK(1, "Mantle Stone", Short.MAX_VALUE, true, TextureSet.STONE),
	
	//Fluids
	WATER(2, "Water", 0, false, TextureSet.WATER),
	LAVA(3, "Lava", 0, false, null),
	OIL_LIGHT(4, "Light Oil", 0, false, null),
	OIL_NORMAL(5, "Oil", 0, false, null),
	OIL_HEAVY(6, "Heavy Oil", 0, false, null),
	
	//Dirts
	DIRT_PLAIN(11, "Dirt", 0, true, TextureSet.DIRT),
	DIRT_DRY(12, "Dry Dirt", 0, true, TextureSet.DIRT),
	
	//Stones
	STONE(20, "Stone", 0, true, TextureSet.STONE),
	
	//Rock Types
	GRANITE(40, "Granite", 0, true, null);	
		
	//Variables
	private final Material material;
	
	Materials(int id, String name, int toughness, boolean solid, TextureSet textures){
		boolean isAir = false;
		if (id == 0){
			isAir = true;
		}		
		//Set Material
		material = new Material((short) id, name, (byte) toughness, solid, isAir, textures);		
	}
	
	public synchronized final Material getMaterial() {
		return material;
	}
	
}
