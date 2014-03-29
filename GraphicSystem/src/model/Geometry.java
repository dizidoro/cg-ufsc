package model;

public class Geometry {
	
	private String name;
	private ObjectType type;
	
	public Geometry(String name, ObjectType type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public ObjectType getType() {
		return type;
	}
}
