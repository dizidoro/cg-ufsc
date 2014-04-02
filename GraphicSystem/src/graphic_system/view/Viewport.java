package graphic_system.view;

import graphic_system.ApplicationConfig;
import graphic_system.controller.GraphicSystem;
import graphic_system.model.Coordinate;
import graphic_system.model.Dot;
import graphic_system.model.Geometry;
import graphic_system.model.Line;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class Viewport extends JPanel {

	private final GraphicSystem graphicSystem;

	public Viewport(GraphicSystem graphicSystem) {
		this.graphicSystem = graphicSystem;
		
		double xMin = ApplicationConfig.initXMin;
		double yMin = ApplicationConfig.initYMin;
		double xMax = ApplicationConfig.initXMax; 
		double yMax = ApplicationConfig.initYMax;
		
		int height = (int) (yMax - yMin);
		int width = (int) (xMax - xMin);
		this.setSize(width, height);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MouseHandlerDot mouseHandlerDot = new MouseHandlerDot();
	private MouseHandlerLine mouseHandlerLine = new MouseHandlerLine();
	private MouseHandlerPolygon mouseHandlerPolygon = new MouseHandlerPolygon();

	private Coordinate p1;
	private Coordinate p2;
	private boolean drawing = false;

	private Geometry.Type graphicTool = null;

	// Seta a ferramenta e carrega o listener associado
	public void setGraphicTool(Geometry.Type graphicTool) {
		if (this.graphicTool != null) {
			removeMouseListener();
		}
		this.graphicTool = graphicTool;
		addMouseListener();
	}

	private void addMouseListener() {
		if (graphicTool.equals(Geometry.Type.POINT)) {
			this.addMouseListener(mouseHandlerDot);
		} else if (graphicTool.equals(Geometry.Type.LINE)) {
			this.addMouseListener(mouseHandlerLine);
		} else if (graphicTool.equals(Geometry.Type.POLYGON)) {
			this.addMouseListener(mouseHandlerPolygon);
		}
	}

	private void removeMouseListener() {
		if (this.graphicTool.equals(Geometry.Type.POINT)) {
			this.removeMouseListener(mouseHandlerDot);
		} else if (this.graphicTool.equals(Geometry.Type.LINE)) {
			this.removeMouseListener(mouseHandlerLine);
		} else if (this.graphicTool.equals(Geometry.Type.POLYGON)) {
			this.removeMouseListener(mouseHandlerPolygon);
		}
	}

	public Geometry.Type getGraphicTool() {
		return graphicTool;
	}

	@Override
	protected void paintComponent(Graphics g) {

		// TODO: Criar op????????????o para configurar essas op????????????es
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));

		if (!objects.isEmpty()) {
			super.paintComponent(g);

			for (Geometry object : objects) {
				if (object.getType().equals(Geometry.Type.POINT)) {
					Dot dot = (Dot) object;
					Coordinate coordinate = dot.getCoordinate();
					g.drawLine((int) coordinate.getX(),
							(int) coordinate.getY(), (int) coordinate.getX(),
							(int) coordinate.getY());
				} else if (object.getType().equals(Geometry.Type.LINE)) {
					Line line = (Line) object;
					Coordinate a = line.getA();
					Coordinate b = line.getB();

					g.drawLine((int) a.getX(), (int) a.getY(), 
							(int) b.getX(), (int) b.getY());

				} else if (object.getType().equals(Geometry.Type.POLYGON)) {

					// TODO
				}
			}
			objects = Collections.emptyList();
			return;
		}

		if (graphicTool == null) {
			super.paintComponent(g);
			return;
		}

		if (graphicTool.equals(Geometry.Type.POINT)) {
			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p1.getX(),
					(int) p1.getY());

		} else if (graphicTool.equals(Geometry.Type.LINE)) {
			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(),
					(int) p2.getY());

		} else if (graphicTool.equals(Geometry.Type.POLYGON)) {

			// TODO
		}

	}

	private class MouseHandlerDot extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("dot: mouse pressed");

			Point p = e.getPoint();
			p1 = new Coordinate(p.x, p.y);
			System.out.println("Coordenada: " + p.x + "," +p.y);
			Dot dot = new Dot(p1);
			graphicSystem.addNewObject(dot);

			repaint();
		}
	}

	private class MouseHandlerLine extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("line: mouse pressed");

			drawing = true;
			Point p = e.getPoint();
			p1 = new Coordinate(p.x, p.y, 0);
			p2 = p1;

			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("line: mouse released");

			drawing = false;
			Point p = e.getPoint();
			p2 = new Coordinate(p.x, p.y, 0);
			Line line = new Line(p1, p2);
			graphicSystem.addNewObject(line);

			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("line: mouse dragged");

			if (drawing) {
				Point p = e.getPoint();
				p2 = new Coordinate(p.x, p.y, 0);
				repaint();
			}
		}
	}

	private class MouseHandlerPolygon extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("polygon: mouse pressed");

			// TODO

			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("polygon: mouse released");
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("polygon: mouse dragged");
			repaint();
		}
	}

	List<Geometry> objects;

	public void redraw(List<Geometry> objects) {
		this.objects = objects;
		repaint();
	}

}