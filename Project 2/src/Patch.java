/** Abstract patch class to represent each cell of the simulation
*/

import java.lang.Math;

public abstract class Patch {
	/* Albedo of the cell */
	private double albedo;
	/* Temperature of the cell */
	private double temperature;
	/* What the cell prints when called */
	private String contents;
	/* Location of the cell in the hashmap */
	private Key location;
	/* Diffusion value of the patch (calculated and then used later) */
	public double tempDiffuse;
	/* Soil quality of the patch */
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
	
	/** Updates the temperature of the patch based on the same calculations from the netlogo
	*** model
	*/
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
	
	/** Diffuse it's own temperature
	*/
	public void diffuse(){
		this.temperature/=2;
		this.temperature+=tempDiffuse;
		this.tempDiffuse = 0;
	}
	
	/** Reduces the soil degradation each tick (if the cell is empty)
	*/
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
