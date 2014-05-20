package tlob.model;


public class Underground extends Monster{
	
	private boolean underground = true;
	
	public Underground (int lifePoint, int xPos, int yPos, int speed,int direction,String name)
	{
		super(lifePoint, xPos, yPos,speed, direction, name);
		setInvicible();
	}
	
	
	public boolean getUnderground()
	{
		return underground;
	}
	
	public void setUnderground(boolean underGround)
	{
		this.underground = underGround;
	}

}
