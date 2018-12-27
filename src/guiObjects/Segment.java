package guiObjects;

public class Segment extends Line {

	Pixel p1;
	Pixel p2;

	public Segment(Line line, Pixel p1, Pixel p2) {
		super(line);
		if (p1.x() < p2.x()) { //in every segment p1.x < p2.x
			this.p1 = p1;
			this.p2 = p2;
		}
		else {
			this.p1 = p2;
			this.p2 = p1;
		}
	}

	public double length() {
		return p1.distance(p2);
	}

	public Pixel cuttingPoint(Segment other) {
		if (m == other.m) {
			if (n == other.n) //Lines converge
				if (p2.x() < other.p1.x() || p1.x() > other.p2.x())  
					return null;
				else return new Pixel(Integer.MAX_VALUE, Integer.MAX_VALUE);
			else //parallel lines
				return null;
		}
		double x = (other.n-n)/(m-other.m);
		if (x < other.p1.x() || x < p1.x() || x > other.p2.x() || x > p2.x())
			return null; //cutting point isn't on two segments
		
		double y = m*x + n;
		return new Pixel((int)x, (int)y);
		
	}

}
