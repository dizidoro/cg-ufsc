package graphic_system.model.raster;

import graphic_system.model.RegionCode;
import graphic_system.model.Viewport;
import graphic_system.model.Window;
import math.Matrix;

public class Coordinate3D {

	private double x;
	private double y;
	private double z;
	private double mag;

	private double xScn;
	private double yScn;
	private double zScn;

	public Coordinate3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.mag = Math.sqrt(x * x + y * y + z * z);

		this.xScn = x;
		this.yScn = y;
		this.zScn = z;
	}

	// Constrói e retorna uma coordenada na mesma direção que este vetor
	public Coordinate3D unitVector() {
		if (mag <= 0.0) {
			return new Coordinate3D(1.0f, 0.0f, 0.0f, 1.0f);
		} else {
			return new Coordinate3D(x / mag, y / mag, z / mag, 1.0f);
		}
	}

	// Retorna coordenada subtraído por esse
	public Coordinate3D minus(Coordinate3D other) {
		return new Coordinate3D(x - other.x, y - other.y, z - other.z);
	}

	// Retorna coordenada mais essa
	public Coordinate3D plus(Coordinate3D other) {
		return new Coordinate3D(x + other.x, y + other.y, z + other.z);
	}

	// Produto escalar deste vetor e outro vetor
	public double dotProduct(Coordinate3D other) {
		return x * other.x + y * other.y + z * other.z;
	}

	// Retorna o coordenada que é o produto deste vetor com outro vetor
	public Coordinate3D crossProduct(Coordinate3D other) {
		double x = this.y * other.z - this.z * other.y;
		double y = this.z * other.x - this.x * other.z;
		double z = this.x * other.y - this.y * other.x;
		return new Coordinate3D(x, y, z);
	}

	// Retorna o cosseno do ângulo entre esse vetor e outro
	public double cos(Coordinate3D other) {
		return (x * other.x + y * other.y + z * other.z) / mag / other.mag;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public double getXScn() {
		return xScn;
	}

	public double getYScn() {
		return yScn;
	}

	public double getZScn() {
		return zScn;
	}

	private Coordinate3D(double x, double y, double z, double mag) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.mag = mag;
	}

	public Coordinate3D getWindowViewportTransformation(Window window,
			Viewport viewport) {
		return getWindowViewportTransformation(window, viewport, x, y);
	}

	public Coordinate3D getWindowViewportTransformationSCN(Window window,
			Viewport viewport) {
		return getWindowViewportTransformation(window, viewport, xScn, yScn);
	}

	private static Coordinate3D getWindowViewportTransformation(Window window,
			Viewport viewport, double x, double y) {
		double viewportX = (x - window.getXMin())
				/ (window.getXMax() - window.getXMin())
				* (viewport.getXMax() - viewport.getXMin());
		double viewportY = (1 - ((y - window.getYMin()) / (window.getYMax() - window
				.getXMin()))) * (viewport.getYMax() - viewport.getYMin());
		return new Coordinate3D(viewportX, viewportY, 1);
	}

	public void transform(double[][] transformationMatrix) {
		final double[][] coordinateMatrix = matrix(x, y, z);
		final double[][] transformedCoordinateMatrix = Matrix.multiply(
				coordinateMatrix, transformationMatrix);

		x = transformedCoordinateMatrix[0][0];
		y = transformedCoordinateMatrix[0][1];
	}

	public void transformSCN(double[][] transformationMatrix) {
		final double[][] coordinateMatrix = matrix(xScn, yScn, zScn);
		final double[][] transformedCoordinateMatrix = Matrix.multiply(
				coordinateMatrix, transformationMatrix);

		xScn = transformedCoordinateMatrix[0][0];
		xScn = transformedCoordinateMatrix[0][1];
	}

	private static double[][] matrix(double x, double y, double z) {
		double[][] coordinateMatrix = new double[1][3];
		coordinateMatrix[0][0] = x;
		coordinateMatrix[0][1] = y;
		coordinateMatrix[0][2] = z;
		return coordinateMatrix;
	}

	public Coordinate3D getTransformed(double[][] transformationMatrix) {
		final double[][] coordinateMatrix = matrix(x, y, z);
		final double[][] transformedCoordinateMatrix = Matrix.multiply(
				coordinateMatrix, transformationMatrix);

		double transformedX = transformedCoordinateMatrix[0][0];
		double transformedY = transformedCoordinateMatrix[0][1];

		return new Coordinate3D(transformedX, transformedY, 1);
	}

	public RegionCode getRegionCode(Window window) {
		RegionCode code = new RegionCode();
		if (y > window.getYMax()) {
			code.top = 1;
			code.all += 8;
		} else if (y < window.getYMin()) {
			code.bottom = 1;
			code.all += 4;
		}
		if (x > window.getXMax()) {
			code.right = 1;
			code.all += 2;
		} else if (x < window.getXMin()) {
			code.left = 1;
			code.all += 1;
		}
		return code;
	}
}
