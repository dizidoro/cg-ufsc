package graphic_system.model;

public class Line extends Geometry {

	private static int lineCount = 1;

	// Uma reta no plano pode ser caracterizada por:
	// dois pontos (a, b) distintos do plano;
	private Coordinate a;
	private Coordinate b;

	public Line(Coordinate a, Coordinate b) {
		super("line" + lineCount, Geometry.Type.LINE);
		this.a = a;
		this.b = b;
		lineCount++;
	}

	private Line(String name, Coordinate a, Coordinate b) {
		super(name, Geometry.Type.LINE);
		this.a = a;
		this.b = b;
	}

	public Coordinate getA() {
		return a;
	}

	public Coordinate getB() {
		return b;
	}

	@Override
	public Line getWindowViewportTransformation(Window window, Viewport viewport) {
		Coordinate viewportA = a.getWindowViewportTransformation(window,
				viewport);
		Coordinate viewportB = b.getWindowViewportTransformation(window,
				viewport);
		return new Line(this.getName(), viewportA, viewportB);
	}

//	@Override
//	public Geometry getWindowViewportTransformation(Window window,
//			Viewport viewport, double zoom) {
//		System.out.println("Zoom: " + zoom);
//		Coordinate viewportA = a.getWindowViewportTransformation(window,
//				viewport);
//		Coordinate viewportB = b.getWindowViewportTransformation(window,
//				viewport);
//		if (a.getX() - b.getX() < 0) {
//			viewportA.setX(viewportA.getX() - zoom);
//			viewportB.setX(viewportB.getX() + zoom);
//		} else {
//			viewportA.setX(viewportA.getX() + zoom);
//			viewportB.setX(viewportB.getX() - zoom);
//		}
//		
//		if (a.getY() - b.getY() < 0) {
//			viewportA.setY(viewportA.getY() + zoom);
//			viewportB.setY(viewportB.getY() - zoom);
//		} else {
//			viewportA.setY(viewportA.getY() - zoom);
//			viewportB.setY(viewportB.getY() + zoom);
//		}
//		return new Line(this.getName(), viewportA, viewportB);
//	}

	@Override
	public Coordinate getCenter() {
		// TODO Auto-generated method stub
		return a;
	}

	@Override
	public void setCenter(Coordinate center) {
		// TODO Auto-generated method stub
		
	}

}
