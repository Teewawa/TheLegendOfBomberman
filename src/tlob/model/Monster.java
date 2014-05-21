package tlob.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Monster extends Character {
	
	private int initialXPos;
	private int initialYPos;
	private boolean action = false;
	private int cooldown = 40;
	private int tick = 0;
	private boolean spawner = false;

	public Monster (int lifePoint, int xPos, int yPos, int speed, Direction direction,String name)
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
	
	public void setRandomDirection() {
		List<Direction> dir = new ArrayList<Direction>();
		if(getL() == 1)
			dir.add(Direction.GAUCHE);
		
		if(getR() == 1)
			dir.add(Direction.DROITE);
		
		if(getU() == 1)
			dir.add(Direction.HAUT);
		
		if(getD() == 1)
			dir.add(Direction.BAS);
		
		int i = new Random().nextInt(dir.size());
		setDirection( dir.get(i) );
	}
	
	public void move()

	{
		if(direction != null)
		{
			if(direction.dx != 0)
				setXPos(getXPos() + direction.dx * getFrozen() * getL() * speed);
			if(direction.dy != 0)
				setXPos(getXPos() + direction.dy * getFrozen() * getL() * speed);
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
