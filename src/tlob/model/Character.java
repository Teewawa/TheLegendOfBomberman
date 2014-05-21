package tlob.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class Character implements Tick, Hitbox {

	protected int lifePoint;
	protected int xPos;
	protected int yPos;
	protected int width;
	protected int height;
	
	protected int speed;
	protected Direction direction;
	private int actualFrame = 1;
	private String name;
	private boolean invincible = false;
	private int tickInvicible = 1;
	private int frozen = 1;
	private int tickFrozen = 1;
	private int t = 0;
	private int myTick = 0;
	private int tick = 0;
	
	private Set<Direction> dirs = new HashSet<Direction>();
	
	public Character(int lifePoint, int xPos, int yPos, int speed, Direction direction, String name){
		this.lifePoint =  lifePoint;
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;
		this.direction = direction;
		this.name = name;
		width = 36;
		height = 36;
		setAllDir();
	}
	
	public int getActualFrame(){
		return this.actualFrame;
	}

	public void setActualFrame(int a)
	{
		this.actualFrame=a;
	}
	
	public void getDamage(int damage){
		if(! invincible)
			lifePoint = Math.max(0, lifePoint - damage);
		invincible = true;
	}
	
	public boolean isInvincible(){
		return this.invincible;
	}
	
	public void setInvicible(){
		this.invincible = true;
	}
	
	public void setInvicible(boolean v){
		this.invincible = v;
	}
	
	public void tickInvincible(){
		tickInvicible++;
		if(tickInvicible == 40){
			this.invincible = false;
			this.tickInvicible = 1;
		}
	}
	
	public int getTickInvincible(){
		return this.tickInvicible;
	}

	public void tickFrozen() {
		tickFrozen++;
		if(tickFrozen == 50) {
			this.frozen=1;
			this.tickFrozen=1;
		}
	}
	
	public int getFrozen() {
		
		return frozen;
	}
	
	public void setFrozen() {
		
		this.frozen = 0;
		
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name;
	}
	
	public int getLifePoint()
	{
		return lifePoint;	
	}
	
	public void setLifePoint(int lifePoint)
	{
		this.lifePoint = lifePoint;
	}
	
	public int getXPos()
	{ 
		return xPos;
	}
	
	public void setXPos(int xPos)
	{
		this.xPos = xPos;
	}
	
	public int getYPos()
	{
		return yPos;
	}
	
	public void setYPos(int yPos)
	{ 
		this.yPos = yPos;
	}
	
	public void setPos(int[] pos)
	{ 
		xPos = pos[0];
		yPos = pos[1];
	}
	
	public int[] nextPos(Direction d, int vitesse) {
		return new int[]{xPos + d.dx * vitesse, yPos + d.dy * vitesse};
	}
	
	public int[] nextPos(Direction d) {
		return nextPos(d, getSpeed());
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
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public Direction getDirection()
	{ 
		return direction;
	}
	
	public void setDirection (Direction direction)
	{
		this.direction = direction;
	}

	public int getTime(){
		return this.t;
	}
	
	public void setTime(int t){
		this.t = t;
	}
	
	@Override
	public void tick(int constante) {
		tick++;
		if(tick == constante) {
			t++;
			tick = 0;
		}
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
	
	
	public boolean isDirSet(Direction d){
		return dirs.contains(d);
	}
	
	public void unsetDir(Direction d) {
		dirs.remove(d);
	}
	
	public void setAllDir() {
		dirs.addAll( Arrays.asList(Direction.values()) );
	}
	
	public void unsetAllDir() {
		dirs.clear();
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
