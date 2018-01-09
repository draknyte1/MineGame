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
	
	public static int SCREEN_RES_X = 800;
	public static int SCREEN_RES_Y = 460;
	
	/*
	 * Asset Manager
	 */
	public static final AssetManager ASSET_MANAGER = new AssetManager();
	
	
	
	/**
	 * Asset Paths
	 */
	
	public static final String PATH_ASSETS = "assets/";
	
	
	public static final String PATH_TEXTURES = /*PATH_ASSETS+*/"textures/";
	
	public static final String PATH_TEXTURES_TILES = PATH_TEXTURES+"tiles/";
	public static final String PATH_TEXTURES_MISC = PATH_TEXTURES+"misc/";
	
	
	public static final String PATH_SOUNDS = PATH_ASSETS+"audio/sounds/";
	public static final String PATH_BGM = PATH_ASSETS+"audio/bgm/";
	
	
}
