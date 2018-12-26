package GeoObjects;

import Coords.MyCoords;
import gui.MainWindow;
import gui.PanelBoard;
import guiObjects.Map;
import guiObjects.Pixel;

public class Box {
	
	private int id;
	private Point3D ne;
	private Point3D sw;
//	private double nothing1;
//	private double nothing2;
	private MyCoords mc = new MyCoords();
	
	public Box(Point3D point1, int id, Point3D point2, double nothing1, double nothing2){
		this.id = id;
//		this.nothing1 = nothing1;
//		this.nothing2 = nothing2;
		
		sw = point1;
		ne = point2;

	}
	
	public int getId() {
		return id;
	}
	
	public Point3D getNe() {
		return ne;
	}
	
	public Point3D getSw() {
		return sw;
	}

	public Pixel getPixelNw(PanelBoard board) {
		Pixel nePixel = board.map.gps2pixel(ne, board.getWidth(), board.getHeight());
		Pixel swPixel = board.map.gps2pixel(sw, board.getWidth(), board.getHeight());
		return new Pixel(swPixel.x(), nePixel.y());
	}

	public Pixel getPixelSe(PanelBoard board) {
		Pixel nePixel = board.map.gps2pixel(ne, board.getWidth(), board.getHeight());
		Pixel swPixel = board.map.gps2pixel(sw, board.getWidth(), board.getHeight());
		return new Pixel(nePixel.x(), swPixel.y());
	}

//	public double getWidth() {
//		return mc.distance2D(nw, ne);
//	}
//	
//	public double getHeight() {
//		return mc.distance2D(nw, se);
//	}
	
}
