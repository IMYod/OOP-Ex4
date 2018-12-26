package GeoObjects;

/**
 * A class that represents a "robot" with a location, 
 * orientation and ability to move (at a defined speed).
 * @author Yoav and elad.
 *
 */
public class Packman extends GenericGeoObject 
{

	public Packman(Point3D location, int id, double spd_wt, double radius) {
		super(location, id, spd_wt, radius);
	}
	
}
