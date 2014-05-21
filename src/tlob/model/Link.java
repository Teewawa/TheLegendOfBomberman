package tlob.model;

import java.util.List;

public class Link extends Character {
	
	private int frameArrow = 0;
	private int numberBomb = 1;
	private int rangeBomb = 1;
	private int numberArrow = 3;
	private boolean gauntlet = false;
	private int staff = -1; //0=fire //1=ice
	private int numberCoin = 0;
	private int maxLife = 3;
	private String baseName;


	public Link (int lifePoint, int xPos, int yPos, int speed, Direction direction, String baseName)
	{
		super(lifePoint, xPos, yPos, speed, direction, "res/" + baseName + "/" + baseName + "Run");
		this.baseName = baseName;
		
		width = 35;
		height = 35;
	}
	

	
	public int getMaxLife(){
		return this.maxLife;
	}

	public void setMaxLife(int maxLife){
		this.maxLife = maxLife;
	}
	
	public int getStaff() {
		
		return staff;
	}
	
	public void setStaff(int staff) {
		
		this.staff=staff;
	}
	
	public int getNumberBomb()
	{
		return numberBomb;
	}
	
	public void setNumberBomb(int numberBomb)
	{ 
		this.numberBomb = numberBomb;
	}
	
	public int getRangeBomb()
	{
		return rangeBomb;
	}
	
	public void setRangeBomb(int rangeBomb)
	{
		this.rangeBomb = rangeBomb;
	}
	
	public int getNumberArrow()
	{
		return numberArrow;
	}
	
	public void setNumberArrow(int numberArrow)
	{
		this.numberArrow = numberArrow;
	}
	
	public boolean getGauntlet()
	{
		return gauntlet;
	}
	
	public void setGauntlet(boolean gauntlet)
	{
		this.gauntlet = gauntlet;
	}
	
	public int getNumberCoin() {
		return numberCoin;
	}
	
	public void setNumberCoin(int numberCoin) {
		this.numberCoin=numberCoin;
	}
	
	public void move(Direction d)
	{
		direction = d;
		if(isDirSet(d)) {
			setXPos(xPos + d.dx * getFrozen() * speed);
			setYPos(yPos + d.dy * getFrozen() * speed);
		}
		tick(6,5);
	}
	
	public void setBomb(List<Bomb> liste)
	{
		int k = 1;
		int[] c = getCenterCase();
		for(Bomb b : liste) {
			if(b.getCenter().equals(c)){
				k = 0;
			}
		}
		if (k == 1 && isInvincible() == false){
			Bomb b = new Bomb(xPos, yPos, "res/Bomb", this);
			b.centerOn(c);
			liste.add(b);
		}
	}
	
	public Arrow fireArrow() {
		if(numberArrow > 0){
			if(getActualFrame() != 1 && frameArrow == 0){
				setActualFrame(1);
				frameArrow = 1;
			}
			
			setName("res/" + baseName + "/" + baseName + "Arrow");
			
			tick(6,5);
			
			if(getActualFrame() == 6){
				frameArrow = 0;
				numberArrow--;
				Arrow a = new Arrow(xPos, yPos, "res/Arrow", direction, this);
				a.centerOn( new int[]{getXPos() + getWidth() / 2, getYPos() + getHeight() / 2} );
				return a;
			}
		}
		return null;
	}

}
