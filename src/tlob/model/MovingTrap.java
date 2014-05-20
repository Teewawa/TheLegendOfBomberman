package tlob.model;

public class MovingTrap extends Monster {
	
	public MovingTrap(int lifePoint, int xPos, int yPos, int speed,int direction,String name)
	{
		super(lifePoint, xPos, yPos,speed, direction, name);
		setInvicible();
	}

}
