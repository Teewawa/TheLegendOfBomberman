package tlob.model;

public class Bomb extends Item {
	
	private int time;
	private int mytick;
	private int direction = -1;
	private int player;
	
	public Bomb(int xPos, int yPos, String name, int player)
	{
		super(xPos,yPos,name);	
		this.player = player;
	}
	
	public int getPlayer(){
		return this.player;
	}
	
	public int getDirection(){
		return this.direction;
	}
	
	public void setDirection(int d){
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
		if(direction == 0){
			setXPos(getXPos() - 5);
		}
		if(direction == 1){
			setXPos(getXPos() + 5);
		}
		if(direction == 2){
			setYPos(getYPos() - 5); //panel 
		}
		if(direction == 3){
			setYPos(getYPos() + 5); //panel
		}
	}
	
}
