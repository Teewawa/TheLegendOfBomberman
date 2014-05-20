package tlob.model;

import java.util.ArrayList;

public class BombDeflagration extends Item implements Tick{
	
	private int player;
	private int U = 1; 
	private int D = 1;
	private int L = 1;
	private int R = 1;
	private ArrayList<Integer> up;
	private ArrayList<Integer> down;
	private ArrayList<Integer> left;
	private ArrayList<Integer> right;
	private int portee = 1;
	private int myTick = 1;
	
	
	public BombDeflagration(int xPos, int yPos, String name,int player){
		super(xPos,yPos, name); 
		this.player = player;
		
	}
	
	public int[][] listeExplosion(int rangeBomb){ //x,y = milieu de la case
		up = new ArrayList<Integer>();
		down = new ArrayList<Integer>();
		left = new ArrayList<Integer>();
		right = new ArrayList<Integer>();
		int l[][] = new int[4][rangeBomb*4+2];  //avance par 10 pixels
		for(int j = 0; j < rangeBomb*4+2;j++){  // 0 GAUCHE, 1 DROITE, 2 HAUT, 3 BAS
			l[0][j] = getXPos() - j*10;
			l[1][j] = getXPos() + j*10;
			l[2][j] = getYPos() - j*10;
			l[3][j] = getYPos() + j*10;	
		}
		return l;
	}
	
	public int getPlayer(){
		return this.player;
	}

	public int getPortee(){
		return this.portee;
	}
	
	public int getU(){
		return this.U;
	}

	public void setU(int U){
		this.U = U;
	}

	public int getD(){
		return this.D;
	}

	public void setD(int D){
		this.D = D;
	}

	public int getL(){
		return this.L;
	}

	public void setL(int L){
		this.L = L;
	}

	public int getR(){
		return this.R;
	}

	public void setR(int R){
		this.R = R;
	}
	
	public ArrayList<Integer> getUp(){
		return this.up;
	}
	
	public ArrayList<Integer> getDown(){
		return this.down;
	}
	
	public ArrayList<Integer> getLeft(){
		return this.left;
	}
	
	public ArrayList<Integer> getRight(){
		return this.right;
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

}