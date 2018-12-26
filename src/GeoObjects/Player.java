package GeoObjects;

import guiObjects.Pixel;

public class Player extends GenericGeoObject {

	private double azimuth;
	
	public Player(Point3D location) {
		super(location, 0, 0, 0);
		// TODO Auto-generated constructor stub
	}

	public double getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(double azimuth) {
		this.azimuth = azimuth;
	}
	

}
