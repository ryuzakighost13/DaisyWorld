/** Parameter class that can be edited to change the parameters of each
*** individual simulation
*/

public class WorldConstants {
	/* Albedo of white daisies */
	public final static double DEFAULT_WHITE_ALBEDO = 0.75;
	/* Albedo of black daisies */
	public final static double DEFAULT_BLACK_ALBEDO = 0.25;
	/* Max age of black daisies */
	public final static int MAX_AGE_BLACK = 26;
	/* Max age of white daisies */
	public final static int MAX_AGE_WHITE = 26;
	/* Albedo of the surface */
	public final static double DEFAULT_SURFACE_ALBEDO = 0.4;
	/* Initial temperature of the planet */
	public final static double INITIAL_TEMPERATURE = 0;
	/* Width (in cells) of the total simulation area */
	public final static int X_PATCHES = 28;
	/* Height (in cells) of the total simulation area */
	public final static int Y_PATCHES = 28;
	/* Starting percentage of black daisies */
	public final static double PERCENT_OF_BLACK = 0.2;
	/* Starting percentage of white daisies */
	public final static double PERCENT_OF_WHITE = 0.2;
	/* Starting luminosity of the simulation */
	public final static double DEFAULT_LUMINOSITY = 0.8;
	/* How many ticks the simulation runs for */
	public final static int RUN_DURATION = 1000;
	
	/* The luminosity model to follow during simulation
	 * 0 = Stable luminosity
	 * 1 = Luminosity fluctuates from low-high-low over a period of time
	*/
	public final static int LUMINOSITY_MODEL = 0;
	
	/* Value that a black daisy degrades the soil by */
	public final static double SOIL_DEGRADE_BLACK = 0.02;
	/* Value that a white daisy degrades the soil by */
	public final static double SOIL_DEGRADE_WHITE = 0.01;
	/* How much the soil recovers when no daisy is present */
	public final static double SOIL_RECOVERY = 0.05;
	/* The minimum value of soil when being randomly generated */
	public final static double DEFAULT_MIN_SOIL = 0.75;
	/* The minimum value of soil quality that white daisies can live in */
	public final static double SOIL_SURVIVAL_WHITE = 0.3;
	/* The minimum value of soil quality that black daisies can live in */
	public final static double SOIL_SURVIVAL_BLACK = 0.5;
	
	/* Determines the type of output when program is run:
	 * 0 = default output
	 * 1 = outputs the global temperature per tick in a csv compliant format
	 * 2 = outputs the global soil quality per tick in a csv compliant format
	 * 3 = outputs the number of each type of daisy per tick in csv compliant 
	 *     format
	 * 4 = outputs all of the above (sans default) per tick in a csv compliant format
	*/
	public final static int OUTPUT_TYPE = 4;
	
	/* Determines whether the final averages for the simulation are printed */
	public final static boolean PRINT_FINAL = false;
}
