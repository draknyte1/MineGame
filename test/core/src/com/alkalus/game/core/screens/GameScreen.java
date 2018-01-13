package com.alkalus.game.core.screens;

import static com.alkalus.game.core.Constants.*;

import com.alkalus.game.CoreLauncher;
import com.alkalus.game.core.Constants;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.core.maps.generator.BaseMapGenerator;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;

public class GameScreen extends ApplicationAdapter implements Screen, InputProcessor {
	
	final CoreLauncher game;
	final BaseMapGenerator world;
	private static boolean isActive = false;
	OrthographicCamera camera;
	private boolean isShiftDown = false;
	
	 //Texture img;
	 TiledMap tiledMap;
	 TiledMapRenderer tiledMapRenderer;

	public boolean isScreenInFocus(){
		return isActive;
	}

	public GameScreen(final CoreLauncher game) {
		this.game = game;
		this.world = new BaseMapGenerator();

		//float w = Gdx.graphics.getWidth();
        //float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,SCREEN_RES_X,SCREEN_RES_Y);
        camera.update();        
        
        if (Gdx.files.absolute(Constants.PATH_TILEMAPS+"BasicTerrainSingleTile.tmx").exists()){
        	Logger.INFO("Found absolute Texture map. Looking in "+Constants.PATH_TILEMAPS+"BasicTerrainSingleTile.tmx");
        }
        else {
        	Logger.INFO("Did not find absolute Texture map. Looking in "+Constants.PATH_TILEMAPS+"BasicTerrainSingleTile.tmx");
        }
        
        if (Gdx.files.local(Constants.PATH_TILEMAPS+"BasicTerrainSingleTile.tmx").exists()){
        	Logger.INFO("Found local Texture map. Looking in "+Constants.PATH_TILEMAPS+"BasicTerrainSingleTile.tmx");
        }
        else {
        	Logger.INFO("Did not find local Texture map. Looking in "+Constants.PATH_TILEMAPS+"BasicTerrainSingleTile.tmx");
        }
        
        //tiledMap = new TmxMapLoader().load(Constants.PATH_TILEMAPS+"BasicTerrainSingleTile.tmx");
        //tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        this.tiledMap = this.world.getDefaultTiledMap();
        this.tiledMapRenderer = this.world.generateRandomInfiniteMap();
        Gdx.input.setInputProcessor(this);
		


	}

	@Override
	public void render(float delta) {
		if (isScreenInFocus()){
			//camera.update();
			//game.batch.setProjectionMatrix(camera.combined);
			//Inventory
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)){
				
			}
			
			Gdx.gl.glClearColor(1, 1, 1, 1);
	        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        camera.update();
	        
	        
	        
	        tiledMapRenderer.setView(camera);
	        tiledMapRenderer.render();
			
			
			
			
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		//rainMusic.play();
		Logger.INFO("Showing Screen - Game Screen");
		isActive = true;
	}

	@Override
	public void hide() {
		Logger.INFO("Hiding Screen - Game Screen");
		isActive = false;
	}

	@Override
	public void pause() {
		Logger.INFO("Pausing Screen - Game Screen (Does Nothing)");
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		try {
			//dropSound.dispose();
			//rainMusic.dispose();
		}
		catch (Throwable r){

		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT){
			Logger.INFO("Shift was set down.");
            this.isShiftDown = true;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if(keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT){
			Logger.INFO("Shift was set up.");
            this.isShiftDown = false;
		}
		
        if(keycode == Input.Keys.ESCAPE){
        	game.setScreen(ScreenManager.SCREEN_PLAYER_INVENTORY);
			this.pause();
        }
        
		if(keycode == Input.Keys.LEFT && !this.isShiftDown){
            camera.translate(-32,0);			
		}
		else if(keycode == Input.Keys.LEFT && this.isShiftDown){
			Logger.INFO("Shift was down.");
            camera.translate(-128,0);			
		}
        if(keycode == Input.Keys.RIGHT && !this.isShiftDown){
            camera.translate(32,0);        	
        }
        else if(keycode == Input.Keys.RIGHT && this.isShiftDown){
			Logger.INFO("Shift was down.");
            camera.translate(128,0);        	
        }
        if(keycode == Input.Keys.UP && !this.isShiftDown){
            camera.translate(0,32);        	
        }
        else if(keycode == Input.Keys.UP && this.isShiftDown){
			Logger.INFO("Shift was down.");
            camera.translate(0,128);        	
        }
        if(keycode == Input.Keys.DOWN && !this.isShiftDown){
            camera.translate(0,-32);        	
        }
        else if(keycode == Input.Keys.DOWN && this.isShiftDown){
			Logger.INFO("Shift was down.");
            camera.translate(0,-128);        	
        }
        
        
        if(keycode == Input.Keys.NUM_1){
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());        	
        }
        if(keycode == Input.Keys.NUM_2){
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());        	
        }
        return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		Logger.INFO("Scrolled: "+amount+".");
		
		//Scroll Up
		if (amount < 0){
			Logger.INFO("Zoom level is currently: "+camera.zoom+", taking 0.05.");
			if (camera.zoom > 0.19999984){
				camera.zoom -= 0.05;
			}
			else {
				camera.zoom = 0.19999984f;
			}
		}
		//Scroll down
		else if (amount > 0){
			Logger.INFO("Zoom level is currently: "+camera.zoom+", adding 0.05.");
			if (camera.zoom < 1){
				camera.zoom += 0.05;
			}
			else {
				camera.zoom = 1;
			}			
		}
		
		return false;
	}

}