package graphic_system.view;

import graphic_system.ApplicationConfig;
import graphic_system.model.BSpline;
import graphic_system.model.Coordinate;
import graphic_system.model.Curve;
import graphic_system.model.Dot;
import graphic_system.model.Geometry;
import graphic_system.model.Line;
import graphic_system.model.Polygon;

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

	public Viewport() {
		double xMin = ApplicationConfig.initXMin;
		double yMin = ApplicationConfig.initYMin;
		double xMax = ApplicationConfig.initXMax;
		double yMax = ApplicationConfig.initYMax;

		int height = (int) (yMax - yMin);
		int width = (int) (xMax - xMin);
		this.setSize(width, height);
		// this.setSize(800, 800);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MouseHandlerDot mouseHandlerDot = new MouseHandlerDot();
	private MouseHandlerLine mouseHandlerLine = new MouseHandlerLine();
	private MouseHandlerPolygon mouseHandlerPolygon = new MouseHandlerPolygon();
	private MouseHandlerCurve mouseHandlerCurve = new MouseHandlerCurve();
	private MouseHandlerBSpline mouseHandlerBSpline = new MouseHandlerBSpline();
	private MovingAdapter mouseDraggin = new MovingAdapter();

	private Coordinate p1;
	private Coordinate p2;
	private boolean drawing = false;
	ArrayList<Coordinate> polygonClicks = new ArrayList<>();
	ArrayList<Coordinate> curveClicks = new ArrayList<>();
	ArrayList<Coordinate> bSplineClicks = new ArrayList<>();

	public int numControlDots = 4;

	private Geometry.Type graphicTool = null;
	private Color colorTool = Color.BLACK;
	private boolean redraw = false;

	// Seta a ferramenta e carrega o listener associado
	public void setGraphicTool(Geometry.Type graphicTool) {
		if (this.graphicTool != null) {
			removeMouseListener();
		}
		this.graphicTool = graphicTool;
		addMouseListener();
	}

	public void setColorTool(Color color) {
		colorTool = color;
	}

	private ArrayList<IGraphicSystem> listenersGC = new ArrayList<>();

	public void addListenerController(IGraphicSystem listener) {
		listenersGC.add(listener);
	}

	private ArrayList<ILayout> listenersLayout = new ArrayList<>();

	public void addListenerLayout(ILayout listener) {
		listenersLayout.add(listener);
	}

	public void addNewObject(Geometry object) {
		for (IGraphicSystem listener : listenersGC) {
			listener.addNewObject(object);
		}
		for (ILayout listener : listenersLayout) {
			listener.add(object.getName());
		}
	}

	private void addMouseListener() {
		if (graphicTool.equals(Geometry.Type.POINT)) {
			this.addMouseListener(mouseHandlerDot);
		} else if (graphicTool.equals(Geometry.Type.LINE)) {
			this.addMouseListener(mouseHandlerLine);
			this.addMouseListener(mouseDraggin);
		} else if (graphicTool.equals(Geometry.Type.POLYGON)) {
			this.addMouseListener(mouseHandlerPolygon);
		} else if (graphicTool.equals(Geometry.Type.CURVE)) {
			this.addMouseListener(mouseHandlerCurve);
		} else if (graphicTool.equals(Geometry.Type.BSPLINE)) {
			this.addMouseListener(mouseHandlerBSpline);
		}
	}

	private void removeMouseListener() {
		if (this.graphicTool.equals(Geometry.Type.POINT)) {
			this.removeMouseListener(mouseHandlerDot);
		} else if (this.graphicTool.equals(Geometry.Type.LINE)) {
			this.removeMouseListener(mouseHandlerLine);
		} else if (this.graphicTool.equals(Geometry.Type.POLYGON)) {
			this.removeMouseListener(mouseHandlerPolygon);
		} else if (this.graphicTool.equals(Geometry.Type.CURVE)) {
			this.removeMouseListener(mouseHandlerCurve);
		} else if (this.graphicTool.equals(Geometry.Type.BSPLINE)) {
			this.removeMouseListener(mouseHandlerBSpline);
		}
	}

	public Geometry.Type getGraphicTool() {
		return graphicTool;
	}

	@Override
	protected void paintComponent(Graphics g) {

		// TODO: Tornar isso configurável
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(colorTool);
		g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_BEVEL));

		if (!objects.isEmpty() || redraw) {
			super.paintComponent(g);

			for (Geometry object : objects) {
				g.setColor(object.getColor());
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
					g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(),
							(int) b.getY());
				} else if (object.getType().equals(Geometry.Type.POLYGON)) {
					Polygon polygon = (Polygon) object;
					ArrayList<Coordinate> coordinates = (ArrayList<Coordinate>) polygon
							.getVertices();

					int nPoints = coordinates.size();
					int xPoints[] = new int[nPoints];
					int yPoints[] = new int[nPoints];
					for (int i = 0; i < nPoints; i++) {
						Coordinate coordinate = coordinates.get(i);
						xPoints[i] = (int) coordinate.getX();
						yPoints[i] = (int) coordinate.getY();
					}
					g.drawPolygon(xPoints, yPoints, nPoints);
				} else if (object.getType().equals(Geometry.Type.CURVE)) {
					Curve curve = (Curve) object;
					ArrayList<Coordinate> coordinates = (ArrayList<Coordinate>) curve
							.getCoordinates();
					drawBezierCurve(g, coordinates);
				} else if (object.getType().equals(Geometry.Type.BSPLINE)) {
					BSpline curve = (BSpline) object;
					ArrayList<Coordinate> coordinates = (ArrayList<Coordinate>) curve
							.getCoordinates();
					drawBSpline(g, coordinates, this.numControlDots);
				}
			}
			objects = Collections.emptyList();
			redraw = false;
			return;
		}

		if (graphicTool == null) {
			super.paintComponent(g);
			return;
		}

		if (graphicTool.equals(Geometry.Type.POINT)) {
			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p1.getX(),
					(int) p1.getY());
			System.out.println("Tool paint = (" + p1.getX() + "," + p1.getY()
					+ ")");

		} else if (graphicTool.equals(Geometry.Type.LINE)) {
			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(),
					(int) p2.getY());

		} else if (graphicTool.equals(Geometry.Type.POLYGON)) {
			int nPoints = polygonClicks.size();
			int xPoints[] = new int[nPoints];
			int yPoints[] = new int[nPoints];
			for (int i = 0; i < nPoints; i++) {
				Coordinate coordinate = polygonClicks.get(i);
				xPoints[i] = (int) coordinate.getX();
				yPoints[i] = (int) coordinate.getY();
			}
			g.drawPolygon(xPoints, yPoints, nPoints);

		} else if (graphicTool.equals(Geometry.Type.CURVE)) {
			drawBezierCurve(g, curveClicks);
			curveClicks.clear();
		} else if (graphicTool.equals(Geometry.Type.BSPLINE)) {
			drawBSpline(g, bSplineClicks, this.numControlDots);
			bSplineClicks.clear();
		}

	}

	private void drawBezierCurve(Graphics g, List<Coordinate> coordinates) {
		int nPoints = coordinates.size();
		double xPoints[] = new double[nPoints];
		double yPoints[] = new double[nPoints];
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] = coordinates.get(i).getX();
			yPoints[i] = coordinates.get(i).getY();
		}

		int i = 0;
		while (i + 3 < nPoints) {
			double rangeX, rangeY, step;

			if (xPoints[i + 3] - xPoints[i] < 0) {
				rangeX = -(xPoints[i + 3] - xPoints[i]);
			} else {
				rangeX = xPoints[i + 3] - xPoints[i];
			}

			if (yPoints[i + 3] - yPoints[i] < 0) {
				rangeY = -(yPoints[i + 3] - yPoints[i]);
			} else {
				rangeY = yPoints[i + 3] - yPoints[i];
			}

			if (rangeX > rangeY) {
				step = 1 / rangeX;
			} else {
				step = 1 / rangeY;
			}

			double x = 0, y = 0, xPrevious = 0, yPrevious = 0;
			for (double t = 0; t <= 1; t += step) {
				x = (((-1 * Math.pow(t, 3) + 3 * Math.pow(t, 2) - 3 * t + 1)
						* xPoints[i]
						+ (3 * Math.pow(t, 3) - 6 * Math.pow(t, 2) + 3 * t)
						* xPoints[i + 1]
						+ (-3 * Math.pow(t, 3) + 3 * Math.pow(t, 2))
						* xPoints[i + 2] + (1 * Math.pow(t, 3))
						* xPoints[i + 3]));
				y = (((-1 * Math.pow(t, 3) + 3 * Math.pow(t, 2) - 3 * t + 1)
						* yPoints[i]
						+ (3 * Math.pow(t, 3) - 6 * Math.pow(t, 2) + 3 * t)
						* yPoints[i + 1]
						+ (-3 * Math.pow(t, 3) + 3 * Math.pow(t, 2))
						* yPoints[i + 2] + (1 * Math.pow(t, 3))
						* yPoints[i + 3]));
				if (t == 0) {
					xPrevious = x;
					yPrevious = y;
				} else {
					g.drawLine((int) xPrevious, (int) yPrevious, (int) x,
							(int) y);
					xPrevious = x;
					yPrevious = y;
				}
			}
			i += 3;
		}
	}

	private void drawBSpline(Graphics g, List<Coordinate> coordinates,
			int numControlDots) {
		int n = coordinates.size();
		double xA, yA, xB, yB, xC, yC, xD, yD;
		double a0, a1, a2, a3, b0, b1, b2, b3;
		double x = 0, y = 0, x0, y0;
		boolean first = true;
		for (int i = 1; i < n - 2; i++) {
			xA = coordinates.get(i - 1).getX();
			xB = coordinates.get(i).getX();
			xC = coordinates.get(i + 1).getX();
			xD = coordinates.get(i + 2).getX();
			yA = coordinates.get(i - 1).getY();
			yB = coordinates.get(i).getY();
			yC = coordinates.get(i + 1).getY();
			yD = coordinates.get(i + 2).getY();
			a3 = (-xA + 3 * (xB - xC) + xD) / 6;
			b3 = (-yA + 3 * (yB - yC) + yD) / 6;
			a2 = (xA - 2 * xB + xC) / 2;
			b2 = (yA - 2 * yB + yC) / 2;
			a1 = (xC - xA) / 2;
			b1 = (yC - yA) / 2;
			a0 = (xA + 4 * xB + xC) / 6;
			b0 = (yA + 4 * yB + yC) / 6;
			for (int j = 0; j <= numControlDots; j++) {
				x0 = x;
				y0 = y;
				double t = (float) j / numControlDots;
				x = ((a3 * t + a2) * t + a1) * t + a0;
				y = ((b3 * t + b2) * t + b1) * t + b0;
				if (first) {
					first = false;
				} else {
					g.drawLine((int) x0, (int) y0, (int) x, (int) y);
				}
			}
		}
	}

	private class MouseHandlerDot extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("dot: mouse pressed");

			Point p = e.getPoint();
			p1 = new Coordinate(p.x, p.y);
			System.out.println("Coordenada: " + p.x + "," + p.y);
			Dot dot = new Dot(p1, colorTool);
			addNewObject(dot);

			repaint();
		}
	}

	private class MouseHandlerLine extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("line: mouse pressed");

			drawing = true;
			Point p = e.getPoint();
			p1 = new Coordinate(p.x, p.y);
			p2 = p1;

			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("line: mouse released");

			drawing = false;
			Point p = e.getPoint();
			p2 = new Coordinate(p.x, p.y);
			line = new Line(p1, p2, colorTool);
			addNewObject(line);

			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("line: mouse dragged");

			if (drawing) {
				Point p = e.getPoint();
				p2 = new Coordinate(p.x, p.y);
				repaint();
			}
		}
	}

	Line line;

	private class MouseHandlerPolygon extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("polygon: mouse clicked");

			if (e.getClickCount() == 2) {
				Polygon polygon = new Polygon(polygonClicks, colorTool);
				addNewObject(polygon);

				repaint();
				polygonClicks.clear();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("polygon: mouse pressed");

			Point p = e.getPoint();
			polygonClicks.add(new Coordinate(p.x, p.y));

			repaint();
		}

	}

	class MovingAdapter extends MouseAdapter {

		private int x;
		private int y;

		@Override
		public void mousePressed(MouseEvent e) {
			x = e.getX();
			y = e.getY();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int dx = e.getX() - x;
			int dy = e.getY() - y;

			if (line != null) {
				line.getA().setX(dx);
				line.getB().setY(dy);
				repaint();
			}

			x += dx;
			y += dy;
		}
	}

	private class MouseHandlerCurve extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("curve: mouse clicked");

			if (e.getClickCount() == 2) {
				Curve curve = new Curve(curveClicks, colorTool);
				addNewObject(curve);

				repaint();
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("curve: mouse pressed");

			Point p = e.getPoint();
			curveClicks.add(new Coordinate(p.x, p.y));
		}
	}

	private class MouseHandlerBSpline extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("curve: mouse clicked");

			if (e.getClickCount() == 2) {
				BSpline curve = new BSpline(bSplineClicks, colorTool);
				addNewObject(curve);

				repaint();
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("curve: mouse pressed");

			Point p = e.getPoint();
			bSplineClicks.add(new Coordinate(p.x, p.y));
		}
	}

	List<Geometry> objects = new ArrayList<>();

	public void redraw(List<Geometry> objects) {
		redraw = true;
		this.objects = objects;
		repaint();
	}

	public void setNumControlDots(int numControlDots) {
		this.numControlDots = numControlDots;
	}

}