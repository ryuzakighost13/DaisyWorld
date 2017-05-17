
public class Main {
	private static World world;
	public static void main(String[] args) {
		world = new World();
		for(int i=0;i<WorldConstants.RUN_DURATION;i++){
			world.printWorld();
			world.update();
		}
		world.printWorld();
	}

}
