
public class EmptyPatch extends Patch {
	public EmptyPatch(Key location, double albedo, double soilQuality){
		super(albedo,WorldConstants.INITIAL_TEMPERATURE,"-",location, soilQuality);
	}
	
	public EmptyPatch(Key location, double temp, double albedo, double soilQuality){
		super(albedo,temp,"-",location, soilQuality);
	}
}
