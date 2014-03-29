package model;

import java.util.ArrayList;

public class Polygon extends Geometry {
	
	private static int polygonCount = 1;

	// Linha poligonal é uma sucessão de segmentos consecutivos e
	// não-colineares, dois a dois
	ArrayList<Coordinate> vertices;
	
	public Polygon(ArrayList<Coordinate> vertices) {
		super("polygon" + polygonCount, ObjectType.POLYGON);

		this.vertices = vertices;
		
		polygonCount++;
	}

	public ArrayList<Coordinate> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Coordinate> vertices) {
		this.vertices = vertices;
	}
	
}
