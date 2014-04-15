package graphic_system.model;

import java.awt.Color;

public class Dot extends Geometry {

	private static int dotCount = 1;

	private Coordinate coordinate;

	public Dot(Coordinate coordinate, Color color) {
		super("dot" + dotCount, Geometry.Type.POINT, color);

		this.coordinate = coordinate;

		dotCount++;
	}

	private Dot(String name, Coordinate coordinate, Color color) {
		super(name, Geometry.Type.POINT, color);
		this.coordinate = coordinate;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	@Override
	public Dot getWindowViewportTransformation(Window window, Viewport viewport) {
		Coordinate viewportCoordinate = coordinate
				.getWindowViewportTransformation(window, viewport);
		return new Dot(this.getName(), viewportCoordinate, this.getColor());
	}

	@Override
	public Coordinate getCenter() {
		return coordinate;
	}

	@Override
	public void setCenter(Coordinate center) {
		this.coordinate = center;
	}

	@Override
	public void scaleLess() {
		// Um ponto escalado �� ele mesmo
	}

	@Override
	public void scalePlus() {
		// Um ponto escalado �� ele mesmo
	}

	@Override
	public void rotateClockwiseAroundCenter() {
		// Um ponto rotacionado sobre ele mesmo fica no mesmo ponto
		// Nada pra fazer aqui
	}
	
	@Override
	public void rotateAntiClockwiseAroundCenter() {
		// Um ponto rotacionado sobre ele mesmo fica no mesmo ponto
		// Nada pra fazer aqui
	}

	@Override
	public void rotateClockwiseAroundOrigin() {
		coordinate.rotateClockwiseAroundOrigin();
	}
	
	public void rotateAntiClockwiseAroundOrigin(){
		coordinate.rotateAntiClockwiseAroundOrigin();
	}

	@Override
	public void rotateClockwiseAroundPoint(Coordinate rotationCoordinate) {
		double[][] rotationMatrix = getClockwiseRotationMatrix(rotationCoordinate);
		coordinate.transform(rotationMatrix);
	}

	@Override
	public void rotateAntiClockwiseAroundPoint(Coordinate rotationCoordinate) {
		double[][] rotationMatrix = getAntiClockwiseRotationMatrix(rotationCoordinate);
		coordinate.transform(rotationMatrix);
	}



}
