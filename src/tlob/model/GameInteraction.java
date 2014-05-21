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
	
	/*
	 * Fonction touch
	 */
	private boolean doesTouch(int x1, int y1, int x2, int y2, int w1, int h1, int w2, int h2) {
		return !(x1 + w1 <= x2 || x2 + w2 <= x1 || y1 + h1 <= y2 || y2 + h2 <= y1);
	}
	
	private boolean doesTouch(Hitbox a, Hitbox b) {
		return doesTouch(a.getXPos(), a.getYPos(), b.getXPos(), b.getYPos(), a.getWidth(), a.getHeight(), b.getWidth(), b.getHeight());
	}
	
	private boolean doesTouch(int[] newpos, Hitbox a, Hitbox b) {
		return doesTouch(newpos[0], newpos[1], b.getXPos(), b.getYPos(), a.getWidth(), a.getHeight(), b.getWidth(), b.getHeight());
	}
	
	private boolean doesTouch(Hitbox a, int[] newpos, Hitbox b) {
		return doesTouch(a.getXPos(), a.getYPos(), newpos[0], newpos[1], a.getWidth(), a.getHeight(), b.getWidth(), b.getHeight());
	}
	
	private boolean doesTouch(int[] newpos1, Hitbox a, int[] newpos2, Hitbox b) {
		return doesTouch(newpos1[0], newpos1[1], newpos2[0], newpos2[1], a.getWidth(), a.getHeight(), b.getWidth(), b.getHeight());
	}
	
	private Direction touch(int x1, int y1, int x2, int y2, int w1, int h1, int w2, int h2) {
		if(! doesTouch(x1, y1, x2, y2, w1, h1, w2, h2))
			return null;
		int DX = (x1 + w1 / 2) - (x2 + w2 / 2);
		int DY = (y1 + h1 / 2) - (y2 + h2 / 2);
		if(Math.abs(DX) > Math.abs(DY))
			return Direction.GAUCHE.inverse(DX < 0);
		else
			return Direction.HAUT.inverse(DY < 0);
	}
	
	private Direction touch(Hitbox a, Hitbox b) {
		return touch(a.getXPos(), a.getYPos(), b.getXPos(), b.getYPos(), a.getWidth(), a.getHeight(), b.getWidth(), b.getHeight());
	}
	
	private Direction touch(int[] newpos, Hitbox a, Hitbox b) {
		return touch(newpos[0], newpos[1], b.getXPos(), b.getYPos(), a.getWidth(), a.getHeight(), b.getWidth(), b.getHeight());
	}
	
	private Direction touch(Hitbox a, int[] newpos, Hitbox b) {
		Direction d = touch(b, newpos, a);
		return d == null ? null : d.inverse();
	}
	
	/*
	 * Vrais fonctions
	 */
	
	public void linkInteraction(Link link){
		link.setAllDir();
		
		List<Decor> decorTouch = new ArrayList<>();
		for(Decor d : decor)
			if(doesTouch(link, d))
				decorTouch.add(d);
		
		for(Decor d : decor) {
			if(doesTouch(link, d)){
				if(d instanceof Door)
				{
					Door door = (Door)d;
					if(door.getOpen()) {
						int line = door.getLine();
						int column = door.getColumn();
						int level  = door.getLevel();
		
						map.saveMap(map.listToMap(decor, monster));
				
						if(column != 0){
							link.setXPos(link.getXPos() - ((32*15)+15)*column );
							map.setRoomColumn(Integer.toString(Integer.parseInt(map.getRoomColumn()) + column));
						}
						
						else if(line != 0){
							link.setYPos(link.getYPos() + ((32*15)+15)*line);
							map.setRoomLine(Integer.toString(Integer.parseInt(map.getRoomLine()) - line));
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
							changeLevel = true;
							link.setXPos(18*15+10);
							link.setYPos(35*15);
							link.setDirection(Direction.HAUT);
		
						}
						char[][] tableau = map.loadRoom();
								
						bonus.clear();
						bomb.clear();
						decor.clear();
						decor.addAll( map.mapToListDecor(tableau) );
						
						monster.clear();
						monster.addAll( map.mapToListMonster(tableau) );
						
						break;
					}
			    }
				else if(d instanceof Treasure) {
					Treasure treasure = (Treasure)d;
					if(link.getDirection() == Direction.HAUT
						&& treasure.isBonusTaken() == false)
					{
						d.setActualFrame(2);
						if(link.getName().contentEquals("res/Link/LinkOpen") == false){
							link.setActualFrame(1);
						}
						link.setName("res/Link/LinkOpen");
						if(link.getActualFrame() != 3){
							this.chestOpen = true;
							link.tick(3,5);
							link.unsetAllDir();
							link.setName("res/Link/LinkOpen");
						}
						else{
							treasure.setBonusTaken(true);
							bonus.add( new Bonus(link.getXPos(), link.getYPos() - 30, treasure.typeToString(), treasure.getBonusType()));
							link.setAllDir();
						}
					}
				}
			}
		}
		
		for(Decor d : decorNotFloor()) {
			for(Direction dir : Direction.values()) {
				if( doesTouch(link.nextPos(dir), link, d) ){
					if(d instanceof Door && ((Door)d).getOpen() == true){
						
					} else if(d instanceof Treasure && ((Treasure) d).isBonusTaken() == true){
						
					} else {
						link.unsetDir(dir);
					}
				}
			}
		}
		
		for(Monster m : monster) {
			Direction a = touch(link, m);
			if(a != null)
			{
				if(link.getDirSet(a) != 0 && link.getInvincible() == 1){
					for(int j = 0; j < 3; j++)
					{
						link.setPos( link.nextPos(a, 5) );
						for(Decor d : decor)
							if(doesTouch(link,d))
								link.unsetDir(a);
					}
				}
				link.getDamage(1);
			}
		}
		for(Bomb b : bomb) {
			Direction a = touch(link,b);
			if(a != null)
			{
				// link.unsetDir(a.inverse());
				if(link.getGauntlet()
						&& link.getDirection() == a.inverse()
						&& b.getPlayer() == link){
					b.setDirection(link.getDirection());
				}
			}
		}
		for(int i = 0; i < bonus.size(); i++) {
			Bonus b  = bonus.get(i);
			if(touch(link, b) != null){
				b.activation(link);
				
				new Sound(b.getName() == "res/Rubis" ? "rupee" : "bonus").play();
				
				bonus.remove(i);
				i--;
			}
		}
	}
	
	public int arrowInteraction(Arrow arrow){		
		for(Link l : link){
			if(doesTouch(arrow, l)) {
				if(arrow.getPlayer() != l) {
					l.getDamage(1);
					return 1;
				}
			}
		}
		
		if(arrow.getPlayer() != null){
			for(Monster m : monster) {
				if(doesTouch(arrow, m)) {
					if(doesTouch(arrow, m)){
						m.getDamage(1);
						return 1;
					}
				}
			}
		}
		
		for(Decor d : decorNotFloor()) {
			if(doesTouch(arrow,d)) {
				return 2;
			}
		}
		
		for(Bomb b : bomb) {
			if(doesTouch(arrow,b)){
				b.setTime(15);
				return 3;
			}
		}
		arrow.move();
		return 0;
	}
	
	public void bombInteraction(Bomb bomb){
		for(Bomb b : this.bomb) {
			if(bomb.getDirection() != null){
				boolean condition = bomb.getDirection().dy == 0 ?
						b.getXPos() != bomb.getXPos() :
						b.getYPos() != bomb.getYPos();
						
				if(doesTouch(bomb.nextPos(bomb.getDirection(),  15), bomb, b) && condition) {
					bomb.setPos( bomb.nextPos(bomb.getDirection().inverse(), 40) );
				}
			}
		}
		
		if(bomb.getPlayer() != null) {
			for(Monster m : monster)
				if(doesTouch(bomb, m))
					bomb.setDirection(null);
		}
		
		for(Link l : link)
			if(doesTouch(bomb, l))
				bomb.setDirection(null);
		
		for(Decor d : decorNotFloor()) {
			if(bomb.getDirection() != null) {
				if(doesTouch(bomb.nextPos(bomb.getDirection(), 15), bomb, d)){
					bomb.setPos( bomb.nextPos(bomb.getDirection().inverse(), 45));
					bomb.setDirection(null);
				}
			}
		}
		
		bomb.move();
	}
	
	public void deflagrationAppear(BombDeflagration bombDef, int rangeBomb){
		int liste[][][] = bombDef.listeExplosion(rangeBomb);
		for(int j = 0; j < bombDef.getPortee();j++)
		{
			for(int i = 0; i < decor.size(); i++)
			{
				Decor d = decor.get(i);
				for(Direction dir : Direction.values())
				{
					if(!(d instanceof Floor))
					{
						if(doesTouch(liste[dir.ordinal()][j], bombDef, d))
						{
							if(d instanceof Jar && bombDef.getDirSet(dir) == 1 && bombDef.getPlayer() != null)
							{
								Jar jar = (Jar)d;
								bombDef.getListe(dir).add( liste[dir.ordinal()][j] );
								bombDef.getListe(dir).add( liste[dir.ordinal()][j+1] );
								//left.add(liste[0][j+2]);
								
								if(link.size() == 1)
									jar.randomBonus(bonus, d.getXPos(), d.getYPos());
								else
									jar.randomBonusVersus(bonus, d.getXPos(), d.getYPos());
								
								decor.remove(i);
								decor.get(i-1).setName("res" + map.getEnvironment() + "/BrokenJar");
								new Sound("jarbroken").play();
							}
							bombDef.unsetDir(dir);
						}
					}
				}
			}
			for(Direction dir : Direction.values())
				if(bombDef.getDirSet(dir) == 1)
					bombDef.getListe(dir).add(liste[dir.ordinal()][j]);
		}
		System.out.println("");
	}
	
	public void defInteraction(BombDeflagration bombDef)
	{
		for(Direction dir : Direction.values()) {
			for(int[] newpos : bombDef.getListe(dir)) {
				for(Link l : link)
					if(doesTouch(newpos, bombDef, l))
						l.getDamage(1);
					
				if(bombDef.getPlayer() != null)
					for(Monster m : monster)
						if(doesTouch(newpos, bombDef, m))
							m.getDamage(1);
		
				for(Bomb b : bomb)
					if(doesTouch(newpos, bombDef, b))
						b.setTime(15);
			}
		}
	}
	
	private boolean accessible(Monster m){
		if(m instanceof MovingTrap)
			return false;
		if(m instanceof Underground && ((Underground) m).getUnderground() == true)
			return false;
		return true;
	}
	
	private void moveRandom(Monster monster){
		monster.setAllDir();
		if( accessible(monster) ) {
			for(Monster m : this.monster) {
				if(m != monster  && ! accessible(m)) {
					Direction dir = monster.getDirection();
					if(doesTouch(monster.nextPos(dir, monster.getSpeed()), monster, m)) {
						monster.setDirection(dir.inverse());
					}
				}
			}
		}
		if(monster.getXPos() % 40 == 0 && monster.getYPos() % 40 == 0)
		{
			for(Decor d : decorNotFloor()) {
				for(Direction dir : Direction.values()) {
					if(doesTouch(monster.nextPos(dir,10), monster, d))
						monster.unsetDir(dir);
				}
			}
			
			for(Bomb b : bomb)
				for(Direction dir : Direction.values())
					if(doesTouch(monster.nextPos(dir,10), monster, b))
						monster.unsetDir(dir);
			
			monster.setRandomDirection();
		}
		monster.move();
	}
	
	private Direction fireDirection(Monster monster)
	{
		Direction direction = null;
		if(monster.getXPos() % 40 == 0 && monster.getYPos() % 40 == 0)
		{
			for(Link l : link)
			{
				int x = l.getXPos() % 40 <= 20 ? 
					l.getXPos() - l.getXPos() % 40 :
					l.getXPos() + 40 - l.getXPos() % 40;
				
				int y = l.getYPos() % 40 <= 20 ?
					l.getYPos() - l.getYPos() % 40 :
					l.getYPos() + 40 - l.getYPos() % 40;
				
				if(Math.abs(l.getXPos() - monster.getXPos()) < 40)
					direction = Direction.HAUT.inverse(l.getYPos() >= monster.getYPos());
				
				if(Math.abs(l.getYPos() - monster.getYPos()) < 40)
					direction = Direction.DROITE.inverse(l.getXPos() >= monster.getXPos());
			
				int mx = monster.getXPos();
				int my = monster.getYPos();
				
				if(direction == Direction.GAUCHE)
				{
					for(int k = x/40; k < mx/40; k++)
						for(Decor d : decorNotFloor())
							if(d.getXPos() == k * 40 && d.getYPos() == my)
								direction = null;
				}
				else if(direction == Direction.DROITE)
				{
					for(int k = mx/40 + 1; k < x/40; k++)
						for(Decor d : decorNotFloor())
							if(d.getXPos() == k * 40 && d.getYPos() == my)
								direction = null;
				}
				else if(direction == Direction.HAUT)
				{
					for(int k = y/40; k < my/40; k++)
						for(Decor d : decorNotFloor())
							if(d.getXPos() == mx && d.getYPos() == k*40)
								direction = null;
				}
				else
				{
					for(int k = my/40 + 1; k < y/40; k++)
						for(Decor d : decorNotFloor())
							if(d.getXPos() == mx && d.getYPos() == k*40)
								direction = null;
				}
			}
		}
		return direction;
	}
	
	public void monsterInteraction(Monster monster){
		Link l = link.get(0);
		if(monster instanceof Melee){
			moveRandom(monster);
		}
		else if(monster instanceof MovingTrap){
			moveRandom(monster);
		}
		else if(monster instanceof Ranged){
			Ranged ranged = (Ranged)monster;
			monster.cdTick(3);
			if(fireDirection(monster) != null && monster.getCooldown() > 40){
				monster.setAction(true);
				monster.setDirection(fireDirection(monster));
				monster.setActualFrame(1);
				monster.setName("res/Monster/RangedArrow");
			}
			if(monster.getAction() == true){
				monster.tick(5);
				if(monster.getTime() == 8){
					ranged.fireArrow(arrow);
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
		else if(monster instanceof Bomber){
			Bomber bomber = (Bomber) monster;
			monster.cdTick(3);
			if(fireDirection(monster) != null && monster.getCooldown() > 40){
				monster.setAction(true);
				monster.setDirection(fireDirection(monster));
				monster.setName("res/Monster/BomberThrow");
			}
			if(monster.getAction() == true){
				bomber.bombTick(4,8);
				if(bomber.getBombFrame() == 4){
					bomber.setBomb(bomb);
					bomb.get(bomb.size() - 1).setDirection( monster.getDirection() );
					monster.setAction(false);
					bomber.setBombFrame(1);
					monster.setCooldown(0);
					monster.setName("res/Monster/BomberRun");
				}
			}
			else{
				moveRandom(monster);
			}
				
		}
		else if(monster instanceof Boss){
			Boss boss = (Boss)monster;
			
			if(boss.getLifePoint() < 3 && boss.getRage() == false){
				boss.setAttackCd(boss.getAttackCd() / 2);
				boss.setBossTick(0);
				boss.setCooldown(0);
				boss.setRage(true);
			}
			
			boss.tickBoss(boss.getRage() ? 5 : 10);
			
			monster.cdTick(1);
			
			int cd = boss.getCooldown();
			int ad = boss.getAttackCd();
			
			if(cd == (int)(ad * 1)){
				boss.fireBall(fireBall, l);
			}
			if(cd == (int)(ad * 1.2)){
				boss.fireBall(fireBall, l);
			}
			if(cd == (int)(ad * 1.4)){
				boss.fireBall(fireBall, l);
			}
			if(cd == (int)(ad * 2)){
				boss.thunder(thunder, l);
				thunder.get(thunder.size()-1).appear(thunder.get(thunder.size()-1).getXPos(), thunder.get(thunder.size()-1).getYPos());
			}
			if(cd == (int)(ad * 3)){
				boss.fireBall2(fireBall);
			}
			if(cd == (int)(ad * 4)){
				teleportation(boss);
				boss.setCooldown(0);
			}

		}
		else if(monster instanceof Underground){
			Underground under = (Underground)monster;
			if(fireDirection(monster) != null
					&& Math.abs(monster.getXPos() - link.get(0).getXPos()) < 80
					&& Math.abs(monster.getYPos() - link.get(0).getYPos()) < 80 
					&& under.getUnderground() == true){
				under.setUnderground(false);
				monster.setInv(1);
				monster.setActualFrame(1);
				monster.setSpeed(2);
				monster.setName("res/Monster/underground");
			}
			if(under.getUnderground() == false
					&& Math.abs(monster.getXPos() - link.get(0).getXPos()) > 140
					&& Math.abs(monster.getYPos() - link.get(0).getYPos()) > 140
					&& monster.getXPos()%40 == 0 && monster.getYPos()%40 == 0){
				under.setUnderground(true);
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
	
	private double norm(double x, double y) {
		return Math.sqrt(x*x + y*y);
	}
	
	private double distance(double x, double y, double x2, double y2) {
		return norm(x-x2, y-y2);
	}
	
	public void fireBallInteraction(FireBall fireBall){
		fireBall.move();
		for(Link l : link) {
			if( distance(l.getXPos() + 10, l.getYPos() + 10, fireBall.getXPos(), fireBall.getYPos()) < 20){
				l.getDamage(1);
			}
		}
	}
	
	public void thunderInteraction(Thunder thunder){
		thunder.tickThunder(40);
		if(thunder.getActualFrame() == 2){
			for(Link l : link)
				for(int[] newpos : thunder.getListPos())
					if(doesTouch(newpos, thunder, l))
						l.getDamage(1);
		}
	}
	
	private List<Decor> decorNotFloor() {
		ArrayList<Decor> li = new ArrayList<Decor>();
		for(Decor d : decor)
			if(!(d instanceof Floor))
				li.add(d);
		return li;
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
		
		for(Link l : link){
			if(l.getXPos() % 40 <= 20){
				x = l.getXPos() - l.getXPos()%40;
			}
			else{
				x = l.getXPos() + 40 - l.getXPos()%40;
			}
			if(l.getYPos()%40 <= 20){
				y = l.getYPos() - l.getYPos()%40;
			}
			else{
				y = l.getYPos() + 40 - l.getYPos()%40;
			}
			if(xPos == x && yPos == y){
				return true;
			}
		}

		if(monster.size() > 0){
			for(Monster m : monster) {
				if(m.getXPos()%40 <= 20){
					x = m.getXPos() - m.getXPos()%40;
				}
				else{
					x = m.getXPos() + 40 - m.getXPos()%40;
				}
				if(m.getYPos()%40 <= 20){
					y = m.getYPos() - m.getYPos()%40;
				}
				else{
					y = m.getYPos() + 40 - m.getYPos()%40;
				}
				if(xPos == x && yPos == y){
					return true;
				}
			}
		}
		for(Decor d : decorNotFloor())
			if(xPos == d.getXPos() && yPos == d.getYPos())
				return true;
		return false;
	}
}
