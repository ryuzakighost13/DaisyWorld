
public class EmptyPatch extends Patch {
	public EmptyPatch(Key location, double albedo){
		super(albedo,WorldConstants.INITIAL_TEMPERATURE,"-",location);
	}
	
	public EmptyPatch(Key location, double temp, double albedo){
		super(albedo,temp,"-",location);
	}
}
