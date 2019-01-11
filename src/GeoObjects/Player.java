package GeoObjects;

import guiObjects.Pixel;

public class Player extends GenericGeoObject {

	public static String imagePath = "ImagesforGui\\player.png";
	private double azimuth;

////////////////////////***Constructor****///////////////////////////////////////////
	
	public Player(Point3D location, int id, double speed, double radius) {
		super(location, id, speed, radius);
		setImagePath(imagePath);
	}

////////////////////*** Getters and Setters**//////////////////////////////////////

	public double getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(double azimuth) {
		this.azimuth = azimuth;
	}
	

}
