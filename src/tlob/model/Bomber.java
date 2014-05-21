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
		int x = xPos%40 <= 20 ?
				xPos - xPos % 40 :
			    xPos + 40 - xPos % 40;
		
		int y = yPos % 40 <= 20 ?
				yPos - yPos % 40 :
			    yPos + 40 - yPos % 40;
		
		for(Bomb b : liste)
			if(x + 5 == b.getXPos() && y + 5 == b.getYPos())
				k = 0;
				
		if (k == 1)
			liste.add( new Bomb(x+5, y+5, "res/Monster/BombMonster", null)) ;
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
