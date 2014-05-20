package tlob.model;

import java.util.List;

public class SpawnerFireBall extends Decor {
	
	public SpawnerFireBall(int xPos, int yPos, String name) {
		super(xPos,yPos,name);
	}
	
	public List<FireBall> fireBall(List<FireBall> fireBall, Link Link) 	{
		cdTick(5);
		if(getCooldown() == 40){
			fireBall.add(new FireBall(getXPos()+10, getYPos()+10, "res/Monster/fire", Link.getXPos()+10, Link.getYPos()+10,15*40,15*40));
			setCooldown(0);
		}
		return fireBall;
	}
	
}

