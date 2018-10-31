package model;

public class Engine {
	
	private Double rpm = null; 
	private Double temperatureC = null;  
	private Boolean isEngineOn = null; 
	
	public void startEngine() {
		this.isEngineOn = true; 
	}
	public void stopEngine() {
		this.isEngineOn = false; 
	}
	public double getRpm() {
		return this.rpm; 
	}
	public boolean isEngineOn() {
		return this.isEngineOn;
	}
	
	private Engine(EngineBuilder builder) {
		this.rpm = builder.rpm;
		this.temperatureC = builder.temperatureC; 
		this.isEngineOn = builder.isEngineOn;
	}
	
	public static class EngineBuilder {
		private Double rpm; 
		private Double temperatureC;
		private Boolean isEngineOn; 
		
		public EngineBuilder withisEngineOn(boolean isEngineOn) {
			this.isEngineOn = isEngineOn; 
			return this; 
		}
		
		public EngineBuilder withRpm(double rpm) {
			this.rpm = rpm; 
			return this; 
		}
		public EngineBuilder withTemperatureC(double temperatureC)
		{
			this.temperatureC = temperatureC; 
			return this; 
		}
		
		public Engine build() {
			return new Engine(this); 
		}
	
		
	}
	
}
