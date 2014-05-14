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
		double xSum = 0;
		double ySum = 0;

		for (Coordinate vertice : vertices) {
			ySum += vertice.getX();
			ySum += vertice.getY();
		}

		final double x = ySum / vertices.size();
		final double y = ySum / vertices.size();

		return new Coordinate(x, y);
	}


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
	protected Geometry getTransformed(double[][] transformationMatrix) {
		List<Coordinate> transformedVertices = this
				.getTransformedVertices(transformationMatrix);
		return new Polygon(name, transformedVertices, color);
	}

	private List<Coordinate> getTransformedVertices(
			double[][] transformationMatrix) {
		List<Coordinate> transformedVertices = new ArrayList<>();
		for (Coordinate vertice : vertices) {
			Coordinate transformedVertice = vertice
					.getTransformed(transformationMatrix);
			transformedVertices.add(transformedVertice);
		}
		return transformedVertices;
	}

//	@Override
//	public Geometry getClipping(Window window) {
//		boolean inner = false;
//		for (Coordinate vertice : vertices) {
//			RegionCode code = vertice.getRegionCode(window);
//			// TODO
//			// Se tem um vértice dentro da window,
//			// precisamos calcular o clipping
//			if (code.all == 0) {
//				inner = true;
//				return this;
//			}
//		}
//		return null;
//	}
	
	@Override
	public Geometry getClipping(Window window) {
		//falta dividir em duas geometrias
		Coordinate previousVertice = null;
		List<Coordinate> clippedVertices = new ArrayList<>();
		
		for(Coordinate vertice : vertices){
			if(previousVertice == null){
				previousVertice = vertice;
				continue;
			}
			
			clipAndAdd(previousVertice, vertice, window, clippedVertices);

			previousVertice = vertice;
		}
		
		Coordinate vertice = vertices.get(0);
		clipAndAdd(previousVertice, vertice, window, clippedVertices);
		
		return new Polygon(name, clippedVertices, color);
	}
	
	private void clipAndAdd(Coordinate a, Coordinate b, Window window, List<Coordinate> clippedVertices){
		RegionCode codeA = a.getRegionCode(window);
		RegionCode codeB = b.getRegionCode(window);
		
		if(codeA.isInsideWindow() && codeB.isInsideWindow()){
			clippedVertices.add(b);
		} else if(codeA.isInsideWindow() && !codeB.isInsideWindow()){
			Line line = new Line(a, b);
			Line clippedLine = line.getClipping(window);
			Coordinate clippedVertice = line.getB();
			clippedVertices.add(clippedVertice);
		} else if(!codeA.isInsideWindow() && codeB.isInsideWindow()) {
			Line line = new Line(a, b);
			Line clippedLine = line.getClipping(window);
			Coordinate clippedVertice = line.getA();
			clippedVertices.add(clippedVertice);
			clippedVertices.add(b);				
		}
	}


}
