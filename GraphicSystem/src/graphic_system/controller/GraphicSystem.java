package graphic_system.controller;

import graphic_system.ApplicationConfig;
import graphic_system.model.Coordinate;
import graphic_system.model.DisplayFile;
import graphic_system.model.Geometry;
import graphic_system.model.Viewport;
import graphic_system.model.Window;
import graphic_system.view.IGraphicSystem;
import graphic_system.view.Layout;

import java.util.List;

public class GraphicSystem implements IGraphicSystem {

	private final Viewport viewport;
	private final Window window;
	private final DisplayFile displayFile;
	private final Layout gui;

	public GraphicSystem(Layout gui) {
		double xMin = ApplicationConfig.initXMin;
		double yMin = ApplicationConfig.initYMin;
		double xMax = ApplicationConfig.initXMax;
		double yMax = ApplicationConfig.initYMax;

		window = new Window(xMin, yMin, xMax, yMax);
		viewport = new Viewport(xMin, yMin, xMax, yMax);
		displayFile = new DisplayFile();
		this.gui = gui;
		this.gui.addListenerController(this);
	}

	@Override
	public void addNewObject(Geometry object) {
		Geometry transformed = object.getWindowViewportTransformation(window,viewport);
		displayFile.add(transformed);
	}

	@Override
	public void zoomIn() {
		window.zoomIn();
		redraw();
	}

	@Override
	public void zoomOut() {
		window.zoomOut();
		redraw();
	}

	@Override
	public void moveUp() {
		window.moveUp();
		redraw();
	}

	@Override
	public void moveDown() {
		window.moveDown();
		redraw();
	}

	@Override
	public void moveRight() {
		window.moveRight();
		redraw();
	}

	@Override
	public void moveLeft() {
		window.moveLeft();
		redraw();
	}

	@Override
	public void getCenter(String objectName) {
		Coordinate center = displayFile.getCenter(objectName);
		gui.fill(Integer.toString((int) center.getX()),
				Double.toString((int) center.getY()));
	}

	@Override
	public void setCenter(String objectName, String str_x, String str_y) {
		double x = Double.parseDouble(str_x);
		double y = Double.parseDouble(str_y);
		displayFile.setCenter(objectName, x, y);
		redraw();
	}

	@Override
	public void scaleLess(String objectName) {
		displayFile.scaleLess(objectName);
		redraw();
	}

	@Override
	public void scalePlus(String objectName) {
		displayFile.scalePlus(objectName);
		redraw();
	}

	@Override
	public void rotateClockwiseAroundOrigin(String objectName) {
		displayFile.rotateClockwiseAroundOrigin(objectName);
		redraw();
	}

	@Override
	public void rotateAntiClockwiseAroundOrigin(String objectName) {
		displayFile.rotateAntiClockwiseAroundOrigin(objectName);
		redraw();
	}

	@Override
	public void rotateClockwiseAroundCenter(String objectName) {
		displayFile.rotateClockwiseAroundCenter(objectName);
		redraw();
	}

	@Override
	public void rotateAntiClockwiseAroundCenter(String objectName) {
		displayFile.rotateAntiClockwiseAroundCenter(objectName);
		redraw();
	}

	@Override
	public void rotateClockwiseAroundPoint(String objectName, Coordinate dot) {
		displayFile.rotateClockwiseAroundPoint(objectName, dot);
		redraw();
	}

	@Override
	public void rotateAntiClockwiseAroundPoint(String objectName, Coordinate dot) {
		displayFile.rotateAntiClockwiseAroundPoint(objectName, dot);
		redraw();
	}
	
	@Override
	public void rotateWindowRight() {
		window.rotateClockwise();
		redraw();

	}

	@Override
	public void rotateWindowLeft() {
		window.rotateAntiClockwise();
		redraw();
	}
	
	private void redraw(){
		DisplayFile rotatedWorld = displayFile.getRotated(window.getCenter(), window.getAngle());
		DisplayFile viewportDisplayFile = rotatedWorld.getViewportDisplayFile(window, viewport);
		List<Geometry> objects = viewportDisplayFile.getObjects();
		gui.redraw(objects);
	}
}
