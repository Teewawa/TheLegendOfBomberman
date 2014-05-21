package tlob.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JPanel;

import sun.java2d.loops.DrawRect;
import tlob.controller.Status;
import tlob.model.*;

public class Panel extends JPanel{

	private static final long serialVersionUID = 1L;
	private Level level;
	private LoadIAD loadIAD;
	private static int decY = 120;
	private static int decX = 100;
	
	Image bg = Toolkit.getDefaultToolkit().getImage("res/BackgroundForest.png");
	Image heart = Toolkit.getDefaultToolkit().getImage("res/Heart.png");
	Image statusBar = Toolkit.getDefaultToolkit().getImage("res/StatusBar.png");
	Image statusBarVersus = Toolkit.getDefaultToolkit().getImage("res/StatusBarVersus.png");
	Image statusBarVersus2 = Toolkit.getDefaultToolkit().getImage("res/StatusBarVersus2.png");
	Image sideBackground = Toolkit.getDefaultToolkit().getImage("res/SideBackground.png");
	Image redlinkwin = Toolkit.getDefaultToolkit().getImage("res/redlinkwin.png");
	Image linkwin = Toolkit.getDefaultToolkit().getImage("res/linkwin.png");
	Image pause = Toolkit.getDefaultToolkit().getImage("res/pause.png");
	
	private Image charNumber[] = new Image[10];
	
	public Panel(Level level, LoadIAD loadIAD) {
		this.level = level;
		this.loadIAD = loadIAD;
		for(int i = 0; i < 10; i++)
			charNumber[i] = Toolkit.getDefaultToolkit().getImage("res/Char" + i + ".png");
	}
	
	private void paintMenu(Graphics g) {
		for (Menu menu : level.getMenu()){
			g.drawImage(loadIAD.stringToIAD(menu.getName()).getImage(), menu.getXPos(), menu.getYPos(),null);
		}
	}
	
	private void paintMulti(Graphics g) {
		g.drawImage(sideBackground, -10, 70, null);
		g.drawImage(sideBackground, 15 * 40 + decX, 70,null);
		
		g.drawImage(statusBarVersus, 0, 0, null);
		g.drawImage(statusBarVersus2, 400, 0, null);
		
		for(int i = 0; i < 15; i++)
			for(int j = 0; j < 15; j++)
				drawDec(g, bg, i * 40, j * 40, 1000, 1000);
		
		for(Decor decor : level.getDecor())
			drawDec(g, loadIAD.stringToIAD(decor.getName()).getImage(), decor);
		
		for(Bomb bomb : level.getBomb())
			drawDec(g, loadIAD.stringToIAD(bomb.getName()).getImage(), bomb);
		
		for(Bonus bonus : level.getBonus())
			drawDec(g, loadIAD.stringToIAD(bonus.getName()).getImage(), bonus);
		
		for(int j = 0; j < level.getLink().size(); j++)
		{
			Link link = level.getLink().get(j);
			g.drawImage(loadIAD.stringToIAD(link.getName()).getImageAnime(link), link.getXPos()+decX, link.getYPos()+decY,null);
			
			for(int i = 0; i < link.getLifePoint(); i++) 
				g.drawImage(loadIAD.stringToIAD("res/Heart").getImage(),80 + i*20 + j*400, 50,null);				
			
			g.drawImage(charNumber[link.getNumberBomb()],238 + j * 400, 62, null);			
			g.drawImage(charNumber[link.getRangeBomb()],195 + j * 400, 62, null);
			
			g.drawImage(charNumber[link.getNumberArrow()],290 + j * 400, 62, null);
			
			if(link.getStaff() == 0) {
				g.drawImage(loadIAD.stringToIAD("res/FireStaff").getImage(), 320 + j * 400, 53, null);
			}
			
			if(link.getStaff() == 1) {
				g.drawImage(loadIAD.stringToIAD("res/IceStaff").getImage(), 320 + j * 400, 53, null);
			}
		}
		
		for(Arrow arrow : level.getArrow())
			drawDec(g, loadIAD.stringToIAD(arrow.getName()).getImageAnime(arrow), arrow);
		
		
		for(Monster monster : level.getMonster())
			drawDec(g, loadIAD.stringToIAD(monster.getName()).getImageAnime(monster), monster);
		
		for(BombDeflagration bombDef : level.getBombDeflagration())
			for(Direction dir : Direction.values())
				for(int[] n : bombDef.getListe(dir))
					drawDec(g, loadIAD.stringToIAD(bombDef.getName()).getImageDirection(dir).get(1), n[0], n[1], bombDef.getWidth(), bombDef.getHeight());
	}
	
	private void drawNumber(Graphics g, int number, int x, int y) {
		do { // le do assure de le faire au moins une fois (cas où number == 0)
			g.drawImage(charNumber[number % 10], x, y, null);
			x -= 5;
			number /= 10;
		} while(number != 0);
	}
	
	private void drawDec(Graphics g, Image img, int x, int y, int w, int h) {
		g.drawImage(img, decX + x, decY + y, null);
		g.drawRect(decX + x, decY + y, w, h);
	}
	
	private void drawDec(Graphics g, Image img, Hitbox h) {
		drawDec(g, img, h.getXPos(), h.getYPos(), h.getWidth(), h.getHeight());
	}
	
	private void paintSolo(Graphics g) {
		for(int i = 0; i < 15; i++){
			for(int j = 0; j < 15; j++) {
				g.drawImage(bg, i * 40 + decX, j * 40 + decY,null);				
			}
		}
		for(Decor decor : level.getDecor()) {
			Image img = decor instanceof Treasure ?
					loadIAD.stringToIAD(decor.getName()).getImageAnime(decor) :
					loadIAD.stringToIAD(decor.getName()).getImage();
					
			drawDec(g, img, decor);
		}
		g.drawImage(sideBackground, -10, 70, null);
		g.drawImage(sideBackground, decX + 15 * 40, 70, null);
		
		g.drawImage(statusBar, 0, 0, null);
		
		for(Bomb bomb : level.getBomb())
			drawDec(g, loadIAD.stringToIAD(bomb.getName()).getImage(), bomb);
		
		for(Bonus bonus : level.getBonus())
			drawDec(g, loadIAD.stringToIAD(bonus.getName()).getImage(), bonus);
		
		for(Thunder thunder : level.getThunder())
			for(int[] pos : thunder.getListPos())
				drawDec(g, loadIAD.stringToIAD(thunder.getName()).getImage(thunder), pos[0], pos[1], thunder.getWidth(), thunder.getHeight());
		
		for(Link link : level.getLink()) {
			Image img = link.getName().contentEquals("res/Link/LinkOpen") ?
					loadIAD.stringToIAD(link.getName()).getImageNoDirection(link) :
					loadIAD.stringToIAD(link.getName()).getImageAnime(link);
			
			drawDec(g, img, link);
			
			for(int i = 0; i < link.getLifePoint(); i++)
				g.drawImage(loadIAD.stringToIAD("res/Heart").getImage(), 153 + i * 20, 50, null);				
			
			drawNumber(g, link.getNumberBomb(), 502, 62);
			drawNumber(g, link.getRangeBomb(), 412, 62);
			drawNumber(g, link.getNumberCoin(), 600, 62);
			drawNumber(g, link.getNumberArrow(), 312, 62);
			
			if(link.getStaff() == 0)
				g.drawImage(loadIAD.stringToIAD("res/FireStaff").getImage(), 650, 53, null);
			
			if(link.getStaff() == 1)
				g.drawImage(loadIAD.stringToIAD("res/IceStaff").getImage(), 650, 53, null);
		}
		
		for(Arrow arrow : level.getArrow()) {
			drawDec(g, loadIAD.stringToIAD(arrow.getName()).getImageAnime(arrow), arrow);
		}
		
		for(Monster monster : level.getMonster()) {
			if(monster instanceof Boss){
				drawDec(g, loadIAD.stringToIAD(monster.getName()).getImage((Boss)monster), monster);
			}
			else if(monster instanceof Underground){
				if(((Underground) monster).getUnderground())
					drawDec(g, loadIAD.stringToIAD(monster.getName()).getImage(), monster);
				else
					drawDec(g, loadIAD.stringToIAD(monster.getName()).getImageNoDirection(monster), monster);
			}
			else if(monster instanceof MovingTrap)
				drawDec(g, loadIAD.stringToIAD(monster.getName()).getImageNoDirection(monster), monster);
			else
				drawDec(g, loadIAD.stringToIAD(monster.getName()).getImageAnime(monster), monster);
		}
		
		for(BombDeflagration bombDef : level.getBombDeflagration()) {
			for(Direction dir : Direction.values()) {
				List<int[]> liste = bombDef.getListe(dir);
				for(int j = 0; j < liste.size(); j++){
					int[] newpos = liste.get(j);
					
					int indice = j == liste.size() - 1 ? 1 : 0;
					List<Image> iad = loadIAD.stringToIAD(bombDef.getName()).getImageDirection(dir);
					drawDec(g, iad.get(indice), newpos[0], newpos[1], bombDef.getWidth(), bombDef.getHeight());
				}
			}
		}
		
		for(FireBall fireBall : level.getFireBall()) {
			drawDec(g, loadIAD.stringToIAD(fireBall.getName()).getImage(), fireBall);
		}
		
	}
	
	private void paintStore(Graphics g) {
		g.drawImage(statusBar,0,0,null);
		for(Link link : level.getLink()){
			int centaineRubis=(link.getNumberCoin()-link.getNumberCoin()%100)/100;
			int dizaineRubis=((link.getNumberCoin()-centaineRubis*100)-(link.getNumberCoin()-centaineRubis*100)%10)/10;
			int dizaineArrow = (link.getNumberArrow()-link.getNumberArrow()%10)/10;
			
			for(int i=0; i<link.getLifePoint();i++) {
				g.drawImage(loadIAD.stringToIAD("res/Heart").getImage(),153+i*20,50,null);				
			}
			
			g.drawImage(charNumber[link.getNumberBomb()],502,62,null);			
			g.drawImage(charNumber[link.getRangeBomb()],412,62,null);
			
			if(centaineRubis!=0) {
				g.drawImage(charNumber[centaineRubis],590,62,null);
			}
			
			if(dizaineRubis!=0) {
				g.drawImage(charNumber[dizaineRubis],595,62,null);
			}
			g.drawImage(charNumber[link.getNumberCoin()-centaineRubis*100-dizaineRubis*10],600,62,null);
			
			if(dizaineArrow!=0) {
				g.drawImage(charNumber[dizaineArrow],307,62,null);
			}
			
			g.drawImage(charNumber[link.getNumberArrow()-dizaineArrow*10],312,62,null);
			
			if(link.getStaff()==0) {
				g.drawImage(loadIAD.stringToIAD("res/FireStaff").getImage(),650,53,null);
			}
			
			if(link.getStaff()==1) {
				g.drawImage(loadIAD.stringToIAD("res/IceStaff").getImage(),650,53,null);
			}
		}
		for(Menu store : level.getStore()){
			g.drawImage(loadIAD.stringToIAD(store.getName()).getImage(), store.getXPos(), store.getYPos()-decY,null);

		}
	}
	
	private void paintGameOver2(Graphics g) {
		paintSolo(g);
		g.drawImage(pause,200,15*20,null);
	}
	
	private void paintGameOver(Graphics g) {
		for(Menu gameOver : level.getGameOver()){
			g.drawImage(loadIAD.stringToIAD(gameOver.getName()).getImage(), gameOver.getXPos(), gameOver.getYPos()-decY,null);

		}
	}
	
	private void paintMultiWin(Graphics g) {
		g.drawImage(linkwin, 0, 0, null);
		for(int i = 0; i < level.getLink().size(); i ++)
			if (level.getLink().get(i).getLifePoint() <= 0)
				g.drawImage(i == 1 ? linkwin : redlinkwin, 0, 0, null);
	}
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		switch(level.getStatus()) {
		case MENU:
			paintMenu(g);
			break;
		
		case GAME_OVER:
			paintGameOver(g);
			break;
		
		case MULTI_WIN:
			paintMultiWin(g);
			break;
			
		case STORE:
			paintStore(g);
			break;
		
		case SOLO:
			paintSolo(g);
			break;
		
		case GAME_OVER_2:
			paintGameOver2(g);
			break;
			
		case MULTI:
			paintMulti(g);
			break;
		}
	}
}
