package graphic_system.model;

import java.awt.Color;

import math.Matrix;

public abstract class Geometry {

	private final String name;
	private final Type type;
	private Color color;

	public Geometry(String name, Type type, Color color) {
		this.name = name;
		this.type = type;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public abstract Geometry getWindowViewportTransformation(Window window,
			Viewport viewport);

	public enum Type {
		POINT, LINE, POLYGON
	}

	public abstract Coordinate getCenter();

	public abstract void setCenter(Coordinate center);

	public abstract void scaleLess();

	public abstract void scalePlus();

	public abstract void rotateClockwiseAroundCenter();
	
	public abstract void rotateAntiClockwiseAroundCenter();
	
	public abstract void rotateClockwiseAroundOrigin();
	
	public abstract void rotateAntiClockwiseAroundOrigin();

	public abstract void rotateClockwiseAroundPoint(Coordinate coordinate);
	
	public abstract void rotateAntiClockwiseAroundPoint(Coordinate coordinate);
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	protected static double[][] getClockwiseRotationMatrix(Coordinate coordinate){
		return getRotationMatrix(coordinate, 10);
	}
	
	protected static double[][] getAntiClockwiseRotationMatrix(Coordinate coordinate){
		return getRotationMatrix(coordinate, 350);
	}
	
	private static double[][] getRotationMatrix(Coordinate coordinate, double degree){
		double[][] moveToCenter = {
				{1,0,0},
				{0,1,0},
				{-coordinate.getX(),-coordinate.getY(), 1}
		};

		
		double[][] returnFromCenter = {
				{1,0,0},
				{0,1,0},
				{coordinate.getX(),coordinate.getY(), 1}
		};
		
		double cos = Math.cos(Math.toRadians(degree));
		double sin = Math.sin(Math.toRadians(degree));
//		System.out.println("cos: " + cos);
//		System.out.println("sin: " + sin);
		
		double[][] rotation = {
				{cos,-sin,0},
				{sin,cos,0},
				{0,0,1}
		};
		double[][] moveAndRotate = Matrix.multiplyMatrix(moveToCenter, rotation);
	
		double[][] rotationMatrix = Matrix.multiplyMatrix(moveAndRotate, returnFromCenter);
		
//		System.out.println();
//		System.out.println("Rotation matrix");
//		Matrix.printMatrix(rotationMatrix);
		
		return rotationMatrix;
	}

}
