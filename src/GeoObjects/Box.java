package GeoObjects;

import Coords.MyCoords;

public class Box {
	
	private int id;
	private Point3D ne;
	private Point3D sw;
//	private double nothing1;
//	private double nothing2;
	MyCoords mc = new MyCoords();
	
	public Box(Point3D ne, int id, Point3D sw, double nothing1, double nothing2){
		this.id = id;
		this.ne = ne;
		this.sw = sw;
//		this.nothing1 = nothing1;
//		this.nothing2 = nothing2;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Point3D getNE() {
		return ne;
	}

	public void setNE(Point3D nw) {
		this.ne = ne;
	}

	public Point3D getSW() {
		return sw;
	}

	public void setSW(Point3D sw) {
		this.sw = sw;
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
