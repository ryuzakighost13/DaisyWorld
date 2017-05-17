
public class WhiteDaisy extends Patch implements Daisy {
	private int age;
	public WhiteDaisy(Key location){
		super(WorldConstants.DEFAULT_WHITE_ALBEDO,WorldConstants.INITIAL_TEMPERATURE,"W",location);
		age = 0;
	}

	@Override
	//boolean = true if plant is still alive, false if plant is dead
	//also need a list of neighbours that have been seeded
	public boolean updateSurvivability() {

		return false;
	}

}
