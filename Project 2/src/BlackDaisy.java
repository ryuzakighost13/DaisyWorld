
public class BlackDaisy extends Patch implements Daisy {
	private int age;
	public BlackDaisy(Key location, int age, double albedo, double soilQuality){
		super(albedo,WorldConstants.INITIAL_TEMPERATURE,"B",location, soilQuality);
		this.age = age;
	}
	public BlackDaisy(Key location, double temp, double albedo, double soilQuality){
		super(albedo,temp,"B",location,soilQuality);
		age = 1;
	}

	@Override
	//boolean = true if plant is still alive, false if plant is dead
	//also need a list of neighbours that have been seeded
	public boolean updateSurvivability() {
		if(age == WorldConstants.MAX_AGE_BLACK){
			return false;
		}else{
			age+=1;
		}
		if(this.soilQuality<WorldConstants.SOIL_SURVIVAL_BLACK){
			return false;
		}else{
			this.soilQuality -= WorldConstants.SOIL_DEGRADE_BLACK;
			if(this.soilQuality > 1.0){
				this.soilQuality = 1.0;
			}
			if(this.soilQuality < 0){
				this.soilQuality = 0;
			}
		}
		return true;
	}
	
	@Override
	public int getAge() {
		return this.age;
	}

}
