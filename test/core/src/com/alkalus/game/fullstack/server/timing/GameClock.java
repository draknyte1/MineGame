package com.alkalus.game.fullstack.server.timing;

import static com.alkalus.game.fullstack.server.timing.MasterGameTimeClock.gameClocks;

import java.io.Serializable;
import java.util.UUID;

public class GameClock implements Serializable {	

	final int COUNTING_UP = 0;
	final int COUNTING_DOWN = 1;
	final int MASTER_MODE = -1;

	static final long serialVersionUID = 1L;

	public UUID id;

	public int hour;
	public int minute;
	public int second;
	public int day;
	public int mode;

	public boolean isPaused = false;

	public synchronized final boolean isPaused() {
		return isPaused;
	}

	public synchronized final void pause() {
		this.isPaused = true;
	}

	public synchronized final void unpause() {
		this.isPaused = false;
	}

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

	public GameClock(GameClockStorage clock){
		this.loadClock(clock);
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
		if (!isPaused()){
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
		return false;
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

	public GameClockStorage saveClock(){
		if (this.mode != MASTER_MODE){
			return new GameClockStorage(this.id, this.mode, this.day, this.hour, this.minute, this.second, this.isPaused());
		}
		return new GameClockStorage(this.id, this.mode, this.day, this.hour, this.minute, this.second, true);
	}

	public void loadClock(GameClockStorage clock){
		this.id = clock.getId();
		this.mode = clock.getMode();
		this.day = clock.getDay();
		this.hour = clock.getHour();
		this.minute = clock.getMinute();
		this.second = clock.getSecond();
		this.isPaused = clock.isPaused();
	}


	public class GameClockStorage implements Serializable {
		static final long serialVersionUID = 1L;

		public synchronized final UUID getId() {
			return id;
		}

		public synchronized final void setId(UUID id) {
			this.id = id;
		}

		public synchronized final int getHour() {
			return hour;
		}

		public synchronized final void setHour(int hour) {
			this.hour = hour;
		}

		public synchronized final int getMinute() {
			return minute;
		}

		public synchronized final void setMinute(int minute) {
			this.minute = minute;
		}

		public synchronized final int getSecond() {
			return second;
		}

		public synchronized final void setSecond(int second) {
			this.second = second;
		}

		public synchronized final int getDay() {
			return day;
		}

		public synchronized final void setDay(int day) {
			this.day = day;
		}

		public synchronized final int getMode() {
			return mode;
		}

		public synchronized final void setMode(int mode) {
			this.mode = mode;
		}

		public synchronized final long getSerialversionuid() {
			return serialVersionUID;
		}

		public synchronized final boolean isPaused() {
			return pause;
		}

		public synchronized final void setPause(boolean pause) {
			this.pause = pause;
		}

		public UUID id;
		public int hour;
		public int minute;
		public int second;
		public int day;
		public int mode;
		public boolean pause;

		public GameClockStorage(UUID id, int mode, int day, int hour, int min, int sec, boolean pause){
			this.id = id;
			this.mode = mode;
			this.day = day;
			this.hour = hour;
			this.minute = min;
			this.second = sec;
			this.pause = pause;
		}

	}


}
