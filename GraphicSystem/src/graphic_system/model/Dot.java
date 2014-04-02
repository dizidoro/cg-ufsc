package graphic_system.model;


public class Dot extends Geometry {

	private static int dotCount = 1;
	
	private Coordinate coordinate;
	
	public Dot(Coordinate coordinate) {
		super("dot" + dotCount, Geometry.Type.POINT);

		this.coordinate = coordinate;
		
		dotCount++;
	}
	
	private Dot(String name, Coordinate coordinate){
		super(name, Geometry.Type.POINT);
		this.coordinate = coordinate;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	@Override
	public Dot getWindowViewportTransformation(Window window, Viewport viewport) {
		Coordinate viewportCoordinate = coordinate.getWindowViewportTransformation(window, viewport);
		return new Dot(this.getName(), viewportCoordinate);
	}

}

