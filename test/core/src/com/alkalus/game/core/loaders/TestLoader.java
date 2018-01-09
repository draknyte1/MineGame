package com.alkalus.game.core.loaders;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.core.loaders.template.BaseLoader;

public class TestLoader extends BaseLoader {

	public TestLoader() {
		super("testLoader");
	}

	@Override
	public boolean preInit() {
		Logger.INFO("1");
		return true;
	}

	@Override
	public boolean init() {
		Logger.INFO("2");
		return true;
	}

	@Override
	public boolean postInit() {
		Logger.INFO("3");
		return true;
	}

}
