package controller;

public interface TimerEventListener {
	public abstract void handleTimerEvent(long timediff_ms, long currentTime_ms);
}
