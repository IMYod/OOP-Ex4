package algorithm;

import java.util.PriorityQueue;
import java.util.Set;

import GeoObjects.AllObjects;
import GeoObjects.Box;
import GeoObjects.Fruit;
import GeoObjects.GenericGeoObject;
import GeoObjects.Point3D;
import gui.PanelBoard;
import guiObjects.Line;
import guiObjects.Map;
import guiObjects.Path;
import guiObjects.PathComperator;
import guiObjects.Pixel;
import guiObjects.Segment;

public class Shortest {

	AllObjects game;
	PanelBoard board;
	public boolean[][] matrixCorners;
	public Pixel[] corners;

	public Shortest(AllObjects game, PanelBoard board) {
		this.game = game;
		this.board = board;
		corners = new Pixel[game.boxes.size()*4+1];
		matrixCorners = new boolean[game.boxes.size()*4+1][game.boxes.size()*4+1];
		buildGraph();
	}

	public void buildGraph() {
	
		//Init the corners array
	
		int k=1;
		for (Box box: game.boxes) {
			Pixel[] boxCorners = box.getPixelsCorners(board);
			for (int j=0; j<boxCorners.length; j++) {
				corners[k] = boxCorners[j];
				k++;
			}
		}
	
		//Init graph of the corners as matrix.
		//matrix[i][j] true iff exist free path between (corners[i],corners[j])
	
		for (int i=1; i<matrixCorners.length; i++)
			for (int j=1; j<matrixCorners.length; j++)
				matrixCorners[i][j] = freeCornersPath(i, j);
	}

	//based on BFS algorithm, using priority queue
	public Pixel findPath(Pixel source, Pixel target) {
		if (source.equals(target))
			return target;
		initSource(source, target);
		
		System.out.println("from:" + source);
		
		PriorityQueue<Path> queue = new PriorityQueue<>(new PathComperator(corners)); //priority queue, poll the shortest path
		queue.add(new Path(0)); //add the source to queue

		while (!queue.isEmpty()) {
			Path shortPath = queue.poll(); //poll the shortest path
			Pixel closestDirectFruit = closestFruit(corners[shortPath.getTail()]); //if exist direct path to fruits - go to the closest
			if (closestDirectFruit != null) { //found fruit from the end of the path
				if (shortPath.size() >= 2) {
					System.out.println("to path:" + shortPath);
					return corners[shortPath.get(1)]; //go to the next corner
				}
				System.out.println("to:" + closestDirectFruit);
				return closestDirectFruit; //go to the closest fruit 
			}
			else
				for (int i=1; i<matrixCorners.length; i++)
					if (matrixCorners[shortPath.getTail()][i] && !shortPath.contains(i)) //exist direct path to other corner && this corner not close a circle on the path
						queue.add(new Path(shortPath, i));
		}
		return null; //not found any fruit or other corner
	}

		public void initSource(Pixel source, Pixel target) {
			corners[0] = source;
			for (int i=1; i<corners.length; i++) {
				boolean free = freePath(source, corners[i]);
				matrixCorners[0][i] = free;
				matrixCorners[i][0] = free;
			}
		}
	
	//	private int nextStep(int source, Pixel target) {
	//		if (freePath(corners[source], target)) //go to the target
	//			return Integer.MAX_VALUE;
	//
	//		int closest = -1; 
	//		double minCalculateDistance = Double.MAX_VALUE;
	//		for (int i=1; i<matrix.length; i++) {
	//			if (i==source || matrix[source][i] == false) //don't calculate the distance between source -> source or no free path
	//				; //next iteration
	//			else{
	//				double calculateDistance = corners[source].distance(corners[i]) + corners[i].distance(target); //one step + distance to the target
	//				if (calculateDistance < minCalculateDistance) {
	//					closest = i;
	//					minCalculateDistance = calculateDistance;
	//				}
	//			}
	//		}
	//
	//		return closest;
	//	}
	
		private boolean freeCornersPath(int c1, int c2) {
			//check if the corners belong to the same box
			if (boxNumber(c1) == boxNumber(c2)) {
				return Math.abs((c1-c2))%2 == 1; //Nearby corners - return true, Opposite corners - return false
			}
			else return freePath(corners[c1], corners[c2]);					
		}
		
		//return the number of the box in the matrix, by thw corner number
		private int boxNumber(int corner) {
			return (corner-1)/4;
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

		private Pixel closestFruit(Pixel source) {
			Pixel closestPixel = null; 
			double minDistance = Double.MAX_VALUE;
			for (Fruit fruit: game.fruits) {
				Pixel fruitPixel = board.map.gps2pixel(fruit.getLocation(),  board.getWidth(), board.getHeight());
				if (freePath(source, fruitPixel)) {
					if (source.distance(fruitPixel) < minDistance) {
						minDistance = source.distance(fruitPixel);
						closestPixel = fruitPixel;
					}
				}
			}
			return closestPixel;
		}
		
//		private void improvePath(Path path) {
		//		for (int i=1; i<path.size()-3; i++)
		//			if (freePath(path.get(i), path.get(i+2))) { //go direct from i to i+2
		//				path.remove(i+1);
		//				improvePath(path);
		//				return;
		//			}
		//	}
		
//	private void improvePath(Path path) {
//		for (int i=1; i<path.size()-3; i++)
//			if (freePath(path.get(i), path.get(i+2))) { //go direct from i to i+2
//				path.remove(i+1);
//				improvePath(path);
//				return;
//			}
//	}
//
//	private int firstStep(Pixel source, Pixel target) {
//		corners[0] = source;
//		for (int i=1; i<corners.length; i++) {
//			boolean free = freePath(source, corners[i]);
//			matrix[0][i] = free;
//			matrix[i][0] = free;
//		}
//		return nextStep(0, target);
//	}
//
//	private int nextStep(int source, Pixel target) {
//		if (freePath(corners[source], target)) //go to the target
//			return Integer.MAX_VALUE;
//
//		int closest = -1; 
//		double minCalculateDistance = Double.MAX_VALUE;
//		for (int i=1; i<matrix.length; i++) {
//			if (i==source || matrix[source][i] == false) //don't calculate the distance between source -> source or no free path
//				; //next iteration
//			else{
//				double calculateDistance = corners[source].distance(corners[i]) + corners[i].distance(target); //one step + distance to the target
//				if (calculateDistance < minCalculateDistance) {
//					closest = i;
//					minCalculateDistance = calculateDistance;
//				}
//			}
//		}
//
//		return closest;
//	}

	
	

}