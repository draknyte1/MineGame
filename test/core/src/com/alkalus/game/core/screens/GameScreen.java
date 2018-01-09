package com.alkalus.game.core.screens;

import static com.alkalus.game.core.Constants.*;

import java.util.Iterator;

import com.alkalus.game.CoreLauncher;
import com.alkalus.game.core.Constants;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.util.AssetUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	final CoreLauncher game;
	private static boolean isActive = false;

	Texture dropImage;
	Texture bucketImage;
	//Sound dropSound;
	//Music rainMusic;
	OrthographicCamera camera;
	Rectangle bucket;
	Array<Rectangle> raindrops;
	long lastDropTime;
	int dropsGathered;

	public boolean isScreenInFocus(){
		return isActive;
	}

	public GameScreen(final CoreLauncher game) {
		this.game = game;

		// load the images for the droplet and the bucket, 64x64 pixels each
		//dropImage = new Texture(Gdx.files.internal("droplet.png"));
		//bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		if(ASSET_MANAGER.isLoaded(Constants.PATH_TEXTURES_MISC+"droplet.png")) {
			Logger.INFO("Found valid asset.");
			dropImage = ASSET_MANAGER.get(Constants.PATH_TEXTURES_MISC+"droplet.png", Texture.class);
		}
		if(ASSET_MANAGER.isLoaded(Constants.PATH_TEXTURES_MISC+"bucket.png")) {
			Logger.INFO("Found valid asset.");
			bucketImage = ASSET_MANAGER.get(Constants.PATH_TEXTURES_MISC+"bucket.png", Texture.class);
		}

		//dropImage = AssetUtils.getPNGTexture("data/", "droplet2");
		//bucketImage = AssetUtils.getPNGTexture(Constants.PATH_TEXTURES_MISC, "bucket");

		// load the drop sound effect and the rain background "music"
		//dropSound = Gdx.audio.newSound(Gdx.files.internal("wd.wav"));
		//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("wu.mp3"));
		//rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_RES_X, SCREEN_RES_Y);

		// create a Rectangle to logically represent the bucket
		bucket = new Rectangle();
		bucket.x = SCREEN_RES_X / 2 - 64 / 2; // center the bucket horizontally
		bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
		// the bottom screen edge
		bucket.width = 64;
		bucket.height = 64;

		// create the raindrops array and spawn the first raindrop
		raindrops = new Array<Rectangle>();
		spawnRaindrop();

	}

	private void spawnRaindrop() {
		if (isScreenInFocus()){
			Rectangle raindrop = new Rectangle();
			raindrop.x = MathUtils.random(0, SCREEN_RES_X - 64);
			raindrop.y = 480;
			raindrop.width = 64;
			raindrop.height = 64;
			raindrops.add(raindrop);
			lastDropTime = TimeUtils.nanoTime();
		}
	}

	@Override
	public void render(float delta) {
		if (isScreenInFocus() && bucket != null && bucketImage != null){
			// clear the screen with a dark blue color. The
			// arguments to glClearColor are the red, green
			// blue and alpha component in the range [0,1]
			// of the color to be used to clear the screen.
			Gdx.gl.glClearColor(0.2f, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			// tell the camera to update its matrices.
			camera.update();

			// tell the SpriteBatch to render in the
			// coordinate system specified by the camera.
			game.batch.setProjectionMatrix(camera.combined);

			// begin a new batch and draw the bucket and
			// all drops

			if (bucket == null){
				Logger.INFO("bucket was null");
			}

			if (bucketImage == null){
				Logger.INFO("bucketImage was null");
			}
			
			game.batch.begin();
			game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, SCREEN_RES_Y);
			game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
			for (Rectangle raindrop : raindrops) {
				game.batch.draw(dropImage, raindrop.x, raindrop.y);
			}
			game.batch.end();

			// process user input
			if (Gdx.input.isTouched()) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				bucket.x = touchPos.x - 64 / 2;
			}
			if (Gdx.input.isKeyPressed(Keys.LEFT))
				bucket.x -= 200 * Gdx.graphics.getDeltaTime();
			if (Gdx.input.isKeyPressed(Keys.RIGHT))
				bucket.x += 200 * Gdx.graphics.getDeltaTime();

			//Inventory
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)){
				game.setScreen(ScreenManager.SCREEN_PLAYER_INVENTORY);
				this.pause();
			}

			// make sure the bucket stays within the screen bounds
			if (bucket.x < 0)
				bucket.x = 0;
			if (bucket.x > SCREEN_RES_X - 64)
				bucket.x = SCREEN_RES_X - 64;

			// check if we need to create a new raindrop
			if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
				spawnRaindrop();

			// move the raindrops, remove any that are beneath the bottom edge of
			// the screen or that hit the bucket. In the later case we increase the 
			// value our drops counter and add a sound effect.
			Iterator<Rectangle> iter = raindrops.iterator();
			while (iter.hasNext()) {
				Rectangle raindrop = iter.next();
				raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
				if (raindrop.y + 64 < 0)
					iter.remove();
				if (raindrop.overlaps(bucket)) {
					dropsGathered++;
					//dropSound.play();
					iter.remove();
				}
			}
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
			dropImage.dispose();
			bucketImage.dispose();
			//dropSound.dispose();
			//rainMusic.dispose();
		}
		catch (Throwable r){

		}
	}

}