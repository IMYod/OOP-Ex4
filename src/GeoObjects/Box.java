package GeoObjects;

//import com.sun.tools.javac.resources.compiler;

import Coords.MyCoords;
import gui.MainWindow;
import gui.PanelBoard;
import guiObjects.Line;
import guiObjects.Map;
import guiObjects.Pixel;
import guiObjects.Segment;

public class Box {
	
	private int id;
	private Point3D ne;
	private Point3D sw;
	private MyCoords mc = new MyCoords();

////////////////////////***Constructor****///////////////////////////////////////////

	public Box(Point3D point1, int id, Point3D point2, double nothing1, double nothing2){
		this.id = id;	
		sw = point1;
		ne = point2;

	}

///////////////////////***Getters and Setters***//////////////////////////////////////
	
	public int getId() {
		return id;
	}
	
	public Point3D getNe() {
		return ne;
	}
	
	public Point3D getSw() {
		return sw;
	}

///////////////////////////*** Methods ***//////////////////////////////////////////

	/**
	 * This method compute the pixels of the corners of the board.
	 * @param board The board of the game.
	 * @return An array with the pixels.
	 */
	public Pixel[] getPixelsCorners(PanelBoard board) {
		Pixel[] corners = new Pixel[4];
		corners[0] = getPixelNe(board);
		corners[1] = getPixelNw(board);
		corners[2] = getPixelSw(board);
		corners[3] = getPixelSe(board);
		return corners;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////	
//********Those methods are the calculation to find the corners pixels of the game board.********//
///////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public Pixel getPixelNw(PanelBoard board) {
		Pixel nePixel = board.map.gps2pixel(ne, board.getWidth(), board.getHeight());
		Pixel swPixel = board.map.gps2pixel(sw, board.getWidth(), board.getHeight());
		return new Pixel(swPixel.x()-2, nePixel.y()-2);
	}

	public Pixel getPixelSe(PanelBoard board) {
		Pixel nePixel = board.map.gps2pixel(ne, board.getWidth(), board.getHeight());
		Pixel swPixel = board.map.gps2pixel(sw, board.getWidth(), board.getHeight());
		return new Pixel(nePixel.x()+2, swPixel.y()+2);
	}
	
	public Pixel getPixelSw(PanelBoard board) {
		Pixel swPixel = board.map.gps2pixel(sw, board.getWidth(), board.getHeight());
		return new Pixel(swPixel.x()-2, swPixel.y()+2);
	}
	
	public Pixel getPixelNe(PanelBoard board) {
		Pixel nePixel = board.map.gps2pixel(ne, board.getWidth(), board.getHeight());
		return new Pixel(nePixel.x()+2, nePixel.y()-2);
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * This method return all the frames of the box as lines of pixels 
      *@param board The board of the game.
	 * @return An array with the segments.
	 */
	public Segment[] getFrame(PanelBoard board) {
		Segment[] frame = new Segment[4];
		frame[0] = getDownSegment(board);
		frame[1] = getUpSegment(board);
		frame[2] = getLeftSegment(board);
		frame[3] = getRightSegment(board);
		return frame;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////	
//********Those methods are the calculation to find the Segment of frame of the game board.******//
///////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Segment getDownSegment(PanelBoard board) {
		Pixel sePixel = getPixelSe(board);
		Pixel swPixel = getPixelSw(board);
		Line line = new Line(sePixel, swPixel);
		return new Segment(line, sePixel, swPixel);
	}
	
	public Segment getUpSegment(PanelBoard board) {
		Pixel nePixel = getPixelNe(board);
		Pixel nwPixel = getPixelNw(board);
		Line line = new Line(nePixel, nwPixel);
		return new Segment(line, nePixel, nwPixel);
	}
	
	public Segment getLeftSegment(PanelBoard board) {
		Pixel nwPixel = getPixelNw(board);
		Pixel swPixel = getPixelSw(board);
		Line line = new Line(nwPixel, swPixel);
		return new Segment(line, nwPixel, swPixel);
	}
	
	public Segment getRightSegment(PanelBoard board) {
		Pixel nePixel = getPixelNe(board);
		Pixel sePixel = getPixelSe(board);
		Line line = new Line(nePixel, sePixel);
		return new Segment(line, nePixel, sePixel);
	}
	

	
}
