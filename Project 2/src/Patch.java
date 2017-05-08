
public abstract class Patch {
	private double albedo;
	private double temperature;
	/**
	 * @param albedo The fraction of sunlight being absorbed by the patches
	 * @param temperature The Initial Temperature of the Patch
	 */
	public Patch(double albedo,double temperature){
		this.albedo = albedo;
		this.temperature = temperature;
	}
}
