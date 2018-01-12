package com.alkalus.game.fullstack.server.weather;

import java.io.Serializable;

public class Weather implements Serializable {

	public enum Types implements Serializable {
	    PERFECT,
	    SUN,
	    CLOUDS,
	    OVERCAST,
	    RAIN,
	    LIGHTNING,
	    FOG,
	    SNOW,
	    HAIL
	}
	
}
