package GeoObjects;

/**
 * Class that represents a "target" in a known geographic location (without movement).
 * The Packman eats those fruits on the Game map.
 * @author Yoav and Elad.
 *
 */
public class Fruit extends GenericGeoObject {
	
	final private int randImage; //what kind of fruit image the object will get.

////////////////////////***Constructor****///////////////////////////////////////////

	public Fruit(Point3D location, int id, double spd_wt) {
		super(location, id, spd_wt, 0);
		randImage = id%6;
	}

////////////////////*** Getters and Setters**//////////////////////////////////////

	public int getRandImage() {
		return randImage;
	}
}

