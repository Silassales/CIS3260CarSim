package model;

public class CarState implements ISimulatable {
	
	@Override
	public String toString() {
		return "CarState [location=" + location + ", engine=" + engine + ", transmission=" + transmission
				+ ", gasLevel=" + gasLevel + ", structuralIntegrity=" + structuralIntegrity + ", frontWheelDeviation="
				+ frontWheelDeviation + "]";
	}

	/* Objects */ 
	public Location location = null;//Using builder in initialize to populate
	public Engine engine = null; 	//Using builder in initialize to populate
	public Transmission transmission = null; 
	
	private Double gasLevel = null; 
	private Double structuralIntegrity = null;
	private Double frontWheelDeviation = null; 
	private boolean isBraking = false;
	private boolean isAccelerating = false;
	
	
	public double getGasLevel() {
		return this.gasLevel; 
	}
	public double getStructuralIntegrity() {
		return this.structuralIntegrity; 
	}
	public double getFrontWheelDeviation() {
		return this.frontWheelDeviation; 
	}
	
	public void setIsBraking(boolean braking) {
		this.isBraking = braking;
	}
	
	public void setIsAccelerating(boolean accelerating) {
		this.isAccelerating = accelerating;
	}
	
	public void turnLeft() {
		this.frontWheelDeviation = -45.0;
	}
	
	public void turnRight() {
		this.frontWheelDeviation = 45.0;
	}
	
	public void turnStraight() {
		this.frontWheelDeviation = 0.0;
	}
	
	public void updateGas(long timeDelta_ms) {
		this.gasLevel -= engine.getRpm()/1000 * timeDelta_ms/1000;
	}

	public void steerVehicle(long timeDelta_ms) {
		location.setDirectionDegrees(location.getDirectionDegrees() + frontWheelDeviation * timeDelta_ms/1000);
	}
	
	public void updateSpeed (double timeDelta_ms) {
		double acceleration = 0;
		
		if (isBraking) {
			acceleration -= 9.8; // Slow down at 9.8 m/s^2
		}
		
		if (isAccelerating) {
			acceleration += 6.0; // Slowing is stronger than speeding up
		} 
		
		acceleration -= 1.5; // Rolling resistance
		
		// TODO handle different acceleration rates with different gears
		// TODO don't go backwards when braking while stopped
		
		location.setSpeedms(location.getSpeedms() + (acceleration * (timeDelta_ms / 1000)));
	}

	@Override
	public void iterateSimulation(long time_ms) {
		// TODO Auto-generated method stub
		engine.iterateSimulation(time_ms);
		location.iterateSimulation(time_ms);
		steerVehicle(time_ms);
		updateGas(time_ms);
		updateSpeed(time_ms);
	}
	
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
