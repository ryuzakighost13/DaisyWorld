/** Main class that runs the program
*/

import java.util.Scanner;

public class Main {
	/* The world to be simulated */
	private static World world;
	/* Starting percentage of white daisies */
	private static double perWhite;
	/* Starting percentage of black daisies */
	private static double perBlack;
	/* Starting luminosity of the world */
	private static double luminosity;
	/* Starting surface albedo of the world */
	private static double surfaceAlbedo;
	/* Starting albedo of white daisies */
	private static double whiteAlbedo;
	/* Starting albedo of black daisies */
	private static double blackAlbedo;
	/* Luminosity type for the simulation to undergo (see WorldConstants for 
	 * what different values mean)
	*/
	private static int stableLuminosity;

	public static void main(String[] args) {
		//setupWorld();

		world = new World();
		printStart();
		
		for(int i=0;i<WorldConstants.RUN_DURATION;i++){
			world.printWorld();
			world.update();
		}
		world.printWorld();
		
		world.printFinal();
	}

	/** Print the headings for each column in the output (used for csv files)
	*/
	private static void printStart(){
		if(WorldConstants.OUTPUT_TYPE == 1){
			System.out.println("Tick,Temperature");
		}else if(WorldConstants.OUTPUT_TYPE == 2){
			System.out.println("Tick,Soil Quality");
		}else if(WorldConstants.OUTPUT_TYPE == 3){
			System.out.println("Tick,Number of Patches,White daisies,Black daisies");
		}else if(WorldConstants.OUTPUT_TYPE == 4){
			System.out.println("Tick,Number of Patches,White daisies,Black daisies,Temperature,Soil Quality,Luminosity");
		}
	}
	
	/**
	 * Set up the world using default or customize settings
	 */
	private static void setupWorld() {
		Scanner reader = new Scanner(System.in);

		//check if use default setting
		System.out.println("Customize world? (y/n) ");
		String customize = reader.next().toLowerCase();
		while (!(customize.equals("n") || customize.equals("y"))) {
			System.out.println("Customize world? (y/n) ");
			customize = reader.next().toLowerCase();
		}
		if (customize.equals("n")) {
			world = new World();
		} else {
			System.out.println("Start percentage of white daisies(0-0.5): ");
			perWhite = reader.nextDouble();
			while (perWhite < 0 || perWhite > 0.5) {
				System.out.println("Percentage should between 0 and 0.5.");
				System.out.println("Start percentage of white daisies(0-0.5): ");
				perWhite = reader.nextDouble();
			}

			System.out.println("Start percentage of black daisies(0-0.5): ");
			perBlack = reader.nextDouble();
			while (perBlack < 0 || perBlack > 0.5) {
				System.out.println("Percentage should between 0 and 0.5.");
				System.out.println("Start percentage of black daisies(0-0.5): ");
				perBlack = reader.nextDouble();
			}

			System.out.println("Select scenario: ");
			System.out.println("a. Ramp up ramp down");
			System.out.println("b. Low solar luminosity");
			System.out.println("c. Our solar luminosity");
			System.out.println("d. High solar luminosity");
			System.out.println("e. Maintain current luminosity");
			String scenario = reader.next().toLowerCase();
			stableLuminosity = 0;
			if (scenario.equals("a")) {
				luminosity = 0.8;
				stableLuminosity = 1;
			} else if (scenario.equals("b")) {
				luminosity = 0.6;
			} else if (scenario.equals("c")) {
				luminosity = 1.0;
			} else if (scenario.equals("d")) {
				luminosity = 1.4;
			} else {
				System.out.println("Solar luminosity(0.001-3.000): ");
				luminosity = reader.nextDouble();
				while (luminosity < 0.001 || luminosity > 3) {
					System.out.println("Luminosity should between 0.001 and 3.");
					System.out.println("Solar luminosity(0.001-3.000): ");
					luminosity = reader.nextDouble();
				}
			}

			System.out.println("Albedo of surface(0-1): ");
			surfaceAlbedo = reader.nextDouble();
			while (surfaceAlbedo < 0 || surfaceAlbedo > 1) {
				System.out.println("Albedo should between 0 and 1.");
				System.out.println("Albedo of Surface(0-1): ");
				surfaceAlbedo = reader.nextDouble();
			}

			System.out.println("Albedo of white daisies(0-0.99): ");
			whiteAlbedo = reader.nextDouble();
			while (whiteAlbedo < 0 || whiteAlbedo > 0.99) {
				System.out.println("Albedo should between 0 and 0.99.");
				System.out.println("Albedo of white daisies(0-0.99): ");
				whiteAlbedo = reader.nextDouble();
			}

			System.out.println("Albedo of black daisies(0-0.99): ");
			blackAlbedo = reader.nextDouble();
			while (blackAlbedo < 0 || blackAlbedo > 0.99) {
				System.out.println("Albedo should between 0 and 0.99.");
				System.out.println("Albedo of black daisies(0-0.99): ");
				blackAlbedo = reader.nextDouble();
			}

			reader.close();

			//setup world
			world = new World(luminosity, stableLuminosity, surfaceAlbedo, whiteAlbedo,
					blackAlbedo, perWhite, perBlack);
		}
	}

}
