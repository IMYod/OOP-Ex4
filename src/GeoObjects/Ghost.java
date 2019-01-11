package GeoObjects;

public class Ghost extends GenericGeoObject {
	
	public static String imagePath = "ImagesforGui\\ghost.png";

////////////////////////***Constructor****///////////////////////////////////////////

////////////////////////***Constructor****///////////////////////////////////////////

	public Ghost(Point3D location, int id, double spd_wt, double radius) {
		super(location, id, spd_wt, radius);
		setImagePath(imagePath);
	}

}
