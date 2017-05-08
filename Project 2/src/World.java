import java.util.HashMap;

public class World {
	public World(){
		HashMap<Key,Patch> patchMap = new HashMap<Key,Patch>();
		
		//Initialize the patch hashmap
		for(int i=0; i < WorldConstants.X_PATCHES; i++){
			for(int j=0; j < WorldConstants.Y_PATCHES; j++){
				Key key = new Key(i,j);
				patchMap.put(key , new EmptyPatch());
			}
		}
		
	}
}
