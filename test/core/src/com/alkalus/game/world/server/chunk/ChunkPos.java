package com.alkalus.game.world.server.chunk;

import java.io.Serializable;

import com.alkalus.game.world.server.world.World;

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

}
