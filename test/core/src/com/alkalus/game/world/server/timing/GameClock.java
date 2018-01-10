package com.alkalus.game.world.server.timing;

import static com.alkalus.game.world.server.timing.MasterGameTimeClock.gameClocks;

import java.io.Serializable;

public class GameClock implements Serializable {

	private final int COUNTING_UP = 0;
	private final int COUNTING_DOWN = 1;
	private final int MASTER_MODE = -1;

	private static final long serialVersionUID = 1L;
	public int hour = 12;
	public int minute = 0;
	public int second = 0;
	public int day = 0;
	private int mode = 0;

	public GameClock(){
		this(0);
	}

	public GameClock(int mode){
		this.hour = 12;
		this.minute = 0;
		this.second = 0;
		this.day = 0;
		this.mode = mode;
		gameClocks.put(this);
	}

	public GameClock(int day, int hour, int min, int sec){
		this(day, hour, min, sec, 0);
	}

	public GameClock(int day, int hour, int min, int sec, int mode){
		this.hour = day;
		this.minute = hour;
		this.second = min;
		this.day = sec;
		this.mode = mode;
	}

	public boolean tock(){
		if (this.mode == COUNTING_UP){
			return tockUp();
		}
		else if (this.mode == COUNTING_DOWN){
			return tockDown();
		}
		else {
			return tockSpecial();
		}
	}

	public boolean tockUp(){	
		if (hour>23){
			hour = 0;
			day++;
		}	
		if (minute>59){
			minute = 0;
			hour++;
		}
		if (second>59){
			second = 0;
			minute++;
		}	
		if (second<=59){
			second+=3;
		}	
		return true;
	}

	public boolean tockDown(){		
		if (hour==0){
			hour = 23;
			day--;
		}
		if (minute==0){
			minute = 59;
			hour--;
		}	
		if (second==0){
			second = 59;
			minute--;
		}
		if (second>0){
			second--;
		}		
		return true;
	}

	public boolean tockSpecial(){
		if (gameClocks.size() > 0){
			for (GameClock t : gameClocks){
				if (t.mode != t.MASTER_MODE){
					t.tock();
				}
				else {
					t.tockUp();
				}
			}
		}
		return tockUp();
	}



}
