package graphic_system.model.raster;

public class EdgeList {

	// 0 é esquerda X, 1 é esquerda Z
	// 2 é direita X, 3 é direita Z
	public double[] coordinates = new double[4];

	public EdgeList(double x, double z) {
		coordinates[0] = x;
		coordinates[1] = z;
	}

	public void add(double x, double z) {
		if (x < coordinates[0]) {
			coordinates[2] = coordinates[0];
			coordinates[3] = coordinates[1];
			coordinates[0] = x;
			coordinates[1] = z;
		} else {
			coordinates[2] = x;
			coordinates[3] = z;
		}
	}

	public double getLeftX() {
		return coordinates[0];
	}

	public double getLeftZ() {
		return coordinates[1];
	}

	public double getRightX() {
		return coordinates[2];
	}

	public double getRightZ() {
		return coordinates[3];
	}

}
