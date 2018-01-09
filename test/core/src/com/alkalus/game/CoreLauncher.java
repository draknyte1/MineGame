package com.alkalus.game;

import com.alkalus.game.core.screens.LoadingScreen_Startup;
import com.alkalus.game.core.screens.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CoreLauncher extends Game {

	public static CoreLauncher instance;
	public SpriteBatch batch;
	public BitmapFont font;
	
	public CoreLauncher(){
		instance = this;
	}

	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new LoadingScreen_Startup(this));
	}

	public void render() {
		super.render(); //important!
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
		ScreenManager.dispose();
	}
}
