package graphic_system.model.raster;

import graphic_system.ApplicationConfig;
import graphic_system.model.RegionCode;
import graphic_system.model.Viewport;
import graphic_system.model.Window;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Triangulo3D extends Geometry3D {

	private static int polygonCount = 1;

	List<Coordinate3D> vertices;

	private boolean facing;
	private Coordinate3D normal;
	private Bounds bounds;

	private Color reflectivity;

	public Triangulo3D(List<Coordinate3D> vertices, Color color) {
		super("polygon" + polygonCount, Geometry3D.Type.POLYGON, color);

		this.vertices = vertices;

		// TODO:
		reflectivity = new Color(100, 100, 100);

		calculateNormal();

		polygonCount++;
	}

	private Triangulo3D(String name, List<Coordinate3D> vertices, Color color) {
		super(name, Geometry3D.Type.POLYGON, color);
		this.vertices = vertices;
	}

	public List<Coordinate3D> getVertices() {
		return vertices;
	}

	public void setReflectivity(Color color) {
		reflectivity = color;
	}

	public void shading(Coordinate3D lightSource, double ambientLight) {
		double reflect = ambientLight;
		if (normal.cos(lightSource) > 0) {
			reflect = ambientLight + normal.cos(lightSource);
		}

		int r = checkColourRange((int) (reflectivity.getRed() * reflect));
		int g = checkColourRange((int) (reflectivity.getGreen() * reflect));
		int b = checkColourRange((int) (reflectivity.getBlue() * reflect));

		setColor(new Color(r, g, b));
	}

	private int checkColourRange(int x) {
		if (x <= 0) {
			x = 0;
		}
		if (x >= 255) {
			x = 255;
		}
		return x;
	}

	private void calculateNormal() {
		normal = ((vertices.get(1).minus(vertices.get(0)))
				.crossProduct(vertices.get(2).minus(vertices.get(1))))
				.unitVector();
		if (normal.getZ() > 0) {
			facing = false;
		} else {
			facing = true;
		}
	}

	public boolean facing() {
		return facing;
	}

	private void calculateBounds() {
		double minX = Math.min(
				Math.min(vertices.get(0).getX(), vertices.get(1).getX()),
				vertices.get(2).getX());
		double minY = Math.min(
				Math.min(vertices.get(0).getY(), vertices.get(1).getY()),
				vertices.get(2).getY());
		double maxX = Math.max(
				Math.max(vertices.get(0).getX(), vertices.get(1).getX()),
				vertices.get(2).getX());
		double maxY = Math.max(
				Math.max(vertices.get(0).getY(), vertices.get(1).getY()),
				vertices.get(2).getY());
		bounds = new Bounds(minX, minY, (maxX - minX), (maxY - minY));
	}

	public EdgeList[] edgeList() {
		calculateBounds();
		EdgeList[] edge = new EdgeList[(int) (bounds.getHeight() + 1)];

		for (int i = 0; i < 3; i++) {
			Coordinate3D va = vertices.get(i);
			Coordinate3D vb = vertices.get((i + 1) % 3);

			if (va.getY() > vb.getY()) {
				vb = va;
				va = vertices.get((i + 1) % 3);
			}

			double mx = (vb.getX() - va.getX()) / (vb.getY() - va.getY());
			double mz = (vb.getZ() - va.getZ()) / (vb.getZ() - va.getZ());
			double x = va.getX();
			double z = va.getZ();

			int j = (int) (Math.round(va.getY()) - Math.round(bounds.getY()));
			int maxj = (int) (Math.round(vb.getY()) - Math.round(bounds.getY()));

			while (j < maxj) {
				if (edge[j] == null) {
					edge[j] = new EdgeList(x, z);
				} else {
					edge[j].add(x, z);

				}
				j++;
				x += mx;
				z += mz;
			}
		}
		return edge;
	}

	public int getMinX() {
		return (int) Math.round(bounds.getY());
	}

	public int getMinY() {
		return (int) Math.round(bounds.getY());
	}

	@Override
	public Triangulo3D getWindowViewportTransformation(Window window,
			Viewport viewport) {
		List<Coordinate3D> viewportVertices = new ArrayList<Coordinate3D>();
		for (Coordinate3D vertice : vertices) {
			viewportVertices.add(vertice.getWindowViewportTransformation(
					window, viewport));
		}
		return new Triangulo3D(this.getName(), viewportVertices,
				this.getColor());
	}

	@Override
	public Coordinate3D getCenter() {
		double xSum = 0;
		double ySum = 0;

		for (Coordinate3D vertice : vertices) {
			ySum += vertice.getX();
			ySum += vertice.getY();
		}

		final double x = ySum / vertices.size();
		final double y = ySum / vertices.size();

		return new Coordinate3D(x, y, 0);
	}

	@Override
	public void setCenter(Coordinate3D center) {
		Coordinate3D oldCenter = getCenter();
		double xDiff = center.getX() - oldCenter.getX();
		double yDiff = center.getY() - oldCenter.getY();

		List<Coordinate3D> transformedVertices = new ArrayList<>();
		for (Coordinate3D vertice : vertices) {
			double transformedX = vertice.getX() + xDiff;
			double transformedY = vertice.getY() + yDiff;
			Coordinate3D coordinate = new Coordinate3D(transformedX,
					transformedY, 0);
			transformedVertices.add(coordinate);
		}
		vertices = transformedVertices;
	}

	// public double getArea() {
	// int i, j;
	// double area = 0;
	// int points = vertices.size();
	// for (i = 0; i < points; i++) {
	// j = (i + 1) % points;
	// area += vertices.get(i).getX() * vertices.get(j).getY();
	// area -= vertices.get(i).getY() * vertices.get(j).getX();
	// }
	// area /= 2.0;
	// return (Math.abs(area));
	// }

	@Override
	public void scaleLess() {
		Coordinate3D center = getCenter();
		setCenter(origin);

		double factor = 1 / ApplicationConfig.OBJECT_SCALE_FACTOR;
		for (int i = 0; i < vertices.size(); i++) {
			Coordinate3D coordinate = vertices.get(i);
			coordinate.setX(coordinate.getX() * factor);
			coordinate.setY(coordinate.getY() * factor);
		}
		setCenter(center);
	}

	@Override
	public void scalePlus() {
		// Movo para a origem e multiplico pelo fator de escala
		// Em seguida volto o objeto para o centro antigo
		Coordinate3D center = getCenter();
		setCenter(new Coordinate3D(0, 0, 0));

		double factor = ApplicationConfig.OBJECT_SCALE_FACTOR;
		for (int i = 0; i < vertices.size(); i++) {
			Coordinate3D coordinate = vertices.get(i);
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
		for (Coordinate3D vertice : vertices) {
			vertice.transformSCN(matrix);
		}
	}

	@Override
	public Geometry3D getWindowViewportTransformationSCN(Window window,
			Viewport viewport) {
		List<Coordinate3D> viewportVertices = new ArrayList<Coordinate3D>();
		for (Coordinate3D vertice : vertices) {
			viewportVertices.add(vertice.getWindowViewportTransformationSCN(
					window, viewport));
		}
		return new Triangulo3D(this.getName(), viewportVertices,
				this.getColor());
	}

	@Override
	protected Geometry3D getTransformed(double[][] transformationMatrix) {
		List<Coordinate3D> transformedVertices = this
				.getTransformedVertices(transformationMatrix);
		return new Triangulo3D(name, transformedVertices, color);
	}

	private List<Coordinate3D> getTransformedVertices(
			double[][] transformationMatrix) {
		List<Coordinate3D> transformedVertices = new ArrayList<>();
		for (Coordinate3D vertice : vertices) {
			Coordinate3D transformedVertice = vertice
					.getTransformed(transformationMatrix);
			transformedVertices.add(transformedVertice);
		}
		return transformedVertices;
	}

	@Override
	public Geometry3D getClipping(Window window) {
		boolean inner = false;
		for (Coordinate3D vertice : vertices) {
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

}
