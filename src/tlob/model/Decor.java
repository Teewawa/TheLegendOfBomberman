package tlob.model;

public abstract class Decor implements Tick, Hitbox {
	
	protected int xPos;
	protected int yPos;
	protected String name;
	private int cooldown = 0;
	private int tick = 0;
	private int myTick = 0;
	private int actualFrame = 1;
	private int width;
	private int height;
	

	public Decor(int xPos, int yPos, String name) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.name = name;
		width = height = 40;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;		
	}
	
	public void setXPos(int xPos) {
		this.xPos=xPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos=yPos;
	}
	
	public int[] getPos() {
		return new int[]{xPos, yPos};
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[] getSize() {
		return new int[]{width, height};
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name;
	}
	
	public void setCooldown(int i) {
		this.cooldown = i;
		
	}

	public int getCooldown() {
		return this.cooldown;
	}
	
	public void cdTick(int constante) {
		tick++;
		if(tick == constante) {
			cooldown++;
			tick = 0;
		}
	}

	@Override
	public void tick(int constante) {
	}
	
	@Override
	public void tick(int frames, int constante) {
		myTick++;
		if(myTick == constante) {
			actualFrame++;
			myTick = 0;
			if(actualFrame == frames +1)
				this.actualFrame = 1;
		}
	}

	public int getActualFrame() {
		return actualFrame;
	}

	public void setActualFrame(int actualFrame) {
		this.actualFrame = actualFrame;
	}
	
	public int[] getCenter() {
		return new int[]{xPos + width / 2, yPos + height / 2};
	}
	
	public int[] getCoordCase() {
		int[] c = getCenter();
		int i = (c[0] - 20) / 40 + ( (c[0] - 20) % 40 <= 20 ? 0 : 1);
		int j = (c[1] - 20) / 40 + ( (c[1] - 20) % 40 <= 20 ? 0 : 1);
		return new int[]{i, j};
	}
	
	public int[] getTopLeftCase() {
		int[] coord = getCoordCase();
		return new int[]{coord[0] * 40, coord[1] * 40};
	}
	
	public int[] getCenterCase() {
		int[] topLeft = getTopLeftCase();
		return new int[]{topLeft[0] + 20, topLeft[1] + 20};
	}
}
