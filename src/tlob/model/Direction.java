package tlob.model;

public enum Direction {
	// Ancien code : 0 = Gauche | 1 = Droite | 2 = Haut | 3 = Bas
	GAUCHE(-1,0), DROITE(1,0), HAUT(0,-1), BAS(0,1);
	
	public final int dx;
	public final int dy;
	
	private Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public Direction inverse() {
		switch(this) {
		case GAUCHE:
			return DROITE;
		case DROITE:
			return GAUCHE;
		case HAUT:
			return BAS;
		default:
			return HAUT;
		}
	}
	
	public boolean horizontale() {
		return dy == 0;
	}
	
	public boolean verticale() {
		return dx == 0;
	}
	
	public Direction inverse(boolean valeur) {
		return valeur ? inverse() : this;
	}
}
