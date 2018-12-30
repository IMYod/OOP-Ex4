package algorithm;

import java.util.PriorityQueue;
import java.util.Set;

import GeoObjects.AllObjects;
import GeoObjects.Box;
import GeoObjects.Fruit;
import GeoObjects.GenericGeoObject;
import GeoObjects.Ghost;
import GeoObjects.Packman;
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

	private Point3D centeralPoint; //for init location
	int maxCloseObjects = 0; //how many objects nearby the most centeral object

	public Shortest(AllObjects game, PanelBoard board) {
		refresh(game, board);
		corners = new Pixel[game.boxes.size()*4+1];
		matrixCorners = new boolean[game.boxes.size()*4+1][game.boxes.size()*4+1];
		buildGraph();
	}

	private void buildGraph() {

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
	public Pixel findPath(Pixel source) {
		initSource(source);

//		Pixel runAwayFromGhost = runAway(source);
//		if (runAwayFromGhost!=null)
//			return runAwayFromGhost;

		PriorityQueue<Path> queue = new PriorityQueue<>(new PathComperator(corners)); //priority queue, poll the shortest path
		queue.add(new Path(0)); //add the source to queue

		while (!queue.isEmpty()) {
			Path shortPath = queue.poll(); //poll the shortest path
			Pixel closestDirectFruit = closestFruitAndPackman(corners[shortPath.getTail()]); //if exist direct path to fruits - go to the closest
			if (closestDirectFruit != null) { //found fruit from the end of the path
				if (shortPath.size() >= 2) {
					return corners[shortPath.get(1)]; //go to the next corner
				}
				return closestDirectFruit; //go to the closest fruit 
			}
			else
				for (int i=1; i<matrixCorners.length; i++)
					if (matrixCorners[shortPath.getTail()][i] && !shortPath.contains(i)) //exist direct path to other corner && this corner not close a circle on the path
						queue.add(new Path(shortPath, i));
		}
		return null; //not found any fruit or other corner
	}

	public void initSource(Pixel source) {
		corners[0] = source;
		for (int i=1; i<corners.length; i++) {
			boolean free = freePath(source, corners[i]);
			matrixCorners[0][i] = free;
			matrixCorners[i][0] = free;
		}
	}


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

	private Pixel closestFruitAndPackman(Pixel source) {
		Pixel closestPixel = null; 
		double minDistance = Double.MAX_VALUE;

		//find closest fruit
		for (Fruit fruit: game.fruits) {
			Pixel fruitPixel = board.map.gps2pixel(fruit.getLocation(),  board.getWidth(), board.getHeight());
			if (freePath(source, fruitPixel)) {
				if (source.distance(fruitPixel) < minDistance) {
					minDistance = source.distance(fruitPixel);
					closestPixel = fruitPixel;
				}
			}
		}

		//find closest packman
		for (Packman packman: game.packmans) {
			Pixel fruitPixel = board.map.gps2pixel(packman.getLocation(),  board.getWidth(), board.getHeight());
			if (freePath(source, fruitPixel)) {
				if (source.distance(fruitPixel)/2 < minDistance) { //get priority of 2 to the packmans
					minDistance = source.distance(fruitPixel);
					closestPixel = fruitPixel;
				}
			}
		}

		return closestPixel;
	}

	public void refresh(AllObjects game, PanelBoard board) {
		this.game = game;
		this.board = board;
	}

	//**********run away from ghosts**************

	public Pixel runAway(Pixel source) {
		if (game.ghosts.isEmpty()) //no ghosts in this game
			return null;

		Pixel closestGhost = closestGhost(source);
		double ghostRadiusEating = game.ghosts.iterator().next().getRadius();

		//the ghost is far away
		if (source.distance(closestGhost) > (1+ghostRadiusEating)*60)
			return null;

		int deltaY = (closestGhost.y() - source.y());
		int deltaX = closestGhost.x() - source.x();

		if (source.distance(closestGhost) < (1+ghostRadiusEating)*40)
			if (freePath(source, new Pixel(source.x()-deltaX, source.y()-deltaY)))
				return new Pixel(source.x()-deltaX, source.y()-deltaY);

		//Go in the free vertical direction from the vector to the ghost
		if (freePath(source, new Pixel(source.x()+deltaY, source.y()-deltaX)))
			return new Pixel(source.x()+deltaY, source.y()-deltaX);
		if (freePath(source, new Pixel(source.x()-deltaY, source.y()+deltaX)))
			return new Pixel(source.x()-deltaY, source.y()+deltaX);

		return null;
	}

	private Pixel closestGhost(Pixel source) {
		Pixel closestPixel = null; 
		double minDistance = Double.MAX_VALUE;
		for (Ghost ghost: game.ghosts) {
			Pixel ghostPixel = board.map.gps2pixel(ghost.getLocation(),  board.getWidth(), board.getHeight());
			if (source.distance(ghostPixel) < minDistance) {
				minDistance = source.distance(ghostPixel);
				closestPixel = ghostPixel;
			}
		}
		return closestPixel;
	}

	//******** find first location *********

	public Point3D mostCenteral(double radius) {
		maxCloseObjects = 0;
		centeralPoint = null;
		for (Fruit fruit: game.fruits)
			calculateCloseObject(fruit, radius);

		for (Packman packman: game.packmans)
			calculateCloseObject(packman, radius);

		return centeralPoint;
	}

	private void calculateCloseObject(GenericGeoObject object, double radius) {
		int counter = countCloseObjects(object.getLocation(), radius);
		if (counter > maxCloseObjects) {
			maxCloseObjects = counter;
			centeralPoint = object.getLocation(); 
		}
	}

	private int countCloseObjects(Point3D location, double radius) {
		Pixel source = board.map.gps2pixel(location, board.getWidth(), board.getHeight());
		int counter = 0;
		for (Fruit fruit: game.fruits) {
			Pixel target = board.map.gps2pixel(fruit.getLocation(), board.getWidth(), board.getHeight());
			if (source.distance(target) < radius && freePath(source, target))
				counter++;
		}
		for (Packman packman: game.packmans) {
			Pixel target = board.map.gps2pixel(packman.getLocation(), board.getWidth(), board.getHeight());
			if (source.distance(target) < radius && freePath(source, target))
				counter++;
		}
		return counter;
	}

}