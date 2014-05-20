package tlob.model;

public class Bonus extends Decor {
	private int number;
	private int maxSpeed = 6;
	
	public Bonus(int xPos, int yPos,String name,int number){
		super(xPos,yPos,name);
		this.number = number;
	}

	public void activation(Link l){
		switch (this.number){
		case 0: //Heart
			if(l.getLifePoint()<l.getMaxLife()) {
				l.setLifePoint(l.getLifePoint()+1); 
			}
			break;
		case 1: //BombPlus
			if(l.getNumberBomb()<9) {
				l.setNumberBomb(l.getNumberBomb()+1); 
			}
			break;
		case 2: //RangeBomb
			if(l.getRangeBomb()<9) {
				l.setRangeBomb(l.getRangeBomb()+1); 
			}

			break;
		case 3: //ArrowPlus
			if(l.getNumberArrow()<99) {
				l.setNumberArrow(l.getNumberArrow()+1);
			}
			break;
		case 4: //Speed
			if(l.getSpeed()<maxSpeed) {
				l.setSpeed(l.getSpeed()+2);
			}
			break;
		case 5: //Gauntlet
			l.setGauntlet(true);
			break;
		case 6:
			break;
		case 7: //FireStaff
			l.setStaff(0);
			break;
		case 8: //IceStaff
			l.setStaff(1);
			break;
		case 9: //Rupee
			if(l.getNumberCoin()<999) {
				l.setNumberCoin(l.getNumberCoin()+1);
			}
			break;
		case 10: //Speed
			if(l.getSpeed()<maxSpeed) {
				l.setSpeed(l.getSpeed()+1);
			}
			break;
		}
			
		}
}

