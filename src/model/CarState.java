package model;

public class CarState {
	
	/* The overrides are to ensure no over/under flow gears  */
	public enum transmissionGear { 
		Park { 
			@Override 
			public transmissionGear prev() {
				//You cannot shift below Park, stay the same
				return this; 
			};	
		}, 
		Neutral, 
		First, 
		Second, 
		Third, 
		Fourth, 
		Fifth { 
			@Override 
			public transmissionGear next(){
				//You cannot shift up passed 5th, stay the same
				return this; 
			};
			
		};
		public transmissionGear next() { 
			//next will return the next gear, IE. From Park -> Neutral -> First.. Etc. 
			return values()[ordinal() + 1]; 
		}
		public transmissionGear prev()  {
			//return the previous gear, IE. From Neutral -> Park, or Third to 2nd.. etc. 
			return values() [ordinal() - 1];
		}
	}
	
	public transmissionGear transGear; 
	
	/* Objects */ 
	public Location location = null;//Using builder in initialize to populate
	public Engine engine = null; 	//Using builder in initialize to populate

	
	public Double gasLevel = null; 
	public Double structuralIntegrity = null;
	//Can be neg and pos to represent left and right deviation? 
	public Double frontWheelDeviation = null; 
	
    public void shiftGearUp() {	
    	//Make add a 3% chance the car just randomly goes into neutral and stalls?? :) 
		this.transGear.next(); 
	}
	public void shiftGearDown() {
		this.transGear.prev(); 
	}
	
	/* Built car has is OFF, in PARK, room temperature, rpm is 0, and in default location */
	public CarState() {
		engine = new Engine.EngineBuilder()	//Engine built as off, or false
				.withisEngineOn(false) 
				.withRpm(0.0)
				.withTemperatureC(20.0)
				.build(); 
		location = new Location.LocationBuilder()
				.withDirectionDegrees(0.0)
				.withLatitude(0.0)
				.withLongitude(0.0)
				.withSpeedms(0.0)
				.build();
		transGear = transmissionGear.Park; 
		gasLevel = 100.0; 
		structuralIntegrity = 100.0; 
		frontWheelDeviation = 0.0; 
		
		
	}

}
