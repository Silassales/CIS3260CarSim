package controller;

import java.util.TimerTask;

import model.SimulationModel;
import view.TopDownCarView;

public class ControlCenter implements UserInputEventListener {
	private SimulationModel model;
	private TopDownCarView view;
	private Ticker gameSimulationTicker;
	
	private long timeInterval_ms = 1000 / 2; // Number of milleseconds per frame, 2fps

	public ControlCenter() {
		model = new SimulationModel();
		view = new TopDownCarView(model);
		gameSimulationTicker = new Ticker(new TimerTask() {
			
			@Override
			public void run() {
				// TODO write the render loop
				System.out.println("Ticker tick!" + System.currentTimeMillis());
			}
		}, timeInterval_ms).startTicker();
	}

	@Override
	public void handleUserInput(InputEvent e) {
		// TODO Auto-generated method stub
		System.out.println("We have an event!" + e);
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to the car sim!");
		ControlCenter controller = new ControlCenter();
	}
}
