package com.alkalus.game.core.screens;

import com.alkalus.game.CoreLauncher;
import com.alkalus.game.core.exceptions.ScreenLoadingException;
import com.badlogic.gdx.Screen;

public class ScreenManager {

	//Static screen instances
	
	/**
	 * Main Menu, Credits, Misc Screens
	 */
	public static Screen SCREEN_DEBUG;
	public static Screen SCREEN_CREDITS;
	public static Screen SCREEN_MAIN_MENU;
	
	/**
	 * Inventory
	 */
	public static Screen SCREEN_PLAYER_INVENTORY;
	
	/**
	 * Game World
	 */
	public static GameScreen SCREEN_LIVE_GAME_WORLD;
	
	public static void initialise(CoreLauncher core){

		/**
		 * Credits, Misc Screens
		 */
		//SCREEN_DEBUG  = new GameScreen(core);
		//SCREEN_CREDITS  = new GameScreen(core);
		
		/**
		 * Main Menu
		 */
		SCREEN_MAIN_MENU  = (Screen) setScreen(new MainMenuScreen(core));

		/**
		 * Inventory
		 */
		SCREEN_PLAYER_INVENTORY  = (Screen) setScreen(new InventoryScreenA(core));

		/**
		 * Game World
		 */
		SCREEN_LIVE_GAME_WORLD  = (GameScreen) setScreen(new GameScreen(core));
	}
	
	public static void dispose(){
		SCREEN_MAIN_MENU.dispose();
		SCREEN_PLAYER_INVENTORY.dispose();
		SCREEN_LIVE_GAME_WORLD.dispose();
	}
	
	private static Object setScreen(Object r){
		
		if (!Screen.class.isInstance(r) || r == null){
				throw new ScreenLoadingException("Division by 0 is not allowed, you cheeky racoon.");
		}
		return (Screen) r;
	}
	
}
