package tlob.model;

import java.util.List;

public class Bomber extends Monster {
	
	private int tick = 0;
	private int bombFrame = 1;
	
	public Bomber (int lifePoint, int xPos, int yPos, int speed, Direction direction, String name)
	{
		super(lifePoint, xPos, yPos,speed, direction, name);
		setTime(40);
	}
	
	public void setBomb(List<Bomb> liste)
	{
		
		int k = 1;
		int[] c = getCenterCase();
		
		for(Bomb b : liste)
			if(b.getCenter().equals(c))
				k = 0;
				
		if (k == 1) {
			Bomb b = new Bomb(xPos, yPos, "res/Monster/BombMonster", null);
			b.centerOn(c);
			liste.add(b);
		}
	}
	
	public void bombTick(int frames, int constante) {
		tick++;
		if(tick == constante) {
			bombFrame++;
			tick = 0;
			if(bombFrame == frames +1)
				this.bombFrame = 1;
		}
	}
	
	public int getBombFrame(){
		return this.bombFrame;
	}
	
	public void setBombFrame(int n){
		this.bombFrame = n;
	}
	
}
