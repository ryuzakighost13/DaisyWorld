import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class World {
	private HashMap<Key,Patch> patchMap;
	private int worldSize = WorldConstants.X_PATCHES*WorldConstants.Y_PATCHES;
	private Random randomGenerator;
	private int tickNum = 0;
	private ArrayList<Key> emptyPatchList;
	public World(){		
		randomGenerator = new Random();
		patchMap = new HashMap<Key,Patch>();
		emptyPatchList = new ArrayList<Key>();
		int initBlack = (int) Math.round(worldSize*WorldConstants.PERCENT_OF_BLACK);
		int initWhite = (int) Math.round(worldSize*WorldConstants.PERCENT_OF_WHITE);
		//Initialize the patch hashmap
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Key key = new Key(i,j);
				patchMap.put(key , new EmptyPatch(key));
				emptyPatchList.add(key);
			}
		}
		//put black daisies randomly in the world
		while(initBlack > 0 && emptyPatchList.size()>0){
			int i = randomGenerator.nextInt(emptyPatchList.size());
			Key randKey = emptyPatchList.get(i);
			patchMap.put(randKey, new BlackDaisy(randKey));
			emptyPatchList.remove(i);
			initBlack --;
		}
		//put white daisies randomly in the world
		while(initWhite > 0 && emptyPatchList.size()>0){
			int i = randomGenerator.nextInt(emptyPatchList.size());
			Key randKey = emptyPatchList.get(i);
			patchMap.put(randKey, new WhiteDaisy(randKey));
			emptyPatchList.remove(i);
			initWhite --;
		}
	}
	
	public void update(){
		tickNum ++;
		ArrayList<Patch> deadPatchList = new ArrayList<Patch>();
		//update temperature and survivability
		for(Patch patch : patchMap.values()){
			patch.updateTemperature();
			if(patch instanceof Daisy){
				if(!((Daisy) patch).updateSurvivability()){
					deadPatchList.add(patch);
				}
			}
		}
		//diffuse temperature
		
		//remove dead plants
		for(Patch patch: deadPatchList){
			Key key = patch.getLocation();
			patch = new EmptyPatch(key);
			emptyPatchList.add(key);
		}
		//grow new plants
		
	}
	
	//returns list of the locations for neighbouring patches, of a specified location
	public ArrayList<Key> getNeighbours(Key location){
		/*
		 * l = left
		 * r = right
		 * u = up
		 * d = down
		 * m = middle
		 */
		int X = location.getX();
		int Y = location.getY();
		ArrayList<Key> neighbours = new ArrayList<Key>();
		//l-m square
		neighbours.add(new Key((X-1)%WorldConstants.X_PATCHES,Y));
		//r-m square
		neighbours.add(new Key((X+1)%WorldConstants.X_PATCHES,Y));
		//m-u square
		neighbours.add(new Key(X,(Y+1)%WorldConstants.Y_PATCHES));
		//m-d square
		neighbours.add(new Key(X,(Y-1)%WorldConstants.Y_PATCHES));
		//l-u square
		neighbours.add(new Key((X-1)%WorldConstants.X_PATCHES,(Y+1)%WorldConstants.Y_PATCHES));
		//r-u square
		neighbours.add(new Key((X+1)%WorldConstants.X_PATCHES,(Y+1)%WorldConstants.Y_PATCHES));
		//l-d square
		neighbours.add(new Key((X-1)%WorldConstants.X_PATCHES,(Y-1)%WorldConstants.Y_PATCHES));
		//r-d square
		neighbours.add(new Key((X+1)%WorldConstants.X_PATCHES,(Y-1)%WorldConstants.Y_PATCHES));
		return neighbours;
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
