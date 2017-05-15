
public class BlackDaisy extends Patch implements Daisy {
	public BlackDaisy(){
		super(WorldConstants.DEFAULT_BLACK_ALBEDO,WorldConstants.INITIAL_TEMPERATURE,"Black Daisy");
	}

	@Override
	public boolean updateSurvivability() {

		return false;
	}

}
