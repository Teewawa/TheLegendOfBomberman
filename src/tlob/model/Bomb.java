package tlob.model;

public class Bomb extends Item {
	
	private int time;
	private int mytick;
	private Direction direction = null;
	private int player;
	
	public Bomb(int xPos, int yPos, String name, int player)
	{
		super(xPos,yPos,name);	
		this.player = player;
	}
	
	public int getPlayer(){
		return this.player;
	}
	
	public Direction getDirection(){
		return this.direction;
	}
	
	public void setDirection(Direction d){
		this.direction = d;
	}
	
	public void tick(){
		this.mytick++;
		if(this.mytick == 10) {
			this.time++;
			mytick = 1;
		}
	}
	
	public int getTime(){
		return time;
	}
	
	public void setTime(int time){
		this.time = time;
		this.mytick = 1;
	}
	
	public void move(){
		if(direction != null) {
			if(direction.dx != 0)
				setXPos(getXPos() + direction.dx * 5);
			if(direction.dy != 0)
				setYPos(getYPos() + direction.dy * 5);
		}
	}
	
}
