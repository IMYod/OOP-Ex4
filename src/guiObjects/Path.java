package guiObjects;

import java.util.ArrayList;

public class Path extends ArrayList<Integer> {

	public Path(int source) {
		super();
		add(source);
	}
	
	public Path (Path path, int pixel) {
		super(path);
		add(pixel);
	}
	
	public int getTail() {
		return get(size()-1);
	}
	
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
