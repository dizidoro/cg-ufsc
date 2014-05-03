package graphic_system.model;

import graphic_system.ApplicationConfig;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Curve extends Geometry {

	private static int curveCount = 1;

	List<Coordinate> coordinates;

	public Curve(List<Coordinate> coordinates, Color color) {
		super("curve" + curveCount, Geometry.Type.CURVE, color);

		this.coordinates = coordinates;
		curveCount++;
	}

	private Curve(String name, List<Coordinate> coordinates, Color color) {
		super(name, Geometry.Type.CURVE, color);
		this.coordinates = coordinates;
	}

	@Override
	public Geometry getWindowViewportTransformation(Window window,
			Viewport viewport) {
		List<Coordinate> viewportCoordinates = new ArrayList<Coordinate>();
		for (Coordinate coordinate : coordinates) {
			viewportCoordinates.add(coordinate.getWindowViewportTransformation(
					window, viewport));
		}
		return new Curve(this.getName(), viewportCoordinates, this.getColor());
	}

	@Override
	public Coordinate getCenter() {
		double xSum = 0;
		double ySum = 0;

		for (Coordinate coordinate : coordinates) {
			xSum += coordinate.getX();
			ySum += coordinate.getY();
		}

		final double x = xSum / coordinates.size();
		final double y = ySum / coordinates.size();

		return new Coordinate(x, y);
	}

	@Override
	public void setCenter(Coordinate center) {
		Coordinate oldCenter = getCenter();
		double xDiff = center.getX() - oldCenter.getX();
		double yDiff = center.getY() - oldCenter.getY();

		List<Coordinate> transformedVertices = new ArrayList<>();
		for (Coordinate vertice : coordinates) {
			double transformedX = vertice.getX() + xDiff;
			double transformedY = vertice.getY() + yDiff;
			Coordinate coordinate = new Coordinate(transformedX, transformedY);
			transformedVertices.add(coordinate);
		}
		coordinates = transformedVertices;
	}

	@Override
	public void scaleLess() {
		Coordinate center = getCenter();
		setCenter(origin);

		double factor = 1 / ApplicationConfig.OBJECT_SCALE_FACTOR;
		for (int i = 0; i < coordinates.size(); i++) {
			Coordinate coordinate = coordinates.get(i);
			coordinate.setX(coordinate.getX() * factor);
			coordinate.setY(coordinate.getY() * factor);
		}
		setCenter(center);
	}

	@Override
	public void scalePlus() {
		// Movo para a origem e multiplico pelo fator de escala
		// Em seguida volto o objeto para o centro antigo
		Coordinate center = getCenter();
		setCenter(new Coordinate(0, 0));

		double factor = ApplicationConfig.OBJECT_SCALE_FACTOR;
		for (int i = 0; i < coordinates.size(); i++) {
			Coordinate coordinate = coordinates.get(i);
			coordinate.setX(coordinate.getX() * factor);
			coordinate.setY(coordinate.getY() * factor);
		}

		setCenter(center);
	}

	@Override
	protected void transform(double[][] matrix) {
		coordinates = this.getTransformedVertices(matrix);
	}

	private List<Coordinate> getTransformedVertices(
			double[][] transformationMatrix) {
		List<Coordinate> transformedVertices = new ArrayList<>();
		for (Coordinate vertice : coordinates) {
			Coordinate transformedVertice = vertice
					.getTransformed(transformationMatrix);
			transformedVertices.add(transformedVertice);
		}
		return transformedVertices;
	}

	@Override
	protected Geometry getTransformed(double[][] transformationMatrix) {
		List<Coordinate> transformedVertices = this
				.getTransformedVertices(transformationMatrix);
		return new Curve(name, transformedVertices, color);
	}

	@Override
	public void transformSCN(double[][] matrix) {
		for (Coordinate vertice : coordinates) {
			vertice.transformSCN(matrix);
		}
	}

	@Override
	public Geometry getWindowViewportTransformationSCN(Window window,
			Viewport viewport) {
		List<Coordinate> viewportVertices = new ArrayList<Coordinate>();
		for (Coordinate vertice : coordinates) {
			viewportVertices.add(vertice.getWindowViewportTransformationSCN(
					window, viewport));
		}
		return new Curve(this.getName(), viewportVertices, this.getColor());
	}

	@Override
	public Geometry getClipping(Window window) {
		boolean inner = false;
		for (Coordinate vertice : coordinates) {
			RegionCode code = vertice.getRegionCode(window);
			// TODO
			// Se tem um vértice dentro da window,
			// precisamos calcular o clipping
			if (code.all == 0) {
				inner = true;
				return this;
			}
		}
		return null;
	}

	public List<Coordinate> getCoordinates() {
		return coordinates;
	}

}
