package tlob.model;


public class Treasure extends Decor {
	
	private int bonusType;
	private String nameBonus[] ={"res/Heart","res/BombPlus","res/BombRange","res/ArrowPlus","res/SpeedBonus",
			"res/Gauntlet2","res/Key","res/FireStaff","res/IceStaff","res/Rubis"};
	private boolean bonusTaken;
	
	public Treasure(int xPos, int yPos, String name,int bonusType) {
		super(xPos,yPos,name);
		this.bonusType=bonusType;
		}
	
	
	public int getBonusType() {
		
		return bonusType;
	}
	
	public void setBonusType(int bonusType) {
		
		this.bonusType=bonusType;
	}
	
	public String typeToString(){
		return nameBonus[bonusType];
	}


	public boolean isBonusTaken() {
		return bonusTaken;
	}


	public void setBonusTaken(boolean bonusTaken) {
		this.bonusTaken = bonusTaken;
	}
}