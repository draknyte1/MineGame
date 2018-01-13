package com.alkalus.game.core;

import com.badlogic.gdx.assets.AssetManager;

public class Constants {

	public static final String GAME_NAME = "MineGame";
	public static final String GAME_VERSION = "0.0.1a";	
	
	//Benchmarking
	public static final boolean BENCHMARKING = false;
	
	/*
	 * Screen Size
	 */
	
	public static int SCREEN_RES_X = 1920;
	public static int SCREEN_RES_Y = 1080;
	
	/*
	 * Asset Manager
	 */
	public static final AssetManager ASSET_MANAGER = new AssetManager();
	
	
	
	/**
	 * Asset Paths
	 */

	//public static final String PATH_ASSETS = "assets/";
	public static final String PATH_ASSETS = "";
	
	
	public static final String PATH_TEXTURES = PATH_ASSETS+"textures\\";

	public static final String PATH_TEXTURE_SETS = PATH_TEXTURES+"textureset\\";
	public static final String PATH_TEXTURES_TILES = PATH_TEXTURES+"tiles\\";
	public static final String PATH_TEXTURES_MISC = PATH_TEXTURES+"misc\\";
	
	
	public static final String PATH_SOUNDS = PATH_ASSETS+"audio\\sounds\\";
	public static final String PATH_BGM = PATH_ASSETS+"audio\\bgm\\";
	

	public static final String PATH_DATA = PATH_ASSETS+"data\\";
	public static final String PATH_BASIC_TERRAIN = PATH_DATA+"basicterrain\\";
	public static final String PATH_TILEMAPS = PATH_BASIC_TERRAIN+"tilemaps\\";
	public static final String PATH_TILESETS = PATH_BASIC_TERRAIN+"tilesets\\";
	
	
}
