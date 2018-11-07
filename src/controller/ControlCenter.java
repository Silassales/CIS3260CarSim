package controller;

import model.SimulationModel;
import view.TopDownCarView;

public class ControlCenter implements UserInputEventListener, TimerEventListener {
	private SimulationModel model;
	private TopDownCarView view;
	private Ticker gameSimulationTicker;
	
	private long timeInterval_ms = 1000 / 2; // Number of milleseconds per frame, 2fps
	
	private static ControlCenter singletonRef = null;

	public static ControlCenter getControlCenter() {
		if (singletonRef == null) {
			singletonRef = new ControlCenter();
		}
		
		return singletonRef;
	}
	
	private ControlCenter() {
		model = new SimulationModel();
		view = new TopDownCarView(model);
		gameSimulationTicker = new Ticker(this, timeInterval_ms);
	}

	@Override
	public void handleUserInput(InputEvent e) {
		// TODO Auto-generated method stub
		System.out.println("We have an event!" + e);
		
		if (e.getEventType() == InputEventType.QUIT_PROGRAM) {
			stopSimulation();
		}
	}
	
	@Override
	public void handleTimerEvent(long currentTime) {
		System.out.println("Timer event! " + currentTime);
		view.updateView();
	}
	
	public void startSimulation() {
		gameSimulationTicker.startTicker();
	}
	
	public void stopSimulation() {
		gameSimulationTicker.stopTicker();
	}

	public static void main(String[] args) {
		System.out.println("Welcome to the car sim!");
		getControlCenter().startSimulation();
	}
}
