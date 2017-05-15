import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class World {
	private HashMap<Key,Patch> patchMap;
	private int worldSize = WorldConstants.X_PATCHES*WorldConstants.Y_PATCHES;
	private Random randomGenerator;
	private int tickNum = 0;
	public World(){		
		randomGenerator = new Random();
		patchMap = new HashMap<Key,Patch>();
		ArrayList<Key> emptyPatchList = new ArrayList<Key>();
		int initBlack = (int) Math.round(worldSize*WorldConstants.PERCENT_OF_BLACK);
		int initWhite = (int) Math.round(worldSize*WorldConstants.PERCENT_OF_WHITE);
		//Initialize the patch hashmap
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Key key = new Key(i,j);
				patchMap.put(key , new EmptyPatch());
				emptyPatchList.add(key);
			}
		}
		//put black daisies randomly in the world
		while(initBlack > 0 && emptyPatchList.size()>0){
			int i = randomGenerator.nextInt(emptyPatchList.size());
			Key randKey = emptyPatchList.get(i);
			patchMap.put(randKey, new BlackDaisy());
			emptyPatchList.remove(i);
			initBlack --;
		}
		//put white daisies randomly in the world
		while(initWhite > 0 && emptyPatchList.size()>0){
			int i = randomGenerator.nextInt(emptyPatchList.size());
			Key randKey = emptyPatchList.get(i);
			patchMap.put(randKey, new WhiteDaisy());
			emptyPatchList.remove(i);
			initWhite --;
		}
	}
	
	public void update(){
		tickNum ++;
		for(Patch patch : patchMap.values()){
			patch.updateTemperature();
			if(patch instanceof Daisy){
				if(!((Daisy) patch).updateSurvivability()){
					patch = new EmptyPatch();
				};
			}
		}
	}
	//prints out the world as a grid of patches, shows "-" for empty, "B" for Black, "W" for White. 
	public void printWorld(){
		System.out.println("Number of Patches: " + worldSize);
		for(int i=0;i<WorldConstants.Y_PATCHES;i++){
			System.out.print("-");
			for(int j=0;j<WorldConstants.X_PATCHES;j++){
				System.out.print("--");
			}
			System.out.print("\n|");
			for(int j=0;j<WorldConstants.X_PATCHES;j++){
				System.out.print(patchMap.get(new Key(i,j)).getContents() + "|");
			}
			System.out.print("\n");
		}
		System.out.print("-");
		for(int j=0;j<WorldConstants.X_PATCHES;j++){
			System.out.print("--");
		}
		System.out.print("\n");
	}
}
