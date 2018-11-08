package model;

public class SimulationModel {

	public CarState carState = null; 
	private long time_ms = 0; 
	
	public void iterateSimulationBy(long delta_ms) {
		this.time_ms += delta_ms; 
		//call methods to iterate all car state variables etc. 
	}
	
	public long getTime() {
		return this.time_ms; 
	}
	
	/* We could have this take arguements for initalization, but that seems greasy */ 
	public SimulationModel() throws NullDataFoundAtBuild {
	
		//If build fails at any point exception is thrown. 
		carState = new CarState.CarStateBuilder()
				/* double:rpm, double:temperature, boolean:isEngineOn */
				.withEngine(0.0, 0.0, false) 
				/* double:speedms, double:directionDegree, double:longitude, double: latitude, double: altitude*/
				.withLocation(0.0, 0.0, 0.0, 0.0, 0.0)
				/* int:totalGears, int:currentGear, boolean:hasReverse */
				.withTransmission(5, 0, false)
				.withGasLevel(100) //double 
				.withFrontWheelDeviation(0) //double
				.withStructuralIntegrity(100) //double
				.build(); 
		
	}
	
}