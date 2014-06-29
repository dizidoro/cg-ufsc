package graphic_system.model.raster;

public class Bounds {
	private double x;
	private double y;
	private double width;
	private double height;

	public Bounds(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
