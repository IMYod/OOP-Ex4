package guiObjects;

import java.util.ArrayList;
/**
 * This class represent a path of pixels.
 * 
 * @author Yoav and Elad.
 */
public class Path extends ArrayList<Integer> {

////////////////////////***Constructors****///////////////////////////////////////////

	public Path(int source) {
		super();
		add(source);
	}
	
	public Path (Path path, int pixel) {
		super(path);
		add(pixel);
	}

///////////////////////////*** Methods ***//////////////////////////////////////////

	//The tail of the path
	public int getTail() {
		return get(size()-1);
	}
	
	/**
	 * This method compute the distance between the corners
	 * @param corners The pixels that we going to check.
	 * @return The distance.
	 */
	public double distance(Pixel[] corners) {
		double distance = 0;
		for (int i=1; i<size(); i++) {
			distance += corners[get(i)].distance(corners[get(i-1)]);
		}
		return distance;
	}
	
	public String toString(Pixel[] corners) {
		StringBuilder sb = new StringBuilder("[");
		for (int i=0; i<size(); i++)
			sb.append(corners[get(i)] + ",");
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
}
