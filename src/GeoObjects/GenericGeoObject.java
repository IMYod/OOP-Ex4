package GeoObjects;
import Coords.MyCoords;
import guiObjects.Pixel;

/**
 * This class is a super class of Generic Geographic Object.
 * In this project we have:
 * 1.Packman
 * 2.Fruit
 * We wanted to use the inheritance and the packman and fruit have some properties in common.
 * So this class is a simple solution to this situation
 * @author Yoav and Elad.
 *
 */
public class GenericGeoObject {

	private Point3D location;
	private int id;
	private double spd_wt;
	private double radius;
	
	////////////////// ***Constructors****///////////////////////////////////

	public GenericGeoObject (Point3D location, int id, double spd_wt, double radius) {
		this.location= location;
		this.id = id;
		this.spd_wt = spd_wt;
		this.radius = radius;
	}
	
	///////////////*** Getters and Setters**/////////////////////



	public void setLocation(Point3D gps) {
		location = gps;
	}

	public Point3D getLocation() {
		return location;
	}

	public int getId() {
		return id;
	}

	public double getSpd_wt() {
		return spd_wt;
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return " [location:" + location + ", id:" + id + " , speed/weight:" + spd_wt + " radius:" + radius + "]";
	}
	///////////////*** Methods ***/////////////////////

	/**
	 * This function compute the distance between two GenericGeoObject
	 * 
	 * @param other The other GenericGeoObject to check with.
	 * @return The distance in double type.
	 */
	public double distance(GenericGeoObject other) {
		MyCoords mc = new MyCoords();
		return mc.distance3d(location, other.location);
	}

}
