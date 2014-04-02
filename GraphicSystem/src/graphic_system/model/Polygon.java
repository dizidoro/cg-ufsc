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
	public Polygon getWindowViewportTransformation(Window window, Viewport viewport) {
		List<Coordinate> viewportVertices = new ArrayList<Coordinate>();
		for(Coordinate vertice : vertices){
			viewportVertices.add(vertice.getWindowViewportTransformation(window, viewport));
		}
		return new Polygon(this.getName(), viewportVertices);
	}
	
	

}
