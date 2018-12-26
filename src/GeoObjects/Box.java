package GeoObjects;

import Coords.MyCoords;

public class Box {
	
	private int id;
	private Point3D nw;
	private Point3D se;
//	private double nothing1;
//	private double nothing2;
	MyCoords mc = new MyCoords();
	
	Box(int id, Point3D nw, Point3D se, double nothing1, double nothing2){
		this.id = id;
		this.nw = nw;
		this.se = se;
//		this.nothing1 = nothing1;
//		this.nothing2 = nothing2;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Point3D getNW() {
		return nw;
	}

	public void setNW(Point3D nw) {
		this.nw = nw;
	}

	public Point3D getSE() {
		return se;
	}

	public void setSE(Point3D se) {
		this.se = se;
	}
	
	public Point3D getNE() {
		return new Point3D(nw.x(), se.y(), 0);
	}
	public Point3D getSW() {
		return new Point3D(se.x(), nw.y(), 0);
	}
	
	public double getWidth() {
		return mc.distance2D(nw, getNE());
	}
	
	public double getHeight() {
		return mc.distance2D(nw, getSW());
	}
	
}
