package controller;

import java.util.ArrayList;

import model.Geometry;

public class DisplayFile {

	private ArrayList<Geometry> objects;
	
	DisplayFile() {
		this.objects = new ArrayList<Geometry>();
	}

	public ArrayList<Geometry> getObjects() {
		return objects;
	}

	public void add(Geometry object) {
		objects.add(object);
	}
	
}
