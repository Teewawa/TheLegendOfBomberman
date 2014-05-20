package tlob.model;

public class Door extends Decor{
	
	private boolean open;
	private int line;
	private int column;
	private int level;
	
	public Door(int xPos, int yPos, String name, boolean open,int line,int column, int level) {
		super(xPos,yPos,name);
		this.open = open;
		this.line = line;
		this.column = column;
		this.level = level;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public boolean getOpen() {
		return this.open;
	}
	public void setLine(int line) {
		this.line = line;
	}
	
	public int getLine() {
		return this.line;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
		public int getLevel() {
		return this.level;
	}

}
