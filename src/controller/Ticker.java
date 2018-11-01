package controller;

import java.util.Timer;
import java.util.TimerTask;

public class Ticker {
	Timer t;
	long timeInterval_ms;
	TimerTask callbackFn;
	
	public Ticker(TimerEventListener listener, long interval_ms) {
		t = new Timer();
		
		this.callbackFn = new TimerTask() {
			
			@Override
			public void run() {
				listener.handleTimerEvent(System.currentTimeMillis());
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
