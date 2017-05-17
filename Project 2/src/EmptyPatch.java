
public class EmptyPatch extends Patch {
	public EmptyPatch(Key location){
		super(WorldConstants.DEFAULT_SURFACE_ALBEDO,WorldConstants.INITIAL_TEMPERATURE,"-",location);
	}
	
	public EmptyPatch(Key location, double temp){
		super(WorldConstants.DEFAULT_SURFACE_ALBEDO,temp,"-",location);
	}
}
