/** Class to represent a white daisy from the netlogo simulation
*/

public class WhiteDaisy extends Patch implements Daisy {
	/* Age of the daisy */
	private int age;
	/* Used for randomly generated daisies (at start of the simulation) */
	public WhiteDaisy(Key location, int age, double albedo, double soilQuality){
		super(albedo,WorldConstants.INITIAL_TEMPERATURE,"W",location, soilQuality);
		this.age = age;
	}
	/* Used for spawned white daisies */
	public WhiteDaisy(Key location, double temp, double albedo, double soilQuality){
		super(albedo,temp,"W",location,soilQuality);
		age = 1;
	}

	@Override
	//boolean = true if plant is still alive, false if plant is dead
	public boolean updateSurvivability() {
		if(age >= WorldConstants.MAX_AGE_WHITE){
			return false;
		}else{
			age+=1;
		}
		
		/* Determine if the daisy can survive based on soil quality */
		if(this.soilQuality < WorldConstants.SOIL_SURVIVAL_WHITE){
			return false;
		}else{
			/* If it survives, degrade the soil */
			this.soilQuality -= WorldConstants.SOIL_DEGRADE_WHITE;
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
