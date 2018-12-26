package GeoObjects;

/**
 * Class that represents a "target" in a known geographic location (without movement).
 * The Packman eats those fruits on the Game map.
 * @author Yoav and Elad.
 *
 */
public class Fruit extends GenericGeoObject {
	
	final private int randImage; //what kind of fruit image the object will get.

	public Fruit(Point3D location, int id, double spd_wt, double radius) {
		super(location, id, spd_wt, radius);
		randImage = (int)(Math.random()*6);
	}
	
	public int getRandImage() {
		return randImage;
	}
}

