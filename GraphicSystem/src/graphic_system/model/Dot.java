package graphic_system.model;

public class Dot extends Geometry {

	private static int dotCount = 1;

	private Coordinate coordinate;

	public Dot(Coordinate coordinate) {
		super("dot" + dotCount, Geometry.Type.POINT);

		this.coordinate = coordinate;

		dotCount++;
	}

	private Dot(String name, Coordinate coordinate) {
		super(name, Geometry.Type.POINT);
		this.coordinate = coordinate;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	@Override
	public Dot getWindowViewportTransformation(Window window, Viewport viewport) {
		Coordinate viewportCoordinate = coordinate
				.getWindowViewportTransformation(window, viewport);
		return new Dot(this.getName(), viewportCoordinate);
	}

	@Override
	public Coordinate getCenter() {
		return coordinate;
	}

	@Override
	public void setCenter(Coordinate center) {
		this.coordinate = center;
	}

	@Override
	public void scaleLess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scalePlus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void objectRotation() {
		// Um ponto rotacionado sobre ele mesmo fica no mesmo ponto
	}

	@Override
	public void worldRotation(Window window, Viewport viewport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dotRatation(Coordinate dot) {
		// TODO Auto-generated method stub
		
	}

}
