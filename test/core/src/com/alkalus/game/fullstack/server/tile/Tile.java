package com.alkalus.game.fullstack.server.tile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alkalus.game.fullstack.server.material.Material;
import com.alkalus.game.fullstack.server.material.Materials;

public class Tile implements Serializable {

	public int xPos;
	public int yPos;

	public Map<Integer, Material> groundLayers = new HashMap<Integer, Material>();

	public void applyGravityToLayers(){
		Map<Integer, Material> i = groundLayers;
		Material before = null, current, after = null;

		for (int e=0;e<groundLayers.size();e++){
			//Sort through the layers
			if (e > 0){
				before = groundLayers.get(e-1);
			}
			current = groundLayers.get(e);
			if (e < groundLayers.size()){
				after = groundLayers.get(e+1);
			}
			
			if (current != Materials.AIR.getMaterial() && before != null && before == Materials.AIR.getMaterial()){
				//Gravity
				
			}			
			
			//Reset Variables
			before = null; 
			current = null;
			after = null;
			
		}

	}




}
