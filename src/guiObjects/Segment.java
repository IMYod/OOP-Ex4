package guiObjects;

public class Segment {

	Line line;
	Pixel p1; //p1.x<p2.x
	Pixel p2;

	public Segment(Line line, Pixel p1, Pixel p2) {
		this.line = new Line(line);
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
		Pixel cuttingLines = line.cuttingPoint(other.line);
		if (cuttingLines == null)
			return null;
		if (cuttingLines.equals(new Pixel(Integer.MAX_VALUE, Integer.MAX_VALUE)))  //Lines converge
			if (onSegment(other.p1) || onSegment(other.p2))
				return new Pixel(Integer.MAX_VALUE, Integer.MAX_VALUE);
			else
				return null;
		if (onSegment(cuttingLines) && other.onSegment(cuttingLines))
			return cuttingLines;
		return null;
	}
	
	public boolean onSegment(Pixel pixel) {
		if (pixel.equals(p1) || pixel.equals(p2))
			return false;
		return (p1.x() <= pixel.x() && pixel.x() <= p2.x() && (p1.y()-pixel.y())*(p2.y()-pixel.y()) <= 0); //p1.x<x'<p2.x && p1.y<y'<p2.y
	}
	

	public String toString() {
		return line.toString() + "[" + p1.toString() + "," + p2.toString() + "]";
	}

}
