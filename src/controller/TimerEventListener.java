package controller;

public interface TimerEventListener {
	public abstract void handleTimerEvent(long currentTime);
}
