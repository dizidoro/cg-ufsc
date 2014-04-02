package graphic_system.model;

public class Coordinate {
	
	private final double x;
	private final double y;
	private final double z;
	
	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Coordinate(double x, double y){
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

	public double getZ() {
		return z;
	}
	
	public Coordinate getWindowViewportTransformation(Window window, Viewport viewport){
		double viewportX = (x - window.getXMin())/(window.getXMax() - window.getXMin()) * (viewport.getXMax() - viewport.getXMin());
		double viewportY = (1 - ((y - window.getYMin())/(window.getYMax() - window.getXMin()))) * (viewport.getYMax() - viewport.getYMin());
		return new Coordinate(viewportX, viewportY);
	}

}
