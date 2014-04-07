package graphic_system.model;

import java.util.ArrayList;
import java.util.List;

public class DisplayFile {

	private List<Geometry> objects;

	public DisplayFile() {
		this.objects = new ArrayList<Geometry>();
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
		System.err.println("Name: " + selected);
		for (Geometry object : objects) {
			System.err.println("FOR: " + object.getName());
			if (object.getName().equals(selected)) {
				return object;
			}
		}
		return null;
	}

}