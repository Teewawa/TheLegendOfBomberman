package tlob.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import java.util.ArrayList;

import tlob.model.*;
import tlob.model.Character;

public class ImageAnimeDirection {
	private List<Image> liste;
	public final static int GAUCHE = 0;
	public final static int DROITE = 1;
	public final static int HAUT = 2;
	public final static int BAS = 3;
	String nomBase;
	private int frames;
	private Image image;
	
	public ImageAnimeDirection(String name){
		this.nomBase = name;
		this.image = Toolkit.getDefaultToolkit().getImage(nomBase + ".png");
	}
	
	public ImageAnimeDirection(String nomBase, int frames) {
		this.frames = frames;
		this.nomBase = nomBase;
		liste = new ArrayList<Image>();
		for(String dir : new String[]{"Left", "Right", "Up", "Down"}){
			for(int i = 1; i<= frames; i++){
				liste.add(Toolkit.getDefaultToolkit().getImage(nomBase + dir + Integer.toString(i) + ".png"));
			}
		}
		liste.add(Toolkit.getDefaultToolkit().getImage("res/void.png"));
		liste.add(Toolkit.getDefaultToolkit().getImage("res/ice.png"));
	}
	
	public ImageAnimeDirection(String nomBase, int frames, boolean b) {
		this.frames = frames;
		this.nomBase = nomBase;
		liste = new ArrayList<Image>();
		for(int i = 1; i<= frames; i++){
			liste.add(Toolkit.getDefaultToolkit().getImage(nomBase + Integer.toString(i) + ".png"));
		}
		liste.add(Toolkit.getDefaultToolkit().getImage("res/void.png"));
		liste.add(Toolkit.getDefaultToolkit().getImage("res/ice.png"));
	}
	
	public String getName(){
		return this.nomBase;
	}
	
	public int getFrames(){
		return this.frames;
	}
	
	public Image getImage(){
		return this.image;
	}
	
	public Image getImageAnime(Character character){
		
		if(character.getInvincible() == 0){
			if(character.getTickInvincible()%2 == 0){
				return liste.get(4*frames);
			}
		}
		
		if(character.getFrozen() == 0){
			return liste.get(4*frames+1);
		}
		
		if(character.getClass() == Bomber.class){
			if(((Bomber) character).getAction() == true){
				if(character.getDirection() == GAUCHE){
					return liste.get(((Bomber) character).getBombFrame() -1);
				}
				else if(character.getDirection() == DROITE){
					return liste.get(((Bomber) character).getBombFrame() + frames-1);
				}
				else if(character.getDirection() == HAUT){
					return liste.get(((Bomber) character).getBombFrame() + 2*frames-1);
				}
				else{
					return liste.get(((Bomber) character).getBombFrame() + 3*frames-1);
				}
			}
		}
		
		if(character.getDirection() == GAUCHE){
			return liste.get(character.getActualFrame() -1);
		}
		else if(character.getDirection() == DROITE){
			return liste.get(character.getActualFrame() + frames-1);
		}
		else if(character.getDirection() == HAUT){
			return liste.get(character.getActualFrame() + 2*frames-1);
		}
		else{
			return liste.get(character.getActualFrame() + 3*frames-1);
		}
	}
	
	public Image getImage(FireBall f){
		return liste.get(0);
	}
	
	public Image getImageNoDirection(Character character){
		return liste.get(character.getActualFrame() -1);
	}
	
	public Image getImageAnime(Decor decor){
		return liste.get(decor.getActualFrame() -1);
	}
	
	public Image getImage(Thunder thunder){
		if(thunder.getActualFrame() == 2){
			if(thunder.getTickThunder()%4 == 0){
				return liste.get(2);
			}
			else{
				return liste.get(1);
			}
		}
		return liste.get(0);
	}
	
	public Image getImage(Boss boss){
		if(boss.getInvincible() == 0){
			if(boss.getTickInvincible()%2 == 0){
				return liste.get(frames);
			}
		}
		if(boss.getActualFrame() == 6){
			if(boss.getBossTick()%2 == 1){
				return liste.get(frames);
			}
		}
		return liste.get(boss.getActualFrame()-1);
	}
	
	public Image getImageAnime(Arrow arrow){
		if(arrow.getDirection() == GAUCHE){
			return liste.get(arrow.getActualFrame() -1);
		}
		else if(arrow.getDirection() == DROITE){
			return liste.get(arrow.getActualFrame()  + frames-1);
		}
		else if(arrow.getDirection() == HAUT){
			return liste.get(arrow.getActualFrame()  + 2*frames-1);
		}
		else{
			return liste.get(arrow.getActualFrame()  + 3*frames-1);
		}
	}
	
	public List<Image> getImageDirection(int direction){
		ArrayList<Image> l = new ArrayList<Image>();
		if(direction == GAUCHE){
			for(int i=0; i< frames;i++){
				l.add(liste.get(i));
			}
		}
		else if(direction == DROITE){
			for(int i=0; i< frames;i++){
				l.add(liste.get(i + frames));
			}
		}
		else if(direction == HAUT){
			for(int i=0; i< frames;i++){
				l.add(liste.get(i + 2*frames));
			}
		}
		else{
			for(int i=0; i< frames;i++){
				l.add(liste.get(i + 3*frames));
			}
		}
		return l;
	}
	
}
	