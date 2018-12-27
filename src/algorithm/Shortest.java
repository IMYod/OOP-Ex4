package algorithm;

import GeoObjects.AllObjects;
import GeoObjects.Box;
import GeoObjects.GenericGeoObject;
import GeoObjects.Point3D;
import gui.PanelBoard;
import guiObjects.Line;
import guiObjects.Map;
import guiObjects.Path;
import guiObjects.Pixel;
import guiObjects.Segment;

public class Shortest {

	AllObjects game;
	PanelBoard board;
	Pixel[] corners;
	boolean[][] matrix;

	public Shortest(AllObjects game, PanelBoard board) {
		this.game = game;
		this.board = board;
		buildGraph();
	}

//	public Segment closestObstacle(Point3D source, GenericGeoObject object) {
//		//		source = game.player.getLocation();
//		Point3D target = object.getLocation();
//
//		Pixel sourcePixel = board.map.gps2pixel(source, board.getWidth(), board.getHeight());
//		Line directLine = new Line(sourcePixel,	board.map.gps2pixel(target, board.getWidth(), board.getHeight()));
//
//		//we want to find the closest obstacle point to the source
//		Segment closestObstacleSegment = null;
//		double minDistance = Double.MAX_VALUE;
//		for (Box box: game.boxes) {
//			Segment[] frame = box.getFrame(board);
//			for (int i=0; i<frame.length; i++) { //the lines on the box's frame
//				Pixel cutting = frame[i].cuttingPoint(directLine); //calculate the cutting point between direct line and the frame of the box
//				if (cutting != null && cutting.x() < Double.MAX_VALUE) { //the lines are cutting
//					double distance = sourcePixel.distance(cutting); 
//					if (distance < minDistance) {
//						minDistance = distance;
//						closestObstacleSegment = frame[i];
//					}
//				}
//
//			}
//		}
//		return closestObstacleSegment;
//	}

	public Path findPath(Pixel source, Pixel target) {
		Path path = new Path(source);		
		int step = firstStep(source, target);
		if (step == -1)
			return null;  //if next step not found
		
		while (step != Integer.MAX_VALUE) {		
			step = nextStep(step, target);
			if (step != Integer.MAX_VALUE) //go to the target
				break;
			
			if (path.contains(corners[step]) || step == -1) //the path have a circle or next step not found
				return null;	
			path.add(corners[step]);
		}
		
		//go to the target
			path.add(target);
			improvePath(path);
			return path;
	}
	
	private void improvePath(Path path) {
		for (int i=1; i<path.size()-3; i++)
			if (freePath(path.get(i), path.get(i+2))) { //go direct from i to i+2
				path.remove(i+1);
				improvePath(path);
				return;
			}
	}
	
	private int firstStep(Pixel source, Pixel target) {
		corners[0] = source;
		for (int i=1; i<corners.length; i++) {
			boolean free = freePath(source, corners[i]);
			matrix[0][i] = free;
			matrix[i][0] = free;
		}
		return nextStep(0, target);
	}
	
	private int nextStep(int source, Pixel target) {
		if (freePath(corners[source], target)) //go to the target
			return Integer.MAX_VALUE;
		
		int closest = -1; 
		double minCalculateDistance = Double.MAX_VALUE;
		for (int i=1; i<matrix.length; i++) {
			if (i==source) //don't calculate the distance between source -> source
				break;
			if (matrix[source][i] == false) // no free path
				break;
			double calculateDistance = corners[source].distance(corners[i]) + corners[i].distance(target); //one step + distance to the target
			if (calculateDistance < minCalculateDistance) {
				closest = i;
				minCalculateDistance = calculateDistance;
			}
		}
		
		return closest;
	}
	
	private boolean freePath(Pixel source, Pixel target) {
		if (source.equals(target))
			return true;
		Segment directSegmant = new Segment(new Line(source,target), source, target);
		for (Box box: game.boxes) {
			Segment[] frame = box.getFrame(board);
			for (int i=0; i<frame.length; i++) { //the segments on the box's frame
				Pixel cutting = frame[i].cuttingPoint(directSegmant); //calculate the cutting point between direct line and the frame of the box
				if (cutting != null && cutting.x() != Integer.MAX_VALUE && cutting.y() != Integer.MAX_VALUE) //the lines are cutting
					return false;
			}
		}
		return true;
	}

	private void buildGraph() {

		//Init the corners array

		int k=1;
		corners = new Pixel[game.boxes.size()*4+1];
		for (Box box: game.boxes) {
			Pixel[] boxCorners = box.getPixelsCorners(board);
			for (int j=0; j<boxCorners.length; j++) {
				corners[k] = boxCorners[j];
				k++;
			}
		}

		//Init graph of the corners as matrix.
		//matrix[i][j] true if exist free path between two corners[i],[j]

		matrix = new boolean[game.boxes.size()*4+1][game.boxes.size()*4+1];
		for (int i=1; i<matrix.length; i++)
			for (int j=1; j<matrix.length; j++)
				matrix[i][j] = freePath(corners[i], corners[j]);
	}
	
}
