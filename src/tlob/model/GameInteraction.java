package tlob.model;

import java.util.ArrayList;
import java.util.List;
import tlob.view.Sound;

public class GameInteraction {
	
	private List<Link> link;
	private List<Decor> decor;
	private List<Monster> monster;
	private List<Bomb> bomb;
	private List<Bonus> bonus;
	private List<Arrow> arrow;
	private List<FireBall> fireBall;
	private List<Thunder> thunder;
	private Map map;
	private boolean changeLevel = false;
	private boolean chestOpen = false;
	
	public GameInteraction(Level level){
		this.link = level.getLink();
		this.decor = level.getDecor();
		this.monster = level.getMonster();
		this.bomb = level.getBomb();
		this.bonus = level.getBonus();
		this.arrow = level.getArrow();
		this.fireBall = level.getFireBall();
		this.thunder = level.getThunder();
		this.map = level.getMap();
	}
	
	public void setChangeLevel(boolean changeLevel){
		this.changeLevel = changeLevel;
	}
	
	public boolean getChangeLevel(){
		return this.changeLevel;
	}
	
	private int touchDecor(int x1, int y1, int x2, int y2){ 
		if(Math.abs(x1-x2)<40 && Math.abs(y1-y2)<30){
			if(x1-x2 < 0){
				return 0; //GAUCHE
			}
			else{
				return 1; //DROITE
			}
		}
		else if(Math.abs(x1-x2)<30 && 40>Math.abs(y1-y2)){
			if(y1-y2 < 0){
				return 2; //HAUT
			}
			else{
				return 3; //BAS
			}
		}
		else{
			return -1;
		}
	}
	
	private int touchDecorB(int x1, int y1, int x2, int y2){ 
		if(Math.abs(x1-x2)<35 && Math.abs(y1-y2)<25){
			if(x1-x2 < 0){
				return 0; //GAUCHE
			}
			else{
				return 1; //DROITE
			}
		}
		else if(Math.abs(x1-x2)<25 && 35>Math.abs(y1-y2)){
			if(y1-y2 < 0){
				return 2; //HAUT
			}
			else{
				return 3; //BAS
			}
		}
		else{
			return -1;
		}
	}
	
	private int touchMonster(int x1, int y1, int x2, int y2){ 
		if(Math.abs(x1-x2)<30 && Math.abs(y1-y2)<25){
			if(x1-x2 < 0){
				return 0; //GAUCHE
			}
			else{
				return 1; //DROITE
			}
		}
		else if(Math.abs(x1-x2)<25 && 30>Math.abs(y1-y2)){
			if(y1-y2 < 0){
				return 2; //HAUT
			}
			else{
				return 3; //BAS
			}
		}
		else{
			return -1;
		}
	}
	
	private int touchArrow(int x1, int y1, int x2, int y2){ 
		if(Math.abs(x1-x2)<30 && Math.abs(y1-y2)<20){
			if(x1-x2 < 0){
				return 0; //GAUCHE
			}
			else{
				return 1; //DROITE
			}
		}
		else if(Math.abs(x1-x2)<20 && 30>Math.abs(y1-y2)){
			if(y1-y2 < 0){
				return 2; //HAUT
			}
			else{
				return 3; //BAS
			}
		}
		else{
			return -1;
		}
	}
	
	private int touchBomb(int x1, int y1, int x2, int y2){ 
		if(Math.abs(x1-x2)<40 && Math.abs(x1-x2)>30 && Math.abs(y1-y2)<25){
			if(x1-x2 < 0){
				return 0; //GAUCHE
			}
			else{
				return 1; //DROITE
			}
		}
		else if(Math.abs(x1-x2)<30 && 40>Math.abs(y1-y2) && Math.abs(y1-y2)>25){
			if(y1-y2 < 0){
				return 2; //HAUT
			}
			else{
				return 3; //BAS
			}
		}
		else{
			return -1;
		}
	}
	
	public void linkInteraction(Link link){
		link.setR(1);
		link.setL(1);
		link.setD(1);
		link.setU(1);
		
		for(int i = 0; i < decor.size(); i++){
			if(touchArrow(link.getXPos(), link.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos()) 
					!= -1 && decor.get(i).getClass() == Door.class && ((Door)decor.get(i)).getOpen()==true){
				int line = ((Door) decor.get(i)).getLine();
				int column = ((Door) decor.get(i)).getColumn();
				int level = ((Door) decor.get(i)).getLevel();
				char[][] tableau = new char[16][16];

				map.saveMap(map.listToMap(decor, monster));
				
		
				if(column != 0){
					link.setXPos(link.getXPos() - ((32*15)+15)*column );
					map.setRoomColumn(Integer.toString(Integer.parseInt(map.getRoomColumn()) + column));
					tableau = map.loadRoom();
				}
				else if(line != 0){
					link.setYPos(link.getYPos() + ((32*15)+15)*line);
					map.setRoomLine(Integer.toString(Integer.parseInt(map.getRoomLine()) - line));
					tableau = map.loadRoom();
				}
				else if (level == 1){
					map.setRoomColumn("1");
					map.setRoomLine("3");
					map.setLevel(Integer.toString(level + Integer.parseInt(map.getLevel())));
					if (map.getLevel().contentEquals("2")){
						map.setEnvironment("/Forest");
					}
					if (map.getLevel().contentEquals("3")){
						map.setEnvironment("/Dungeon");
					}
					tableau = map.loadRoom();
					changeLevel = true;
					link.setXPos(18*15+10);
					link.setYPos(35*15);
					link.setDirection(2);

				}
						
				bonus.removeAll(bonus);
				bomb.removeAll(bomb);
				decor.removeAll(decor);
				List<Decor> decor2 = map.mapToListDecor(tableau);
				for(int j = 0; j < decor2.size();j++){
					decor.add(decor2.get(j));
				}
				
				monster.removeAll(monster);
				List<Monster> monster2 = map.mapToListMonster(tableau);
				for(int j = 0; j < monster2.size(); j++){
					monster.add(monster2.get(j));
				}
				break;
			}
			
			if(touchDecor(link.getXPos(), link.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos()) != -1 && link.getDirection() == 2
					&& decor.get(i).getClass() == Treasure.class && ((Treasure)decor.get(i)).isBonusTaken() == false){
				decor.get(i).setActualFrame(2);
				if(link.getName().contentEquals("res/Link/LinkOpen") == false){
					link.setActualFrame(1);
				}
				link.setName("res/Link/LinkOpen");
				if(link.getActualFrame() != 3){
					this.chestOpen = true;
					link.tick(3,5);
					link.setR(0);
					link.setL(0);
					link.setD(0);
					link.setU(0);
					link.setName("res/Link/LinkOpen");
				}
				else{
					((Treasure)decor.get(i)).setBonusTaken(true);
					bonus.add(new Bonus(link.getXPos(), link.getYPos() -30, ((Treasure) decor.get(i)).typeToString(),((Treasure) decor.get(i)).getBonusType()));
					link.setR(1);
					link.setL(1);
					link.setD(1);
					link.setU(1);
				}
			}
			
			if(decor.get(i).getClass() != Floor.class){
				if(decor.get(i).getClass() == Door.class && ((Door)decor.get(i)).getOpen()==false){
					int a = touchDecor(link.getXPos(),link.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos());
					if(a == 0)
						link.setR(0);
					if(a == 1)
						link.setL(0);
					if(a == 2)
						link.setD(0);
					if(a == 3)
						link.setU(0);
				}
				int a = touchDecor(link.getXPos(),link.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos());
				if(a == 0)
					link.setR(0);
				if(a == 1)
					link.setL(0);
				if(a == 2)
					link.setD(0);
				if(a == 3)
					link.setU(0);
				if((decor.get(i).getClass() == Treasure.class && ((Treasure) decor.get(i)).isBonusTaken() == true) && a != -1){
					link.setR(1);
					link.setL(1);
					link.setD(1);
					link.setU(1);
				}
						
				if((decor.get(i).getClass() == Door.class && ((Door)decor.get(i)).getOpen()==true) && a != -1){
					link.setR(1);
					link.setL(1);
					link.setD(1);
					link.setU(1);
				}
			}
		}
		for(int i = 0; i < monster.size(); i++){
			int a = touchMonster(link.getXPos(),link.getYPos(),monster.get(i).getXPos(),monster.get(i).getYPos());
			if(a == 0){
				if(link.getL() != 0 && link.getInvincible() == 1){
					for(int j = 0; j < 3; j++){
						link.setXPos(link.getXPos() - 5);
						for(int k = 0; k < decor.size(); k++){
							if(touchDecor(link.getXPos(),link.getYPos(),decor.get(k).getXPos(),decor.get(k).getYPos()) == -1){
								link.setL(0);
							}
						}
					}
				}
				link.getDamage(1);
			}
			if(a == 1){
				if(link.getR() != 0 && link.getInvincible() == 1){
					for(int j = 0; j < 3; j++){
						link.setXPos(link.getXPos() + 5);
						for(int k = 0; k < decor.size(); k++){
							if(touchDecor(link.getXPos(),link.getYPos(),decor.get(k).getXPos(),decor.get(k).getYPos()) == -1){
								link.setR(0); 
							}
						}
					}
				}
				link.getDamage(1);
			}
			if(a == 2){
				if(link.getU() != 0 && link.getInvincible() == 1){
					for(int j = 0; j < 3; j++){
						link.setYPos(link.getYPos() - 5);
						for(int k = 0; k < decor.size(); k++){
							if(touchDecor(link.getXPos(),link.getYPos(),decor.get(k).getXPos(),decor.get(k).getYPos()) == -1){
								link.setU(0);
							}
						}
					}
				}
				link.getDamage(1);
			}
			if(a == 3){
				if(link.getD() != 0 && link.getInvincible() == 1){
					for(int j = 0; j < 3; j++){
						link.setYPos(link.getYPos() + 5);
						for(int k = 0; k < decor.size(); k++){
							if(touchDecor(link.getXPos(),link.getYPos(),decor.get(k).getXPos(),decor.get(k).getYPos()) == -1){
								link.setU(0);
							}
						}
					}
				}
				link.getDamage(1);
			}
		}
		for(int i = 0; i < bomb.size(); i++){
			int a = touchBomb(link.getXPos(),link.getYPos(),bomb.get(i).getXPos(),bomb.get(i).getYPos());
			if(a == 0){
				link.setR(0);
				if(link.getDirection() == 1 && link.getGauntlet() == true && bomb.get(i).getPlayer() != -1){
					bomb.get(i).setDirection(link.getDirection());
				}
			}
			if(a == 1){
				link.setL(0);
				if(link.getDirection() == 0 && link.getGauntlet() == true && bomb.get(i).getPlayer() != -1){
					bomb.get(i).setDirection(link.getDirection());
				}
			}
			if(a == 2){
				link.setD(0);
				if(link.getDirection() == 3 && link.getGauntlet() == true && bomb.get(i).getPlayer() != -1){
					bomb.get(i).setDirection(link.getDirection());
				}
			}
			if(a == 3){
				link.setU(0);
				if(link.getDirection() == 2 && link.getGauntlet() == true && bomb.get(i).getPlayer() != -1){
					bomb.get(i).setDirection(link.getDirection());
				}
			}
		}
		for(int i = 0; i < bonus.size(); i++){
			if(touchMonster(link.getXPos(),link.getYPos(),bonus.get(i).getXPos(),bonus.get(i).getYPos()) != -1){
				bonus.get(i).activation(link);
				
				if(bonus.get(i).getName() == "res/Rubis") { 
					Sound soundRupee = new Sound();
					soundRupee.playSound("rupee");	
				}
				
				else {
					Sound soundBonus = new Sound();
					soundBonus.playSound("bonus");
				}
				
				bonus.remove(i);
			}
		}
	}
	
	public int arrowInteraction(Arrow arrow){		
		for(int i = 0; i < link.size(); i++){
			if(arrow.getPlayer() != link.get(i).getPlayer()) {
				if(touchArrow(arrow.getXPos(), arrow.getYPos(),link.get(i).getXPos(),link.get(i).getYPos()) != -1){
					link.get(i).getDamage(1);
					return 1;
				}
			}
		}
		if(arrow.getPlayer() != -1){
			for(int i = 0; i < monster.size(); i++){
				if(touchArrow(arrow.getXPos(),arrow.getYPos(),monster.get(i).getXPos(),monster.get(i).getYPos()) != -1){
					monster.get(i).getDamage(1);
					return 1;
				}
			}
		}
		for(int i = 0; i < decor.size(); i++){
			if(touchArrow(arrow.getXPos(),arrow.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos()) != -1
					&& decor.get(i).getClass() != Floor.class){
				return 2;
			}
		}
		for(int i = 0; i < bomb.size(); i++){
			if(touchArrow(arrow.getXPos(),arrow.getYPos(),bomb.get(i).getXPos(),bomb.get(i).getYPos()) != -1){
				bomb.get(i).setTime(15);
				return 3;
			}
		}
		arrow.move();
		return 0;
	}
	
	public void bombInteraction(Bomb bomb){
		for(int i = 0; i < this.bomb.size(); i++){
			if(bomb.getDirection() == 0){
				if(touchDecorB(bomb.getXPos()-15, bomb.getYPos(), this.bomb.get(i).getXPos(), this.bomb.get(i).getYPos()) != -1
						&& this.bomb.get(i).getXPos() != bomb.getXPos()){ //&& bomb.get(i).getYPos() != getYPos()){
					bomb.setXPos(this.bomb.get(i).getXPos()+40);
					bomb.setDirection(-1);
				}
			}
			if(bomb.getDirection() == 1){
				if(touchDecorB(bomb.getXPos()+15, bomb.getYPos(),this.bomb.get(i).getXPos(),this.bomb.get(i).getYPos()) != -1
						&& this.bomb.get(i).getXPos() != bomb.getXPos()){
					bomb.setXPos(this.bomb.get(i).getXPos()-40);
					bomb.setDirection(-1);
				}
			}
			if(bomb.getDirection() == 2){
				if(touchDecorB(bomb.getXPos(), bomb.getYPos()-15,this.bomb.get(i).getXPos(),this.bomb.get(i).getYPos()) != -1
						&& this.bomb.get(i).getYPos() != bomb.getYPos()){
					bomb.setYPos(this.bomb.get(i).getYPos()+40);
					bomb.setDirection(-1);
				}
			}
			if(bomb.getDirection() == 3){
				if(touchDecorB(bomb.getXPos(), bomb.getYPos()+15,this.bomb.get(i).getXPos(),this.bomb.get(i).getYPos()) != -1
						&& this.bomb.get(i).getYPos() != bomb.getYPos()){
					bomb.setYPos(this.bomb.get(i).getYPos()-40);
					bomb.setDirection(-1);
				}
			}
		}
		if(bomb.getPlayer() != -1){
			for(int i = 0; i < monster.size(); i++){
				if(touchDecorB(bomb.getXPos(), bomb.getYPos(),monster.get(i).getXPos(),monster.get(i).getYPos()) != -1){
					bomb.setDirection(-1);
				}
			}
		}
		
		for(int i = 0; i < link.size(); i++){
			if(touchDecorB(bomb.getXPos(), bomb.getYPos(),link.get(i).getXPos(),link.get(i).getYPos()) != -1){
				bomb.setDirection(-1);
			}
		}
		
		for(int i = 0; i < decor.size(); i++){
			if(bomb.getDirection() == 0){
				if(touchDecorB(bomb.getXPos()-15, bomb.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos()) != -1
						&& decor.get(i).getClass() != Floor.class){
					bomb.setXPos(decor.get(i).getXPos()+45);
					bomb.setDirection(-1);
				}
			}
			if(bomb.getDirection() == 1){
				if(touchDecorB(bomb.getXPos()+5, bomb.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos()) != -1
						&& decor.get(i).getClass() != Floor.class){
					bomb.setXPos(decor.get(i).getXPos()-35);
					bomb.setDirection(-1);
				}
			}
			if(bomb.getDirection() == 2){
				if(touchDecorB(bomb.getXPos(), bomb.getYPos()-15,decor.get(i).getXPos(),decor.get(i).getYPos()) != -1
						&& decor.get(i).getClass() != Floor.class){
					bomb.setYPos(decor.get(i).getYPos()+45);
					bomb.setDirection(-1);
				}
			}
			if(bomb.getDirection() == 3){
				if(touchDecorB(bomb.getXPos(), bomb.getYPos()+5,decor.get(i).getXPos(),decor.get(i).getYPos()) != -1
						&& decor.get(i).getClass() != Floor.class){
					bomb.setYPos(decor.get(i).getYPos()-35);
					bomb.setDirection(-1);
				}
			}
		}
		bomb.move();
	}
	
	public void deflagrationAppear(BombDeflagration bombDef, int rangeBomb){
		int liste[][] = bombDef.listeExplosion(rangeBomb);
		for(int j = 0; j < bombDef.getPortee();j++){
			for(int i = 0; i < decor.size(); i++){
				if(touchArrow(liste[0][j],bombDef.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos()) != -1
						&& decor.get(i).getClass() != Floor.class){
					if(decor.get(i).getClass() == Jar.class && bombDef.getL() == 1 && bombDef.getPlayer() != -1){
						bombDef.getLeft().add(liste[0][j]);
						bombDef.getLeft().add(liste[0][j+1]);
						//left.add(liste[0][j+2]);
						if(link.size() == 1) {
							
							((Jar) decor.get(i)).randomBonus(bonus, decor.get(i).getXPos(), decor.get(i).getYPos());
						}
						
						else {
							
							((Jar) decor.get(i)).randomBonusVersus(bonus, decor.get(i).getXPos(), decor.get(i).getYPos());
						}
						decor.remove(i);
						decor.get(i-1).setName("res" + map.getEnvironment() + "/BrokenJar");
						Sound soundJar = new Sound();
						soundJar.playSound("jarbroken");
					}
					bombDef.setL(0);
				}
				if(touchArrow(liste[1][j],bombDef.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos()) != -1
						&& decor.get(i).getClass() != Floor.class){
					if(decor.get(i).getClass() == Jar.class && bombDef.getR() == 1 && bombDef.getPlayer() != -1){
						bombDef.getRight().add(liste[1][j]);
						bombDef.getRight().add(liste[1][j+1]);
						if(link.size() == 1) {
							
							((Jar) decor.get(i)).randomBonus(bonus, decor.get(i).getXPos(), decor.get(i).getYPos());
						}
						
						else {
							
							((Jar) decor.get(i)).randomBonusVersus(bonus, decor.get(i).getXPos(), decor.get(i).getYPos());
						}
						decor.remove(i);
						decor.get(i-1).setName("res" + map.getEnvironment() + "/BrokenJar");
						Sound soundJar = new Sound();
						soundJar.playSound("jarbroken");
					}
					bombDef.setR(0);
				}
				if(touchArrow(bombDef.getXPos(),liste[2][j],decor.get(i).getXPos(),decor.get(i).getYPos()) != -1
						&& decor.get(i).getClass() != Floor.class){
					if(decor.get(i).getClass() == Jar.class && bombDef.getU() == 1 && bombDef.getPlayer() != -1){
						bombDef.getDown().add(liste[2][j]);
						bombDef.getDown().add(liste[2][j+1]);
						if(link.size() == 1) {
							
							((Jar) decor.get(i)).randomBonus(bonus, decor.get(i).getXPos(), decor.get(i).getYPos());
						}
						
						else {
							
							((Jar) decor.get(i)).randomBonusVersus(bonus, decor.get(i).getXPos(), decor.get(i).getYPos());
						}
						
						decor.remove(i);
						decor.get(i-1).setName("res" + map.getEnvironment() + "/BrokenJar");
						Sound soundJar = new Sound();
						soundJar.playSound("jarbroken");						
					}
					bombDef.setU(0);
					}
				
				if(touchArrow(bombDef.getXPos(),liste[3][j],decor.get(i).getXPos(),decor.get(i).getYPos()) != -1
						&& decor.get(i).getClass() != Floor.class){
					if(decor.get(i).getClass() == Jar.class && bombDef.getD() == 1 && bombDef.getPlayer() != -1){
						bombDef.getUp().add(liste[3][j]);
						bombDef.getUp().add(liste[3][j+1]);
						if(link.size() == 1) {
							
							((Jar) decor.get(i)).randomBonus(bonus, decor.get(i).getXPos(), decor.get(i).getYPos());
						}
						
						else {
							
							((Jar) decor.get(i)).randomBonusVersus(bonus, decor.get(i).getXPos(), decor.get(i).getYPos());
						}
						decor.remove(i);
						decor.get(i-1).setName("res" + map.getEnvironment() + "/BrokenJar");
						Sound soundJar = new Sound();
						soundJar.playSound("jarbroken");
					}
					bombDef.setD(0);
				}
			}
			if(bombDef.getL() == 1){
				bombDef.getLeft().add(liste[0][j]);
			}
			if(bombDef.getR() == 1){
				bombDef.getRight().add(liste[1][j]);
			}
			if(bombDef.getU() == 1){
				bombDef.getUp().add(liste[2][j]);
			}
			if(bombDef.getD() == 1){
				bombDef.getDown().add(liste[3][j]);
			}
		}	
	}
	
	public void defInteraction(BombDeflagration bombDef){
		for(int j = 0; j < bombDef.getUp().size(); j++){
			for(int i = 0; i< link.size(); i++){
				if(touchArrow(bombDef.getXPos(),bombDef.getUp().get(j),link.get(i).getXPos(),link.get(i).getYPos()) != -1){
					link.get(i).getDamage(1);
				}
			}
			
			if(bombDef.getPlayer() != -1){
				for(int i = 0; i< monster.size(); i++){
					if(touchArrow(bombDef.getXPos(),bombDef.getUp().get(j),monster.get(i).getXPos(),monster.get(i).getYPos()) != -1){
						monster.get(i).getDamage(1);
					}
				}
			}

			for(int i = 0; i< bomb.size(); i++){
				if(touchArrow(bombDef.getXPos(),bombDef.getUp().get(j),bomb.get(i).getXPos(),bomb.get(i).getYPos()) != -1){
					bomb.get(i).setTime(15);
				}
			}
		}
		
		for(int j = 0; j < bombDef.getDown().size(); j++){
			for(int i = 0; i< link.size(); i++){
				if(touchArrow(bombDef.getXPos(),bombDef.getDown().get(j),link.get(i).getXPos(),link.get(i).getYPos()) != -1){
					link.get(i).getDamage(1);
				}
			}
			
			if(bombDef.getPlayer() != -1){
				for(int i = 0; i< monster.size(); i++){
					if(touchArrow(bombDef.getXPos(),bombDef.getDown().get(j),monster.get(i).getXPos(),monster.get(i).getYPos()) != -1){
						monster.get(i).getDamage(1);
					}
				}
			}

			for(int i = 0; i< bomb.size(); i++){
				if(touchArrow(bombDef.getXPos(),bombDef.getDown().get(j),bomb.get(i).getXPos(),bomb.get(i).getYPos()) != -1){
					bomb.get(i).setTime(15);
				}
			}
		}

		for(int j = 0; j < bombDef.getLeft().size(); j++){
			for(int i = 0; i< link.size(); i++){
				if(touchArrow(bombDef.getLeft().get(j),bombDef.getYPos(),link.get(i).getXPos(),link.get(i).getYPos()) != -1){
					link.get(i).getDamage(1);
				}
			}
			
			if(bombDef.getPlayer() != -1){
				for(int i = 0; i< monster.size(); i++){
					if(touchArrow(bombDef.getLeft().get(j),bombDef.getYPos(),monster.get(i).getXPos(),monster.get(i).getYPos()) != -1){
						monster.get(i).getDamage(1);
					}
				}
			}

			for(int i = 0; i< bomb.size(); i++){
				if(touchArrow(bombDef.getLeft().get(j),bombDef.getYPos(),bomb.get(i).getXPos(),bomb.get(i).getYPos()) != -1){
					bomb.get(i).setTime(15);
				}
			}
		}
		
		for(int j = 0; j < bombDef.getRight().size(); j++){
			for(int i = 0; i< link.size(); i++){
				if(touchArrow(bombDef.getRight().get(j),bombDef.getYPos(),link.get(i).getXPos(),link.get(i).getYPos()) != -1){
					link.get(i).getDamage(1);
				}
			}
			
			if(bombDef.getPlayer() != -1){
				for(int i = 0; i< monster.size(); i++){
					if(touchArrow(bombDef.getRight().get(j),bombDef.getYPos(),monster.get(i).getXPos(),monster.get(i).getYPos()) != -1){
						monster.get(i).getDamage(1);
					}
				}
			}

			for(int i = 0; i< bomb.size(); i++){
				if(touchArrow(bombDef.getRight().get(j),bombDef.getYPos(),bomb.get(i).getXPos(),bomb.get(i).getYPos()) != -1){
					bomb.get(i).setTime(15);
				}
			}
		}
	}
	
	private void moveRandom(Monster monster){
		monster.setR(1);
		monster.setL(1);
		monster.setD(1);
		monster.setU(1);
		if(monster.getClass() != MovingTrap.class){
			if(monster.getClass() == Underground.class && ((Underground) monster).getUnderground() == true){
				
			}
			else{
				for(int i = 0; i < this.monster.size(); i++){
					if(this.monster.get(i) != monster 
							&& (this.monster.get(i).getClass() == Underground.class && ((Underground) this.monster.get(i)).getUnderground() == true)
							|| this.monster.get(i).getClass() == MovingTrap.class){
						if(touchDecor(monster.getXPos()-monster.getSpeed(),monster.getYPos(),this.monster.get(i).getXPos(),this.monster.get(i).getYPos()) != -1
								&& monster.getDirection() == 0){
							monster.setDirection(1);
						}
						if(touchDecor(monster.getXPos()+monster.getSpeed(),monster.getYPos(),this.monster.get(i).getXPos(),this.monster.get(i).getYPos()) != -1
								&& monster.getDirection() == 1){
							monster.setDirection(0);
						}
						if(touchDecor(monster.getXPos(),monster.getYPos()-monster.getSpeed(),this.monster.get(i).getXPos(),this.monster.get(i).getYPos()) != -1
								&& monster.getDirection() == 2){
							monster.setDirection(3);
						}
						if(touchDecor(monster.getXPos(),monster.getYPos()-monster.getSpeed(),this.monster.get(i).getXPos(),this.monster.get(i).getYPos()) != -1
								&& monster.getDirection() == 3){
							monster.setDirection(2);
						}
					}
				}
			}
		}
		if(monster.getXPos()%40 == 0 && monster.getYPos()%40 == 0){
			
			for(int i = 0; i < decor.size(); i++){
				if(decor.get(i).getClass() != Floor.class){
					if(touchDecor(monster.getXPos()+5,monster.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos()) == 0){
						monster.setR(0);
					}

					if(touchDecor(monster.getXPos()-5,monster.getYPos(),decor.get(i).getXPos(),decor.get(i).getYPos()) == 1){
						monster.setL(0);
					}

					if(touchDecor(monster.getXPos(),monster.getYPos()+5,decor.get(i).getXPos(),decor.get(i).getYPos()) == 2){
						monster.setD(0);
					}

					if(touchDecor(monster.getXPos(),monster.getYPos()-5,decor.get(i).getXPos(),decor.get(i).getYPos()) == 3){
						monster.setU(0);
					}

				}
			}
			for(int i = 0; i < bomb.size(); i++){

				if(touchDecor(monster.getXPos()+5,monster.getYPos(),bomb.get(i).getXPos(),bomb.get(i).getYPos()) == 0){
					monster.setR(0);
				}

				if(touchDecor(monster.getXPos()-5,monster.getYPos(),bomb.get(i).getXPos(),bomb.get(i).getYPos()) == 1){
					monster.setL(0);
				}

				if(touchDecor(monster.getXPos(),monster.getYPos()+5,bomb.get(i).getXPos(),bomb.get(i).getYPos()) == 2){
					monster.setD(0);
				}

				if(touchDecor(monster.getXPos(),monster.getYPos()-5,bomb.get(i).getXPos(),bomb.get(i).getYPos()) == 3){
					monster.setU(0);
				}

			}
			
			if(monster.getDirection() == 0){
				if(monster.getL() == 0 && monster.getU() == 0 && monster.getD() == 0){
					monster.setDirection(1);
				}
				else if(monster.getU() == 0 && monster.getD() == 0){

				}
				else{
					List<Integer> dir = new ArrayList<Integer>();
					if(monster.getL() == 1){
						dir.add(0);
					}
					if(monster.getR() == 1){
						dir.add(1);
					}
					if(monster.getU() == 1){
						dir.add(2);
					}
					if(monster.getD() == 1){
						dir.add(3);
					}
					java.util.Random r=new java.util.Random();
					int random = r.nextInt(dir.size()+1);
					monster.setDirection(random);
					
				}
			}
			else if(monster.getDirection() == 1){
				if(monster.getR() == 0 && monster.getU() == 0 && monster.getD() == 0){
					monster.setDirection(0);
				}
				else if(monster.getU() == 0 && monster.getD() == 0){

				}
				else{
					List<Integer> dir = new ArrayList<Integer>();
					if(monster.getL() == 1){
						dir.add(0);
					}
					if(monster.getR() == 1){
						dir.add(1);
					}
					if(monster.getU() == 1){
						dir.add(2);
					}
					if(monster.getD() == 1){
						dir.add(3);
					}
					java.util.Random r=new java.util.Random() ;
					int random = r.nextInt(dir.size()+1);
					monster.setDirection(random);
					
				}
			}
			else if(monster.getDirection() == 2){
				if(monster.getU() == 0 && monster.getL() == 0 && monster.getR() == 0){
					monster.setDirection(3);
				}
				else if(monster.getL() == 0 && monster.getR() == 0){

				}
				else{
					List<Integer> dir = new ArrayList<Integer>();
					if(monster.getL() == 1){
						dir.add(0);
					}
					if(monster.getR() == 1){
						dir.add(1);
					}
					if(monster.getU() == 1){
						dir.add(2);
					}
					if(monster.getD() == 1){
						dir.add(3);
					}
					java.util.Random r=new java.util.Random() ;
					int random = r.nextInt(dir.size()+1);
					monster.setDirection(random);
				}
			}
			else{
				if(monster.getD() == 0 && monster.getL() == 0 && monster.getR() == 0){
					monster.setDirection(2);
				}
				else if(monster.getL() == 0 && monster.getR() == 0){

				}
				else{
					List<Integer> dir = new ArrayList<Integer>();
					if(monster.getL() == 1){
						dir.add(0);
					}
					if(monster.getR() == 1){
						dir.add(1);
					}
					if(monster.getU() == 1){
						dir.add(2);
					}
					if(monster.getD() == 1){
						dir.add(3);
					}
					java.util.Random r=new java.util.Random() ;
					int random = r.nextInt(dir.size()+1);
					monster.setDirection(random);
				}
			}
		}
		monster.move();
	}
	
		private int fireDirection(Monster monster){
		int direction = -1;
		int x = 0;
		int y = 0;
		if(monster.getXPos()%40 == 0 && monster.getYPos()%40 == 0){
			for(int i = 0; i < link.size(); i++){
				if(link.get(i).getXPos()%40 <= 20){
					x = link.get(i).getXPos() - link.get(i).getXPos()%40;
				}
				else{
					x = link.get(i).getXPos() + 40 - link.get(i).getXPos()%40;
				}
				if(link.get(i).getYPos()%40 <= 20){
					y = link.get(i).getYPos() - link.get(i).getYPos()%40;
				}
				else{
					y = link.get(i).getYPos() + 40 - link.get(i).getYPos()%40;
				}
				if(Math.abs(link.get(i).getXPos() - monster.getXPos()) < 40){
					if(link.get(i).getYPos() > monster.getYPos()){
						direction = 3;
					}
					if(link.get(i).getYPos() < monster.getYPos()){
						direction = 2;
					}
				}
				if(Math.abs(link.get(i).getYPos() - monster.getYPos()) < 40){
					if(link.get(i).getXPos() > monster.getXPos()){
						direction = 1;
					}
					if(link.get(i).getXPos() < monster.getXPos()){
						direction = 0;
					}
				}
			}
			if(direction == 0){
				for(int k = x/40; k < monster.getXPos()/40; k++){
					for(int i = 0; i < decor.size(); i++){
						if(decor.get(i).getClass() != Floor.class && decor.get(i).getXPos() == k*40 && decor.get(i).getYPos() == monster.getYPos()){
							if(direction != -1){
							}
							direction = -1;
						}
					}
				}
			}
			else if(direction == 1){
				for(int k = monster.getXPos()/40+1; k < x/40; k++){
					for(int i = 0; i < decor.size(); i++){
						if(decor.get(i).getClass() != Floor.class && decor.get(i).getXPos() == k*40 && decor.get(i).getYPos() == monster.getYPos()){
							if(direction != -1){
							}
							direction = -1;
						}
					}
				}
			}
			else if(direction == 2){
				for(int k = y/40; k < monster.getYPos()/40; k++){
					for(int i = 0; i < decor.size(); i++){
						if(decor.get(i).getClass() != Floor.class && decor.get(i).getXPos() == monster.getXPos() && decor.get(i).getYPos() == k*40){
							if(direction != -1){
							}
							direction = -1;
						}
					}
				}
			}
			else{
				for(int k = monster.getYPos()/40+1; k < y/40; k++){
					for(int i = 0; i < decor.size(); i++){
						if(decor.get(i).getClass() != Floor.class && decor.get(i).getXPos() == monster.getXPos() && decor.get(i).getYPos() == k*40){
							if(direction != -1){
							}
							direction = -1;
						}
					}
				}
			}
		}
		return direction;
	}
	
	public void monsterInteraction(Monster monster){
		if(monster.getClass() == Melee.class){
			moveRandom(monster);
		}
		else if(monster.getClass() == MovingTrap.class){
			moveRandom(monster);
		}
		else if(monster.getClass() == Ranged.class){
			monster.cdTick(3);
			if(fireDirection(monster) != -1 && monster.getCooldown() > 40){
				monster.setAction(true);
				monster.setDirection(fireDirection(monster));
				monster.setActualFrame(1);
				monster.setName("res/Monster/RangedArrow");
				}
			if(monster.getAction() == true){
				monster.tick(5);
				if(monster.getTime() == 8){
					((Ranged) monster).fireArrow(arrow);
					monster.setAction(false);
					monster.setTime(0);
					monster.setCooldown(0);
					monster.setName("res/Monster/RangedRun");
				}
			}
			else{
				moveRandom(monster);
			}
		}
		else if(monster.getClass() == Bomber.class){
			monster.cdTick(3);
			if(fireDirection(monster) != -1 && monster.getCooldown() > 40){
				monster.setAction(true);
				monster.setDirection(fireDirection(monster));
				monster.setName("res/Monster/BomberThrow");
				}
			if(monster.getAction() == true){
				((Bomber) monster).bombTick(4,8);
				if(((Bomber) monster).getBombFrame() == 4){
					((Bomber) monster).setBomb(bomb);
					System.out.println(bomb.size());
					bomb.get(bomb.size()-1).setDirection(monster.getDirection());
					monster.setAction(false);
					((Bomber) monster).setBombFrame(1);
					monster.setCooldown(0);
					monster.setName("res/Monster/BomberRun");
				}
			}
			else{
				moveRandom(monster);
			}
				
		}
		else if(monster.getClass() == Boss.class){
			if(monster.getLifePoint() < 3 && ((Boss) monster).getRage() == false){
				((Boss) monster).setAttackCd(((Boss) monster).getAttackCd()/2);
				((Boss) monster).setBossTick(0);
				monster.setCooldown(0);
				((Boss) monster).setRage(true);
			}
			if(((Boss) monster).getRage() == true){
				((Boss) monster).tickBoss(5);
			}
			else{
				((Boss) monster).tickBoss(10);
			}
			monster.cdTick(1);
			if(monster.getCooldown() == ((Boss) monster).getAttackCd()){
				((Boss) monster).fireBall(fireBall, link.get(0));
			}
			if(monster.getCooldown() == (((Boss) monster).getAttackCd() + 2*((Boss) monster).getAttackCd()/10)){
				((Boss) monster).fireBall(fireBall, link.get(0));
			}
			if(monster.getCooldown() == (((Boss) monster).getAttackCd() + 4*((Boss) monster).getAttackCd()/10)){
				((Boss) monster).fireBall(fireBall, link.get(0));
			}
			if(monster.getCooldown() == ((Boss) monster).getAttackCd()*2){
				((Boss) monster).thunder(thunder, link.get(0));
				thunder.get(thunder.size()-1).appear(thunder.get(thunder.size()-1).getXPos(), thunder.get(thunder.size()-1).getYPos());
			}
			if(monster.getCooldown() == ((Boss) monster).getAttackCd()*3){
				((Boss) monster).fireBall2(fireBall);
			}
			if(monster.getCooldown() == ((Boss) monster).getAttackCd()*4){
				teleportation(monster);
				monster.setCooldown(0);
			}

		}
		else if(monster.getClass() == Underground.class){
			if(fireDirection(monster) != -1 && Math.abs(monster.getXPos() - link.get(0).getXPos()) < 80 && Math.abs(monster.getYPos() - link.get(0).getYPos()) < 80 
					&& ((Underground) monster).getUnderground() == true){
				((Underground) monster).setUnderground(false);
				monster.setInv(1);
				monster.setActualFrame(1);
				monster.setSpeed(2);
				monster.setName("res/Monster/underground");
			}
			if(((Underground) monster).getUnderground() == false && Math.abs(monster.getXPos() - link.get(0).getXPos()) > 140 && Math.abs(monster.getYPos() - link.get(0).getYPos()) > 140
					&& monster.getXPos()%40 == 0 && monster.getYPos()%40 == 0){
				((Underground) monster).setUnderground(true);
				monster.setInvicible();
				monster.setActualFrame(1);
				monster.setSpeed(4);
				monster.setName("res/Monster/hidden");
			}
			else{
				moveRandom(monster);
			}
		}
	}
	
	public void fireBallInteraction(FireBall fireBall){
		fireBall.move();
		for(int i = 0; i < link.size(); i++){
			if(Math.sqrt(Math.pow((link.get(i).getXPos()+ 10 - fireBall.getXPos()),2) + Math.pow((link.get(i).getYPos() +10  - fireBall.getYPos()),2))<20){
				link.get(i).getDamage(1);
			}
		}
	}
	
	public void thunderInteraction(Thunder thunder){
		thunder.tickThunder(40);
		if(thunder.getActualFrame() == 2){
			for(int i = 0; i < link.size(); i++){
				for(int j = 0; j < 5; j++){
					if(touchDecorB(link.get(i).getXPos(), link.get(i).getYPos(), thunder.getListPos().get(j)[0], thunder.getListPos().get(j)[1]) != -1){
						link.get(i).getDamage(1);
					}
				}
			}
		}
	}
	
	private void teleportation(Monster monster){
		java.util.Random r=new java.util.Random();
		int randomX = r.nextInt(12);
		int randomY = r.nextInt(12);
		while(caseOccupied((randomX+2)*40,(randomY+2)*40)){
			randomX = r.nextInt(12);
			randomY = r.nextInt(12);
		}
		monster.setXPos((randomX+2)*40);
		monster.setYPos((randomY+2)*40);
	}
	
	public boolean getChestOpen(){
		return this.chestOpen;
	}
	
	public void setChestOpen(boolean chestOpen){
		this.chestOpen = chestOpen;
	}
	
	private boolean caseOccupied(int xPos, int yPos){
		int x = 0;
		int y = 0;
		
		for(int i = 0; i < link.size(); i++){
			if(link.get(i).getXPos()%40 <= 20){
				x = link.get(i).getXPos() - link.get(i).getXPos()%40;
			}
			else{
				x = link.get(i).getXPos() + 40 - link.get(i).getXPos()%40;
			}
			if(link.get(i).getYPos()%40 <= 20){
				y = link.get(i).getYPos() - link.get(i).getYPos()%40;
			}
			else{
				y = link.get(i).getYPos() + 40 - link.get(i).getYPos()%40;
			}
			if(xPos == x && yPos == y){
				return true;
			}
		}

		if(monster.size() > 0){
			for(int i = 0; i < monster.size(); i++){
				if(monster.get(i).getXPos()%40 <= 20){
					x = monster.get(i).getXPos() - monster.get(i).getXPos()%40;
				}
				else{
					x = monster.get(i).getXPos() + 40 - monster.get(i).getXPos()%40;
				}
				if(monster.get(i).getYPos()%40 <= 20){
					y = monster.get(i).getYPos() - monster.get(i).getYPos()%40;
				}
				else{
					y = monster.get(i).getYPos() + 40 - monster.get(i).getYPos()%40;
				}
				if(xPos == x && yPos == y){
					return true;
				}
			}
		}
		for(int i = 0; i < decor.size(); i++){
			if(xPos == decor.get(i).getXPos() && yPos == decor.get(i).getYPos() && decor.get(i).getClass() != Floor.class){
				return true;
			}
		}
		return false;
	}
}
