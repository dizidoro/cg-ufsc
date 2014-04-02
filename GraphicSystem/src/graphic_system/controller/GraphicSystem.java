package graphic_system.controller;

import graphic_system.ApplicationConfig;
import graphic_system.model.Coordinate;
import graphic_system.model.DisplayFile;
import graphic_system.model.Dot;
import graphic_system.model.Geometry;
import graphic_system.model.Line;
import graphic_system.model.Polygon;
import graphic_system.model.Viewport;
import graphic_system.model.Window;

import java.util.List;

//public class GraphicSystem implements IGraphicSystem{
public class GraphicSystem {
	
	private final Viewport viewport;
	private final Window window;
	private final DisplayFile displayFile;
	
	public GraphicSystem(){
		double xMin = ApplicationConfig.initXMin;
		double yMin = ApplicationConfig.initYMin;
		double xMax = ApplicationConfig.initXMax; 
		double yMax = ApplicationConfig.initYMax;
		
		window = new Window(xMin, yMin, xMax, yMax);
		viewport = new Viewport(xMin, yMin, xMax, yMax);
		displayFile = new DisplayFile();
	}
	
	public void addNewObject(Geometry object){
		Geometry transformed = object.getWindowViewportTransformation(window, viewport);
		displayFile.add(transformed);
	}
	
	public void zoomIn(){
		window.zoomIn();
	}
	
	public void zoomOut(){
		window.zoomOut();
	}
	
	public void moveUp(){
		window.moveUp();
	}
	
	public void moveDown(){
		window.moveDown();
	}
	
	public void moveRight(){
		window.moveRight();
	}
	
	public void moveLeft(){
		window.moveLeft();
	}
	
	public List<Geometry> getObjects(){
		return displayFile.getViewportDisplayFile(window, viewport).getObjects();
	}
	
}
