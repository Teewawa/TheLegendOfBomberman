package tlob.model;

import java.util.List;

public class SpawnerMonster extends Decor {
	
	public SpawnerMonster(int xPos, int yPos, String name) {
			super(xPos,yPos,name);
		}
		
	public void spawnMonster(List<Monster> monster){
		int numberMonster = 0;
		for(int i = 0; i < monster.size(); i++){
			if(monster.get(i).getSpawner() == true){
				numberMonster++;
			}
		}
		if(numberMonster < 5){
			cdTick(5);
		}
		else{
			setCooldown(0);
		}
		if(getCooldown() == 40 && numberMonster < 5){
			java.util.Random r=new java.util.Random( ) ; 
			int random = r.nextInt(4);	
			switch(random){
			case 0:
				monster.add(new Melee(2,this.xPos,this.yPos,1,3,"res/Monster/MeleeRun"));
				monster.get(monster.size()-1).setSpawner(true);
				break;
			case 1:
				monster.add(new Ranged(1,this.xPos,this.yPos,1,3,"res/Monster/RangedRun"));
				monster.get(monster.size()-1).setSpawner(true);
				break;
			case 3:
				monster.add(new Bomber(2,this.xPos,this.yPos,1,3,"res/Monster/BomberRun"));
				monster.get(monster.size()-1).setSpawner(true);
				break;
			}
			setCooldown(0);
		}
	}




}


