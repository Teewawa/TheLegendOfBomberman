package tlob.model;

import java.util.ArrayList;
import java.util.List;

public class Level{
	
	private Map map;
	private List<Link> link;
	private List<Decor> decor;
	private List<Monster> monster;
	private List<Bomb> bomb;
	private List<BombDeflagration> bombDeflagration;
	private List<Arrow> arrow;
	private List<Bonus> bonus;
	private List<FireBall> fireBall;
	private List<Thunder> thunder;
	private List<Menu> menu;
	private List<Menu> gameOver;
	private List<Menu> store;
	private int status = 0;
	
	public Level(List<Menu> menu, List<Menu>gameOver, List<Menu>store){
		this.menu = menu;
		this.gameOver = gameOver;
		this.store = store;
		
	}
	
	public void createLevel(Map map){
		this.map = map;
		char[][] tableau = new char[16][16];
		tableau = map.loadRoom();
		decor = map.mapToListDecor(tableau);
		monster = map.mapToListMonster(tableau);
		link = new ArrayList<Link>();
		bomb = new ArrayList<Bomb>();
		arrow = new ArrayList<Arrow>();
		bonus = new ArrayList<Bonus>();
		bombDeflagration = new ArrayList<BombDeflagration>();
		fireBall = new ArrayList<FireBall>();
		thunder = new ArrayList<Thunder>();
	}

	public Map getMap(){
		return this.map;
	}
	
	public List<Menu> getStore(){
		return this.store;
	}
	
	public void setMap(Map map){
		this.map = map;
	}
	public void setStatus(int status){
		this.status = status;
	}
	public int getStatus(){
		return this.status;
	}
	
	public List<Link> getLink(){
		return this.link;
	}

	public List<Decor> getDecor(){
		return this.decor;
	}

	public List<Monster> getMonster(){
		return this.monster;
	}

	public List<Bomb> getBomb(){
		return this.bomb;
	}

	public List<BombDeflagration> getBombDeflagration(){
		return this.bombDeflagration;
	}

	public List<Arrow> getArrow(){
		return this.arrow;
	}
	
	public List<Bonus> getBonus(){
		return this.bonus;
	}
	
	public List<FireBall> getFireBall(){
		return this.fireBall;
	}
	
	public List<Thunder> getThunder(){
		return this.thunder;
	}
	
	public List<Menu> getMenu() {
		return this.menu;
	}

	public List<Menu> getGameOver() {
		return this.gameOver;
	}
}