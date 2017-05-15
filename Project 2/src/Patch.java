
public abstract class Patch {
	private double albedo;
	private double temperature;
	private String contents;
	/**
	 * @param albedo The fraction of sunlight being absorbed by the patches
	 * @param temperature The Initial Temperature of the Patch
	 */
	public Patch(double albedo,double temperature,String contents){
		this.albedo = albedo;
		this.temperature = temperature;
		this.contents = contents;
	}
	
	public void updateTemperature(){
		
	}
	
	public String getContents(){
		return contents;
	}
}
