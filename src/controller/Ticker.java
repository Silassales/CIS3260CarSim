package controller;

import java.util.Timer;
import java.util.TimerTask;

public class Ticker {
	Timer t;
	long timeInterval_ms;
	TimerTask callbackFn;
	long prevTime;
	
	public Ticker(TimerEventListener listener, long interval_ms) {
		t = new Timer();
		prevTime = System.currentTimeMillis();
		
		this.callbackFn = new TimerTask() {
			
			@Override
			public void run() {
				long currentTime = System.currentTimeMillis();
				
				listener.handleTimerEvent(currentTime - prevTime, System.currentTimeMillis());
				
				prevTime = currentTime;
			}
		};
		
		this.timeInterval_ms = interval_ms;
		
	}
	
	public Ticker startTicker() {
		t.scheduleAtFixedRate(this.callbackFn, 0, this.timeInterval_ms);
		return this;
	}
	
	public Ticker stopTicker() {
		t.cancel();
		return this;
	}
}
