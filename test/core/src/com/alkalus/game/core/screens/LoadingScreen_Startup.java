package com.alkalus.game.core.screens;

import static com.alkalus.game.CoreLauncher.world;
import static com.alkalus.game.core.Constants.*;

import java.time.LocalTime;

import org.magnos.asset.Assets;
import org.magnos.asset.image.ImageFormat;
import org.magnos.asset.source.ClasspathSource;

import com.alkalus.game.CoreLauncher;
import com.alkalus.game.assets.AssetLoader;
import com.alkalus.game.assets.loaders.TmxFileFormatLoader;
import com.alkalus.game.core.engine.MainGameLoader;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.util.BenchmarkUtils;
import com.alkalus.game.util.math.MathUtils;
import com.alkalus.game.world.client.config.ConfigHandler;
import com.alkalus.game.world.client.world.WorldIO;
import com.alkalus.game.world.server.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class LoadingScreen_Startup  implements Screen {

	/**
	 * Loading Screen Variables
	 */

	private static boolean INIT_SCREEN_MANAGER = false;
	private static boolean INIT_ASSET_MANAGER = false;
	private static LocalTime INIT_INSTANT;
	
	
	final CoreLauncher game;
	public static final ConfigHandler config;
	
	int timer = 0;
	int assetsToLoadMax = 0;
	int assetsLeftToLoad = 0;
	int assetsCompleted = 0;

	OrthographicCamera camera;
	
	static{
		config = new ConfigHandler();		
	}
	
	public LoadingScreen_Startup(final CoreLauncher game) {
		this.game = game;
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, SCREEN_RES_X, SCREEN_RES_Y);
		INIT_INSTANT = LocalTime.now();
		
		Assets.addFormats(new TmxFileFormatLoader(), new ImageFormat());
		Assets.addSource("cls", new ClasspathSource());
		Assets.setDefaultSource("cls");
		
		//Create a New World.
		world = WorldIO.getWorld("Test");	
	
		//World Was created/loaded successfully.
		if (world != null){
			MainGameLoader.THREAD_LOGIC.worldLoaded = true;
			world.getWeatherHandler(world).begin();
		}
		else {
			//World Failed to load.
		}
		
	}

	@Override
	public void show() {
		Logger.INFO("Showing Screen - Loading Screen");		
	}
	
	private String getDots(int timer){
		int x = timer;
		String r = String.valueOf(x);		
		if (x < 10){
			r = "0";
		}		
		char v = r.charAt(0);
		int j = Integer.valueOf((""+v));
		if (j < 2){
			return "";
		}
		else if (j < 4){
			return ".";
		}
		else if (j < 6){
			return "..";
		}
		else if (j < 8){
			return "...";
		}
		else {
			return "....";			
		}
	}

	@Override
	public void render(float delta) {
		timer++;
		Gdx.gl.glClearColor(0, 0.2f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Loading."+getDots(timer), 16, 32);
		//game.font.draw(game.batch, "Click to begin!", 100, 100);
		game.batch.end();


		if (timer % 10 == 0) {
			Logger.INFO("Loaded "+(timer != 100 ? (timer+MathUtils.randInt(-5, 5)) : "100")+"%.");
		}
		
		//Load Screens
		if (timer >= 10 && !INIT_SCREEN_MANAGER) {
			AssetLoader.initialise();
			ScreenManager.initialise(game);

			assetsToLoadMax = ASSET_MANAGER.getQueuedAssets();
			assetsLeftToLoad = ASSET_MANAGER.getQueuedAssets();
			assetsCompleted = assetsToLoadMax-assetsLeftToLoad;
			
			INIT_SCREEN_MANAGER = true;
		}
		
		//Load Asset Manager
		if(timer >= 10 && INIT_SCREEN_MANAGER && ASSET_MANAGER.update()) {
	         // we are done loading, let's move to another screen!
			INIT_ASSET_MANAGER = true;
			ASSET_MANAGER.finishLoading();
			BenchmarkUtils.pollMethod(INIT_INSTANT, "Asset_Manager");
	      }
		
		if (timer >= 100 && INIT_ASSET_MANAGER) {
			game.setScreen(ScreenManager.SCREEN_MAIN_MENU);
			BenchmarkUtils.pollMethod(INIT_INSTANT);
			dispose();
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
		Logger.INFO("Hiding Screen - Loading Screen");	
	}

	@Override
	public void dispose() {
	}

}
