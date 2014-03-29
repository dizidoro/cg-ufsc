package controller;

import java.awt.EventQueue;
import java.awt.List;

import model.Coordinate;
import model.Dot;
import model.Line;
import view.IGraphicController;
import view.Layout;
import view.Viewport;

public class GraphicSystem implements IGraphicController {
	
	private Window window;
	
	private DisplayFile displayFile;
	
	private Viewport viewport;

	public GraphicSystem() {
		double initX = 100;
		double initY = 100;
		
		window = new Window(-initX, -initY, initX, initY);
		displayFile = new DisplayFile();
		viewport = new Viewport();
		Layout gui = new Layout(viewport);
		gui.addListenerController(this);
	}
	
	@Override
	public String createDot(Coordinate coordinate) {
		Dot dot = new Dot(coordinate);
		return dot.getName();
	}
	
	@Override
	public String createLine(Coordinate a, Coordinate b) {
		Line line = new Line(a, b);
		return line.getName();
	}

	@Override
	public String createPolygon(List coordinates) {
		System.out.println("Polygon");
		
		// TODO Auto-generated method stub
		
		return null;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new GraphicSystem();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
