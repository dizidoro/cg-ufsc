package view;

import java.awt.List;

import model.Coordinate;

public interface IGraphicController {
	
	
	String createPolygon(List coordinates);

	String createDot(Coordinate coordinate);

	String createLine(Coordinate a, Coordinate b);
	
}
