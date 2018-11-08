package controller;

import model.NullDataFoundAtBuild;
import model.SimulationModel;
import view.userInterface.TopDownCarView;

public class ControlCenter implements UserInputEventListener, TimerEventListener {
	private SimulationModel model;
	private Ticker gameSimulationTicker;
	private static TopDownCarView topDownCarView = null;
	
	private long timeInterval_ms = 1000 / 2; // Number of milleseconds per frame, 2fps
	
	private static ControlCenter singletonRef = null;

	public static ControlCenter getControlCenter() {
		if (singletonRef == null) {
			singletonRef = new ControlCenter();
		}
		
		return singletonRef;
	}
	
	private ControlCenter() {
		try {
			model = new SimulationModel();
		} catch (NullDataFoundAtBuild e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gameSimulationTicker = new Ticker(this, timeInterval_ms);
	}

	@Override
	public void handleUserInput(InputEvent e) {
	    if(topDownCarView == null) {
	        throw new IllegalStateException("topDownCarView not initialized in handleUserInput");
        }

		// TODO Auto-generated method stub
		System.out.println("We have an event!" + e);

		switch(e.getEventType()) {
            case QUIT_PROGRAM:
                stopSimulation();
                break;
            case TEST:
                System.out.println("testing");
                topDownCarView.updateTest();
                break;
            default:
                break;
        }
	}
	
	@Override
	public void handleTimerEvent(long time_delta_ms, long currentTime) {
		System.out.println("Timer event! " + currentTime);
		model.iterateSimulation(time_delta_ms);
		System.out.println(model);
		// TODO
		//view.updateView(); once I get POC working for updating things on the UI thread, I will fix this
	}
	
	public void startSimulation() {
		gameSimulationTicker.startTicker();
	}
	
	public void stopSimulation() { gameSimulationTicker.stopTicker(); }

	public static void main(String[] args) {
		System.out.println("Welcome to the car sim!");
        getControlCenter().startSimulation();
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(TopDownCarView.class);
            }
        }.start();
        topDownCarView = TopDownCarView.waitForTopDownCarView();
	}
}
