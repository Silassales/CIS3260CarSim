package model;

import model.Location.LocationBuilder;

public class CarState {
	
	
	
	/* Objects */ 
	public Location location = null;//Using builder in initialize to populate
	public Engine engine = null; 	//Using builder in initialize to populate
	public Transmission transmission = null; 
	
	private Double gasLevel = null; 
	private Double structuralIntegrity = null;
	private Double frontWheelDeviation = null; 
	
	
	public double getGasLevel() {
		return this.gasLevel; 
	}
	public double getStructuralIntegrity() {
		return this.structuralIntegrity; 
	}
	public double getFrontWheelDeviation() {
		return this.frontWheelDeviation; 
	}
	
	public void updateGas(double gasRate, double timeDelta_ms) {
		this.gasLevel = gasRate *timeDelta_ms;
	}

	/* not final methods just ideas */
	public void decreaseGasBy(double percent) {
		this.gasLevel -= percent; 
	}
	
	/* Not sure the logic of the rest of the 'sets' */
		
	
	/* Built car has is OFF, in PARK, room temperature, rpm is 0, and in default location */
	private CarState(CarStateBuilder builder) {
		this.location = builder.location; 
		this.engine = builder.engine; 
		this.transmission = builder.transmission; 
		
		this.gasLevel = builder.gasLevel; 
		this.structuralIntegrity = builder.structuralIntegrity;
		this.frontWheelDeviation = builder.frontWheelDeviation; 
		
		
	}
	
	public static class CarStateBuilder {
		/* Objects */ 
		public Location location = null;//Using builder in initialize to populate
		public Engine engine = null; 	//Using builder in initialize to populate
		public Transmission transmission = null; 
		
		private Double gasLevel = null; 
		private Double structuralIntegrity = null;
		private Double frontWheelDeviation = null; 
		
		public CarStateBuilder withGasLevel(double gasLevel) {
			this.gasLevel = gasLevel; 
			return this;
		}
		public CarStateBuilder withStructuralIntegrity(double structuralIntegrity) {
			this.structuralIntegrity = structuralIntegrity; 
			return this; 
		}
		public CarStateBuilder withFrontWheelDeviation(double frontWheelDeviation) {
			this.frontWheelDeviation = frontWheelDeviation; 
			return this; 
		}
		
		/* It doesn't feel right to do it this way, let me know if there is a better, cleaner way */
		public CarStateBuilder withLocation(double speedms, double directionDegrees, double longitude, double latitude, double altitude) throws NullDataFoundAtBuild {
			this.location = new Location.LocationBuilder()
					.withDirectionDegrees(directionDegrees)
					.withLatitude(latitude)
					.withLongitude(longitude)
					.withSpeedms(speedms)
					.withAltitudeM(altitude)
					.build();
			return this; 
			
		}
		public CarStateBuilder withEngine(double rpm, double temperatureC, boolean isEngineOn) throws NullDataFoundAtBuild {
			this.engine = new Engine.EngineBuilder()	
					.withisEngineOn(isEngineOn) 
					.withRpm(rpm)
					.withTemperatureC(temperatureC)
					.build(); 
			return this; 
		}
		public CarStateBuilder withTransmission(int totalGears, int currentGear, boolean hasReverse) throws NullDataFoundAtBuild {
			this.transmission = new Transmission.TransmissionBuilder() 
					.withCurrentGear(currentGear)
					.withHasReverse(hasReverse)
					.withTotalGear(totalGears)
					.build();
	
			return this; 
		}
		
		public CarState build() throws NullDataFoundAtBuild {
			if (this.gasLevel == null) {
				throw new NullDataFoundAtBuild("missing gas level");
			}
			if (this.structuralIntegrity == null) {
				throw new NullDataFoundAtBuild("missing structural integrity");
			}
			if (this.frontWheelDeviation == null) {
				throw new NullDataFoundAtBuild("missing front wheel deviation");
			}
			if (this.engine == null) {
				throw new NullDataFoundAtBuild("Engine failed to build, is still null"); 
			}
			if (this.transmission == null) {
				throw new NullDataFoundAtBuild("Transmission failed to build, is still null"); 
			}
			if (this.location == null) {
				throw new NullDataFoundAtBuild("Location failed to build, is still null"); 
			}
			
			return new CarState(this);
		}
		
		
	}
	

}
