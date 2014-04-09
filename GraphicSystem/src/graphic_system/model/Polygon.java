package graphic_system.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon extends Geometry {

	private static int polygonCount = 1;

	List<Coordinate> vertices;

	public Polygon(List<Coordinate> vertices) {
		super("polygon" + polygonCount, Geometry.Type.POLYGON);

		this.vertices = vertices;
		polygonCount++;
	}

	private Polygon(String name, List<Coordinate> vertices) {
		super(name, Geometry.Type.POLYGON);
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
		return new Polygon(this.getName(), viewportVertices);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scalePlus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void objectRotation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void worldRotation(Window window, Viewport viewport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dotRatation(Coordinate dot) {
		// TODO Auto-generated method stub
		
	}

}
