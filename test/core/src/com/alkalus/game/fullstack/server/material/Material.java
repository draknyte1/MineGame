package com.alkalus.game.fullstack.server.material;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.core.engine.objects.TextureSet;
import com.alkalus.game.core.interfaces.IMaterial;

public class Material implements Serializable, IMaterial{
	
	//Special Static list of all materials
	public static final Map<Short, Material> materialRegistry = new HashMap<Short, Material>();

	//Material ID
	private short materialID;
	private String materialName;
	
	private byte toughness;
	
	private boolean solid;
	private boolean air;
	
	private TextureSet textures;
	
	public Material(String name, byte toughness, boolean solid, boolean air, TextureSet textures){
		this((short) materialRegistry.size(), name, toughness, solid, air, textures);
	}
	
	protected Material(short id, String name, byte toughness, boolean solid, boolean air, TextureSet textures){
		this.setMaterialID(id);
		this.setMaterialName(name);
		this.setToughness(toughness);
		this.setSolid(solid);
		this.setIsAirType(air);
		this.setTextureSet(textures);
		register();
	}

	private final boolean register(){
		int startSize = materialRegistry.size();
		materialRegistry.put(this.materialID, this);
		int finishSize = materialRegistry.size();
		if (finishSize > startSize){
			Logger.INFO("Sucessfully registered Material: "+this.getMaterialName()+" with ID["+this.getMaterialID()+"].");
			return true;
		}		
		Logger.INFO("Failed to register Material: "+this.getMaterialName()+" with ID["+this.getMaterialID()+"].");
		return false;
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
	public String getMaterialName() {
		return this.materialName;
	}

	@Override
	public String setMaterialName(String name) {
		this.materialName = name;
		return this.materialName;
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

}
