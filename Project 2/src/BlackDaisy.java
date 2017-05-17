
public class BlackDaisy extends Patch implements Daisy {
	private int age;
	public BlackDaisy(Key location){
		super(WorldConstants.DEFAULT_BLACK_ALBEDO,WorldConstants.INITIAL_TEMPERATURE,"B",location);
		age = 0;
	}

	@Override
	//boolean = true if plant is still alive, false if plant is dead
	//also need a list of neighbours that have been seeded
	public boolean updateSurvivability() {

		return false;
	}

}
