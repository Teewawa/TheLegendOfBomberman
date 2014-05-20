package tlob.model;

import java.util.List;

public class Boss extends Monster{	
	
	private int bossTick = 0;
	private int attackCd = 80;
	private boolean rage = false;

	public Boss (int lifePoint, int xPos, int yPos, int speed,int direction,String name)
	{
		super(lifePoint, xPos, yPos,speed, direction, name);
	}
	
	public boolean getRage()
	{
		return rage;
	}
	
	public void setRage(boolean rage)
	{
		this.rage = rage;
	}
		
	public List<FireBall> fireBall(List<FireBall> fireBall, Link link) 
	{
		fireBall.add(new FireBall(getXPos()+10, getYPos()+10, "res/Monster/fire", link.getXPos()+10, link.getYPos()+10,15*40,15*40));
		return fireBall;
	}
	
	public List<FireBall> fireBall2(List<FireBall> fireBall) 
	{
		fireBall.add(new FireBall(getXPos()+10, getYPos()+10, "res/Monster/fire", getXPos()+16, getYPos()+10,15*40,15*40));
		fireBall.add(new FireBall(getXPos()+10, getYPos()+10, "res/Monster/fire", getXPos()+4, getYPos()+10,15*40,15*40));
		fireBall.add(new FireBall(getXPos()+10, getYPos()+10, "res/Monster/fire", getXPos()+10, getYPos()+16,15*40,15*40));
		fireBall.add(new FireBall(getXPos()+10, getYPos()+10, "res/Monster/fire", getXPos()+10, getYPos()+4,15*40,15*40));
		fireBall.add(new FireBall(getXPos()+10, getYPos()+10, "res/Monster/fire", getXPos()+16, getYPos()+16,15*40,15*40));
		fireBall.add(new FireBall(getXPos()+10, getYPos()+10, "res/Monster/fire", getXPos()+16, getYPos()+4,15*40,15*40));
		fireBall.add(new FireBall(getXPos()+10, getYPos()+10, "res/Monster/fire", getXPos()+4, getYPos()+16,15*40,15*40));
		fireBall.add(new FireBall(getXPos()+10, getYPos()+10, "res/Monster/fire", getXPos()+4, getYPos()+4,15*40,15*40));
		return fireBall;
	}
	
	public List<Thunder> thunder(List<Thunder> thunder, Link link) // pas sur
	{
		thunder.add(new Thunder(link.getXPos(), link.getYPos(), "res/Monster/Thunder"));
		return thunder;
	}
	
	public int getBossTick(){
		return this.bossTick;
	}
	
	public void setBossTick(int n){
		this.bossTick = n;
	}
	
	public void tickBoss(int c){
		bossTick++;
		if(getCooldown() > attackCd*4-20){
			setActualFrame(6);
			bossTick = 0;
		}
		else if(bossTick == c && getActualFrame() != 6){
			setActualFrame(getActualFrame()+1);
				if(getActualFrame() == 6){
					setActualFrame(1);
				}
			bossTick = 0;
		}
		else if(bossTick == c && getActualFrame() == 6){
			setActualFrame(1);
			bossTick = 0;
		}
	}

	public int getAttackCd() {
		return attackCd;
	}

	public void setAttackCd(int attackCd) {
		this.attackCd = attackCd;
	}

}


