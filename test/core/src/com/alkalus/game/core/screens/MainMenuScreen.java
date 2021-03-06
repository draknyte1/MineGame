package com.alkalus.game.core.screens;

import static com.alkalus.game.CoreLauncher.world;
import static com.alkalus.game.core.Constants.*;

import com.alkalus.game.CoreLauncher;
import com.alkalus.game.core.engine.MainGameLoader;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.fullstack.server.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen  implements Screen {

	final CoreLauncher game;

	OrthographicCamera camera;

	public MainMenuScreen(final CoreLauncher game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_RES_X, SCREEN_RES_Y);

	}

	@Override
	public void show() {
		Logger.INFO("Showing Screen - Main Menu");		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Welcome to MineGame!!! ", 100, 150);
		game.font.draw(game.batch, "Click to begin!", 100, 100);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			if (world != null){
				//Start Time Ticking
				if (World.getWorldClock().isPaused()){
					World.getWorldClock().unpause();
					Logger.INFO("Started world clock ticking again.");
				}
				//Start Weather Handler
				//World.getWeatherHandler().begin();
				//Logger.INFO("Started world weather handler ticking again.");
				
				game.setScreen(ScreenManager.SCREEN_LIVE_GAME_WORLD);
				MainGameLoader.gameLoaded = true;
				//dispose();				
			}
			else {
				Logger.INFO("World was not valid, not loading game environment.");
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		Logger.INFO("Hiding Screen - Main Menu");		
	}

	@Override
	public void dispose() {
	}

}
