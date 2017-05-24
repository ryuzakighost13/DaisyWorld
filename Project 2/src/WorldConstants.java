public class WorldConstants {
	public final static double DEFAULT_WHITE_ALBEDO = 0.65;
	public final static double DEFAULT_BLACK_ALBEDO = 0.25;
	public final static int MAX_AGE_BLACK = 26;
	public final static int MAX_AGE_WHITE = 26;
	public final static double DEFAULT_SURFACE_ALBEDO = 0.4;
	public final static double INITIAL_TEMPERATURE = 0;
	public final static int X_PATCHES = 28;
	public final static int Y_PATCHES = 28;
	public final static double PERCENT_OF_BLACK = 0.2;
	public final static double PERCENT_OF_WHITE = 0.2;
	public final static double DEFAULT_LUMINOSITY = 0.8;
	public final static int RUN_DURATION = 1000;
	
	/* The luminosity model to follow during simulation
	 * 0 = Stable luminosity
	 * 1 = Luminosity fluctuates from low-high-low over a period of time
	*/
	public final static int LUMINOSITY_MODEL = 0;
	
	/* Value that a black daisy degrades the soil by */
	public final static double SOIL_DEGRADE_BLACK = 0.05;
	/* Value that a white daisy degrades the soil by */
	public final static double SOIL_DEGRADE_WHITE = 0.01;
	/* How much the soil recovers when no daisy is present */
	public final static double SOIL_RECOVERY = 0.01;
	/* The minimum value of soil when being randomly generated */
	public final static double DEFAULT_MIN_SOIL = 0.75;
	/* The minimum value of soil quality that white daisies can live in */
	public final static double SOIL_SURVIVAL_WHITE = 0.8;
	/* The minimum value of soil quality that black daisies can live in */
	public final static double SOIL_SURVIVAL_BLACK = 0.3;
	
	/* Determines the type of output when program is run:
	 * 0 = default output
	 * 1 = outputs the global temperature per tick in a csv compliant format
	 * 2 = outputs the global soil quality per tick in a csv compliant format
	 * 3 = outputs the number of each type of daisy per tick in csv compliant format
	 * 4 = outputs all of the above (sans default) per tick in a csv compliant format
	*/
	public final static int OUTPUT_TYPE = 4;
	
	public final static int PRINT_FINAL = 0;
}
