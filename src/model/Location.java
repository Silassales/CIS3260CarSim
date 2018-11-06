package model;

public class Location {


	private Double speedms = null; //Speed Milliseconds
	private Double directionDegrees = null; //Degree, 0 to 360
	private Double latitude = null; //x position of carState
	private Double longitude = null; //y position of carState
	private Double altitudeM = null; //Meters Altitude
	
	// We don't know if this is correct or not, please help.
	public void updateSpeed (double timeDelta_ms, double rpm) {
		speedms = rpm * Math.PI * timeDelta_ms/60/1000;
	
	}
	
	public void updateLocation(double timeDelta_ms) {
		double distance = speedms * timeDelta_ms;
		double dispLat = distance * Math.cos(Math.toRadians(directionDegrees));
		double dispLon = distance * Math.sin(Math.toRadians(directionDegrees));
		latitude += dispLat;
		longitude += dispLon;
		
	}
	
	public void steerVehicle(double timeDelta_ms, double frontWheelDeviation) {
		directionDegrees += frontWheelDeviation * timeDelta_ms;
	}
	
	
	public Double getSpeedms() {
		return speedms;
	}
	public void setSpeedms(Double speedms) {
		this.speedms = speedms;
	}
	public Double getDirectionDegrees() {
		return directionDegrees;
	}
	public void setDirectionDegrees(Double directionDegrees) {
		this.directionDegrees = directionDegrees;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getAltitudeM() {
		return altitudeM;
	}

	public void setAltitudeM(Double altitudeM) {
		this.altitudeM = altitudeM;
	}

	private Location(LocationBuilder builder) {	
		this.speedms = builder.speedms;
		this.directionDegrees = builder.directionDegrees; 
		this.latitude = builder.latitude;
		this.longitude = builder.longitude; 
		this.altitudeM = builder.altitudeM; 
	}
	
	public static class LocationBuilder {
		private Double speedms = null; //Speed Milliseconds
		private Double directionDegrees = null; //Degree, 0 to 360
		private Double latitude = null; //x position of carState
		private Double longitude = null; //y position of carState
		private Double altitudeM = null; //Meters Altitude
		
		public LocationBuilder withSpeedms(double speedms) {
			//this.speedms will remain NULL if outside of constraints, will be thrown by builder
			if (speedms >= 0) { 
				this.speedms = speedms; 
			}
			return this; 
		}
		public LocationBuilder withDirectionDegrees(double directionDegrees)
		{
			//this.directionDegree will remain NULL if outside of constraints, will be thrown by builder
			if (directionDegrees >= 0 && directionDegrees <= 360) {
				this.directionDegrees = directionDegrees; 
			}
			return this; 
		}
		public LocationBuilder withLatitude(double latitude)
		{
			//this.latitude will remain NULL if outside of constraints, will be thrown by builder
			if (latitude >= 0) { 
				this.latitude = latitude; 
			}
			return this; 
		}
		public LocationBuilder withLongitude(double longitude)
		{
			//this.longitude will remain NULL if outside of constraints, will be thrown by builder
			if (longitude >= 0) { 
				this.longitude = longitude; 
			}
			return this; 
		}
		
		public LocationBuilder withAltitudeM(double altitudeM) { 
			this.altitudeM = altitudeM; 
			return this; 
		}
		
		//At some point should check for null values
		public Location build() throws NullDataFoundAtBuild {		
			
			//Check for bad, or missing values (therefore null) before building
			if (this.speedms == null) {
				throw new NullDataFoundAtBuild("this.speedms is null at build");
			}
			if (this.directionDegrees == null) {
				throw new NullDataFoundAtBuild("this.directionDegrees is null at build");
			}
			if (this.latitude == null) {
				throw new NullDataFoundAtBuild("this.latitude is null at build");
			}
			if (this.longitude == null) {
				throw new NullDataFoundAtBuild("this.longitude is null at build");
			}
			if (this.altitudeM == null) {
				throw new NullDataFoundAtBuild("this.altitudeM is null at build");
			}
			
			return new Location(this); 
		}
	
		
	}
	
	
	
	
}
