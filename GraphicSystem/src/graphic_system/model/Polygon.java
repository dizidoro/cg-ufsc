package graphic_system.model;

import graphic_system.ApplicationConfig;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Polygon extends Geometry {

	private static int polygonCount = 1;

	List<Coordinate> vertices;

	public Polygon(List<Coordinate> vertices, Color color) {
		super("polygon" + polygonCount, Geometry.Type.POLYGON, color);

		this.vertices = vertices;
		polygonCount++;
	}

	private Polygon(String name, List<Coordinate> vertices, Color color) {
		super(name, Geometry.Type.POLYGON, color);
		this.vertices = vertices;
	}

	public List<Coordinate> getVertices() {
		return vertices;
	}

	@Override
	public Polygon getWindowViewportTransformation(Window window,
			Viewport viewport) {
		List<Coordinate> viewportVertices = new ArrayList<Coordinate>();
		for (Coordinate vertice : vertices) {
			viewportVertices.add(vertice.getWindowViewportTransformation(
					window, viewport));
		}
		return new Polygon(this.getName(), viewportVertices, this.getColor());
	}

	@Override
	public Coordinate getCenter() {
		double x_sum = 0;
		double y_sum = 0;

		for (Coordinate vertice : vertices) {
			x_sum += vertice.getX();
			y_sum += vertice.getY();
		}

		final double x = x_sum / vertices.size();
		final double y = y_sum / vertices.size();

		return new Coordinate(x, y);
	}

//	@Override
//	public void setCenter(Coordinate center) {
//		Coordinate oldCenter = getCenter();
//		double xDiff = center.getX() - oldCenter.getX();
//		double yDiff = center.getY() - oldCenter.getY();
//		for (Coordinate vertice : vertices) {
//			vertice.setX(vertice.getX() + xDiff);
//			vertice.setY(vertice.getY() + yDiff);
//		}
//	}
	
	@Override
	public void setCenter(Coordinate center) {
		Coordinate oldCenter = getCenter();
		double xDiff = center.getX() - oldCenter.getX();
		double yDiff = center.getY() - oldCenter.getY();
		
		List<Coordinate> transformedVertices = new ArrayList<>();
		for (Coordinate vertice : vertices) {
			double transformedX = vertice.getX() + xDiff;
			double transformedY = vertice.getY() + yDiff;
			Coordinate coordinate = new Coordinate(transformedX, transformedY);
			transformedVertices.add(coordinate);
		}
		vertices = transformedVertices;
	}

//	public double getArea() {
//		int i, j;
//		double area = 0;
//		int points = vertices.size();
//		for (i = 0; i < points; i++) {
//			j = (i + 1) % points;
//			area += vertices.get(i).getX() * vertices.get(j).getY();
//			area -= vertices.get(i).getY() * vertices.get(j).getX();
//		}
//		area /= 2.0;
//		return (Math.abs(area));
//	}

	@Override
	public void scaleLess() {
		Coordinate center = getCenter();
		setCenter(origin);

		double factor = 1 / ApplicationConfig.OBJECT_SCALE_FACTOR;
		for (int i = 0; i < vertices.size(); i++) {
			Coordinate coordinate = vertices.get(i);
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
		for (int i = 0; i < vertices.size(); i++) {
			Coordinate coordinate = vertices.get(i);
			coordinate.setX(coordinate.getX() * factor);
			coordinate.setY(coordinate.getY() * factor);
		}

		setCenter(center);
	}

	@Override
	protected void transform(double[][] matrix) {
		vertices = this.getTransformedVertices(matrix);
	}

	@Override
	public void transformSCN(double[][] matrix) {
		for (Coordinate vertice : vertices) {
			vertice.transformSCN(matrix);
		}
	}

	@Override
	public Geometry getWindowViewportTransformationSCN(Window window,
			Viewport viewport) {
		List<Coordinate> viewportVertices = new ArrayList<Coordinate>();
		for (Coordinate vertice : vertices) {
			viewportVertices.add(vertice.getWindowViewportTransformationSCN(
					window, viewport));
		}
		return new Polygon(this.getName(), viewportVertices, this.getColor());
	}

	@Override
	protected Geometry getTransformed(double[][] transformationMatrix) {
		List<Coordinate> transformedVertices = this.getTransformedVertices(transformationMatrix);
		return new Polygon(name, transformedVertices, color);
	}
	
	private List<Coordinate> getTransformedVertices(double[][] transformationMatrix){
		List<Coordinate> transformedVertices = new ArrayList<>();
		for (Coordinate vertice : vertices) {
			Coordinate transformedVertice = vertice.getTransformed(transformationMatrix);
			transformedVertices.add(transformedVertice);
		}
		return transformedVertices;
	}

}
