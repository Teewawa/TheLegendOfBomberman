package tlob.model;

public interface Tick {
	
	int myTick = 0;
	int t = 0;
	
	public void tick(int constante);
	public void tick(int frames, int constante);
}
