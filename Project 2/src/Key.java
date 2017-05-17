
public class Key {
	private int a;
	private int b;
	
	public Key(int a, int b){
		this.a = a;
		this.b = b;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return (a == key.a && b == key.b);
	}
	
    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + b;
        return result;
    }
    
    public int getX(){
    	return a;
    }
    public int getY(){
    	return b;
    }
    
}
