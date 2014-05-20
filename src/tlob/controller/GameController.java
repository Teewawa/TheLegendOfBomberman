package tlob.controller;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.List;

import tlob.model.*;
import tlob.view.*;

public class GameController implements KeyListener{
	
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
	private int w;
	
	java.util.Random r=new java.util.Random( ) ;
	
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
	private int status = 0; // 0 = menu, 1 = multi, 2 = solo, 3 game Over, 4 store, 5 gameover, 6 multi win
	private int k = 2; // va modifier le status dans le menu
	private Level level;
	private boolean pressedOnce = true; // premiere foi qu on appuie
	private Sound sound = new Sound();
	private boolean escapePressed = false;
	private Map map;
	
	public GameController(Level level){
		this.level = level;
		this.menu = level.getMenu();
		this.gameOver = level.getGameOver();
		this.store = level.getStore();
		this.sound.playSound("menu");
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
	
	public void update() {
		Sound soundChange = new Sound();
		Sound soundChoose = new Sound();
		status = level.getStatus();
		if (status == 0){
			
			if(sound.isFinished(sound.getAudioStream()))
			{
				sound.playSound("menu");
			}
			if (downPressedMenu && pressedOnce && k > 1 ){
				for (int i = 0; i<menu.size();i++){
					if(menu.get(i).getStatus() == k)
						menu.get(i).setName("res/1player");
					else if(menu.get(i).getStatus() == k - 1)
						menu.get(i).setName("res/2playersbombs");
				}
				k-=1;
				pressedOnce = false;
				soundChange.playSound("menuchange");
			}
			else if (upPressedMenu && pressedOnce & k < 2){
				for (int i = 0; i<menu.size();i++){
					if(menu.get(i).getStatus() == k)
						menu.get(i).setName("res/2players");
					else if(menu.get(i).getStatus() == k + 1)
						menu.get(i).setName("res/1playerbombs");

				}
				k+=1;
				pressedOnce = false;
				soundChange.playSound("menuchange");

			}
			else if (!downPressedMenu && !upPressedMenu && !enterPressed){
				pressedOnce = true;
			}
			
			else if (enterPressed && pressedOnce){ // lance le solo
				status = k;
				deleteCopy();
				if(status == 1){
					Map map = new Map(16,16,"0","0","0");
					map.setEnvironment("/Forest");
					level.createLevel(map);
					createGameController(level);
					link.add(new Link(3,1*40,1*40,2,1,"res/Link/LinkRun",0));
					link.add(new Link(3,13*40,13*40,2,0,"res/RedLink/RedLinkRun",1));
					sound.soundEnd(sound.getAudioStream());
					sound.playSound("forest1");
					soundChoose.playSound("menuchoose");

				}
				else{
					Map map = new Map(16,16,"1","3","1");
					level.createLevel(map);
					createGameController(level);
					link.add(new Link(3,7*40,13*40,3,2,"res/Link/LinkRun",0));

					sound.soundEnd(sound.getAudioStream());
					sound.playSound("desert1");
					soundChoose.playSound("menuchoose");
					w=0;
				}
				level.setStatus(status);
				enterPressed = false;
				k=1;

			}
		}
		
		else if (status == 6){
			if (!enterPressed){
				pressedOnce = true;
				
			}
			else if (enterPressed && pressedOnce){
				status = 0;
				k = 2;
				level.setStatus(status);
				enterPressed = false;
				menu.get(2).setName("res/2players");
				menu.get(1).setName("res/1playerbombs");
				sound.soundEnd(sound.getAudioStream());
				sound.playSound("menu");
			}
			
		}
		
		else if (status == 1){
			
			// mode multi
			if(sound.isFinished(sound.getAudioStream()))
			{
				int random = r.nextInt(2);
				if (random == 0){
					sound.playSound("forest1");
				}
				else if(random == 1){
					sound.playSound("forest2");
				}
			}
			

			
			for(int i = 0; i < link.size(); i++){
				
				
				if(link.get(i).getLifePoint() <= 0){
					status=6;
					level.setStatus(status);
					sound.soundEnd(sound.getAudioStream());
					sound.playSound("multigameover");
					break;
				}
				
				//link.get(i).setSpeed(1);
				
				if(link.get(i).getInvincible() == 0){
					link.get(i).tickInvincible();
				}
			
				interaction.linkInteraction(link.get(i));

				if(rightPressed){
					link.get(0).setName("res/Link/LinkRun");
					link.get(0).moveRight();
				
				}
				if(leftPressed){
					link.get(0).setName("res/Link/LinkRun");
					link.get(0).moveLeft();
				}
			
				if(downPressed){
					link.get(0).setName("res/Link/LinkRun");
					link.get(0).moveDown();
				}
			
				if(upPressed){
					link.get(0).setName("res/Link/LinkRun");
					link.get(0).moveUp();
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
					if(link.get(0).getStaff()==0) {
						link.get(1).getDamage(1);			
						useStaff=false; 
						link.get(0).setStaff(-1); 
					}
					else if(link.get(0).getStaff()==1) {
							link.get(1).setFrozen();
							link.get(1).tickFrozen();	
							if(link.get(1).getFrozen()==1) {
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
					link.get(1).moveRight();
				
				}
				if(leftPressed2){
					link.get(1).setName("res/RedLink/RedLinkRun");
					link.get(1).moveLeft();
				}
			
				if(downPressed2){
					link.get(1).setName("res/RedLink/RedLinkRun");
					link.get(1).moveDown();
				}
			
				if(upPressed2){
					link.get(1).setName("res/RedLink/RedLinkRun");
					link.get(1).moveUp();
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

			if(arrow.size()>0){
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
			}
	
			if(bomb.size()>0){
				for(int p = 0; p < bomb.size(); p++){
					interaction.bombInteraction(bomb.get(p));
					bomb.get(p).tick();
					if(bomb.get(p).getTime() == 15){ //changer dans deflagration si changement de temps
						bombDeflagration.add(new BombDeflagration(bomb.get(p).getXPos(),bomb.get(p).getYPos(),
								"res/Deflagration",bomb.get(p).getPlayer()));
						bomb.remove(p);
						Sound soundBomb = new Sound();
						soundBomb.playSound("bomb");
					}
				}
			}
			if(bombDeflagration.size()>0){
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
			

			
		}
	
		else if(status == 5){
			if (pausePressed && pressedOnce){
				status = 2;
				level.setStatus(status);
				pressedOnce = false;
			}
			else if (!pausePressed && !escapePressed){
				pressedOnce = true;
			}
			
			else if(escapePressed && pressedOnce)
			{
				sound.soundEnd(sound.getAudioStream());
				sound.playSound("menu");
				status = 0;
				k =2;
				level.setStatus(status);
				pressedOnce = false;
			}

		}
		
		else if(status == 2){
			if (pausePressed && pressedOnce){
				status = 5;
				level.setStatus(status);
				pressedOnce = false;
			}
			
			else if (!pausePressed){
				pressedOnce = true;
			}
						
			if (gameOver(link.get(0))){
				status = 3;
				level.setStatus(status);
				enterPressed = false;
				fireArrow = false;
				sound.soundEnd(sound.getAudioStream());
				sound.playSound("gameOver");
				k = 2;
			}
			
			if(interaction.getChestOpen() == true){
				interaction.setChestOpen(false);
				soundChange.playSound("treasure");
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
					status = 6;
					level.setStatus(status);
					sound.soundEnd(sound.getAudioStream());
					sound.playSound("endgame");
				}
			}
			
			for(int t = 0; t < monster.size();t++){
				if(monster.get(t).getClass() == Boss.class && w == 0){
					sound.soundEnd(sound.getAudioStream());
					sound.playSound("Boss");
					w = 1;
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
				sound.soundEnd(sound.getAudioStream());
				k = 1;
				status = 4;
				level.setStatus(status);
				interaction.setChangeLevel(false);
				sound.playSound("shop");
			}
			else{
				if(sound.isFinished(sound.getAudioStream()))
				{	
					for(int i = 0;i<monster.size();i++){
						if(monster.get(i).getClass() == Boss.class){
							sound.playSound("Boss");
							break;
						}
					}
							
					 if (map.getEnvironment() == "/Desert"){
						int random = r.nextInt(2);
						if (random == 0){
							sound.playSound("desert");
						}
						else if(random == 1){
							sound.playSound("desert1");
						}
					}
					else if (map.getEnvironment() == "/Forest"){
						int random = r.nextInt(2);
						if (random == 0){
							sound.playSound("forest1");
						}
						else if(random == 1){
							sound.playSound("forest2");
					}
					}
					else if (map.getEnvironment().contentEquals("/Dungeon")){
						int random = r.nextInt(2);
						if (random == 0){
							sound.playSound("dungeon");
						}
							else if(random == 1){
							sound.playSound("dungeon1");
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
						link.get(0).moveRight();
					
					}
					if(leftPressed){
						link.get(0).setName("res/Link/LinkRun");
						link.get(0).moveLeft();
					}
				
					if(downPressed){
						link.get(0).setName("res/Link/LinkRun");
						link.get(0).moveDown();
					}
				
					if(upPressed){
						link.get(0).setName("res/Link/LinkRun");
						link.get(0).moveUp();
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
			
			
				if(monster.size() > 0){
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
				}	
	
				if(arrow.size()>0){
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
				}
		
				if(bomb.size()>0){
					for(int p = 0; p < bomb.size(); p++){
						interaction.bombInteraction(bomb.get(p));
						bomb.get(p).tick();
						if(bomb.get(p).getTime() == 15){ //changer dans deflagration si changement de temps
							bombDeflagration.add(new BombDeflagration(bomb.get(p).getXPos(),
									bomb.get(p).getYPos(),"res/Deflagration",bomb.get(p).getPlayer()));
							bomb.remove(p);
							Sound soundBomb = new Sound();
							soundBomb.playSound("bomb");
						}
					}
				}
				if(bombDeflagration.size()>0){
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
				}
				
				if(fireBall.size()>0){
					for(int p = 0; p < fireBall.size(); p++){
						fireBall.get(p).tick();
						interaction.fireBallInteraction(fireBall.get(p));
							if(fireBall.get(p).getList().size() < fireBall.get(p).getPos()-1){
								fireBall.remove(p);
							}
					}
				}
				if(thunder.size()>0){
					for(int p = 0; p < thunder.size(); p++){
						interaction.thunderInteraction(thunder.get(p));
						if(thunder.get(p).getTickThunder() == 100){
							thunder.remove(p);
						}
					}
				}
			}
		}
		else if(status == 3){
			
			if (leftPressedMenu && pressedOnce & k < 2 ){
				for (int i = 0; i<gameOver.size();i++){
					if(gameOver.get(i).getStatus() == k)
						gameOver.get(i).setName("res/no");		
					else if(gameOver.get(i).getStatus() == k + 1)
						gameOver.get(i).setName("res/yesbombs"); 
				}
				k+=1;
				pressedOnce = false;
				soundChange.playSound("menuchange");

			}
			else if (rightPressedMenu && pressedOnce && k > 1){
				for (int i = 0; i<gameOver.size();i++){
					if(gameOver.get(i).getStatus() == k)
						gameOver.get(i).setName("res/yes");	
					else if(gameOver.get(i).getStatus() == k - 1)
						gameOver.get(i).setName("res/nobombs");  

				}
				k-=1;
				pressedOnce = false;
				soundChange.playSound("menuchange");


			}
			else if (!leftPressedMenu && !rightPressedMenu && !enterPressed){
				pressedOnce = true;
			}
			
			else if (enterPressed && pressedOnce){ 
				soundChoose.playSound("menuchoose");
				deleteCopy();
				if (k == 2){
					status = 0;
					sound.soundEnd(sound.getAudioStream());
					sound.soundEnd(sound.getAudioStream());
					sound.playSound("menu");
					level.setStatus(status);
				}
				else if (k == 1){ // ferme lapplication
					System.exit(-1);
				}
				
				enterPressed = false;
			}
		}	
			
			
			
		else if (status == 4){ // /!\ il faut reinitialise le k avant de lancer le shop
			if(sound.isFinished(sound.getAudioStream()))
			{
				sound.playSound("shop");
			}
		
			if (rightPressedMenu && pressedOnce & k < 6 ){
				switch(k){
				case(1):
					store.get(2).setName("res/store2choose");
					store.get(1).setName("res/store1");
					break;					
				
				case(2):
					store.get(3).setName("res/store3choose");
					store.get(2).setName("res/store2");
					break;
					
				case(3):
					store.get(4).setName("res/store4choose");
					store.get(3).setName("res/store3");
					break;
		
				case(4):
					store.get(5).setName("res/store5choose");
					store.get(4).setName("res/store4");
					break;
				
				case(5):
					store.get(6).setName("res/store6choose");
					store.get(5).setName("res/store5");
					break;
					
				}
				k+=1;
				pressedOnce = false;
				soundChange.playSound("menuchange");
			}
			
			else if (leftPressedMenu && pressedOnce && k > 1){
				switch(k){
				case(6):
					store.get(5).setName("res/store5choose");
					store.get(6).setName("res/store6");
					break;					
					
				case(2):
					store.get(1).setName("res/store1choose");
					store.get(2).setName("res/store2");
					break;
					
				case(3):
					store.get(2).setName("res/store2choose");
					store.get(3).setName("res/store3");
					break;
		
				case(4):
					store.get(3).setName("res/store3choose");
					store.get(4).setName("res/store4");
					break;
					
				case(5):
					store.get(4).setName("res/store4choose");
					store.get(5).setName("res/store5");
					break;
					
				}
				k-=1;
				pressedOnce = false;
				soundChange.playSound("menuchange");


				}
			else if (!leftPressedMenu && !rightPressedMenu && !enterPressed){
				pressedOnce = true;
			}
				
			else if (enterPressed && pressedOnce){ 
				switch (k){
				case(1): // fleche
					if(link.get(0).getNumberCoin() - 10 >= 0 && link.get(0).getNumberArrow()<99){
						link.get(0).setNumberCoin(link.get(0).getNumberCoin() - 10);
						link.get(0).setNumberArrow(link.get(0).getNumberArrow() + 1);
						soundChange.playSound("shopbuy");
						soundChange.playSound("rupee");
					}	
					else
						soundChange.playSound("shopcantbuy");
					break;
				
				case(2): //bombrange
					if(link.get(0).getNumberCoin() - 10 >= 0 && link.get(0).getRangeBomb()<9){
						link.get(0).setNumberCoin(link.get(0).getNumberCoin() - 30);
						link.get(0).setRangeBomb(link.get(0).getRangeBomb() + 1);
						soundChange.playSound("shopbuy");
						soundChange.playSound("rupee");
						}	
					else
						soundChange.playSound("shopcantbuy");
					break;
					
				case(3): // bomb
					if(link.get(0).getNumberCoin() - 10 >= 0 && link.get(0).getNumberBomb()<9){
						link.get(0).setNumberCoin(link.get(0).getNumberCoin() - 30);
						link.get(0).setNumberBomb(link.get(0).getNumberBomb()+1);
						soundChange.playSound("shopbuy");
						soundChange.playSound("rupee");

					}	
					else
						soundChange.playSound("shopcantbuy");
						break;
				
				case(4)://speed
					if(link.get(0).getNumberCoin() - 10 >= 0 && link.get(0).getSpeed()<6){
						link.get(0).setNumberCoin(link.get(0).getNumberCoin() - 50);
						link.get(0).setSpeed(link.get(0).getSpeed()+1);
						soundChange.playSound("shopbuy");
						soundChange.playSound("rupee");
						}	
					else
						soundChange.playSound("shopcantbuy");
						break;
				
				case(5)://heart
					if(link.get(0).getNumberCoin() - 10 >= 0 && link.get(0).getMaxLife()<5){
						link.get(0).setNumberCoin(link.get(0).getNumberCoin() - 100);
						link.get(0).setMaxLife(link.get(0).getMaxLife() + 1);
						link.get(0).setLifePoint(link.get(0).getMaxLife());							
						soundChange.playSound("shopbuy");
						soundChange.playSound("rupee");
						}	
					else
						soundChange.playSound("shopcantbuy");
						break;
					
				case(6):
					soundChoose.playSound("menuchoose");
					System.out.println(map.getLevel());
					if (map.getLevel().contentEquals("2")){
						sound.soundEnd(sound.getAudioStream());
						sound.playSound("forest2");
					}
					else if(map.getLevel().contentEquals("3")){
						sound.soundEnd(sound.getAudioStream());
						sound.playSound("dungeon");
						}
					status = 2;
					level.setStatus(status);
					break;
				}
					
					
					
				enterPressed = false;
			}
		}
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
	
	@Override
	public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    
	    if (keyCode == KeyEvent.VK_D)
	    	rightPressed= true;
	    
    	else if(keyCode == KeyEvent.VK_Q) {
    		leftPressed = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_P)
    		pausePressed= true;
	    
    	else if(keyCode == KeyEvent.VK_S) {
    		downPressed= true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_Z) {
    		upPressed = true;
    	}
	    
    	else if (keyCode == KeyEvent.VK_ENTER){
    		enterPressed = true;
    	}
    	
    	else if(keyCode == KeyEvent.VK_V){
    		fireArrow = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_SPACE){
    		setBomb = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_C) {
    		useStaff = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_ESCAPE) {
    		escapePressed = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_UP) {
    		upPressed2 = true;
    		upPressedMenu = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_RIGHT) {
    		rightPressed2 = true;
    		rightPressedMenu = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_LEFT) {
    		leftPressed2 = true;
    		leftPressedMenu = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_DOWN) {
    		downPressed2 = true;
    		downPressedMenu = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_U) {
    		setBomb2 = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_I) {
    		fireArrow2 = true;
    	}
	    
    	else if(keyCode == KeyEvent.VK_O) {
    		useStaff2 = true;
    	}
	    
	    
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//liste.get(0).setActualFrame(1);
	    int keyCode = e.getKeyCode();
	    if (keyCode == KeyEvent.VK_D){
	    	rightPressed = false;
	    }
	    
    	else if(keyCode == KeyEvent.VK_Q){
    		leftPressed = false;
    	}
	    
    	else if(keyCode == KeyEvent.VK_ESCAPE) {
    		escapePressed = false;
    	}
	    
    	else if(keyCode == KeyEvent.VK_P)
    		pausePressed = false;
	    
    	else if(keyCode == KeyEvent.VK_S) {
    		downPressed = false;
    	}
	    
    	else if(keyCode == KeyEvent.VK_Z) {
    		upPressed = false;
    	}
	    
	    if (keyCode == KeyEvent.VK_RIGHT){
	    	rightPressed2 = false;
    		rightPressedMenu = false;
	    }
	    
    	else if(keyCode == KeyEvent.VK_LEFT){
    		leftPressed2 = false;
    		leftPressedMenu = false;
    	}
	    
    	else if(keyCode == KeyEvent.VK_DOWN) {
    		downPressed2 = false;
    		downPressedMenu = false;
    	}
	    
    	else if(keyCode == KeyEvent.VK_UP) {
    		upPressed2 = false;
    		upPressedMenu = false;
    	}
	    
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
