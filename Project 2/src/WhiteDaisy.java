
public class WhiteDaisy extends Patch implements Daisy {
	public WhiteDaisy(){
		super(WorldConstants.DEFAULT_WHITE_ALBEDO,WorldConstants.INITIAL_TEMPERATURE,"White Daisy");
	}

	@Override
	public boolean updateSurvivability() {

		return false;
	}

}
