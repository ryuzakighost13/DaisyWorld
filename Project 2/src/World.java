import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class World {
	private HashMap<Key,Patch> patchMap;
	private int worldSize = WorldConstants.X_PATCHES*WorldConstants.Y_PATCHES;
	private Random randomGenerator;
	private int tickNum = 0;
	private ArrayList<Key> emptyPatchList;
	private double solarLuminosity = WorldConstants.DEFAULT_LUMINOSITY;
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
			patchMap.put(randKey, new BlackDaisy(randKey,(int)(Math.random()*WorldConstants.DEFAULT_DAISY_MAX_AGE)));
			emptyPatchList.remove(i);
			initBlack --;
		}
		//put white daisies randomly in the world
		while(initWhite > 0 && emptyPatchList.size()>0){
			int i = randomGenerator.nextInt(emptyPatchList.size());
			Key randKey = emptyPatchList.get(i);
			patchMap.put(randKey, new WhiteDaisy(randKey,(int)(Math.random()*WorldConstants.DEFAULT_DAISY_MAX_AGE)));
			emptyPatchList.remove(i);
			initWhite --;
		}
		updateTemperature();
	}
	
	public void update(){
		tickNum ++;
		updateTemperature();
		
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Patch patch = patchMap.get(new Key(i,j));
				if(patch instanceof Daisy){
					if(!((Daisy) patch).updateSurvivability()){
						Key loc = patch.getLocation();
						double temp = patch.getTemp();
						patchMap.put(loc , new EmptyPatch(loc,temp));
					}else{
						double seedThreshold = ((0.1457 * patch.getTemp()) - (0.0032 * (Math.pow(patch.getTemp(),2)))-0.6443);
						double rand = Math.random();
						if(rand < seedThreshold){
							ArrayList<Patch> emptyPatches = getEmptyAdjacentPatches(i, j);
							if(emptyPatches.size()>0){
								int index = (int)(Math.random()*(emptyPatches.size()-1));
								Patch seedPatch = emptyPatches.get(index);
								Key key = seedPatch.getLocation();
								if(patch instanceof WhiteDaisy){
									patchMap.put(key, new WhiteDaisy(key,seedPatch.getTemp()));
								}else if(patch instanceof BlackDaisy){
									patchMap.put(key, new BlackDaisy(key,seedPatch.getTemp()));
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	public void updateTemperature(){
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Patch patch = patchMap.get(new Key(i,j));
				patch.updateTemperature(solarLuminosity);
			}
		}
		setDiffusion();
		diffuse();
	}
	
	private double calculateGlobalTemp(){
		double temp = 0;
		int count = 0;
		for(Patch patch : patchMap.values()){
			temp+=patch.getTemp();
			count+=1;
		}
		return temp/count;
	}
	
	private void setDiffusion(){
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Key key = new Key(i,j);
				Patch patch = patchMap.get(key);
				patch.tempDiffuse = 0;
				for(int k=-1;k<=1;k++){
					for(int l=-1;l<=1;l++){
						if(!(l==0 && k==0)){
							patch.tempDiffuse+=patchMap.get(wrap(i+k,j+l)).getTemp()/16;
						}
					}
				}
			}
		}
	}
	
	private void diffuse(){
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Key key = new Key(i,j);
				Patch patch = patchMap.get(key);
				patch.diffuse();
			}
		}
	}
	
	private Key wrap(int x, int y){
		if(x<0){
			x=WorldConstants.X_PATCHES-1;
		}
		if(y<0){
			y=WorldConstants.Y_PATCHES-1;
		}
		return new Key(x%WorldConstants.X_PATCHES,y%WorldConstants.Y_PATCHES);
	}
	
	ArrayList<Patch> getEmptyAdjacentPatches(int x, int y){
		ArrayList<Patch> emptyPatches = new ArrayList<Patch>();
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				if(!(i==0 && j==0)){
					Patch patch = patchMap.get(wrap(x+i,y+j));
					if(patch instanceof EmptyPatch){
						emptyPatches.add(patch);
					}
				}
			}
		}
		return emptyPatches;
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
		System.out.println("Current tick: " + tickNum);
		System.out.println("Current global temperature: " + calculateGlobalTemp());
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
