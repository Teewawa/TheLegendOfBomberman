package tlob.model;

public class Bomb extends Item {
	
	private int time;
	private int mytick;
	private Direction direction = null;
	private Link player;
	
	public Bomb(int xPos, int yPos, String name, Link player)
	{
		super(xPos,yPos,name);	
		this.player = player;
	}
	
	public Link getPlayer(){
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
			setXPos(getXPos() + direction.dx * 5);
			setYPos(getYPos() + direction.dy * 5);
		}
	}
	
	public int[] nextPos(Direction dir, int vitesse) {
		if(dir == null)
			return new int[]{getXPos(), getYPos()};
		return new int[]{getXPos() + dir.dx * vitesse, getYPos() + dir.dy * vitesse};
	}
	
	public void setPos(int[] pos) {
		setXPos(pos[0]);
		setYPos(pos[1]);
	}
	
}
