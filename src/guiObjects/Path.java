package guiObjects;

import java.util.ArrayList;

public class Path extends ArrayList<Pixel> {

	public Path(Pixel source) {
		super();
		add(source);
	}
	
	public double distance() {
		double distance = 0;
		for (int i=1; i<size(); i++) {
			distance += get(i).distance(get(i-1));
		}
		return distance;
	}
}
