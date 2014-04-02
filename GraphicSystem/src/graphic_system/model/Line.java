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

}
