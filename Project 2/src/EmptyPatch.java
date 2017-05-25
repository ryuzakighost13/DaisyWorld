/** Empty patch class used to represent patches without a daisy
*/

public class EmptyPatch extends Patch {
	/* Used in initialization when all patches are made */
	public EmptyPatch(Key location, double albedo, double soilQuality){
		super(albedo,WorldConstants.INITIAL_TEMPERATURE,"-",location, soilQuality);
	}
	/* Used when a daisy patch becomes empty */
	public EmptyPatch(Key location, double temp, double albedo, double soilQuality){
		super(albedo,temp,"-",location, soilQuality);
	}
}
