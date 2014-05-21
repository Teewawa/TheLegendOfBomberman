package tlob.model;

import java.util.ArrayList;

public class BombDeflagration extends Item {
	
	private Link player;
	private int dirSetHaut = 1; 
	private int dirSetBas = 1;
	private int dirSetGauche = 1;
	private int dirSetDroite = 1;
	private ArrayList<int[]> up;
	private ArrayList<int[]> down;
	private ArrayList<int[]> left;
	private ArrayList<int[]> right;
	private int portee = 1;
	private int myTick = 1;
	
	
	public BombDeflagration(int xPos, int yPos, String name, Link player){
		super(xPos,yPos, name); 
		this.player = player;
	}
	
	public int[][][] listeExplosion(int rangeBomb){ //x,y = milieu de la case
		up = new ArrayList<int[]>();
		down = new ArrayList<int[]>();
		left = new ArrayList<int[]>();
		right = new ArrayList<int[]>();
		int l[][][] = new int[4][rangeBomb * 4 + 2][2];  //avance par 10 pixels
		
		for(int j = 0; j < rangeBomb*4+2;j++)
			for(Direction dir : Direction.values())
				l[dir.ordinal()][j] = nextPos(dir, j * 10);
		
		return l;
	}
		
	public int[] nextPos(Direction dir, int vitesse) {
		return new int[]{getXPos() + dir.dx * vitesse, getYPos() + dir.dy * vitesse};
	}
	
	public Link getPlayer(){
		return this.player;
	}

	public int getPortee(){
		return this.portee;
	}
	
	public ArrayList<int[]> getListe(Direction d){
		if(d == Direction.GAUCHE)
			return left;
		else if(d == Direction.DROITE)
			return right;
		else if(d == Direction.HAUT)
			return up;
		else
			return down;
	}
	
	@Override
	public void tick(int frames, int constante) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(int constante) {
		myTick++;
		if(myTick == constante) {
			portee++;
			myTick = 1;
		}
	}
	
	public int getDirSet(Direction d){
		if(d == Direction.GAUCHE)
			return dirSetGauche;
		else if(d == Direction.DROITE)
			return dirSetDroite;
		else if(d == Direction.HAUT)
			return dirSetHaut;
		else
			return dirSetBas;
	}
	
	public void unsetDir(Direction d) {
		if(d == null)
			return;
		if(d == Direction.GAUCHE)
			dirSetGauche = 0;
		else if(d == Direction.DROITE)
			dirSetDroite = 0;
		else if(d == Direction.HAUT)
			dirSetHaut = 0;
		else if(d == Direction.BAS)
			dirSetBas = 0;
	}
	
	public void setAllDir() {
		dirSetDroite = 1;
		dirSetGauche = 1;
		dirSetBas = 1;
		dirSetHaut = 1;
	}
	
	public void unsetAllDir() {
		dirSetDroite = 0;
		dirSetGauche = 0;
		dirSetBas = 0;
		dirSetHaut = 0;
	}

}