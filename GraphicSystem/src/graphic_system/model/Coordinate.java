package graphic_system.model;
import java.lang.Math;
import math.Matrix;

public class Coordinate {

	private double x;
	private double y;
	private final double z;

	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
		this.z = 1;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}


	public double getZ() {
		return z;
	}

	public Coordinate getWindowViewportTransformation(Window window,
			Viewport viewport) {
		double viewportX = (x - window.getXMin())
				/ (window.getXMax() - window.getXMin())
				* (viewport.getXMax() - viewport.getXMin());
		double viewportY = (1 - ((y - window.getYMin()) / (window.getYMax() - window
				.getXMin()))) * (viewport.getYMax() - viewport.getYMin());
		return new Coordinate(viewportX, viewportY);
	}
	
	public void rotateClockwiseAroundOrigin(){
		double angle = 350;
		rotateAroundOrigin(angle);
	}
	
	public void rotateAntiClockwiseAroundOrigin(){
		double angle = 10;
		rotateAroundOrigin(angle);
	}
	
	private void rotateAroundOrigin(double angle){
		double cos = Math.cos(Math.toRadians(angle));
		double sin = Math.sin(Math.toRadians(angle));
		double temp_x = x * cos - y * sin;
		double temp_y = x * sin + y * cos;
		x = temp_x;
		y = temp_y;
	}
	
	public void transform(double[][] transformationMatrix){
		double[][] coordinateMatrix = this.matrix();
		
		double[][] transformedCoordinateMatrix = Matrix.multiplyMatrix(coordinateMatrix, transformationMatrix);
		
		x = transformedCoordinateMatrix[0][0];
		y = transformedCoordinateMatrix[0][1];
	}
	
	private double[][] matrix(){
		double[][] coordinateMatrix = new double[1][3];
		coordinateMatrix[0][0] = x;
		coordinateMatrix[0][1] = y;
		coordinateMatrix[0][2] = 1;
		return coordinateMatrix;
	}

}
