package com.alkalus.game.fullstack.server.material;

import com.alkalus.game.core.engine.objects.TextureSet;
import com.alkalus.game.core.interfaces.IMaterial;

public class Material implements IMaterial{

	//Material ID
	private short materialID;
	
	private byte toughness;
	
	private boolean solid;
	private boolean air;
	
	private TextureSet textures;
	
	public Material(byte toughness, boolean solid, boolean air, TextureSet textures){
		this((short) Materials.materialRegistry.size(), toughness, solid, air, textures);
	}
	
	protected Material(short id, byte toughness, boolean solid, boolean air, TextureSet textures){
		this.setMaterialID(id);
		this.setToughness(toughness);
		this.setSolid(solid);
		this.setIsAirType(air);
		this.setTextureSet(textures);
	}

	@Override
	public short getMaterialID() {
		return this.materialID;
	}

	@Override
	public short setMaterialID(short id) {
		this.materialID = id;
		return this.materialID;
	}	
	
	@Override
	public byte getToughness() {
		return this.toughness;
	}

	@Override
	public byte setToughness(byte i) {
		this.toughness = i;
		return this.toughness;
	}

	@Override
	public boolean isSolid() {
		return this.solid;
	}

	@Override
	public boolean setSolid(boolean bool) {
		this.solid = bool;
		return this.solid;
	}

	@Override
	public boolean isAir() {
		return this.air;
	}

	@Override
	public boolean setIsAirType(boolean bool) {
		this.air = bool;
		return this.air;
	}

	@Override
	public Material getMaterial() {
		return this;
	}

	@Override
	public TextureSet getTextureSet() {
		return this.textures;
	}

	@Override
	public TextureSet setTextureSet(TextureSet textures) {
		this.textures = textures;
		return this.textures;
	}
	
	
	//Register Base Materials here
	public static class InternalMaterialRegistry{
		
	}

}
