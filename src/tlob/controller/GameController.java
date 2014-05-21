package tlob.controller;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.List;

import tlob.model.*;
import tlob.view.*;

public class GameController implements KeyListener{
	static enum Commands {
		LEFT, RIGHT, DOWN, UP,
		BOMB, ARROW, STAFF,
		PAUSE, ESCAPE
	}
	
	static enum StoreItem {
		FLECHE(10, 99),
		BOMB_RANGE(30, 9),
		BOMB(30, 9),
		SPEED(50, 6),
		HEART(100, 5);
		
		public int price;
		public int max;
		
		private StoreItem(int price, int max) {
			this.price = price;
			this.max = max;
		}
	}
	
	private boolean rightPressed = false;
	private boolean leftPressed = false;
	private boolean downPressed = false;
	private boolean upPressed = false;
	private boolean enterPressed = false;
	private boolean fireArrow = false;
	private boolean setBomb = false;
	private boolean useStaff = false;
	
	private boolean rightPressed2 = false;
	private boolean leftPressed2 = false;
	private boolean downPressed2 = false;
	private boolean upPressed2 = false;
	private boolean fireArrow2 = false;
	private boolean setBomb2 = false;
	private boolean useStaff2 = false;
	
	private boolean upPressedMenu = false;
	private boolean downPressedMenu = false;
	private boolean leftPressedMenu = false;
	private boolean rightPressedMenu = false;
	
	private boolean pausePressed = false;
	private boolean escapePressed = false;
	
	private boolean attributW;
	
	java.util.Random r = new java.util.Random( ) ;
	
	// 0 = menu, 1 = multi, 2 = solo
	// 3 game Over, 4 store, 5 gameover, 6 multi win
	
	
	private List<Link> link;
	private List<Monster> monster;
	private List<Bomb> bomb;
	private List<Arrow> arrow;
	private List<FireBall> fireBall;
	private List<Thunder> thunder;
	private List<BombDeflagration> bombDeflagration;
	private List<Menu> menu;
	private List<Menu> gameOver;
	private List<Menu> store;
	private GameInteraction interaction;
	private Status status = Status.MENU;
	private int statusMenu = 2; // va modifier le status dans le menu
	private Level level;
	private boolean pressedOnce = true; // premiere foi qu on appuie
	private Sound sound = new Sound();
	private Map map;
	
	public GameController(Level level){
		this.level = level;
		this.menu = level.getMenu();
		this.gameOver = level.getGameOver();
		this.store = level.getStore();
		this.sound.play("menu");
	}
	
	public void createGameController(Level level){
		this.link = level.getLink();
		this.monster = level.getMonster();
		this.bomb = level.getBomb();
		this.arrow = level.getArrow();
		this.bombDeflagration = level.getBombDeflagration();
		this.fireBall = level.getFireBall();
		this.thunder = level.getThunder();
		this.interaction = new GameInteraction(level);
		this.map = level.getMap();
	}
		
	public void setLink (List<Link> link){
		this.link = link;
	}
	
	public void setMonster(List<Monster> monster){
		this.monster = monster;
	}
	
	public void setBomb (List<Bomb> bomb){
		this.bomb = bomb;
	}
	
	public void setArrow (List<Arrow> arrow){
		this.arrow = arrow;
	}
	
	public void setBombDeflagration (List<BombDeflagration> bombDeflagration){
		this.bombDeflagration = bombDeflagration;
	}
	
	public void setInteraction (GameInteraction interaction){
		this.interaction = interaction;
	}
	
	public void updateMenu() {
		Sound soundChange = new Sound("menuchange");
		Sound soundChoose = new Sound("menuchoose");
		
		if(sound.isFinished())
			sound.play("menu");
		
		if (downPressedMenu && pressedOnce && statusMenu > 1 ){
			for (Menu m : menu) {
				if(m.getStatus() == statusMenu)
					m.setName("res/1player");
				else if(m.getStatus() == statusMenu - 1)
					m.setName("res/2playersbombs");
			}
			statusMenu -= 1;
			pressedOnce = false;
			soundChange.play();
		}
		else if (upPressedMenu && pressedOnce && statusMenu < 2){
			for (Menu m : menu) {
				if(m.getStatus() == statusMenu)
					m.setName("res/2players");
				else if(m.getStatus() == statusMenu + 1)
					m.setName("res/1playerbombs");

			}
			statusMenu += 1;
			pressedOnce = false;
			soundChange.play();

		}
		else if (!downPressedMenu && !upPressedMenu && !enterPressed){
			pressedOnce = true;
		}
		
		else if (enterPressed && pressedOnce){ // lance le solo
			status = Status.values()[statusMenu];
			deleteCopy();
			if(status == Status.MULTI){
				Map map = new Map(16, 16, "0", "0", "0");
				map.setEnvironment("/Forest");
				level.createLevel(map);
				createGameController(level);
				link.add( new Link(3, 1*40, 1*40, 2, Direction.DROITE, "res/Link/LinkRun", 0) );
				link.add( new Link(3, 13*40, 13*40, 2, Direction.GAUCHE, "res/RedLink/RedLinkRun", 1) );
				sound.soundEnd();
				sound.play("forest1");
				soundChoose.play();

			}
			else{
				Map map = new Map(16, 16, "1", "3", "1");
				level.createLevel(map);
				createGameController(level);
				link.add(new Link(3, 7 * 40, 13 * 40, 3, Direction.HAUT, "res/Link/LinkRun",0));

				sound.soundEnd();
				sound.play("desert1");
				soundChoose.play();
				attributW = false;
			}
			level.setStatus(status);
			enterPressed = false;
			statusMenu = 1;
		}
	}
	
	public void updateMulti() {
		if(sound.isFinished())
		{
			if (r.nextInt(2) == 0)
				sound.play("forest1");
			else
				sound.play("forest2");
		}
		
		for(int i = 0; i < link.size(); i++) {
			Link l = link.get(i);
			
			if(l.getLifePoint() <= 0){
				status = Status.MULTI_WIN;
				level.setStatus(status);
				sound.soundEnd();
				sound.play("multigameover");
				break;
			}
			
			//link.get(i).setSpeed(1);
			
			if(l.getInvincible() == 0){
				l.tickInvincible();
			}
		
			interaction.linkInteraction(link.get(i));

			if(rightPressed){
				link.get(0).setName("res/Link/LinkRun");
				link.get(0).move(Direction.DROITE);
			
			}
			if(leftPressed){
				link.get(0).setName("res/Link/LinkRun");
				link.get(0).move(Direction.GAUCHE);
			}
		
			if(downPressed){
				link.get(0).setName("res/Link/LinkRun");
				link.get(0).move(Direction.BAS);
			}
		
			if(upPressed){
				link.get(0).setName("res/Link/LinkRun");
				link.get(0).move(Direction.HAUT);
			}
		
			if(fireArrow){
				link.get(0).fireArrow(arrow);
				if(link.get(0).getActualFrame() == 6){
					fireArrow = false;
					link.get(0).setActualFrame(1);
				}
			}
			if(setBomb){
				int bomb0 = 0;
				for(Bomb b : bomb){
					if(b.getPlayer() == 0){
						bomb0++;
					}
				}
				if(bomb0< link.get(0).getNumberBomb()){
					link.get(0).setBomb(bomb);				
				}
				setBomb = false;
			}
			if(useStaff==true && link.get(0).getStaff()!=-1) {
				if(link.get(0).getStaff() == 0) {
					link.get(1).getDamage(1);			
					useStaff = false; 
					link.get(0).setStaff(-1); 
				}
				else if(link.get(0).getStaff() == 1) {
					link.get(1).setFrozen();
					link.get(1).tickFrozen();	
					if(link.get(1).getFrozen() == 1) {
						useStaff=false; 
						link.get(0).setStaff(-1); 
					}
				}
			}
			else {
				useStaff = false;
			}
		
			if(rightPressed2){
				link.get(1).setName("res/RedLink/RedLinkRun");
				link.get(1).move(Direction.DROITE);
			
			}
			if(leftPressed2){
				link.get(1).setName("res/RedLink/RedLinkRun");
				link.get(1).move(Direction.GAUCHE);
			}
		
			if(downPressed2){
				link.get(1).setName("res/RedLink/RedLinkRun");
				link.get(1).move(Direction.BAS);
			}
		
			if(upPressed2){
				link.get(1).setName("res/RedLink/RedLinkRun");
				link.get(1).move(Direction.HAUT);
			}
		
			if(fireArrow2){
				link.get(1).fireArrow(arrow);
				if(link.get(1).getActualFrame() == 6){
					fireArrow2 = false;
					link.get(1).setActualFrame(1);
				}
			}
			if(setBomb2){
				int bomb1 = 0;
				for(Bomb b : bomb){
					if(b.getPlayer() == 1){
						bomb1++;
					}
				}
				if(bomb1< link.get(1).getNumberBomb()){
					link.get(1).setBomb(bomb);				
				}
				setBomb2 = false;
			}
			
			if(useStaff2==true && link.get(1).getStaff()!=-1) {
				if(link.get(1).getStaff()==0) {
					link.get(0).getDamage(1);
					useStaff2=false; 
					link.get(1).setStaff(-1); 
				}
				else if(link.get(1).getStaff()==1) {
					link.get(0).setFrozen();
					link.get(0).tickFrozen();
					if(link.get(0).getFrozen()==1) {
						useStaff2=false; 
						link.get(1).setStaff(-1); 
					}
				}
			}
			
			else {
				useStaff2 = false;
			}
			
			interaction.linkInteraction(link.get(i));
		}

		for(int p = 0; p < arrow.size(); p++){
			int a = interaction.arrowInteraction(arrow.get(p));
			if(a != 0){
				if(a == 2){
					arrow.get(p).tick(5);
					//arrow.get(p).setActualFrame(1);
					if(arrow.get(p).getTime() == 3){
						arrow.remove(p);
					}
				}
				else{
					arrow.remove(p);
				}
			}
		}

		for(int p = 0; p < bomb.size(); p++){
			interaction.bombInteraction(bomb.get(p));
			bomb.get(p).tick();
			if(bomb.get(p).getTime() == 15){ //changer dans deflagration si changement de temps
				bombDeflagration.add(new BombDeflagration(bomb.get(p).getXPos(),bomb.get(p).getYPos(),
						"res/Deflagration",bomb.get(p).getPlayer()));
				bomb.remove(p);
				Sound soundBomb = new Sound();
				soundBomb.play("bomb");
			}
		}
		
		for(int p = 0; p < bombDeflagration.size(); p++){
			bombDeflagration.get(p).tick(2);
			if(bombDeflagration.get(p).getPortee() < link.get(bombDeflagration.get(p).getPlayer()).getRangeBomb()*4+2){
				interaction.deflagrationAppear(bombDeflagration.get(p),
						link.get(bombDeflagration.get(p).getPlayer()).getRangeBomb());
				interaction.defInteraction(bombDeflagration.get(p));
			}
			else{
				bombDeflagration.remove(p);
			}
		}
	}
	
	public void updateSolo() {
		Sound soundChange = new Sound();
		
		// /!\ il faut reinitialise le k avant de lancer le shop
		if (pausePressed && pressedOnce){
			status = Status.GAME_OVER;
			level.setStatus(status);
			pressedOnce = false;
		}
		
		else if (!pausePressed){
			pressedOnce = true;
		}
					
		if (gameOver(link.get(0))){
			status = Status.GAME_OVER;
			level.setStatus(status);
			enterPressed = false;
			fireArrow = false;
			sound.soundEnd();
			sound.play("gameOver");
			statusMenu = 2;
		}
		
		if(interaction.getChestOpen() == true){
			interaction.setChestOpen(false);
			soundChange.play("treasure");
		}
		
		for (int i =0;i<level.getDecor().size();i++){
			if(map.getRoomColumn().contentEquals("2")  && map.getLevel().contentEquals("2") && map.getRoomLine().contentEquals("2")){
				if(level.getDecor().get(i).getClass() == Door.class){
					((Door)(level.getDecor().get(i))).setOpen(false);
					level.getDecor().get(i).setName("res/Desert/Obstacle3");
				}					
			}
		}
		
		if (monster.size() == 0){
			for (int i =0;i<level.getDecor().size();i++){
				if(level.getDecor().get(i).getClass() == Door.class){
					((Door)(level.getDecor().get(i))).setOpen(true);
					if(((Door)level.getDecor().get(i)).getLine() == 1){
						level.getDecor().get(i).setName("res" + map.getEnvironment() +"/DoorU");
					}
					if(((Door)level.getDecor().get(i)).getLevel() == 1){
						level.getDecor().get(i).setName("res" + map.getEnvironment() + "/DoorR");
					}
				}				
			}
		}
		for(int t = 0; t < monster.size();t++){
			if(monster.get(t).getClass() == Boss.class && monster.get(t).getLifePoint() <= 0){
				status = Status.MULTI_WIN;
				level.setStatus(status);
				sound.soundEnd();
				sound.play("endgame");
			}
		}
		
		for(int t = 0; t < monster.size();t++){
			if(monster.get(t).getClass() == Boss.class && attributW == false){
				sound.soundEnd();
				sound.play("Boss");
				attributW = true;
			}
		}

		for (int p = 0; p < level.getDecor().size(); p ++){
			if(level.getDecor().get(p).getClass() == SpawnerMonster.class){
				((SpawnerMonster)(level.getDecor().get(p))).spawnMonster(level.getMonster());	
			}
			if(level.getDecor().get(p).getClass() == SpawnerFireBall.class){
				((SpawnerFireBall)(level.getDecor().get(p))).fireBall(fireBall, link.get(0));
			}
			
		}
		
		if(interaction.getChangeLevel() == true)
		{
			sound.soundEnd();
			statusMenu = 1;
			status = Status.STORE;
			level.setStatus(status);
			interaction.setChangeLevel(false);
			sound.play("shop");
		}
		else{
			if(sound.isFinished())
			{	
				for(int i = 0;i<monster.size();i++){
					if(monster.get(i).getClass() == Boss.class){
						sound.play("Boss");
						break;
					}
				}
						
				 if (map.getEnvironment() == "/Desert"){
					int random = r.nextInt(2);
					if (random == 0){
						sound.play("desert");
					}
					else if(random == 1){
						sound.play("desert1");
					}
				}
				else if (map.getEnvironment() == "/Forest"){
					int random = r.nextInt(2);
					if (random == 0){
						sound.play("forest1");
					}
					else if(random == 1){
						sound.play("forest2");
				}
				}
				else if (map.getEnvironment().contentEquals("/Dungeon")){
					int random = r.nextInt(2);
					if (random == 0){
						sound.play("dungeon");
					}
						else if(random == 1){
						sound.play("dungeon1");
					}
				}
			}
		
			
			for(int i = 0; i < link.size(); i++){
						
				if(link.get(i).getInvincible() == 0){
					link.get(i).tickInvincible();
				}
			
				interaction.linkInteraction(link.get(i));

				if(rightPressed){
					link.get(0).setName("res/Link/LinkRun");
					link.get(0).move(Direction.DROITE);
				
				}
				if(leftPressed){
					link.get(0).setName("res/Link/LinkRun");
					link.get(0).move(Direction.GAUCHE);
				}
			
				if(downPressed){
					link.get(0).setName("res/Link/LinkRun");
					link.get(0).move(Direction.BAS);
				}
			
				if(upPressed){
					link.get(0).setName("res/Link/LinkRun");
					link.get(0).move(Direction.HAUT);
				}
				
				if(fireArrow){
					link.get(0).fireArrow(arrow);
					if(link.get(0).getActualFrame() == 6){
						fireArrow = false;
						link.get(0).setActualFrame(1);
					}
				}
				if(setBomb){
					int bomb0 = 0;
					for(Bomb b : bomb){
						if(b.getPlayer() == 0){
							bomb0++;
						}
					}
					if(bomb0< link.get(0).getNumberBomb()){
						link.get(0).setBomb(bomb);				
					}
					setBomb = false;
				}
				if(useStaff && link.get(0).getStaff() != -1) {
					if(link.get(0).getStaff()==0) {
						for (Monster m : monster) {
							m.getDamage(1);			
						}
						useStaff=false; 
						link.get(0).setStaff(-1); 
					}
					else if(link.get(0).getStaff()==1) {
						for (Monster m : monster) {
							m.setFrozen();
							m.tickFrozen();	
							if(m.getFrozen()==1) {
								useStaff=false; 
								link.get(0).setStaff(-1); 
							}
						}
					}
				}
				else {
					useStaff = false;
				}
			}
		
		
			for(int i = 0; i < monster.size(); i++){
				if(monster.get(i).getInvincible() == 0 && monster.get(i).getClass() != MovingTrap.class){
					if((monster.get(i).getClass() == Underground.class && ((Underground) monster.get(i)).getUnderground() == true)){
					}
					else{
						monster.get(i).tickInvincible();
					}
				}
				interaction.monsterInteraction(monster.get(i));
				if(monster.get(i).getLifePoint() == 0){
					monster.remove(i);
				}
			}

			for(int p = 0; p < arrow.size(); p++){
				int a = interaction.arrowInteraction(arrow.get(p));
				if(a != 0){
					if(a == 2){
						arrow.get(p).tick(5);
						//arrow.get(p).setActualFrame(1);
						if(arrow.get(p).getTime() == 3){
							arrow.remove(p);
						}
					}
					else{
						arrow.remove(p);
					}
				}
			}
	
			for(int p = 0; p < bomb.size(); p++){
				interaction.bombInteraction(bomb.get(p));
				bomb.get(p).tick();
				if(bomb.get(p).getTime() == 15){ //changer dans deflagration si changement de temps
					bombDeflagration.add(new BombDeflagration(bomb.get(p).getXPos(),
							bomb.get(p).getYPos(),"res/Deflagration",bomb.get(p).getPlayer()));
					bomb.remove(p);
					Sound soundBomb = new Sound();
					soundBomb.play("bomb");
				}
			}
			
			for(int p = 0; p < bombDeflagration.size(); p++){
				bombDeflagration.get(p).tick(2);
				if(bombDeflagration.get(p).getPlayer() == -1){
					if(bombDeflagration.get(p).getPortee() < 2*4+2){
						interaction.deflagrationAppear(bombDeflagration.get(p), 2);
						interaction.defInteraction(bombDeflagration.get(p));
					}
					else{
						bombDeflagration.remove(p);
					}
				}
				else{
					if(bombDeflagration.get(p).getPortee() < link.get(bombDeflagration.get(p).getPlayer())
							.getRangeBomb()*4+2){
						interaction.deflagrationAppear(bombDeflagration.get(p), 
								link.get(bombDeflagration.get(p).getPlayer()).getRangeBomb());
						interaction.defInteraction(bombDeflagration.get(p));
					}
					else{
						bombDeflagration.remove(p);
					}
				}
			}
			
			for(int p = 0; p < fireBall.size(); p++){
				fireBall.get(p).tick();
				interaction.fireBallInteraction(fireBall.get(p));
					if(fireBall.get(p).getList().size() < fireBall.get(p).getPos()-1){
						fireBall.remove(p);
					}
			}
			
			for(int p = 0; p < thunder.size(); p++){
				interaction.thunderInteraction(thunder.get(p));
				if(thunder.get(p).getTickThunder() == 100){
					thunder.remove(p);
				}
			}
		}
	}
	
	public void updateGameOver() {
		Sound soundChange = new Sound();
		Sound soundChoose = new Sound();
		
		if (leftPressedMenu && pressedOnce && statusMenu < 2){
			for (int i = 0; i < gameOver.size();i++){
				if(gameOver.get(i).getStatus() == statusMenu)
					gameOver.get(i).setName("res/no");		
				else if(gameOver.get(i).getStatus() == statusMenu + 1)
					gameOver.get(i).setName("res/yesbombs"); 
			}
			statusMenu++;
			pressedOnce = false;
			soundChange.play("menuchange");

		}
		else if (rightPressedMenu && pressedOnce && statusMenu > 1){
			for (int i = 0; i<gameOver.size();i++){
				if(gameOver.get(i).getStatus() == statusMenu)
					gameOver.get(i).setName("res/yes");	
				else if(gameOver.get(i).getStatus() == statusMenu - 1)
					gameOver.get(i).setName("res/nobombs");  

			}
			statusMenu--;
			pressedOnce = false;
			soundChange.play("menuchange");


		}
		else if (!leftPressedMenu && !rightPressedMenu && !enterPressed){
			pressedOnce = true;
		}
		
		else if (enterPressed && pressedOnce){ 
			soundChoose.play("menuchoose");
			deleteCopy();
			if (statusMenu == 2){
				status = Status.MENU;
				sound.soundEnd();
				sound.soundEnd();
				sound.play("menu");
				level.setStatus(status);
			}
			else if (statusMenu == 1){ // ferme lapplication
				System.exit(-1);
			}
			
			enterPressed = false;
		}
	}
	
	public void updateStore() {
		Sound soundChange = new Sound("menuchange");
		Sound soundChoose = new Sound("menuchoose");
		
		if(sound.isFinished())
		{
			sound.play("shop");
		}
	
		if (rightPressedMenu && pressedOnce && statusMenu < 6 ){
			store.get(statusMenu).setName("res/store" + statusMenu);
			store.get(statusMenu+1).setName("res/store" + (statusMenu+1) + "choose");
			statusMenu++;
			pressedOnce = false;
			soundChange.play();
		}
		
		else if (leftPressedMenu && pressedOnce && statusMenu > 1){
			store.get(statusMenu).setName("res/store" + statusMenu);
			store.get(statusMenu-1).setName("res/store" + (statusMenu-1) + "choose");
			statusMenu--;
			pressedOnce = false;
			soundChange.play();
		}
		else if (!leftPressedMenu && !rightPressedMenu && !enterPressed){
			pressedOnce = true;
		}
			
		else if (enterPressed && pressedOnce){
			
			if(1 <= statusMenu && statusMenu <= 5) {
				StoreItem item = StoreItem.values()[statusMenu-1];
				Link l = link.get(0);
				
				boolean bought = false;
				
				switch (statusMenu){
				case(1): // fleche
					if(l.getNumberCoin() - item.price >= 0 && l.getNumberArrow() < item.max){
						l.setNumberCoin(l.getNumberCoin() - item.price);
						l.setNumberArrow(l.getNumberArrow() + 1);
						bought = true;
					}
					break;
				
				case(2): //bombrange
					if(l.getNumberCoin() - item.price >= 0 && l.getRangeBomb() < item.max){
						l.setNumberCoin(l.getNumberCoin() - item.price);
						l.setRangeBomb(l.getRangeBomb() + 1);
						bought = true;
					}
					break;
					
				case(3): // bomb
					if(l.getNumberCoin() - item.price >= 0 && l.getNumberBomb() < item.max){
						l.setNumberCoin(l.getNumberCoin() - item.price);
						l.setNumberBomb(l.getNumberBomb() + 1);
						bought = true;
					}
					break;
				
				case(4)://speed
					if(l.getNumberCoin() - item.price >= 0 && l.getSpeed() < item.max){
						l.setNumberCoin(l.getNumberCoin() - item.price);
						l.setSpeed(l.getSpeed() + 1);
						bought = true;
					}
					break;
				
				case(5)://heart
					if(l.getNumberCoin() - item.price >= 0 && l.getMaxLife() < item.max){
						l.setNumberCoin(l.getNumberCoin() - item.price);
						l.setMaxLife(l.getMaxLife() + 1);
						l.setLifePoint(l.getMaxLife());							
						bought = true;
					}
					break;
				}
				
				if(bought) {
					soundChange.play("shopbuy");
					soundChange.play("rupee");
				} else {
					soundChange.play("shopcantbuy");
				}
			}
			else if(statusMenu == 6) {
				soundChoose.play();
				System.out.println(map.getLevel());
				if (map.getLevel().contentEquals("2")){
					sound.soundEnd();
					sound.play("forest2");
				}
				else if(map.getLevel().contentEquals("3")){
					sound.soundEnd();
					sound.play("dungeon");
				}
				status = Status.SOLO;
				level.setStatus(status);
			}
			
			enterPressed = false;
		}
	}
	
	public void updateGameOver2() {
		if (pausePressed && pressedOnce){
			status = Status.SOLO;
			level.setStatus(status);
			pressedOnce = false;
		}
		else if (!pausePressed && !escapePressed){
			pressedOnce = true;
		}
		
		else if(escapePressed && pressedOnce)
		{
			sound.soundEnd();
			sound.play("menu");
			status = Status.MENU;
			statusMenu = 2;
			level.setStatus(status);
			pressedOnce = false;
		}
	}
	
	public void updateMultiWin() {
		
		if (!enterPressed){
			pressedOnce = true;
			
		}
		else if (enterPressed && pressedOnce){
			status = Status.MENU;
			statusMenu = 2;
			level.setStatus(status);
			enterPressed = false;
			menu.get(2).setName("res/2players");
			menu.get(1).setName("res/1playerbombs");
			sound.soundEnd();
			sound.play("menu");
		}
	}
	
	public void update() {
		Status s = level.getStatus();
		if(s == Status.MENU)
			updateMenu();
		
		else if(s == Status.MULTI_WIN)
			updateMultiWin();
		
		else if(s == Status.MULTI)
			updateMulti();
	
		else if(s == Status.GAME_OVER_2)
			updateGameOver2();
			
		else if(s == Status.SOLO)
			updateSolo();
			
		else if(s == Status.GAME_OVER)
			updateGameOver();
			
		else if(s == Status.STORE)
			updateStore();
	}
	
	private void deleteCopy() {
		for (int i = 1; i <= 3; i ++){
			for(int j = 1; j <= 3; j++){
				for (int k = 1; k <= 3;k ++){
					File f = new File("res/" + i + "-" + j + "-" + k + "copy.txt");
					if (f.exists())
						f.delete();					
				}
			}
		}
		
	}


	public boolean gameOver(Link link){
		if (link.getLifePoint()<= 0)
			return true;
		return false;
	}
	
	private void keyBoolean(int keyCode, boolean value) {
		if (keyCode == KeyEvent.VK_D)
	    	rightPressed = value;
	    
    	else if(keyCode == KeyEvent.VK_Q)
    		leftPressed = value;
	    
    	else if(keyCode == KeyEvent.VK_ESCAPE) 
    		escapePressed = value;
	    
    	else if(keyCode == KeyEvent.VK_P)
    		pausePressed = value;
	   
    	else if(keyCode == KeyEvent.VK_S)
    		downPressed = value;
	    
    	else if(keyCode == KeyEvent.VK_Z)
    		upPressed = value;
	    
    	else if(keyCode == KeyEvent.VK_UP) {
    		upPressed2 = value;
    		upPressedMenu = value;
    	}
	    
    	else if(keyCode == KeyEvent.VK_RIGHT) {
    		rightPressed2 = value;
    		rightPressedMenu = value;
    	}
	    
    	else if(keyCode == KeyEvent.VK_LEFT) {
    		leftPressed2 = value;
    		leftPressedMenu = value;
    	}
	    
    	else if(keyCode == KeyEvent.VK_DOWN) {
    		downPressed2 = value;
    		downPressedMenu = value;
    	}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    
	    keyBoolean(keyCode, true);
	    
    	if (keyCode == KeyEvent.VK_ENTER)
    		enterPressed = true;
    	
    	else if(keyCode == KeyEvent.VK_SPACE)
    		setBomb = true;
	    
    	else if(keyCode == KeyEvent.VK_V)
    		fireArrow = true;
    	
    	else if(keyCode == KeyEvent.VK_C)
    		useStaff = true;
	    
    	else if(keyCode == KeyEvent.VK_U)
    		setBomb2 = true;
	    
    	else if(keyCode == KeyEvent.VK_I)
    		fireArrow2 = true;
	    
    	else if(keyCode == KeyEvent.VK_O)
    		useStaff2 = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    
	    keyBoolean(keyCode, false);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
