package graphic_system.model;

import graphic_system.ApplicationConfig;

import java.awt.Color;

public class Line extends Geometry {

	private static int lineCount = 1;

	// Uma reta no plano pode ser caracterizada por:
	// dois pontos (a, b) distintos do plano;
	private Coordinate a;
	private Coordinate b;

	public Line(Coordinate a, Coordinate b, Color color) {
		super("line" + lineCount, Geometry.Type.LINE, color);
		this.a = a;
		this.b = b;
		lineCount++;
	}

	public Line(String name, Coordinate a, Coordinate b, Color color) {
		super(name, Geometry.Type.LINE, color);
		this.a = a;
		this.b = b;
	}
	
	public Line(Coordinate a, Coordinate b) {
		super("line", Geometry.Type.LINE, Color.black);
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
		return new Line(this.getName(), viewportA, viewportB, this.getColor());
	}



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

		a = new Coordinate(ax, ay);
		b = new Coordinate(bx, by);

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

		a = new Coordinate(ax, ay);
		b = new Coordinate(bx, by);

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

		a = new Coordinate(ax, ay);
		b = new Coordinate(bx, by);

		setCenter(center);
	}

	@Override
	protected void transform(double[][] matrix) {
		a.transform(matrix);
		b.transform(matrix);
	}


	@Override
	protected Geometry getTransformed(double[][] transformationMatrix) {
		Coordinate transformedA = a.getTransformed(transformationMatrix);
		Coordinate transformedB = b.getTransformed(transformationMatrix);
		return new Line(name, transformedA, transformedB, color);
	}

	@Override
	public Line getClipping(Window window) {
		boolean accept = false, done = false;
		RegionCode codeA = a.getRegionCode(window);
		RegionCode codeB = b.getRegionCode(window);
		RegionCode outCode;

		double x0 = a.getX();
		double y0 = a.getY();
		double x1 = b.getX();
		double y1 = b.getY();
		double x = 0;
		double y = 0;

		double xmax = window.getXMax();
		double xmin = window.getXMin();
		double ymax = window.getYMax();
		double ymin = window.getYMin();

		do {

			if (codeA.all == 0 && codeB.all == 0) {
				// A linha está toda contida na window, então desenha!
				accept = true;
				done = true;
			} else if ((codeA.all & codeB.all) != 0) {
				// A linha está toda fora!
				done = true;
			} else {

				if (codeA.all != 0) {
					outCode = codeA;
				} else {
					outCode = codeB;
				}

				if (outCode.top == 1) {
					x = x0 + (x1 - x0) * (ymax - y0) / (y1 - y0);
					y = ymax;
				} else if (outCode.bottom == 1) {
					x = x0 + (x1 - x0) * (ymin - y0) / (y1 - y0);
					y = ymin;
				} else if (outCode.right == 1) {
					y = y0 + (y1 - y0) * (xmax - x0) / (x1 - x0);
					x = xmax;
				} else if (outCode.left == 1) {
					y = y0 + (y1 - y0) * (xmin - x0) / (x1 - x0);
					x = xmin;
				}

				if (outCode.all == codeA.all) {
					x0 = x;
					y0 = y;
					Coordinate newCode = new Coordinate(x0, y0);
					codeA = newCode.getRegionCode(window);
				} else {
					x1 = x;
					y1 = y;
					Coordinate newCode = new Coordinate(x1, y1);
					codeB = newCode.getRegionCode(window);
				}
			}
		} while (!done);

		if (accept) {
			System.err.println("new line at (" + x0 + "," + y0 + ") e (" + x1 + "," + y1 + ").");
			Coordinate a = new Coordinate(x0, y0);
			Coordinate b = new Coordinate(x1, y1);
			return new Line(a, b, this.color);
		}
		return null;
	}

}
