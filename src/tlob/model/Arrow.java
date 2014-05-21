package tlob.model;

public class Arrow extends Item {
	
	private Direction direction;
	private int t = 0;
	private int mytick;
	private int x;
	private int y;
	private Link player;
	
	public Arrow(int xPos, int yPos, String name, Direction direction, Link player){
		super(xPos,yPos,name);
		this.direction = direction;
		this.player = player;
		width = direction.verticale() ? 20 : 30;
		height = direction.verticale() ? 30 : 20;
	}
	
	public Link getPlayer(){
		return this.player;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public Direction getDirection()
	{ 
		return direction;
	}
	
	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}
	
	public int getTime(){
		return this.t;
	}
	
	public void move(){
		if(direction == null)
			return;
		if(direction.dx != 0)
			setXPos(getXPos() + 10 * direction.dx);
		if(direction.dy != 0)
			setYPos(getXPos() + 10 * direction.dx);
		tick(3,5);
	}

	@Override
	public void tick(int constante) {
		this.mytick++;
		if(this.mytick == constante) {
			this.t++;
			this.mytick = 0;
		}	
	}

	@Override
	public void tick(int frames, int constante) {
		setMyTick(getMyTick()+1);
		if(getMyTick() == constante) {
			setActualFrame(getActualFrame()+1);
			setMyTick(0);
			if(getActualFrame() == frames +1)
				setActualFrame(1);
		}
	}
	
}
