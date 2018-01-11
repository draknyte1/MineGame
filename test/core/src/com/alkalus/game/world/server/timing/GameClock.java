package com.alkalus.game.world.server.timing;

import static com.alkalus.game.world.server.timing.MasterGameTimeClock.gameClocks;

import java.io.Serializable;
import java.util.UUID;

public class GameClock implements Serializable {

	private final int COUNTING_UP = 0;
	private final int COUNTING_DOWN = 1;
	private final int MASTER_MODE = -1;

	private static final long serialVersionUID = 1L;

	public UUID id;

	public int hour;
	public int minute;
	public int second;
	public int day;
	private int mode;

	public GameClock(){
		this(0);
	}

	public GameClock(int mode){
		this.hour = 12;
		this.minute = 0;
		this.second = 0;
		this.day = 0;
		this.mode = mode;
		id = UUID.randomUUID();
		gameClocks.put(id, this);
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
		if (this.hour>23){
			this.hour = 0;
			this.day++;
		}	
		if (this.minute>59){
			this.minute = 0;
			this.hour++;
		}
		if (this.second>59){
			this.second = 0;
			this.minute++;
		}	
		if (this.second<=59){
			this.second+=3;
		}	
		return true;
	}

	public boolean tockDown(){		
		if (this.hour==0){
			this.hour = 23;
			this.day--;
		}
		if (this.minute==0){
			this.minute = 59;
			this.hour--;
		}	
		if (this.second==0){
			this.second = 59;
			this.minute--;
		}
		if (this.second>0){
			this.second--;
		}
		
		if (this.day == 0 && this.hour == 0 && this.minute == 0 && this.second == 0 && this.mode == this.COUNTING_DOWN){
			return stopClock();
		}
		
		return true;
	}

	public boolean tockSpecial(){
		if (gameClocks.size() > 0){
			for (GameClock t : gameClocks.values()){
				if (t != null){
					if (t.mode != t.MASTER_MODE){
						t.tock();
					}
				}
			}
		}
		return tockUp();
	}

	public boolean stopClock(){
		if (gameClocks.remove(this.id) != null){
			return true;
		}
		return false;
	}


}
