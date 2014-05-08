package graphic_system.model;

import java.awt.Color;

import math.Matrix;

public abstract class Geometry {

	protected static final Coordinate origin = new Coordinate(0, 0);

	private static final double CLOCKWISE_DEGREE = 10;
	private static final double ANTI_CLOCKWISE_DEGREE = 350;

	protected final String name;
	protected final Type type;
	protected Color color;

	public Geometry(String name, Type type, Color color) {
		this.name = name;
		this.type = type;
		this.color = color;
	}

	public enum Type {
		POINT, LINE, POLYGON, CURVE, BSPLINE
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public abstract Geometry getWindowViewportTransformation(Window window,
			Viewport viewport);

	public abstract Coordinate getCenter();

	public abstract void setCenter(Coordinate center);

	public abstract void scaleLess();

	public abstract void scalePlus();

	protected abstract void transform(double[][] transformationMatrix);

	protected abstract Geometry getTransformed(double[][] transformationMatrix);

	public void rotateClockwiseAroundOrigin() {
		this.rotateClockwiseAroundPoint(origin);
	}

	public void rotateAntiClockwiseAroundOrigin() {
		this.rotateAntiClockwiseAroundPoint(origin);
	}

	public void rotateClockwiseAroundCenter() {
		this.rotateClockwiseAroundPoint(getCenter());
	}

	public void rotateAntiClockwiseAroundCenter() {
		this.rotateAntiClockwiseAroundPoint(getCenter());
	}

	public void rotateClockwiseAroundPoint(Coordinate coordinate) {
		double[][] rotationMatrix = getClockwiseRotationMatrix(coordinate);
		this.transform(rotationMatrix);
	}

	public void rotateAntiClockwiseAroundPoint(Coordinate coordinate) {
		double[][] rotationMatrix = getAntiClockwiseRotationMatrix(coordinate);
		this.transform(rotationMatrix);
	}

	private static double[][] getClockwiseRotationMatrix(Coordinate coordinate) {
		return getRotationMatrix(coordinate, CLOCKWISE_DEGREE);
	}

	private static double[][] getAntiClockwiseRotationMatrix(
			Coordinate coordinate) {
		return getRotationMatrix(coordinate, ANTI_CLOCKWISE_DEGREE);
	}

	private static double[][] getRotationMatrix(Coordinate coordinate,
			double angle) {
		final double cos = Math.cos(Math.toRadians(angle));
		final double sin = Math.sin(Math.toRadians(angle));

		final double[][] rotation = { { cos, -sin, 0 }, { sin, cos, 0 },
				{ 0, 0, 1 } };

		final double[][] moveToCenter = { { 1, 0, 0 }, { 0, 1, 0 },
				{ -coordinate.getX(), -coordinate.getY(), 1 } };

		final double[][] returnFromCenter = { { 1, 0, 0 }, { 0, 1, 0 },
				{ coordinate.getX(), coordinate.getY(), 1 } };

		final double[][] moveAndRotate = Matrix
				.multiply(moveToCenter, rotation);
		final double[][] rotationMatrix = Matrix.multiply(moveAndRotate,
				returnFromCenter);

		return rotationMatrix;
	}

	public void rotateAntiClockwiseAroundPointSCN(Coordinate coordinate) {
		double[][] rotationMatrix = getAntiClockwiseRotationMatrix(coordinate);
		this.transformSCN(rotationMatrix);
	}

	public abstract void transformSCN(double[][] rotationMatrix);

	public void rotateClockwiseAroundPointSCN(Coordinate coordinate) {
		double[][] rotationMatrix = getClockwiseRotationMatrix(coordinate);
		this.transformSCN(rotationMatrix);
	}

	public abstract Geometry getWindowViewportTransformationSCN(Window window,
			Viewport viewport);

	public Geometry getRotatedAroundPoint(Coordinate coordinate, double angle) {
		double[][] rotationMatrix = getRotationMatrix(coordinate, angle);
		return getTransformed(rotationMatrix);
	}

	public RegionCode getRegionCode(Coordinate coordinate, Window window) {
		RegionCode code = new RegionCode();
		if (coordinate.getY() > window.getYMax()) {
			code.top = 1;
			code.all += 8;
		} else if (coordinate.getY() < window.getYMin()) {
			code.bottom = 1;
			code.all += 4;
		}
		if (coordinate.getX() > window.getXMax()) {
			code.right = 1;
			code.all += 2;
		} else if (coordinate.getX() < window.getXMin()) {
			code.left = 1;
			code.all += 1;
		}
		return code;
	}

	public abstract Geometry getClipping(Window window);

}
