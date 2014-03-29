package model;


public class Dot extends Geometry {

	private static int dotCount = 1;
	
	private Coordinate coordinate;
	
	public Dot(Coordinate coordinate) {
		super("dot" + dotCount, ObjectType.DOT);

		this.coordinate = coordinate;
		
		dotCount++;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
}

