package guiObjects;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Coords.MyCoords;
import GeoObjects.Point3D;
/**
 * A class that represents a map that includes a map image file and all the required 
 * parameters of its alignment to a global coordinate system.
 *  The class enables conversion of global representation coordinates, pixel to image and vice versa. 
 *  In addition, the department enables the calculation of distances in meters between 
 *  two pixels in an image and the angle between them.

 * @author Yoav and Elad
 *
 */
public class Map {

	public BufferedImage myImage;

	public final Point3D nw;
	public final Point3D ne;
	public final Point3D sw;
	public final Point3D se;
	MyCoords mc = new MyCoords();

//////////////////***Constructor****///////////////////////////////////

	public Map(String imagePath) { 
		try {
			myImage = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//The edges of the map//
		nw = new Point3D( 32.105394,  35.202532, 0);
		ne = new Point3D( 32.105444,  35.212496, 0);
		sw = new Point3D( 32.101899,  35.202447, 0);
		se = new Point3D( 32.101899,  35.212496, 0);
	}

////////////////////*** Methods ***/////////////////////

	public int getOriginHeight() {
		return myImage.getHeight();
	}
	
	public int getOriginWidht() {
		return myImage.getWidth();
	}
	/**
	 * This function convert gps point to a pixel.
	 * @return The pixel of the gps point on the map.
	 * @param point gps point
	 * @param width , width of the map in pixels
	 * @param height , height of the map in pixels
	 */
	public Pixel gps2pixel(Point3D point, int widht, int height) { 
		double imageLatD = nw.x() - se.x();
		double currentLatD = nw.x() - point.x();
		double fractionNorth = currentLatD / imageLatD; 
		double latpixel = fractionNorth * height;
		
		Point3D leftMergin = mc.midPoint(nw, sw, fractionNorth);
		Point3D rightMergin = mc.midPoint(ne, se, fractionNorth);
		
		double currentImageLonD = rightMergin.y() - leftMergin.y();
		double currentLonD = point.y() - leftMergin.y();
		double fractionWest = currentLonD / currentImageLonD;
		double lonpixel = fractionWest * widht;

		return new Pixel((int)lonpixel, (int)latpixel);
	}
/**
 * This function convert a pixel to gps point.
 * @return A gps point from pixel in the screen.
 * @param pixel pixel in the map
 * @param widht width of the map pixels
 * @param height height of the map pixels
 */
	public Point3D pixel2gps (Pixel pixel, int widht, int height) {
		//TODO
		double ratioH = (double)pixel.y() / height;
		double ratioW = (double)pixel.x() / widht;
		double imageLatD = nw.x() - se.x();
		double lat = nw.x() - ratioH * imageLatD;
		
		Point3D leftMergin = mc.midPoint(nw, sw, ratioH);
		Point3D rightMergin = mc.midPoint(ne, se, ratioH);
		
		double imageLonD = rightMergin.y() - leftMergin.y();
		double lon = leftMergin.y() + ratioW * imageLonD;
		
		return new Point3D(lat, lon, 0);
	}
	
	//This functions calculate how many pixels between two points, following the formula:
	//width or hight of two frame on pixels * (distance between two points / length of right to left (or bottom to top) of the frame)  
	
	public int pixelHorizontalDistance (Point3D point1, Point3D point2 ,int widht, int height) {
		Pixel pixel1 = gps2pixel(point1, widht, height); 
		Pixel left = new Pixel(0, pixel1.y());
		Point3D west = pixel2gps(left, widht, height);
		
		Pixel pixel2 = gps2pixel(point2, widht, height); 
		Pixel right = new Pixel(widht, pixel2.y());
		Point3D east = pixel2gps(right, widht, height);
		
		double currentLatDis = mc.distance2D(east, west);
		double pointsDis = mc.distance2D(point1, point2);
		
		return (int)((pointsDis/currentLatDis) * widht);
	}
	
	public int pixelVerticalDistance(Point3D point1, Point3D point2, int widht, int height) {
		Pixel pixel1 = gps2pixel(point1, widht, height); 
		Pixel up = new Pixel(pixel1.x(), 0);
		Point3D north = pixel2gps(up, widht, height);
		
		Pixel pixel2 = gps2pixel(point2, widht, height); 
		Pixel down = new Pixel(pixel1.x(), height);
		Point3D south = pixel2gps(down, widht, height);
		
		double currentLonDis = mc.distance2D(north, south);
		double pointsDis = mc.distance2D(point1, point2);
		
		return (int)((pointsDis/currentLonDis) * widht);
	}
	
}
