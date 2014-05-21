package tlob.model;

import java.util.ArrayList;

public class FireBall extends Item{
	
	private int mytick = 1;
	private ArrayList<int[]> listpos;
	private int damage = 1;
	int attributPos = 0;
	
	public FireBall(int xPos, int yPos, String name,int xCible, int yCible, int xMax, int yMax){
		super(xPos,yPos,name); 
		listeFireball1(xCible, yCible, xMax, yMax);
	}
	
	private void listeFireball1(int xCible, int yCible, int xMax, int yMax){
		ArrayList<int[]> list = new ArrayList<int[]>();
		for(int y=0; y<yMax; y++)
			for(int x=0; x<xMax;x++)
				if(distance(x,y,xCible,yCible))
					list.add(new int[]{x,y});
		
		listpos = new ArrayList<int[]>();
		int cible = aim(getXPos(), getYPos(), xCible, yCible);
		if(cible == 1 || cible ==2){
			for(int i = 0; i < list.size(); i++){
				if(aim(getXPos(), getYPos(),list.get(list.size()-1 -i)[0],list.get(list.size()-1 -i)[1]) == cible){
					listpos.add(list.get(list.size()-1 -i));
				}
			}
		}
		else{
			for(int i = 0; i < list.size(); i++){
				if(aim(getXPos(), getYPos(),list.get(i)[0],list.get(i)[1]) == cible){
					listpos.add(list.get(i));
				}
			}
		}
	}
	
	public int getDamage(){
		return this.damage;
	}
	
	public ArrayList<int[]> getList(){
		return this.listpos;
	}
	
	public void tick(){
		this.mytick++;
		if(this.mytick == 2) {
			attributPos++;
			mytick = 1;
		}
	}
	
	private boolean distance(int x, int y,int x2, int y2){
		int x1 = getXPos();
		int y1 = getYPos();
		double dist = ((y1-y2)*x+(x2-x1)*y+x1*y2-x2*y1)/Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		return Math.abs(dist) < 0.4;
	}
	
	private int aim(int x, int y, int xCible, int yCible){
		if(xCible <= x && yCible <= y){
			return 1;
		}
		else if(xCible > x && yCible < y){
			return 2;
		}
		else if(xCible >= x && yCible >= y){
			return 3;
		}
		else{
			return 4;
		}
	}
	
	public void move(){
		if(attributPos < listpos.size()){
			setXPos(listpos.get(attributPos)[0]);
			setYPos(listpos.get(attributPos)[1]);
		}
	}

	public int getAttributPos() {
		return attributPos;
	}
}
