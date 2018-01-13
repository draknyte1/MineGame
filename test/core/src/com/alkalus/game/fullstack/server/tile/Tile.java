package com.alkalus.game.fullstack.server.tile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alkalus.game.fullstack.client.tile.TileRender;
import com.alkalus.game.fullstack.server.chunk.Chunk;
import com.alkalus.game.fullstack.server.material.Material;
import com.alkalus.game.fullstack.server.material.Materials;

public class Tile implements Serializable {

	public final Chunk parentChunk;
	public int xPos;
	public int yPos;
	public int topLayerY;
	private TileRender tilerender;
	public boolean requiresUpdate = false;

	public Map<Integer, Material> groundLayers = new HashMap<Integer, Material>();
	
	public Tile(Chunk parentChunk){
		this.parentChunk = parentChunk;
		this.generateDefaultTerrain();
		this.tilerender = this.render();
	}
	
	public synchronized Tile getTileWithOffset(int x, int y) {
		return this.parentChunk.getTile(this.xPos+x, this.yPos+y);
	}
	
	/**
	 * Gets an array of the 4 surrounding chunks.
	 * Chunks are returned in the order of: North, East, South then West.
	 * @return ChunkArray[4]
	 */

	public synchronized Tile[] getNeighbours(){
		Tile m[] = new Tile[4];
		//North
		if (getTileWithOffset(0, 1) != null){
			m[0] = getTileWithOffset(0, 1);
		}
		else {
			m[0] = null;
		}		
		//East
		if (getTileWithOffset(1, 0) != null){
			m[1] = getTileWithOffset(1, 0);
		}
		else {
			m[1] = null;
		}		
		//South
		if (getTileWithOffset(0, -1) != null){
			m[2] = getTileWithOffset(0, -1);
		}
		else {
			m[2] = null;
		}		
		//West
		if (getTileWithOffset(-1, 0) != null){
			m[3] = getTileWithOffset(-1, 0);
		}
		else {
			m[3] = null;
		}
		return m;
	}
	
	public synchronized boolean generateDefaultTerrain(){
		groundLayers.put(0, Materials.BEDROCK.getMaterial());
		groundLayers.put(1, Materials.STONE.getMaterial());
		groundLayers.put(2, Materials.STONE.getMaterial());
		groundLayers.put(3, Materials.DIRT_PLAIN.getMaterial());
		return false;
	}
	
	
	public synchronized Material getTopLayer(){
		int layerSize = this.groundLayers.size();
		Map<Integer, Material> invertedLayers = new HashMap<Integer, Material>();
		for (Material f : this.groundLayers.values()){
			invertedLayers.put(layerSize--, f);
		}		
		for (Material j : invertedLayers.values()){
			if (j == Materials.AIR.getMaterial()){
				continue;
			}
			else {
				return j;
			}
		}		
		return null;
	}
	
	public synchronized int getTopLayerID(){
		int layerSize = this.groundLayers.size();
		Map<Integer, Material> invertedLayers = new HashMap<Integer, Material>();
		for (Material f : this.groundLayers.values()){
			invertedLayers.put(layerSize--, f);
		}	
		int layer = this.groundLayers.size();
		for (Material j : invertedLayers.values()){
			if (j == Materials.AIR.getMaterial()){
				layer--;
				continue;
			}
			else {
				return layer;
			}
		}		
		return layer;
	}
	

	public synchronized void applyGravityToLayers(){
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
		this.tilerender = this.render();

	}
	
	public synchronized void markForUpdate(){
		this.requiresUpdate = true;
	}
	
	public synchronized boolean update(){
		applyGravityToLayers();		
		
		this.requiresUpdate = false;
		return true;
	}


	public synchronized TileRender render(){
		return new TileRender(this.getTopLayer().getTextureSet());
	}


}
