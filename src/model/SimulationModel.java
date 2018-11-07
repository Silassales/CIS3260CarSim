package model;

public class SimulationModel {

	public CarState carState = null; 
	private long time_ms = 0; 
	
	public void iterateSimulation(long delta_ms) {
		this.time_ms += delta_ms; 
		//call methods to iterate all car state variables etc. 
	}
	
	public SimulationModel() throws NullDataFoundAtBuild {
	
		carState = new CarState.CarStateBuilder()
				.withEngine(0, 0, false)
				.withLocation(0, 0, 0, 0, 0)
				.withTransmission(5, 0, false)
				.withGasLevel(100)
				.withFrontWheelDeviation(0)
				.withStructuralIntegrity(100)
				.build(); 
	}
	
}
