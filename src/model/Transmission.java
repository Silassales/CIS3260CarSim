package model;

public class Transmission {
	
	private Integer currentGear = null; 
	private Integer totalGears = null;
	
	@Override
	public String toString() {
		return "Transmission [currentGear=" + currentGear + ", totalGears=" + totalGears + "]";
	}


	private Boolean hasReverse = null; 

	private Transmission(TransmissionBuilder builder) {
		
		this.currentGear = builder.currentGear; 
		this.totalGears = builder.totalGears; 
		this.hasReverse = builder.hasReverse; 
	}
	
	public void shiftGearUp() {
		if (this.currentGear + 1 <= this.totalGears) {
			this.currentGear ++; 
		}
		else {
			//TODO: Tell user they cannot do this
		}
	}
	public void shiftGearDown() {
		
		//Reverse allows shift to -1 
		if (this.hasReverse) {
			
			if (this.currentGear >= 0) {
				this.currentGear--; 
			}
			else {
				//TODO: Tell use they cannot shift below -1 (Reverse) 
			}
			
		}
		//Car does not support reverse
		else {
			if (this.currentGear > 0) {
				this.currentGear--; 
			}
			else {
				//TODO: Tell user they cannot shift below 0 (No reverse) 
			}
			
		}
	}
	public Integer getCurrentGear() {
		return this.currentGear; 
	}
	public Integer getTotalGears() {
		return this.totalGears; 
	}

	
	public static class TransmissionBuilder {
		private Integer currentGear = null; 
		private Integer totalGears = null; 
		private Boolean hasReverse = null; 
	
		public TransmissionBuilder withCurrentGear(int currentGear) {
			this.currentGear = currentGear; 
			return this; 
		}
		public TransmissionBuilder withTotalGear(int totalGears) {
			this.totalGears = totalGears; 
			return this; 
		}
		public TransmissionBuilder withHasReverse(boolean hasReverse) {
			this.hasReverse = hasReverse; 
			return this; 
		}
		public Transmission build() throws NullDataFoundAtBuild {
			
			if (this.currentGear == null) {
				throw new NullDataFoundAtBuild("currentGear == null"); 
			}
			if (this.totalGears == null) {
				throw new NullDataFoundAtBuild("totalGears == null"); 
			}
			if (this.hasReverse == null) {
				throw new NullDataFoundAtBuild("hasReverse == null"); 
			}
		
			return new Transmission(this); 
		}
		
	}
	
}
