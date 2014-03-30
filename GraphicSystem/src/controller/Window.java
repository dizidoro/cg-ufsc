package controller;

public class Window {
	
	private double xMin, yMin, xMax, yMax, diagonal;
	
	Window(double xMin, double yMin, double xMax, double yMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		
		// diagonal = UtilGraphicSystem.getDistance2Points(xMin, yMin, xMax, yMax);
	}
	
	public void moveUp() {
		// Recálculo posição dos elementos da Display File
		System.out.println("moveUp in");
	}

	public double getXMin() {
		return xMin;
	}

	public void setXMin(double xMin) {
		this.xMin = xMin;
	}

	public double getYMin() {
		return yMin;
	}

	public void setYMin(double yMin) {
		this.yMin = yMin;
	}

	public double getXMax() {
		return xMax;
	}

	public void setXMax(double xMax) {
		this.xMax = xMax;
	}

	public double getYMax() {
		return yMax;
	}

	public void setYMax(double yMax) {
		this.yMax = yMax;
	}

	public double moveUp(double factor) {
		// TODO
		
		// Isso é só um teste
		yMin += factor;
		yMax += factor;
		return factor;
	}
}
