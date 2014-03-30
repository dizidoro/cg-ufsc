package controller;

import model.Geometry;
import view.IGraphicSystem;
import view.Layout;
import view.Viewport;

public class GraphicSystem implements IGraphicSystem {
	
	private Window window;
	
	private DisplayFile displayFile;
	
	private Viewport viewport;
	
	private Layout gui;

	public GraphicSystem() {
		// Dimensão inicial da window
		double initX = 300;
		double initY = 300;
		
		window = new Window(-initX, -initY, initX, initY);
		viewport = new Viewport(-initX, -initY, initX, initY);
		viewport.addListenerController(this);
		displayFile = new DisplayFile();
		gui = new Layout(viewport);
		gui.addListenerController(this);
	}

	@Override
	public void addNewObject(Geometry object) {
		displayFile.add(object);
		gui.add(object.getName());
	}
	
	
	@Override
	public void moveUp() {
		
		// Vai expandir a window e ela vai me retornar uma fator
		// para que eu possa aplicar sobre os objetos do DisplayFile
		double factor = window.moveUp(10);
		
		// Calcula e armazena deslocamento de todos os objetos
		displayFile.moveUp(factor);
		
		// Redesenha a lista de objetos atualizada
		viewport.redraw(displayFile.getObjects());
		
	}
	
}
