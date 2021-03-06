package com.alkalus.game.fullstack.server.chunk;

import java.io.Serializable;

import com.alkalus.game.fullstack.server.world.World;

public class ChunkPos implements Serializable {

	int xChunk;
	int yChunk;

	public ChunkPos(int x, int y){
		xChunk = x;
		yChunk = y;
	}
	
	public Chunk getChunk(){
		return World.getChunk(xChunk, xChunk);
	}
	
	public int getX(){
		return this.xChunk;
	}
	
	public int getY(){
		return this.yChunk;
	}
	
	public boolean isSpawnChunk(){
		if (xChunk == 0 && yChunk == 0){
			return true;
		}
		return false;
	}

}
