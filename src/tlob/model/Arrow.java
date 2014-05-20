package tlob.model;

public class Arrow extends Item implements Tick{
	
	private int direction;
	private int t = 0;
	private int mytick;
	private int x;
	private int y;
	private int player;
	
	public Arrow(int xPos, int yPos,String name, int direction, int player){
		super(xPos,yPos,name);
		this.x = xPos;
		this.y = yPos;
		this.direction = direction;
		this.player = player;
	}
	
	public int getPlayer(){
		return this.player;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getDirection()
	{ 
		return direction;
	}
	
	public void setDirection (int direction)
	{
		this.direction = direction;
	}
	
	public int getTime(){
		return this.t;
	}
	
	public void move(){
		if(direction == 0){
			setXPos(getXPos() - 10);
			tick(3,5);
		}
		if(direction == 1){
			setXPos(getXPos() + 10);
			tick(3,5);
		}
		if(direction == 2){
			setYPos(getYPos() - 10);
			tick(3,5);
		}
		if(direction == 3){
			setYPos(getYPos() + 10);
			tick(3,5);
		}
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
