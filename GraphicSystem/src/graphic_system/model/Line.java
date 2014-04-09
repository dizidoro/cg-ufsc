package graphic_system.model;

import graphic_system.ApplicationConfig;

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

	// @Override
	// public Geometry getWindowViewportTransformation(Window window,
	// Viewport viewport, double zoom) {
	// System.out.println("Zoom: " + zoom);
	// Coordinate viewportA = a.getWindowViewportTransformation(window,
	// viewport);
	// Coordinate viewportB = b.getWindowViewportTransformation(window,
	// viewport);
	// if (a.getX() - b.getX() < 0) {
	// viewportA.setX(viewportA.getX() - zoom);
	// viewportB.setX(viewportB.getX() + zoom);
	// } else {
	// viewportA.setX(viewportA.getX() + zoom);
	// viewportB.setX(viewportB.getX() - zoom);
	// }
	//
	// if (a.getY() - b.getY() < 0) {
	// viewportA.setY(viewportA.getY() + zoom);
	// viewportB.setY(viewportB.getY() - zoom);
	// } else {
	// viewportA.setY(viewportA.getY() - zoom);
	// viewportB.setY(viewportB.getY() + zoom);
	// }
	// return new Line(this.getName(), viewportA, viewportB);
	// }

	@Override
	public Coordinate getCenter() {
		double x = (a.getX() + b.getX()) / 2;
		double y = (a.getY() + b.getY()) / 2;
		Coordinate center = new Coordinate(x, y);
		return center;
	}

	@Override
	public void setCenter(Coordinate center) {
		Coordinate oldCenter = getCenter();
		double xDiff = center.getX() - oldCenter.getX();
		double yDiff = center.getY() - oldCenter.getY();

		double ax = a.getX() + xDiff;
		double ay = a.getY() + yDiff;
		double bx = b.getX() + xDiff;
		double by = b.getY() + yDiff;

		a.setX(ax);
		a.setY(ay);
		b.setX(bx);
		b.setY(by);
	}

	@Override
	public void scaleLess() {
		// Movo para a origem e multiplico pelo fator de escala
		// Em seguida volto o objeto para o centro antigo
		Coordinate center = getCenter();
		setCenter(new Coordinate(0, 0));

		double factor = 1 / ApplicationConfig.OBJECT_SCALE_FACTOR;
		double ax = a.getX() * factor;
		double ay = a.getY() * factor;
		double bx = b.getX() * factor;
		double by = b.getY() * factor;

		a.setX(ax);
		a.setY(ay);
		b.setX(bx);
		b.setY(by);

		setCenter(center);
	}

	@Override
	public void scalePlus() {
		// Movo para a origem e multiplico pelo fator de escala
		// Em seguida volto o objeto para o centro antigo
		Coordinate center = getCenter();
		setCenter(new Coordinate(0, 0));

		double factor = ApplicationConfig.OBJECT_SCALE_FACTOR;
		double ax = a.getX() * factor;
		double ay = a.getY() * factor;
		double bx = b.getX() * factor;
		double by = b.getY() * factor;

		a.setX(ax);
		a.setY(ay);
		b.setX(bx);
		b.setY(by);

		setCenter(center);
	}

	@Override
	public void objectRotation() {
		// TODO Auto-generated method stub

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
