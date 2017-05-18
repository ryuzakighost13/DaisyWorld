import java.util.Scanner;

public class Main {
	private static World world;
	private static double perWhite;
	private static double perBlack;
	private static double luminosity;
	private static double surfaceAlbedo;
	private static double whiteAlbedo;
	private static double blackAlbedo;

	public static void main(String[] args) {
		setupWorld();
		for(int i=0;i<WorldConstants.RUN_DURATION;i++){
			world.printWorld();
			world.update();
		}
		world.printWorld();
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
			if (scenario.equals("a")) {
				luminosity = 0.8;
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
			world = new World(luminosity, surfaceAlbedo, whiteAlbedo,
					blackAlbedo, perWhite, perBlack);
		}
	}

}
