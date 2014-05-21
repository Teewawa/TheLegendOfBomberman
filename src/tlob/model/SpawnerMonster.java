package tlob.model;

import java.util.List;

import java.util.Random;

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
			int random = new Random().nextInt(4);	
			Monster m = null;
			switch(random){
			case 0:
				m = new Melee(2, this.xPos, this.yPos, 1, Direction.BAS, "res/Monster/MeleeRun");
				break;
			case 1:
				m = new Ranged(1, this.xPos, this.yPos, 1, Direction.BAS, "res/Monster/RangedRun");
				break;
			case 3:
				m = new Bomber(2, this.xPos, this.yPos, 1, Direction.BAS, "res/Monster/BomberRun");
				break;
			}
			if(m != null) {
				m.setSpawner(true);
				monster.add(m);
			}
			setCooldown(0);
		}
	}




}


