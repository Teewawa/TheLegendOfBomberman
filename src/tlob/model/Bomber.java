package tlob.model;

import java.util.List;

public class Bomber extends Monster {
	
	private int tick = 0;
	private int bombFrame = 1;
	
	public Bomber (int lifePoint, int xPos, int yPos, int speed,int direction, String name)
	{
		super(lifePoint, xPos, yPos,speed, direction, name);
		setTime(40);
	}
	
	public List<Bomb> setBomb(List<Bomb> liste)
	{
		int x,y;
		int k = 1;
		if(xPos%40 <= 20){
			x = xPos - xPos%40;
		}
		else{
			x = xPos + 40 - xPos%40;
		}
		if(yPos%40 <= 20){
			y = yPos - yPos%40;
		}
		else{
			y = yPos + 40 - yPos%40;
		}
		for(int i = 0; i < liste.size(); i++){
			if(x + 5 == liste.get(i).getXPos() && y + 5 == liste.get(i).getYPos()){
				k = 0;
			}
		}
		if (k == 1){
			liste.add( new Bomb(x+5, y+5, "res/Monster/BombMonster", -1)) ;
		}
		return liste;
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
