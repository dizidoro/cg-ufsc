package graphic_system.model;

public abstract class Geometry {

	private final String name;
	private final Type type;

	public Geometry(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public abstract Geometry getWindowViewportTransformation(Window window,
			Viewport viewport);

	public enum Type {
		POINT, LINE, POLYGON
	}

	public abstract Coordinate getCenter();

	public abstract void setCenter(Coordinate center);

	public abstract void scaleLess();

	public abstract void scalePlus();

	public abstract void objectRotation();

	public abstract void worldRotation(Window window, Viewport viewport);

	public abstract void dotRatation(Coordinate dot);

}
