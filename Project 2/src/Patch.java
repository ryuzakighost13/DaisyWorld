
public abstract class Patch {
	private double albedo;
	private double temperature;
	private String contents;
	private Key location;
	/**
	 * @param albedo The fraction of sunlight being absorbed by the patches
	 * @param temperature The Initial Temperature of the Patch
	 */
	public Patch(double albedo,double temperature,String contents,Key location){
		this.albedo = albedo;
		this.temperature = temperature;
		this.contents = contents;
		this.location = location;
	}
	
	public void updateTemperature(){
		
	}
	
	public String getContents(){
		return contents;
	}
	
	public Key getLocation(){
		return location;
	}
}
