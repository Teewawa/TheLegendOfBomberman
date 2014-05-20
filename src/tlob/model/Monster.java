package tlob.model;

public class Monster extends Character {
	
	private int initialXPos;
	private int initialYPos;
	private boolean action = false;
	private int cooldown = 40;
	private int tick = 0;
	private boolean spawner = false;

	public Monster (int lifePoint, int xPos, int yPos, int speed,int direction,String name)
	{
		super (lifePoint, xPos, yPos, speed, direction, name);
		this.initialXPos = xPos;
		this.initialYPos = yPos;
		setPlayer(-1);
	}
	
	public int getInitialXPos(){
		return this.initialXPos;
	}
	
	public void setInitialXPos(int initialXPos){
		this.initialXPos = initialXPos;
	}
	
	public int getInitialYPos(){
		return this.initialYPos;
	}
	
	public void setInitialYPos(int initialYPos){
		this.initialYPos = initialYPos;
	}
	
	public void move()

	{
		if(direction == 0){
			setXPos(getXPos() - getFrozen()*getL()*speed);
			tick(4,5);
		}
		if(direction == 1){
			setXPos(getXPos() + getFrozen()*getR()*speed);
			tick(4,5);
		}
		if(direction == 2){
			setYPos(getYPos() - getFrozen()*getU()*speed);
			tick(4,5);
		}
		if(direction == 3){
			setYPos(getYPos() + getFrozen()*getD()*speed);
			tick(4,5);
		}
	}
	
	public int getCooldown(){
		return this.cooldown;
	}
	
	public void setCooldown(int cd){
		this.cooldown = cd;
	}
	
	public void cdTick(int constante) {
		tick++;
		if(tick == constante) {
			cooldown++;
			tick = 0;
		}
	}
	
	public void setAction(boolean b) {
		this.action = b;
		
	}

	public boolean getAction() {
		return this.action;
	}

	public boolean getSpawner() {
		return spawner;
	}

	public void setSpawner(boolean spawner) {
		this.spawner = spawner;
	}
	
}
