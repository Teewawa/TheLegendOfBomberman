package tlob.model;

public interface Hitbox {
	int getXPos();
	int getYPos();
	int[] getPos();
	
	int getWidth();
	int getHeight();
	int[] getSize();
	
	int[] getCenter();
	int[] getCoordCase();
	int[] getTopLeftCase();
	int[] getCenterCase();
}
