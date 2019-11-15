package model;

public class Engine implements ISimulatable {

	@Override
	public String toString() {
		return "Engine [rpm=" + rpm + ", temperatureC=" + temperatureC + ", isEngineOn=" + isEngineOn + "]";
	}

	private Double rpm = null;
	private Double temperatureC = null;
	private Boolean isEngineOn = null;

	public boolean isEngineOn() {
		return this.isEngineOn;
	}
	public void startEngine() {
		this.isEngineOn = true;
	}
	public void stopEngine() {
		this.isEngineOn = false;
	}

	public void updateEngineTemp(double timeDelta_ms) {
		if (isEngineOn) {
			double tempChange = timeDelta_ms/1000 * rpm/1000 * 0.5;
			this.temperatureC +=  tempChange;
		}
	}

	public double getRpm() {
		return this.rpm;
	}
	public void setRpm(double rpm) {
		this.rpm = rpm;
	}
	public double getTemperatureC() {
		return this.temperatureC;
	}
	public void setTemperatureC(double temperatureC) {
		this.temperatureC = temperatureC;
	}

	@Override
	public void iterateSimulation(long time_ms) {
		// TODO Auto-generated method stub
		updateEngineTemp(time_ms);
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

		public Engine build() throws NullDataFoundAtBuild {

			if (this.rpm == null) {
				throw new NullDataFoundAtBuild("this.rpm == null. Cannot build.");
			}
			if (this.temperatureC == null) {
				throw new NullDataFoundAtBuild("this.temperatureC == null. Cannot build.");
			}
			if (this.isEngineOn == null) {
				throw new NullDataFoundAtBuild("this.isEngineOn == null. Cannot build.");
			}

			return new Engine(this);
		}


	}

}
