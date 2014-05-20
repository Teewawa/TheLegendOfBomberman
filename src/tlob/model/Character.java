package tlob.model;

public abstract class  Character implements Tick{

	protected int lifePoint;
	protected int xPos;
	protected int yPos;
	protected int speed;
	protected int direction; // 0 GAUCHE, 1 DROITE, 2 HAUT, 3 BAS
	private int actualFrame = 1;
	private String name;
	private int invincible = 1; // 1 = OFF, 0 = ON
	private int tickInvicible = 1;
	private int frozen = 1;
	private int tickFrozen = 1;
	private int t = 0;
	private int myTick =0;
	private int tick = 0;
	private int U = 1;
	private int D = 1;
	private int L = 1;
	private int R = 1;
	private int player = 0;
	
	public  Character(int lifePoint, int xPos, int yPos, int speed, int direction, String name){
		this.lifePoint =  lifePoint;
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;
		this.direction = direction;
		this.name = name;
	}
	
	public int getPlayer(){
		return this.player;
	}
	
	public void setPlayer(int player){
		this.player = player;
	}
	
	public int getU(){
		return this.U;
	}
	
	public void setU(int U){
		this.U = U;
	}
	
	public int getD(){
		return this.D;
	}
	
	public void setD(int D){
		this.D = D;
	}
	
	public int getL(){
		return this.L;
	}
	
	public void setL(int L){
		this.L = L;
	}
	
	public int getR(){
		return this.R;
	}
	
	public void setR(int R){
		this.R = R;
	}
	
	public int getActualFrame(){
		return this.actualFrame;
	}

	public void setActualFrame(int a)
	{
		this.actualFrame=a;
	}
	
	public void getDamage(int damage){
		lifePoint = lifePoint - invincible*damage;
		invincible = 0;
	}
	
	public int getInvincible(){
		return this.invincible;
	}
	
	public void setInvicible(){
		this.invincible = 0;
	}
	
	public void setInv(int n){
		this.invincible = n;
	}
	
	public void tickInvincible(){
		tickInvicible++;
		if(tickInvicible == 40){
			this.invincible = 1;
			this.tickInvicible = 1;
		}
	}
	
	public int getTickInvincible(){
		return this.tickInvicible;
	}

	public void tickFrozen() {
		tickFrozen++;
		if(tickFrozen==50) {
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
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public int getDirection()
	{ 
		return direction;
	}
	
	public void setDirection (int direction)
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
}
