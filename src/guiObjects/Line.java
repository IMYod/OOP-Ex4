package guiObjects;

//y=mx+n
//

public class Line {
	
	double m; //slope
	double n; //y-intercept
	
	public Line(double m, double n) {
		this.m = m;
		this.n= n;
	}
	
	public Line(Line other) {
		this(other.m, other.n);
	}
	
	//calculate line equation by two points
	public Line(Pixel p0, Pixel p1) {
		if (p1.x() == p0.x()) { //parallel to the y-axis
			m = Double.MAX_VALUE;
		}
		else
			m = (p1.y() - p0.y())/(double)(p1.x()-p0.x());
		n = p1.y()-m*p1.x();
	}
	
	public Pixel cuttingPoint(Line other) {
		if (m == other.m) {
			if (n == other.n) //Lines converge
				return new Pixel(Integer.MAX_VALUE, Integer.MAX_VALUE);
			else //parallel lines
				return null;
		}
		double x = (other.n-n)/(m-other.m);
		double y = m*x + n;
		return new Pixel((int)x, (int)y);
	}

	public double getM() {
		return m;
	}

	public double getN() {
		return n;
	}

}
