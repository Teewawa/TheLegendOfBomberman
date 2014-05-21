package tlob.model;

import java.util.ArrayList;

public class Thunder extends Item {

	private int tickThunder = 0;
	
	private ArrayList<int[]> listPos;
	
	public Thunder(int xPos, int yPos, String name) {
		super(xPos, yPos, name);
	}

	public void appear(int xPos, int yPos){
		int x = 0;
		int y = 0;
		listPos = new ArrayList<int[]>();
		if(xPos%40 <= 20){
			x = xPos - xPos%40;
		}
		else{
			x = xPos + 40 - xPos%40;
		}
		if(yPos%40 <= 20){
			y = yPos - yPos%40;
		}
		else{
			y = yPos + 40 - yPos%40;
		}
		listPos.add( new int[]{x,y} );
		for(Direction d : Direction.values())
			listPos.add( new int[]{x + d.dx * 50, y + d.dy * 50} );
	}
	
	public ArrayList<int[]> getListPos(){
		return this.listPos;
	}
	
	public int getTickThunder(){
		return this.tickThunder;
	}
	
	public void tickThunder(int c){
		tickThunder++;
		if(tickThunder == c){
			setActualFrame(2);
		}
	}
}
