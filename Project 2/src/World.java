/** Class that runs the simulation
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class World {
	/* Map of all the patches (cells) in the word */
	private HashMap<Key,Patch> patchMap;
	/* The size of the word (width * height) */
	private int worldSize = WorldConstants.X_PATCHES*WorldConstants.Y_PATCHES;
	/* Used for producing random values */
	private Random randomGenerator;
	/* Counts the number of ticks the simulation has gone through */
	private int tickNum = 0;
	/* List of all the empty patches (to place initial daisies in) */
	private ArrayList<Key> emptyPatchList;
	/* Luminosity of the simulation */
	private double solarLuminosity;
	/* Albedo of the surface */
	private double surfaceAlbedo;
	/* Albedo of white daisies */
	private double whiteAlbedo;
	/* Albedo of black daisies */
	private double blackAlbedo;
	/* Percentage of world with white daisies at start of simulation */
	private double perWhite;
	/* Percentage of world with black daisies at start of simulation */
	private double perBlack;
	/* Type of luminosity alteration to do */
	private int stableLuminosity;
	/* Sum of global temperatures throughout the simulation */
	private double sumTemp = 0;
	/* Sum of the global soil quality throughout the simulation */
	private double sumSoil = 0;
	/* Sum of the number of white daisies throughout the simulation */
	private int sumWhite = 0;
	/* Sum of the number of black daisies throughout the simulation */
	private int sumBlack = 0;

	/**
	 * Set up the world using default settings
	 */
	public World(){
		this.solarLuminosity = WorldConstants.DEFAULT_LUMINOSITY;
		this.surfaceAlbedo = WorldConstants.DEFAULT_SURFACE_ALBEDO;
		this.whiteAlbedo = WorldConstants.DEFAULT_WHITE_ALBEDO;
		this.blackAlbedo = WorldConstants.DEFAULT_BLACK_ALBEDO;
		this.perWhite = WorldConstants.PERCENT_OF_WHITE;
		this.perBlack = WorldConstants.PERCENT_OF_BLACK;
		this.stableLuminosity = WorldConstants.LUMINOSITY_MODEL;
		setup();
	}

	/**
	 * Set up world using customized settings
	 * @param solarLuminosity solar luminosity
	 * @param surfaceAlbedo albedo of surface
	 * @param whiteAlbedo albedo of white daisies
	 * @param blackAlbedo albedo of black daisies
	 * @param perWhite start percentage of white daisies
	 * @param perBlack start percentage of black daisies
	 */
	public World(double solarLuminosity, int stableLuminosity, 
	             double surfaceAlbedo, double whiteAlbedo,
				 double blackAlbedo, double perWhite, double perBlack) {
		this.solarLuminosity = solarLuminosity;
		this.stableLuminosity = stableLuminosity;
		this.surfaceAlbedo = surfaceAlbedo;
		this.whiteAlbedo = whiteAlbedo;
		this.blackAlbedo = blackAlbedo;
		this.perWhite = perWhite;
		this.perBlack = perBlack;
		setup();
	}

	/**
	 * Set up the world
	 */
	public void setup() {
		randomGenerator = new Random();
		patchMap = new HashMap<Key,Patch>();
		emptyPatchList = new ArrayList<Key>();
		int initBlack = (int) Math.round(worldSize*perBlack);
		int initWhite = (int) Math.round(worldSize*perWhite);
		//Initialize the patch hashmap
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Key key = new Key(i,j);
				double soilQualityChance = Math.random()*(1-WorldConstants.DEFAULT_MIN_SOIL)
                                         + WorldConstants.DEFAULT_MIN_SOIL;
				patchMap.put(key , new EmptyPatch(key, surfaceAlbedo, soilQualityChance));
				emptyPatchList.add(key);
			}
		}
		//put black daisies randomly in the world
		while(initBlack > 0 && emptyPatchList.size()>0){
			int i = randomGenerator.nextInt(emptyPatchList.size());
			Key randKey = emptyPatchList.get(i);
			double quality = patchMap.get(randKey).getSoilDegradation();
			patchMap.put(randKey, new BlackDaisy(randKey,
					(int)(Math.random()*WorldConstants.MAX_AGE_BLACK), blackAlbedo,quality));
			emptyPatchList.remove(i);
			initBlack --;
		}
		//put white daisies randomly in the world
		while(initWhite > 0 && emptyPatchList.size()>0){
			int i = randomGenerator.nextInt(emptyPatchList.size());
			Key randKey = emptyPatchList.get(i);
			double quality = patchMap.get(randKey).getSoilDegradation();
			patchMap.put(randKey, new WhiteDaisy(randKey,
					(int)(Math.random()*WorldConstants.MAX_AGE_WHITE), whiteAlbedo,quality));
			emptyPatchList.remove(i);
			initWhite --;
		}
		updateTemperature();
	}
	
	/** Updates the world (done each tick)
	*/
	public void update(){
		tickNum ++;
		updateTemperature();
		updateDaisySpawn();
		
		/* Add the sums of each of the end statistics */
		sumTemp += this.calculateGlobalTemp();
		sumSoil += this.calculateSoilQuality();
		sumWhite += this.calculateWhiteDaisy();
		sumBlack += this.calculateBlackDaisy();
	}
	
	/** Updates the temperature of each patch (cell)
	*/
	public void updateTemperature(){
		//ramp up ramp down scenario
		if(stableLuminosity==1) {
			if (tickNum > 200 && tickNum <= 400) {
				solarLuminosity += 0.005;
			}
			if (tickNum > 600 && tickNum <= 850) {
				solarLuminosity -= 0.0025;
			}
			if (solarLuminosity > 3.000) {
				solarLuminosity = 3.000;
			}
			if (solarLuminosity < 0.001) {
				solarLuminosity = 0.001;
			}
		}
		/* Update the temperature of each patch */
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Patch patch = patchMap.get(new Key(i,j));
				patch.updateTemperature(solarLuminosity);
			}
		}
		/* Diffuse that temperature */
		setDiffusion();
		diffuse();
	}
	
	/** Updates the spawns of new daisies
	*/
	public void updateDaisySpawn(){
		/* For each patch (cell) */
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Patch patch = patchMap.get(new Key(i,j));
				/* If that patch has a daisy */
				if(patch instanceof Daisy){
					/* If the daisy isn't dead */
					if(((Daisy) patch).updateSurvivability()){
						double seedThreshold = ((0.1457 * patch.getTemp()) - 
						                       (0.0032 * (Math.pow(patch.getTemp(),2))) - 0.6443);
						double rand = Math.random();
						/* If the chance of seeding is within the temperature
						 * threshold and if the daisy isn't newly spawned
						 */
						if(rand<seedThreshold && ((Daisy)patch).getAge()>2){
							ArrayList<Patch> emptyPatches = 
							    getEmptyAdjacentPatches(i, j);
							/* While there are new patches */
							while(emptyPatches.size()>0){
								int index = (int)(Math.random()*(emptyPatches.size()-1));
								Patch seedPatch = emptyPatches.get(index);
								emptyPatches.remove(seedPatch);
								Key key = seedPatch.getLocation();
								/* Check which daisy type is to be placed */
								if(patch instanceof WhiteDaisy){
									double soil = 0;
									/* Determines the threshold for a new daisy to be born
									 * in certain soil qualities
									*/
									if(patch.soilQuality - 
									         WorldConstants.SOIL_SURVIVAL_WHITE <= 0){
											soil = 0;
									}else if(WorldConstants.SOIL_SURVIVAL_WHITE >= 1){
										soil = 0;
									}else if(WorldConstants.SOIL_SURVIVAL_WHITE <= 0){
										soil = 400;
									}else{
										soil = (patch.soilQuality - 
										        WorldConstants.SOIL_SURVIVAL_WHITE)
												/(1-WorldConstants.SOIL_SURVIVAL_WHITE) * 400;
									}
									
									/* If the new daisy can be planted, plant the daisy then break
									 * out of the loop
									*/
									if(patch.soilQuality > WorldConstants.SOIL_SURVIVAL_WHITE &&
									   rand < Math.pow(soil,0.5)/20){
										patchMap.put(key, new WhiteDaisy(key,seedPatch.getTemp(),
										                      whiteAlbedo,
															  seedPatch.getSoilDegradation()));
										break;
									}
								/* Does the same process, but for black daisies */
								}else if(patch instanceof BlackDaisy){
									double soil;
									if(patch.soilQuality - 
									         WorldConstants.SOIL_SURVIVAL_BLACK <= 0){
											soil = 0;
									}else if(WorldConstants.SOIL_SURVIVAL_BLACK >= 1){
										soil = 0;
									}else if(WorldConstants.SOIL_SURVIVAL_BLACK <= 0){
										soil = 400;
									}else{
										soil = (patch.soilQuality - 
										        WorldConstants.SOIL_SURVIVAL_BLACK)
												/(1-WorldConstants.SOIL_SURVIVAL_BLACK) * 400;
									}
									
									if(patch.soilQuality > WorldConstants.SOIL_SURVIVAL_BLACK &&
									   rand < Math.pow(soil,0.5)/20){
										patchMap.put(key, new BlackDaisy(key,seedPatch.getTemp(),
										                      blackAlbedo,
															  seedPatch.getSoilDegradation()));
										break;
									}
								}
							}
						}
					/* Otherwise, delete the daisy */
					}else{
						Key loc = patch.getLocation();
						double temp = patch.getTemp();
						double quality = patch.getSoilDegradation();
						patchMap.put(loc , new EmptyPatch(loc,temp, surfaceAlbedo,quality));
					}
				/* Otherwise, modify the soil degradation */
				}else{
					patch.reduceSoilDegradation();
				}
			}
		}
	}
	
	/** Calculates the global temperature
	*/
	private double calculateGlobalTemp(){
		double temp = 0;
		int count = 0;
		for(Patch patch : patchMap.values()){
			temp+=patch.getTemp();
			count+=1;
		}
		return temp/count;
	}
	
	/** Calculates the global soil quality
	*/
	private double calculateSoilQuality(){
		double temp = 0;
		int count = 0;
		for(Patch patch : patchMap.values()){
			temp+=patch.getSoilDegradation();
			count+=1;
		}
		return temp/count;
	}
	
	/** Calculates the number of white daisies
	*/
	private int calculateWhiteDaisy(){
		int count = 0;
		for(Patch patch : patchMap.values()){
			if(patch instanceof WhiteDaisy){
				count+=1;
			}
		}
		return count;
	}
	
	/** Calculates the number of black daisies
	*/
	private int calculateBlackDaisy(){
		int count = 0;
		for(Patch patch : patchMap.values()){
			if(patch instanceof BlackDaisy){
				count+=1;
			}
		}
		return count;
	}
	
	/** Sets the diffusion values for each patch
	*/
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
	
	/** Applies the diffusion values to each patch
	*/
	private void diffuse(){
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Key key = new Key(i,j);
				Patch patch = patchMap.get(key);
				patch.diffuse();
			}
		}
	}
	
	/** Generates a key value from an x/y value that is wrapped around the world space
	*/
	private Key wrap(int x, int y){
		if(x<0){
			x=WorldConstants.X_PATCHES-1;
		}
		if(y<0){
			y=WorldConstants.Y_PATCHES-1;
		}
		return new Key(x%WorldConstants.X_PATCHES,y%WorldConstants.Y_PATCHES);
	}
	
	/** Gets all the adjacent empty patches from an x-y position
	*/
	private ArrayList<Patch> getEmptyAdjacentPatches(int x, int y){
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
	
	/** Prints out the world as a grid of patches, shows "-" for empty, "B" for Black, 
	*** "W" for White
	*/
	public void printWorld(){
		
		/* If an outpute type has been chosen, print only that output,
		 * see WorldConstants for the outputs from each type
		*/
		if(WorldConstants.OUTPUT_TYPE == 1){
			System.out.println(tickNum+","+calculateGlobalTemp());
			return;
		}else if(WorldConstants.OUTPUT_TYPE == 2){
			System.out.println(tickNum+","+calculateSoilQuality());
			return;
		}else if(WorldConstants.OUTPUT_TYPE == 3){
			System.out.println(tickNum+","+worldSize+","+calculateWhiteDaisy()+","
			                  +calculateBlackDaisy());
			return;
		}else if(WorldConstants.OUTPUT_TYPE == 4){
			System.out.println(tickNum+","+worldSize+","+calculateWhiteDaisy()+","
			                  +calculateBlackDaisy()+","+calculateGlobalTemp()+","
							  +calculateSoilQuality() + "," + this.solarLuminosity);
			return;
		}
		
		/* Otherwise, print a full display of the current tick */
		System.out.println("Number of Patches: " + worldSize);
		System.out.println("Current tick: " + tickNum);
		System.out.println("Current global temperature: " + calculateGlobalTemp());
		System.out.println("Current soil quality: " + calculateSoilQuality());
		System.out.println("White daisy population: " + calculateWhiteDaisy());
		System.out.println("Black daisy population: " + calculateBlackDaisy());
		
		/* Print a visual display of the current simulation grid */
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
	
	/** Print the final statistics (averages) after the simulation has finished
	*/
	public void printFinal(){
		if(WorldConstants.PRINT_FINAL){
			System.out.println("Tick Number: " +tickNum);
			System.out.println("Average Global Temperature: "+sumTemp/tickNum);
			System.out.println("Average Soil Quality: "+sumSoil/tickNum);
			System.out.println("Average Number of White Daisies: "+((double)sumWhite)/tickNum);
			System.out.println("Average Number of Black Daisies: "+((double)sumBlack)/tickNum);
		}
	}
	
}
