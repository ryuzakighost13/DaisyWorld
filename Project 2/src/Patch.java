import java.lang.Math;

public abstract class Patch {
	private double albedo;
	private double temperature;
	private String contents;
	private Key location;
	public double tempDiffuse;
	protected double soilQuality;
	/**
	 * @param albedo The fraction of sunlight being absorbed by the patches
	 * @param temperature The Initial Temperature of the Patch
	 */
	public Patch(double albedo,double temperature,String contents,Key location, double soilQuality){
		this.albedo = albedo;
		this.temperature = temperature;
		this.contents = contents;
		this.location = location;
		this.soilQuality = soilQuality;
	}
	
	public void updateTemperature(double solarLuminosity){
		double absorbedLuminosity = 0;
		double localHeating = 0;
		
		absorbedLuminosity = ((1 - this.albedo) * solarLuminosity);
		
		if(absorbedLuminosity > 0){
			localHeating = 72*Math.log(absorbedLuminosity) + 80;
		}else{
			localHeating = 80;
		}
		this.temperature = ((this.temperature + localHeating) / 2);
	}
	
	public String getContents(){
		return contents;
	}
	
	public Key getLocation(){
		return location;
	}
	
	public double getTemp(){
		return temperature;
	}
	
	
	public void diffuse(){
		this.temperature/=2;
		this.temperature+=tempDiffuse;
	}
	
	public void reduceSoilDegradation(){
		this.soilQuality += WorldConstants.SOIL_RECOVERY;
		if(this.soilQuality > 1){
			this.soilQuality = 1;
		}
	}
	
	public double getSoilDegradation(){
		return this.soilQuality;
	}
	
}
