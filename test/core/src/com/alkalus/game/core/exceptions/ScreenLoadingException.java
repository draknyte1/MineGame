package com.alkalus.game.core.exceptions;

import com.alkalus.game.core.engine.objects.Logger;

public class ScreenLoadingException extends RuntimeException {

	/**
	 * Java Garbage
	 */
	private static final long serialVersionUID = -1886522647235771972L;

	public ScreenLoadingException(String message){
	    super(message);
	}
	
	@Override
	public void printStackTrace() {
		Logger.INFO("Error Initialising a screen.");
		super.printStackTrace();
	}

}
