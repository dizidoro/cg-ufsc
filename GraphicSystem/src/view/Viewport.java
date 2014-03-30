package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.Coordinate;
import model.Dot;
import model.Geometry;
import model.Line;
import model.ObjectType;
import controller.UtilGraphicSystem;

public class Viewport extends JPanel {
	
	private double xMin, yMin, xMax, yMax;
	
	public Viewport(double xMin, double yMin, double xMax, double yMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		
		int height = (int) UtilGraphicSystem.getDistance(yMin, yMax);
		int widht = (int) UtilGraphicSystem.getDistance(xMin, xMax);
		this.setSize(height, widht);
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
    
    private ObjectType graphicTool = null;
    
    private ArrayList<IGraphicSystem> listeners = new ArrayList<>();
	public void addListenerController(IGraphicSystem listener) {
		listeners.add(listener);
	}
	
	public void addNewObject(Geometry object) {
		for (IGraphicSystem listener : listeners) {
        	listener.addNewObject(object);
		}
	}
    
	// Seta a ferramenta e carrega o listener associado
    public void setGraphicTool(ObjectType graphicTool) {
    	if (this.graphicTool != null) {
    		removeMouseListener();
    	}
    	this.graphicTool = graphicTool;
    	addMouseListener();
    }
    
    private void addMouseListener() {
    	if (graphicTool.equals(ObjectType.DOT)) {
    		this.addMouseListener(mouseHandlerDot);
        } else if (graphicTool.equals(ObjectType.LINE)) {
    		this.addMouseListener(mouseHandlerLine);
        } else if (graphicTool.equals(ObjectType.POLYGON)) {
    		this.addMouseListener(mouseHandlerPolygon);
    	}
	}

	private void removeMouseListener() {
    	if (this.graphicTool.equals(ObjectType.DOT)) {
			this.removeMouseListener(mouseHandlerDot);
		} else if (this.graphicTool.equals(ObjectType.LINE)) {
			this.removeMouseListener(mouseHandlerLine);
		} else if (this.graphicTool.equals(ObjectType.LINE)) {
			this.removeMouseListener(mouseHandlerPolygon);
		}
	}

	public ObjectType getGraphicTool() {
    	return graphicTool;
    }
	
    @Override
	protected void paintComponent(Graphics g) {
    	
        // TODO: Criar opção para configurar essas opções
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        
    	if (objects != null) {
    		super.paintComponent(g);
    		
    		for (Geometry object : objects) {
    			if (object.getType().equals(ObjectType.DOT)) {
    				Dot dot = (Dot) object;
    				Coordinate coordinate = dot.getCoordinate();
    		        g.drawLine(
		        		(int) coordinate.getX(),
		        		(int) coordinate.getY(),
		        		(int) coordinate.getX(),
		        		(int) coordinate.getY()
    		        );
    			} else if (object.getType().equals(ObjectType.LINE)) {
    				Line line = (Line) object;
    				Coordinate a = line.getA();
    				Coordinate b = line.getB();
    				
    	        	g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());

    	        } else if (object.getType().equals(ObjectType.POLYGON)) {
    	        	
    	        	// TODO
    	        }
    		}
    		objects = null;
    		return;
    	}
    	
        if (graphicTool == null) {
        	super.paintComponent(g);
        	return;
        }
        
        if (graphicTool.equals(ObjectType.DOT)) {
	        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p1.getX(), (int) p1.getY());
	        
        } else if (graphicTool.equals(ObjectType.LINE)) {
        	 g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());

        } else if (graphicTool.equals(ObjectType.POLYGON)) {
        	
        	// TODO
        }
        
    }
    
	private class MouseHandlerDot extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
        	System.out.println("dot: mouse pressed");
        	
        	Point p = e.getPoint();
        	p1 = new Coordinate(p.x, p.y, 0);
        	Dot dot = new Dot(p1);
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
            addNewObject(line);
        	
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
        	
        	// TODO
        	
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        	System.out.println("polygon: mouse dragged");
        	
        	// TODO
        	
            repaint();
        }
    }
	
	ArrayList<Geometry> objects;
	public void redraw(ArrayList<Geometry> objects) {
		this.objects = objects;
		repaint();
	}
    
}