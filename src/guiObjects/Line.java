package guiObjects;
/**
 * This class represent a line in the space of 2d.
 * y = mx + n.
 *
 */
public class Line {
	
	double m; //slope
	double n; //y-intercept
	boolean vertical;  //parallel to the y-axis

////////////////////////***Constructors****///////////////////////////////////////////

	public Line(double m, double n) {
		this.m = m;
		this.n= n;
		vertical = (m!= Double.MAX_VALUE && m != Double.MIN_VALUE);
	}
	
	public Line(Line other) {
		this(other.m, other.n);
		vertical = other.vertical;
	}

///////////////////////////*** Methods ***//////////////////////////////////////////

	
	/**
	 * This method calculate line equation by two points
	 * @param p0 first point 
	 * @param p1 second point
	 */
	public Line(Pixel p0, Pixel p1) {
		if (p1.x() == p0.x()) { //parallel to the y-axis
			m = Double.MAX_VALUE;
			n = -p1.x(); //when n=x': x-x'=0
			vertical = true;
		}
		else {
			m = (p1.y() - p0.y())/(double)(p1.x()-p0.x());
			n = p1.y()-m*p1.x();
			vertical = false;
		}
	}
	
	/**
	 * This method compute if there is a cutting point between two lines. 
	 * @param other The other line to check.
	 * @return The pixel of the cutting point.
	 */
	public Pixel cuttingPoint(Line other) {
		double x;
		double y;
		if (m == other.m) {
			if (n == other.n) //Lines converge
				return new Pixel(Integer.MAX_VALUE, Integer.MAX_VALUE);
			else //parallel lines
				return null;
		}

		// If one of them is vertical, I will assign the x value to other
		if (vertical) {
			if (other.vertical)
				return null;
			else {
				x = -n;
				y = other.m*x + other.n;
			}
				
		}
		else if (other.vertical) {
			x = -other.n;
			y = m*x + n;
		}
		else{ //It is neither vertical nor other vertical
			x = (other.n-n)/(m-other.m);
			y = m*x + n;
		}
		return new Pixel((int)(x+0.001), (int)(y+0.001));
	}

	public String toString() {
		if (vertical)
			return "x="+(-n);
		else
			return "y="+m+"*x+"+n;
	}

	////////////////////*** Getters and Setters**//////////////////////////////////////

	public double getM() {
		return m;
	}

	public double getN() {
		return n;
	}

}
