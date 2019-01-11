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
		setImagePath(chooseImage(randImage));
	}

////////////////////*** Getters and Setters**//////////////////////////////////////

	public int getRandImage() {
		return randImage;
	}
	
	//return image path, by index between 0 to 5
	public static String chooseImage(int i) {
		switch (i) {
		case 0:
			return("ImagesforGui\\apple.png");
		case 1:
			return("ImagesforGui\\apple2.png");
		case 2:
			return("ImagesforGui\\banana.png");
		case 3:
			return("ImagesforGui\\orange.png");
		case 4:
			return("ImagesforGui\\peach.png");
		case 5:
			return("ImagesforGui\\watermalon.png");
		default:
			return null;
		}
	}
	
}

