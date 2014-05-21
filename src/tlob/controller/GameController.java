package tlob.controller;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
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
	
	static class Booleans {
		private boolean rightPressed = false;
		private boolean leftPressed = false;
		private boolean downPressed = false;
		private boolean upPressed = false;
		private boolean fireArrow = false;
		private boolean setBomb = false;
		private boolean useStaff = false;
	};
	
	private List<Booleans> boutons = new ArrayList<Booleans>();
	String[] baseNames = new String[]{"Link", "RedLink"};
	
	private boolean enterPressed = false;
	
	private boolean upPressedMenu = false;
	private boolean downPressedMenu = false;
	private boolean leftPressedMenu = false;
	private boolean rightPressedMenu = false;
	
	private boolean pausePressed = false;
	private boolean escapePressed = false;
	
	private boolean attributW;
	
	private java.util.Random random = new java.util.Random();
	
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
		for(int i = 0; i < 2; i++)
			boutons.add( new Booleans() );
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
				link.add( new Link(3, 1*40, 1*40, 2, Direction.DROITE, baseNames[0]) );
				link.add( new Link(3, 13*40, 13*40, 2, Direction.GAUCHE, baseNames[1]) );
				sound.soundEnd();
				sound.play("forest1");
				soundChoose.play();

			}
			else{
				Map map = new Map(16, 16, "1", "3", "1");
				level.createLevel(map);
				createGameController(level);
				link.add(new Link(3, 7 * 40, 13 * 40, 3, Direction.HAUT, baseNames[0]));

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
			if (random.nextInt(2) == 0)
				sound.play("forest1");
			else
				sound.play("forest2");
		}
		
		for(int i = 0; i < link.size(); i++) {
			Link l = link.get(i);
			Booleans bouton = boutons.get(i);
			String baseName = baseNames[i];
			
			if(l.getLifePoint() <= 0){
				status = Status.MULTI_WIN;
				level.setStatus(status);
				sound.soundEnd();
				sound.play("multigameover");
				break;
			}
			
			if(l.getInvincible() == 0)
				l.tickInvincible();
		
			interaction.linkInteraction(link.get(i));

			if(bouton.rightPressed){
				l.setName("res/" + baseName + "/" + baseName + "Run");
				l.move(Direction.DROITE);
			
			}
			if(bouton.leftPressed){
				l.setName("res/" + baseName + "/" + baseName + "Run");
				l.move(Direction.GAUCHE);
			}
		
			if(bouton.downPressed){
				l.setName("res/" + baseName + "/" + baseName + "Run");
				l.move(Direction.BAS);
			}
		
			if(bouton.upPressed){
				l.setName("res/" + baseName + "/" + baseName + "Run");
				l.move(Direction.HAUT);
			}
		
			if(bouton.fireArrow){
				l.fireArrow(arrow);
				if(l.getActualFrame() == 6){
					bouton.fireArrow = false;
					l.setActualFrame(1);
				}
			}
			if(bouton.setBomb){
				int bomb0 = 0;
				for(Bomb b : bomb){
					if(b.getPlayer() == l){
						bomb0++;
					}
				}
				if(bomb0 < l.getNumberBomb()){
					l.setBomb(bomb);				
				}
				bouton.setBomb = false;
			}
			if(bouton.useStaff == true && l.getStaff() != -1) {
				for(Link a : link) {
					if(a != l) {
						if(l.getStaff() == 0) {
							a.getDamage(1);			
							bouton.useStaff = false; 
							l.setStaff(-1); 
						}
						else if(l.getStaff() == 1) {
							a.setFrozen();
							a.tickFrozen();	
							if(a.getFrozen() == 1) {
								bouton.useStaff = false; 
								l.setStaff(-1); 
							}
						}
					}
				}
			}
			else {
				bouton.useStaff = false;
			}
		
			interaction.linkInteraction(l);
		}

		for(int p = 0; p < arrow.size(); p++){
			Arrow arr = arrow.get(p);
			int a = interaction.arrowInteraction(arr);
			if(a != 0){
				if(a == 2){
					arr.tick(5);
					//arrow.get(p).setActualFrame(1);
					if(arr.getTime() == 3){
						arrow.remove(p);
						p--;
					}
				}
				else{
					arrow.remove(p);
					p--;
				}
			}
		}

		for(int p = 0; p < bomb.size(); p++){
			Bomb b = bomb.get(p);
			
			interaction.bombInteraction(b);
			b.tick();
			if(b.getTime() == 15){ //changer dans deflagration si changement de temps
				bombDeflagration.add( new BombDeflagration(b.getXPos(), b.getYPos(), "res/Deflagration", b.getPlayer()) );
				bomb.remove(p);
				p--;
				new Sound("bomb").play();
			}
		}
		
		for(int p = 0; p < bombDeflagration.size(); p++){
			BombDeflagration def = bombDeflagration.get(p);
			
			def.tick(2);
			if(def.getPortee() < def.getPlayer().getRangeBomb() * 4 + 2){
				interaction.deflagrationAppear(def, def.getPlayer().getRangeBomb());
				interaction.defInteraction(def);
			}
			else{
				bombDeflagration.remove(p);
				p--;
			}
		}
	}
	
	public void updateSolo() {
		Link l = link.get(0);
		Booleans bouton = boutons.get(0);
		
		if (pausePressed && pressedOnce){
			status = Status.GAME_OVER;
			level.setStatus(status);
			pressedOnce = false;
		}
		
		else if (!pausePressed){
			pressedOnce = true;
		}
					
		if (l.getLifePoint() <= 0){
			status = Status.GAME_OVER;
			level.setStatus(status);
			enterPressed = false;
			bouton.fireArrow = false;
			sound.soundEnd();
			sound.play("gameOver");
			statusMenu = 2;
		}
		
		if(interaction.getChestOpen() == true){
			interaction.setChestOpen(false);
			new Sound("treasure").play();
		}
		
		for (Decor d : level.getDecor()) {
			if(map.getRoomColumn().contentEquals("2")
				&& map.getLevel().contentEquals("2")
				&& map.getRoomLine().contentEquals("2")){
				if(d instanceof Door){
					((Door)d).setOpen(false);
					d.setName("res/Desert/Obstacle3");
				}					
			}
		}
		
		if (monster.size() == 0){
			for (Decor d : level.getDecor()) {
				if(d instanceof Door){
					Door door = (Door)d;
					door.setOpen(true);
					if(door.getLine() == 1){
						door.setName("res" + map.getEnvironment() + "/DoorU");
					}
					if(door.getLevel() == 1){
						door.setName("res" + map.getEnvironment() + "/DoorR");
					}
				}				
			}
		}
		
		for(Monster m : monster) {
			if(m instanceof Boss && m.getLifePoint() <= 0){
				status = Status.MULTI_WIN;
				level.setStatus(status);
				sound.soundEnd();
				sound.play("endgame");
			}
		}
		
		for(Monster m : monster) {
			if(m instanceof Boss && attributW == false){
				sound.soundEnd();
				sound.play("Boss");
				attributW = true;
			}
		}

		for (Decor d : level.getDecor()) {
			if(d instanceof SpawnerMonster)
				((SpawnerMonster)d).spawnMonster(level.getMonster());	
			else if(d instanceof SpawnerFireBall)
				((SpawnerFireBall)d).fireBall(fireBall, l);
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
				for(Monster m : monster) {
					if(m instanceof Boss){
						sound.play("Boss");
						break;
					}
				}
				
				if (map.getEnvironment() == "/Desert"){
					sound.play(random.nextInt(2) == 0 ? "desert" : "desert1");
				}
				else if (map.getEnvironment() == "/Forest"){
					sound.play(random.nextInt(2) == 0 ? "forest1" : "forest2");
				}
				else if (map.getEnvironment().contentEquals("/Dungeon")){
					sound.play(random.nextInt(2) == 0 ? "dungeon" : "dungeon1");
				}
			}
			
			for(int i = 0; i < 1; i++){
				
				if(l.getInvincible() == 0){
					l.tickInvincible();
				}
			
				interaction.linkInteraction(l);

				if(bouton.rightPressed){
					l.setName("res/Link/LinkRun");
					l.move(Direction.DROITE);
				}
				if(bouton.leftPressed){
					l.setName("res/Link/LinkRun");
					l.move(Direction.GAUCHE);
				}
			
				if(bouton.downPressed){
					l.setName("res/Link/LinkRun");
					l.move(Direction.BAS);
				}
			
				if(bouton.upPressed){
					l.setName("res/Link/LinkRun");
					l.move(Direction.HAUT);
				}
				
				if(bouton.fireArrow){
					l.fireArrow(arrow);
					if(l.getActualFrame() == 6){
						bouton.fireArrow = false;
						l.setActualFrame(1);
					}
				}
				
				if(bouton.setBomb){
					int bomb0 = 0;
					for(Bomb b : bomb)
						if(b.getPlayer() == l)
							bomb0++;
						
					if(bomb0 < l.getNumberBomb())
						l.setBomb(bomb);		
					
					bouton.setBomb = false;
				}
				
				if(bouton.useStaff && l.getStaff() != -1) {
					if(l.getStaff() == 0) {
						for (Monster m : monster)
							m.getDamage(1);
							
						bouton.useStaff = false;
						l.setStaff(-1); 
					}
					else if(l.getStaff() == 1) {
						for (Monster m : monster) {
							m.setFrozen();
							m.tickFrozen();	
							if(m.getFrozen()==1) {
								bouton.useStaff = false; 
								l.setStaff(-1); 
							}
						}
					}
				}
				else {
					bouton.useStaff = false;
				}
			}
		
			for(int p = 0; p < monster.size(); p++){
				Monster m = monster.get(p);
				if(m.getInvincible() == 0 || m instanceof MovingTrap){
					if(m instanceof Underground && ((Underground) m).getUnderground() == true){
						
					}
					else{
						m.tickInvincible();
					}
				}
				interaction.monsterInteraction(m);
				if(m.getLifePoint() <= 0){
					monster.remove(p);
					p--;
				}
			}

			for(int p = 0; p < arrow.size(); p++){
				Arrow arr = arrow.get(p);
				int a = interaction.arrowInteraction(arr);
				if(a != 0){
					if(a == 2){
						arr.tick(5);
						//arrow.get(p).setActualFrame(1);
						if(arr.getTime() == 3){
							arrow.remove(p);
							p--;
						}
					}
					else{
						arrow.remove(p);
						p--;
					}
				}
			}
	
			for(int p = 0; p < bomb.size(); p++){
				Bomb b = bomb.get(p);
				interaction.bombInteraction(b);
				b.tick();
				if(b.getTime() == 15){ //changer dans deflagration si changement de temps
					bombDeflagration.add(new BombDeflagration(b.getXPos(), b.getYPos(), "res/Deflagration", b.getPlayer()));
					bomb.remove(p);
					p--;
					new Sound("bomb").play("bomb");
				}
			}
			
			for(int p = 0; p < bombDeflagration.size(); p++){
				BombDeflagration def = bombDeflagration.get(p);
				def.tick(2);
				if(def.getPlayer() == null){
					if(def.getPortee() < 2 * 4 + 2){
						interaction.deflagrationAppear(def, 2);
						interaction.defInteraction(def);
					}
					else{
						bombDeflagration.remove(p);
						p--;
					}
				}
				else{
					if(def.getPortee() < def.getPlayer().getRangeBomb() * 4 + 2){
						interaction.deflagrationAppear(def, def.getPlayer().getRangeBomb());
						interaction.defInteraction(def);
					}
					else{
						bombDeflagration.remove(p);
						p--;
					}
				}
			}
			
			for(int p = 0; p < fireBall.size(); p++){
				FireBall f = fireBall.get(p);
				f.tick();
				interaction.fireBallInteraction(f);
				if(f.getList().size() < f.getAttributPos() - 1){
					fireBall.remove(p);
					p--;
				}
			}
			
			for(int p = 0; p < thunder.size(); p++){
				Thunder t  = thunder.get(p);
				interaction.thunderInteraction(t);
				if(t.getTickThunder() == 100){
					thunder.remove(p);
					p--;
				}
			}
		}
	}
	
	public void updateGameOver() {
		Sound soundChange = new Sound();
		Sound soundChoose = new Sound();
		
		if (leftPressedMenu && pressedOnce && statusMenu < 2){
			for (Menu g : gameOver) {
				if(g.getStatus() == statusMenu)
					g.setName("res/no");		
				else if(g.getStatus() == statusMenu + 1)
					g.setName("res/yesbombs"); 
			}
			statusMenu++;
			pressedOnce = false;
			soundChange.play("menuchange");

		}
		else if (rightPressedMenu && pressedOnce && statusMenu > 1){
			for (Menu g : gameOver) {
				if(g.getStatus() == statusMenu)
					g.setName("res/yes");	
				else if(g.getStatus() == statusMenu - 1)
					g.setName("res/nobombs");  
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
		for (int i = 1; i <= 3; i ++)
			for(int j = 1; j <= 3; j++)
				for (int k = 1; k <= 3;k ++) {
					File f = new File("res/" + i + "-" + j + "-" + k + "copy.txt");
					if (f.exists())
						f.delete();					
				}
	}

	private void keyBoolean(int keyCode, boolean value) {
		if (keyCode == KeyEvent.VK_D)
			boutons.get(0).rightPressed = value;
	    
    	else if(keyCode == KeyEvent.VK_Q)
    		boutons.get(0).leftPressed = value;
	    
    	else if(keyCode == KeyEvent.VK_S)
    		boutons.get(0).downPressed = value;
	    
    	else if(keyCode == KeyEvent.VK_Z)
    		boutons.get(0).upPressed = value;
		
    	else if(keyCode == KeyEvent.VK_ESCAPE) 
    		escapePressed = value;
	    
    	else if(keyCode == KeyEvent.VK_P)
    		pausePressed = value;
	   
    	else if(keyCode == KeyEvent.VK_UP) {
    		boutons.get(1).upPressed = value;
    		upPressedMenu = value;
    	}
	    
    	else if(keyCode == KeyEvent.VK_RIGHT) {
    		boutons.get(1).rightPressed = value;
    		rightPressedMenu = value;
    	}
	    
    	else if(keyCode == KeyEvent.VK_LEFT) {
    		boutons.get(1).leftPressed = value;
    		leftPressedMenu = value;
    	}
	    
    	else if(keyCode == KeyEvent.VK_DOWN) {
    		boutons.get(1).downPressed = value;
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
    		boutons.get(0).setBomb = true;
	    
    	else if(keyCode == KeyEvent.VK_V)
    		boutons.get(0).fireArrow = true;
    	
    	else if(keyCode == KeyEvent.VK_C)
    		boutons.get(0).useStaff = true;
	    
    	else if(keyCode == KeyEvent.VK_U)
    		boutons.get(1).setBomb = true;
	    
    	else if(keyCode == KeyEvent.VK_I)
    		boutons.get(1).fireArrow = true;
	    
    	else if(keyCode == KeyEvent.VK_O)
    		boutons.get(1).useStaff = true;
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
