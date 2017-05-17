
public class BlackDaisy extends Patch implements Daisy {
	private int age;
	public BlackDaisy(Key location, int age){
		super(WorldConstants.DEFAULT_BLACK_ALBEDO,WorldConstants.INITIAL_TEMPERATURE,"B",location);
		this.age = age;
	}
	public BlackDaisy(Key location, double temp){
		super(WorldConstants.DEFAULT_BLACK_ALBEDO,temp,"B",location);
		age = 1;
	}

	@Override
	//boolean = true if plant is still alive, false if plant is dead
	//also need a list of neighbours that have been seeded
	public boolean updateSurvivability() {
		if(age == WorldConstants.DEFAULT_DAISY_MAX_AGE){
			return false;
		}else{
			age+=1;
		}
		return true;
	}

}
