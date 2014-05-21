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


	public Link (int lifePoint, int xPos, int yPos, int speed, Direction direction, String image, int player)
	{
		super(lifePoint, xPos, yPos, speed, direction, image);
		setPlayer(player);
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
		if(d.dx != 0)
			setXPos(xPos + d.dx * getFrozen() * getR() * speed);
		if(d.dy != 0)
			setYPos(yPos + d.dy * getFrozen() * getU() * speed);
		tick(6,5);
	}
	
	public void setBomb(List<Bomb> liste)
	{
		int x,y;
		int k = 1;
		if(xPos % 40 <= 20){
			x = xPos - xPos % 40;
		}
		else{
			x = xPos + 40 - xPos % 40;
		}
		if(yPos%40 <= 20){
			y = yPos - yPos % 40;
		}
		else{
			y = yPos + 40 - yPos % 40;
		}
		for(Bomb b : liste) {
			if(x + 5 == b.getXPos() && y + 5 == b.getYPos()){
				k = 0;
			}
		}
		if (k == 1 && getInvincible() == 1){
			liste.add( new Bomb(x+5, y+5, "res/Bomb", getPlayer())) ;
		}
	}
	
	public void fireArrow(List<Arrow> liste){
		
		if(numberArrow > 0){
			if(getActualFrame() != 1 && frameArrow == 0){
				setActualFrame(1);
				frameArrow = 1;
			}
			
			if(getPlayer() == 0) {
				setName("res/Link/LinkArrow");
			}
			
			else { 
				setName("res/RedLink/RedLinkArrow");
			}
			
			tick(6,5);
			
			if(getActualFrame() == 6){
				liste.add(new Arrow(xPos, yPos, "res/Arrow", direction, getPlayer()));
				frameArrow = 0;
				numberArrow--;
			}
		}
	}
}
