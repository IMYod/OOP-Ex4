package guiObjects;
/**
 * This class represent a segment for the board of the game.
 * 
 * @author Yoav and Elad.
 */
public class Segment {

	Line line;
	Pixel p1; //p1.x<p2.x
	Pixel p2;

////////////////////////***Constructors****///////////////////////////////////////////

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
	
	public Segment(Pixel p1, Pixel p2) {
		this(new Line(p1,p2), p1, p2);
	}

///////////////////////////*** Methods ***//////////////////////////////////////////

	public double length() {
		return p1.distance(p2);
	}
	
	/**
	 * This method checks if the is a cutting point with other segment.
	 * @param other The other segment.
	 * @return The pixel of the cuuting.
	 */
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
	
	/**
	 * This method checks if there is a some pixel on the segment.
	 * @param pixel That we will check with.
	 * @return True Or False.
	 */
	public boolean onSegment(Pixel pixel) {
		if (pixel.equals(p1) || pixel.equals(p2))
			return false;
		return (p1.x() <= pixel.x() && pixel.x() <= p2.x() && (p1.y()-pixel.y())*(p2.y()-pixel.y()) <= 0); //p1.x<x'<p2.x && p1.y<y'<p2.y
	}
	

	public String toString() {
		return line.toString() + "[" + p1.toString() + "," + p2.toString() + "]";
	}

}
