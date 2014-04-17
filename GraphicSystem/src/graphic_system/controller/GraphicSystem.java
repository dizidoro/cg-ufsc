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
		Geometry transformed = object.getWindowViewportTransformation(window,
				viewport);
		displayFile.add(transformed);
	}

	@Override
	public void zoomIn() {
		window.zoomIn();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void zoomOut() {
		window.zoomOut();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void moveUp() {
		window.moveUp();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void moveDown() {
		window.moveDown();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void moveRight() {
		window.moveRight();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void moveLeft() {
		window.moveLeft();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	public List<Geometry> getObjects() {
		return displayFile.getViewportDisplayFile(window, viewport)
				.getObjects();
	}

	@Override
	public void getCenter(String objectName) {
		Geometry object = displayFile.getObject(objectName);
		Coordinate center = object.getCenter();
		this.gui.fill(Integer.toString((int) center.getX()),
				Double.toString((int) center.getY()));
	}

	@Override
	public void setCenter(String objectName, String x, String y) {
		Geometry object = displayFile.getObject(objectName);
		Coordinate center = new Coordinate(Double.parseDouble(x),
				Double.parseDouble(y));
		object.setCenter(center);
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void scaleLess(String objectName) {
		Geometry object = displayFile.getObject(objectName);
		object.scaleLess();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void scalePlus(String objectName) {
		Geometry object = displayFile.getObject(objectName);
		object.scalePlus();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());

	}

	@Override
	public void rotateClockwiseAroundOrigin(String objectName) {
		Geometry object = displayFile.getObject(objectName);
		object.rotateClockwiseAroundOrigin();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void rotateAntiClockwiseAroundOrigin(String objectName) {
		Geometry object = displayFile.getObject(objectName);
		object.rotateAntiClockwiseAroundOrigin();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void rotateClockwiseAroundCenter(String objectName) {
		Geometry object = displayFile.getObject(objectName);
		object.rotateClockwiseAroundCenter();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void rotateAntiClockwiseAroundCenter(String objectName) {
		Geometry object = displayFile.getObject(objectName);
		object.rotateAntiClockwiseAroundCenter();
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void rotateClockwiseAroundPoint(String objectName, Coordinate dot) {
		Geometry object = displayFile.getObject(objectName);
		object.rotateClockwiseAroundPoint(dot);
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

	@Override
	public void rotateAntiClockwiseAroundPoint(String objectName, Coordinate dot) {
		Geometry object = displayFile.getObject(objectName);
		object.rotateAntiClockwiseAroundPoint(dot);
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

//	@Override
//	public void rotateWindowRight() {
//		// Rotaciona mundo em torno do ponto que �� centro da window
//		displayFile.rotateRight(window.getCenter());
//		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
//				.getObjects());
//
//	}
//
//	@Override
//	public void rotateWindowLeft() {
//		// Rotaciona mundo em torno do ponto que �� centro da window
//		displayFile.rotateLeft(window.getCenter());
//		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
//				.getObjects());
//	}
	
	@Override
	public void rotateWindowRight() {
		// Rotaciona mundo em torno do ponto que �� centro da window
		DisplayFile rotatedWorld = displayFile.getRotated(window.getCenter(), window.getAngle());
		gui.redraw(rotatedWorld.getViewportDisplayFile(window, viewport)
				.getObjects());

	}

	@Override
	public void rotateWindowLeft() {
		// Rotaciona mundo em torno do ponto que �� centro da window
		displayFile.rotateLeft(window.getCenter());
		gui.redraw(displayFile.getViewportDisplayFile(window, viewport)
				.getObjects());
	}

}
