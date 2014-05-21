package tlob.model;

import java.util.List;

public class Ranged extends Monster {
	
	public Ranged (int lifePoint, int xPos, int yPos, int speed, Direction direction, String name)
	{
		super(lifePoint, xPos, yPos,speed, direction,name);
	}
	
	
	public Arrow fireArrow() {
		Arrow a = new Arrow(xPos, yPos, "res/Arrow", direction, null);
		a.centerOn( new int[]{getXPos() + getWidth() / 2, getYPos() + getHeight() / 2} );
		setName("res/RangedRun");
		return a;
	}

}
