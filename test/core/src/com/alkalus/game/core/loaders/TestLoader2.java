package com.alkalus.game.core.loaders;

import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.core.loaders.template.BaseLoader;

public class TestLoader2 extends BaseLoader {

	public TestLoader2() {
		super("testLoader2");
	}

	@Override
	public boolean preInit() {
		Logger.INFO("11");
		return true;
	}

	@Override
	public boolean init() {
		Logger.INFO("22");
		return true;
	}

	@Override
	public boolean postInit() {
		Logger.INFO("33");
		return true;
	}

}
