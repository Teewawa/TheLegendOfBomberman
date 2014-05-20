package tlob.model;

import java.util.List;

public class Ranged extends Monster {
	
	public Ranged (int lifePoint, int xPos, int yPos, int speed,int direction, String name)
	{
		super(lifePoint, xPos, yPos,speed, direction,name);
	}
	
	
	public List<Arrow> fireArrow(List<Arrow> liste){
		liste.add(new Arrow(xPos, yPos, "res/Arrow", direction, getPlayer()));
		setName("res/RangedRun");
		return liste;
		 
	}

}
