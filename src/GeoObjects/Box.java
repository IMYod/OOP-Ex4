package GeoObjects;

public class Box {
	
	private int id = 0;
	private Point3D nw;
	private Point3D se;
	private double nothing1;
	private double nothing2;
	
	Box(int id, Point3D nw, Point3D se, double nothing1, double nothing2){
		this.id = id;
		this.nw = nw;
		this.se = se;
		this.nothing1 = nothing1;
		this.nothing2 = nothing2;
	}
	
}
