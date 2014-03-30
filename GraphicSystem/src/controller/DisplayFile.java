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

	// Isso é mais teste de transformação
	// acho que deve ser algo mais genérico
	// e passar uma matriz para o display file
	// não um fator.
	public void moveUp(double factor) {
		System.out.println("Calcula e armazena deslocamento de todos os objetos");
		
		for (Geometry object : objects) {
			object.moveUp(factor);
		}
	}
	
}
