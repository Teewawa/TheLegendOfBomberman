package tlob.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

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
	Image Char0 = Toolkit.getDefaultToolkit().getImage("res/Char0.png");
	Image Char1 = Toolkit.getDefaultToolkit().getImage("res/Char1.png");
	Image Char2 = Toolkit.getDefaultToolkit().getImage("res/Char2.png");
	Image Char3 = Toolkit.getDefaultToolkit().getImage("res/Char3.png");
	Image Char4 = Toolkit.getDefaultToolkit().getImage("res/Char4.png");
	Image Char5 = Toolkit.getDefaultToolkit().getImage("res/Char5.png");
	Image Char6 = Toolkit.getDefaultToolkit().getImage("res/Char6.png");
	Image Char7 = Toolkit.getDefaultToolkit().getImage("res/Char7.png");
	Image Char8 = Toolkit.getDefaultToolkit().getImage("res/Char8.png");
	Image Char9 = Toolkit.getDefaultToolkit().getImage("res/Char9.png");
	private Image charNumber[]={Char0,Char1,Char2,Char3,Char4,Char5,Char6,Char7,Char8,Char9};
	
	public Panel(Level level, LoadIAD loadIAD) {
		this.level = level;
		this.loadIAD = loadIAD;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		if(level.getStatus() == 0){
			for (Menu menu : level.getMenu()){
				g.drawImage(loadIAD.stringToIAD(menu.getName()).getImage(), menu.getXPos(), menu.getYPos(),null);
			}
		}
		else if (level.getStatus() == 3){
			for(Menu gameOver : level.getGameOver()){
				g.drawImage(loadIAD.stringToIAD(gameOver.getName()).getImage(), gameOver.getXPos(), gameOver.getYPos()-decY,null);

			}
		}
		
		else if (level.getStatus()==6){
			g.drawImage(linkwin,0,0,null);
			for(int i = 0; i < level.getLink().size(); i ++){
				if (level.getLink().get(i).getLifePoint()<= 0){
					if(i == 1){
						g.drawImage(linkwin,0,0,null);
					}
					else{
						g.drawImage(redlinkwin,0,0,null);
					}						
				}					
			}
		}
		
		else if (level.getStatus() == 4){
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
		
		else if (level.getStatus() == 2 || level.getStatus() == 5){
			
			
			for(int i =0; i<15;i++){
				for(int j=0;j<15;j++) {
					g.drawImage(bg,i*40+decX,j*40+decY,null);				
				}
			}
			for(Decor decor : level.getDecor()) {
				
				if(decor.getClass() == Treasure.class){
					g.drawImage(loadIAD.stringToIAD(decor.getName()).getImageAnime(decor), decor.getXPos()+decX, decor.getYPos()+decY,null);
				}
				else{
					g.drawImage(loadIAD.stringToIAD(decor.getName()).getImage(), decor.getXPos()+decX, decor.getYPos()+decY,null);

				}
			}
			g.drawImage(sideBackground,-10,70,null);
			g.drawImage(sideBackground,15*40+decX,70,null);
			
			g.drawImage(statusBar,0,0,null);
			
			for(Bomb bomb : level.getBomb()) {
				g.drawImage(loadIAD.stringToIAD(bomb.getName()).getImage(), bomb.getXPos()+decX, bomb.getYPos()+decY,null);
			}
			
			for(Bonus bonus : level.getBonus()){
				g.drawImage(loadIAD.stringToIAD(bonus.getName()).getImage(), bonus.getXPos()+decX, bonus.getYPos()+decY,null);
			}
			
			for(Thunder thunder : level.getThunder()){
				for(int i = 0; i < thunder.getListPos().size(); i++){
					g.drawImage(loadIAD.stringToIAD(thunder.getName()).getImage(thunder), thunder.getListPos().get(i)[0]+decX, thunder.getListPos().get(i)[1]+decY,null);
				}
			}
			
			for(Link link : level.getLink()) {
				if(link.getName().contentEquals("res/Link/LinkOpen")){
					g.drawImage(loadIAD.stringToIAD(link.getName()).getImageNoDirection(link), link.getXPos()+decX, link.getYPos()+decY,null);
				}
				else{
					g.drawImage(loadIAD.stringToIAD(link.getName()).getImageAnime(link), link.getXPos()+decX, link.getYPos()+decY,null);
				}
				
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
			
			for(Arrow arrow : level.getArrow()) {
				g.drawImage(loadIAD.stringToIAD(arrow.getName()).getImageAnime(arrow), arrow.getXPos()+decX, arrow.getYPos()+decY,null);
			}
			
			for(Monster monster : level.getMonster()) {
				if(monster.getClass() == Boss.class){
					g.drawImage(loadIAD.stringToIAD(monster.getName()).getImage((Boss)monster), monster.getXPos()+decX, monster.getYPos()+decY,null);
				}
				else if(monster.getClass() == Underground.class){
					if(((Underground) monster).getUnderground()){
						g.drawImage(loadIAD.stringToIAD(monster.getName()).getImage(), monster.getXPos()+decX, monster.getYPos()+decY,null);
					}
					else{
						g.drawImage(loadIAD.stringToIAD(monster.getName()).getImageNoDirection(monster), monster.getXPos()+decX, monster.getYPos()+decY,null);
					}
				}
				else if(monster.getClass() == MovingTrap.class){
					g.drawImage(loadIAD.stringToIAD(monster.getName()).getImageNoDirection(monster), monster.getXPos()+decX, monster.getYPos()+decY,null);
				}
				else{
					g.drawImage(loadIAD.stringToIAD(monster.getName()).getImageAnime(monster), monster.getXPos()+decX, monster.getYPos()+decY,null);
				}
			}
			
			for(BombDeflagration bombDef : level.getBombDeflagration()) {
				for(int j = 0; j< bombDef.getUp().size(); j++){
					if(j==bombDef.getUp().size()-1){
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(2).get(1), bombDef.getXPos()+decX, bombDef.getUp().get(j)+decY,null);
					}
					else{
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(2).get(0), bombDef.getXPos()+decX, bombDef.getUp().get(j)+decY,null);
					}
				}
				for(int j = 0; j< bombDef.getDown().size(); j++){
					if(j==bombDef.getUp().size()-1){
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(3).get(1), bombDef.getXPos()+decX, bombDef.getDown().get(j)+decY,null);
					}
					else{
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(3).get(0), bombDef.getXPos()+decX, bombDef.getDown().get(j)+decY,null);
					}
				}
				for(int j = 0; j< bombDef.getLeft().size(); j++){
					if(j==bombDef.getUp().size()-1){
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(0).get(1), bombDef.getLeft().get(j)+decX, bombDef.getYPos()+decY,null);
					}
					else{
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(0).get(0), bombDef.getLeft().get(j)+decX, bombDef.getYPos()+decY,null);
					}
				}
				for(int j = 0; j< bombDef.getRight().size(); j++){
					if(j==bombDef.getUp().size()-1){
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(1).get(1), bombDef.getRight().get(j)+decX, bombDef.getYPos()+decY,null);
					}
					else{
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(1).get(0), bombDef.getRight().get(j)+decX, bombDef.getYPos()+decY,null);
					}
				}
			}
			
			for(FireBall fireBall : level.getFireBall()) {
				g.drawImage(loadIAD.stringToIAD(fireBall.getName()).getImage(), fireBall.getXPos()+decX, fireBall.getYPos()+decY,null);
				}
			
			if(level.getStatus() == 5)
				g.drawImage(pause,200,15*20,null);

			}
		
		else if(level.getStatus() == 1) {
			
			g.drawImage(sideBackground,-10,70,null);
			g.drawImage(sideBackground,15*40+decX,70,null);
			
			g.drawImage(statusBarVersus,0,0,null);
			g.drawImage(statusBarVersus2,400,0,null);
			
			for(int i =0; i<15;i++){
				for(int j=0;j<15;j++) {
					g.drawImage(bg,i*40+decX,j*40+decY,null);				
				}
			}
			for(Decor decor : level.getDecor()) {
				g.drawImage(loadIAD.stringToIAD(decor.getName()).getImage(), decor.getXPos()+decX, decor.getYPos()+decY,null);
			}
			
			for(Bomb bomb : level.getBomb()) {
				g.drawImage(loadIAD.stringToIAD(bomb.getName()).getImage(), bomb.getXPos()+decX, bomb.getYPos()+decY,null);
			}
			
			for(Bonus bonus : level.getBonus()){
				g.drawImage(loadIAD.stringToIAD(bonus.getName()).getImage(), bonus.getXPos()+decX, bonus.getYPos()+decY,null);
			}
			
			for(Link link : level.getLink()) {
				g.drawImage(loadIAD.stringToIAD(link.getName()).getImageAnime(link), link.getXPos()+decX, link.getYPos()+decY,null);
				
				int j=level.getLink().indexOf(link)*400;
				
				for(int i=0; i<link.getLifePoint();i++) {
					g.drawImage(loadIAD.stringToIAD("res/Heart").getImage(),80+i*20+j,50,null);				
				}
				
				g.drawImage(charNumber[link.getNumberBomb()],238+j,62,null);			
				g.drawImage(charNumber[link.getRangeBomb()],195+j,62,null);
				
				g.drawImage(charNumber[link.getNumberArrow()],290+j,62,null);
				
				if(link.getStaff()==0) {
				g.drawImage(loadIAD.stringToIAD("res/FireStaff").getImage(),320+j,53,null);
				}
				
				if(link.getStaff()==1) {
					g.drawImage(loadIAD.stringToIAD("res/IceStaff").getImage(),320+j,53,null);
					}
			}
			
			for(Arrow arrow : level.getArrow()) {
				g.drawImage(loadIAD.stringToIAD(arrow.getName()).getImageAnime(arrow), arrow.getXPos()+decX, arrow.getYPos()+decY,null);
			}
			
			for(Monster monster : level.getMonster()) {
				g.drawImage(loadIAD.stringToIAD(monster.getName()).getImageAnime(monster), monster.getXPos()+decX, monster.getYPos()+decY,null);
			}
			
			for(BombDeflagration bombDef : level.getBombDeflagration()) {
				for(int j = 0; j< bombDef.getUp().size(); j++){
					if(j==bombDef.getUp().size()-1){
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(2).get(1), bombDef.getXPos()+decX, bombDef.getUp().get(j)+decY,null);
					}
					else{
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(2).get(0), bombDef.getXPos()+decX, bombDef.getUp().get(j)+decY,null);
					}
				}
				for(int j = 0; j< bombDef.getDown().size(); j++){
					if(j==bombDef.getUp().size()-1){
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(3).get(1), bombDef.getXPos()+decX, bombDef.getDown().get(j)+decY,null);
					}
					else{
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(3).get(0), bombDef.getXPos()+decX, bombDef.getDown().get(j)+decY,null);
					}
				}
				for(int j = 0; j< bombDef.getLeft().size(); j++){
					if(j==bombDef.getUp().size()-1){
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(0).get(1), bombDef.getLeft().get(j)+decX, bombDef.getYPos()+decY,null);
					}
					else{
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(0).get(0), bombDef.getLeft().get(j)+decX, bombDef.getYPos()+decY,null);
					}
				}
				for(int j = 0; j< bombDef.getRight().size(); j++){
					if(j==bombDef.getUp().size()-1){
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(1).get(1), bombDef.getRight().get(j)+decX, bombDef.getYPos()+decY,null);
					}
					else{
						g.drawImage(loadIAD.stringToIAD(bombDef.getName()).getImageDirection(1).get(0), bombDef.getRight().get(j)+decX, bombDef.getYPos()+decY,null);
					}
				}
			}
			
		}
		}
	}
