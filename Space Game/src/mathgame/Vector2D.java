package mathgame;

public class Vector2D {
	
	private double x,y;
	
	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D v){
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vector2D add(Vector2D v){
		return new Vector2D(x + v.getX(), y + v.getY());
	}
	
	public Vector2D subtract(Vector2D v){	
		return new Vector2D(x - v.getX(), y - v.getY());
	}
	
	public Vector2D scale(double value){
		return new Vector2D(x*value, y*value);
	}
	
	public Vector2D limit(double value){
		if(getMagnitude() > value){
			return this.normalize().scale(value);
		}
		return this;
	}
	
	public Vector2D normalize(){
		return new Vector2D (x / getMagnitude(), y / getMagnitude());
	}
	
	public double getMagnitude(){
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector2D setDirection(double angle){
		return new Vector2D(Math.cos(angle)*getMagnitude(), Math.sin(angle)*getMagnitude());
	}
	
	public double getAngle() {
		return Math.asin(y/getMagnitude());
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}

}
