package guiObjects;
/**
 * This class is Represents a pixel that on the screen.
 * @author Yoav and Elad.
 *
 */
public class Pixel {
	
	private int x;
	private int y;

	////////////////////***Constructor****///////////////////////////////////

	public Pixel(int x, int y) {
		if (x<0 || y<0) {
//			System.out.println("out of bounds");
			x = Math.max(0, x);
			y = Math.max(0, y);
		}
		this.x = x;
		this.y = y;
	}

	///////////////*** Getters and Setters**/////////////////////

	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public double distance(Pixel other) {
		int dx = other.x-x;
		int dy = other.y-y;
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public boolean equals(Pixel other) {
		return (x==other.x && y==other.y);
	}

}
