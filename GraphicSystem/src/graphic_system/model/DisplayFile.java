package graphic_system.model;

import java.util.ArrayList;
import java.util.List;

public class DisplayFile {

	private List<Geometry> objects;

	public DisplayFile() {
		this.objects = new ArrayList<Geometry>();
	}
	
	private DisplayFile(List<Geometry> objects){
		this.objects = objects;
	}

	public List<Geometry> getObjects() {
		return objects;
	}

	public void add(Geometry object) {
		objects.add(object);
	}

	public DisplayFile getViewportDisplayFile(Window window, Viewport viewport) {
		DisplayFile viewportDisplayFile = new DisplayFile();
		for (Geometry object : objects) {
			Geometry viewportObject = object.getWindowViewportTransformation(
					window, viewport);
			viewportDisplayFile.add(viewportObject);
		}
		return viewportDisplayFile;
	}

	public Geometry getObject(String selected) {
		for (Geometry object : objects) {
			if (object.getName() == selected) {
				return object;
			}
		}
		return null;
	}

	public DisplayFile getRotated(Coordinate coordinate, double angle) {
		List<Geometry> rotatedObjects = new ArrayList<>();
		for (Geometry object : objects) {
			Geometry rotatedObject = object.getRotatedAroundPoint(coordinate, angle);
			rotatedObjects.add(rotatedObject);
		}
		return new DisplayFile(rotatedObjects);
	}

	public void scaleLess(String objectName) {
		Geometry object = getObject(objectName);
		object.scaleLess();
	}

	public void scalePlus(String objectName) {
		Geometry object = getObject(objectName);
		object.scalePlus();
	}

	public void rotateClockwiseAroundOrigin(String objectName) {
		Geometry object = getObject(objectName);
		object.rotateClockwiseAroundOrigin();
	}

	public void rotateAntiClockwiseAroundOrigin(String objectName) {
		Geometry object = getObject(objectName);
		object.rotateAntiClockwiseAroundOrigin();
	}

	public void rotateClockwiseAroundCenter(String objectName) {
		Geometry object = getObject(objectName);
		object.rotateClockwiseAroundCenter();
	}

	public void rotateAntiClockwiseAroundCenter(String objectName) {
		Geometry object = getObject(objectName);
		object.rotateAntiClockwiseAroundCenter();
	}

	public void rotateClockwiseAroundPoint(String objectName, Coordinate dot) {
		Geometry object = getObject(objectName);
		object.rotateClockwiseAroundPoint(dot);
	}

	public void rotateAntiClockwiseAroundPoint(String objectName, Coordinate dot) {
		Geometry object = getObject(objectName);
		object.rotateAntiClockwiseAroundPoint(dot);
	}
	
	public Coordinate getCenter(String objectName) {
		Geometry object = getObject(objectName);
		Coordinate center = object.getCenter();
		return center;
	}
	
	public void setCenter(String objectName, double x, double y) {
		Geometry object = getObject(objectName);
		Coordinate center = new Coordinate(x,y);
		object.setCenter(center);
	}
}
