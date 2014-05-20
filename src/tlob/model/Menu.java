package tlob.model;

public class Menu {
	
	private int x;
	private int y;
	private String name;
	private int status;
	
	public Menu (int x, int y, String name, int status){
		this.x = x;
		this.y = y;
		this.name = name;
		this.status = status;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus (){
		return this.status;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getXPos(){
		return x;
	}
	
	public int getYPos (){
		return y;
	}
	
	public String getName(){
		return name;
	}

}
