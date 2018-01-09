package com.alkalus.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.alkalus.game.CoreLauncher;
import com.alkalus.game.core.Constants;
import com.alkalus.game.core.engine.MainGameLoader;
import com.alkalus.game.core.engine.objects.Logger;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		/**
		 * Set Game Constants
		 */
		
		config.title = Constants.GAME_NAME+" @ "+Constants.GAME_VERSION;
	    config.width = 800;
	    config.height = 600;
		
	    /**
	     * If Game passes all internal startup checks, begin the render.
	     */
		if (MainGameLoader.runGameLoad()){
			Logger.INFO("Starting render.");
			new LwjglApplication(new CoreLauncher(), config);
		}
	}
}
