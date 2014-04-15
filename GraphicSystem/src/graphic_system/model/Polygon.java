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
		int points = vertices.size();
		Coordinate[] polygon = new Coordinate[points];

		for (int q = 0; q < points; q++) {
			polygon[q] = new Coordinate(vertices.get(q).getX(), vertices.get(q)
					.getY());
		}

		double cx = 0, cy = 0;
		double A = getArea();
		int i, j;

		double factor = 0;
		for (i = 0; i < points; i++) {
			j = (i + 1) % points;
			factor = (polygon[i].getX() * polygon[j].getY() - polygon[j].getX()
					* polygon[i].getY());
			cx += (polygon[i].getX() + polygon[j].getX()) * factor;
			cy += (polygon[i].getY() + polygon[j].getY()) * factor;
		}

		factor = 1.0 / (6.0 * A);
		cx *= factor;
		cy *= factor;
		return new Coordinate((int) Math.abs(Math.round(cx)),
				(int) Math.abs(Math.round(cy)));
	}

	@Override
	public void setCenter(Coordinate center) {
		Coordinate oldCenter = getCenter();
		double xDiff = center.getX() - oldCenter.getX();
		double yDiff = center.getY() - oldCenter.getY();
		for (int i = 0; i < vertices.size(); i++) {
			Coordinate coordinate = vertices.get(i);
			coordinate.setX(coordinate.getX() + xDiff);
			coordinate.setY(coordinate.getY() + yDiff);
		}
	}

	public double getArea() {
		int i, j;
		double area = 0;
		int points = vertices.size();
		for (i = 0; i < points; i++) {
			j = (i + 1) % points;
			area += vertices.get(i).getX() * vertices.get(j).getY();
			area -= vertices.get(i).getY() * vertices.get(j).getX();
		}
		area /= 2.0;
		return (Math.abs(area));
	}

	@Override
	public void scaleLess() {
		// Movo para a origem e multiplico pelo fator de escala
		// Em seguida volto o objeto para o centro antigo
		Coordinate center = getCenter();
		setCenter(new Coordinate(0, 0));

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
	public void rotateClockwiseAroundOrigin() {
		for(Coordinate vertice : vertices){
			vertice.rotateClockwiseAroundOrigin();
		}

	}
	
	@Override
	public void rotateAntiClockwiseAroundOrigin() {
		for(Coordinate vertice : vertices){
			vertice.rotateAntiClockwiseAroundOrigin();
		}
	}

	@Override
	public void rotateClockwiseAroundCenter() {
		Coordinate center = this.getCenter();
		this.rotateClockwiseAroundPoint(center);
	}
	
	@Override
	public void rotateAntiClockwiseAroundCenter() {
		Coordinate center = this.getCenter();
		this.rotateAntiClockwiseAroundPoint(center);
	}
	
	@Override
	public void rotateClockwiseAroundPoint(Coordinate coordinate) {
		double[][] rotationMatrix = getClockwiseRotationMatrix(coordinate);
		this.transform(rotationMatrix);
	}

	@Override
	public void rotateAntiClockwiseAroundPoint(Coordinate coordinate) {
		double[][] rotationMatrix = getAntiClockwiseRotationMatrix(coordinate);
		this.transform(rotationMatrix);
	}
	
	private void transform(double[][] matrix){
		for(Coordinate vertice : vertices){
			vertice.transform(matrix);
		}
	}

}
