package guiObjects;

import java.util.Comparator;
/**
 * This class is a Comparator for paths
 * @author Yaov and Elad.
 *
 */
public class PathComperator implements Comparator<Path> {

	private Pixel[] corners;

////////////////////////***Constructors****///////////////////////////////////////////

	public PathComperator(Pixel[] corners) {
		this.corners = corners;
	}
	
///////////////////////////*** Methods ***//////////////////////////////////////////
	
	//the function compares the lengths of the paths 
	@Override
	public int compare(Path p1, Path p2) {
		if (p1.distance(corners) > p2.distance(corners))
			return 1;
		else if (p1.distance(corners) < p2.distance(corners))
			return -1;
		return 0;
	}	
}
