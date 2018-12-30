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
//		if (x<0 || y<0) {
////			System.out.println("out of bounds");
//			x = Math.max(0, x);
//			y = Math.max(0, y);
//		}
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
	
	public boolean equals(Object other) {
		if (other instanceof Pixel) {
			return (x==((Pixel)other).x && y==((Pixel)other).y);
		}
		return false;
	}
	
	public double azimuthPixel (Pixel target) {
		int deltaY = target.y - y;
		int deltaX = target.x - x;
		double azimuth = Math.atan((double)deltaY/deltaX);
		
		//to degrees
		azimuth = Math.toDegrees(azimuth);
		azimuth = (azimuth+360)%360;
		
		if (deltaX>0) {
			if (deltaY<0)
				azimuth = 180 - azimuth;
		}
		else if (deltaY > 0)
			azimuth = 360 - azimuth;
		else
			azimuth += 180;
		return azimuth;
	}
}
