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
import model.ObjectType;

public class Viewport extends JPanel {
	public Viewport() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MouseHandlerDot mouseHandlerDot = new MouseHandlerDot();
	private MouseHandlerLine mouseHandlerLine = new MouseHandlerLine();
	private MouseHandlerPolygon mouseHandlerPolygon = new MouseHandlerPolygon();
	
	private Point p1;
	private Point p2;
	private boolean drawing = false;
    
    private ObjectType graphicTool = null;
    
    private ArrayList<IGraphicController> listenersController = new ArrayList<>();
    private ArrayList<IGraphicLayout> listenersLayout = new ArrayList<>();
    
	public void addListenerController(IGraphicController listener) {
		listenersController.add(listener);
	}
	
	public void addListenerLayout(IGraphicLayout listener) {
		listenersLayout.add(listener);
	}
	
	public void createDot(Point point) {
		for (IGraphicController listener : listenersController) {
        	String name = listener.createDot(new Coordinate(p1.x, p1.y, 0));
        	createObject(name);
		}
	}
	
	public void createLine(Point a, Point b) {
		for (IGraphicController listener : listenersController) {
        	String name = listener.createLine(new Coordinate(a.x, a.y, 0), new Coordinate(b.x, b.y, 0));
        	createObject(name);
		}
	}
	
	public void createPolygon() {
		
		// TODO
		
	}
    
	private void createObject(String name) {
		for (IGraphicLayout listenerLayout: listenersLayout) {
    		listenerLayout.createObject(name);
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
        if (graphicTool == null) {
        	
        	// Isso limpa o painel
        	super.paintComponent(g);
        	
        	return;
        }
        
        // TODO: Criar opção para configurar essas opções
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(
    		5,
    		BasicStroke.CAP_ROUND,
    		BasicStroke.JOIN_BEVEL
		));
        
        if (graphicTool.equals(ObjectType.DOT)) {
	        g.drawLine(p1.x, p1.y, p1.x, p1.y);
	        
        } else if (graphicTool.equals(ObjectType.LINE)) {
        	 g.drawLine(p1.x, p1.y, p2.x, p2.y);

        } else if (graphicTool.equals(ObjectType.POLYGON)) {
        	
        	// TODO
        }
    }
    
	private class MouseHandlerDot extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
        	System.out.println("dot: mouse pressed");
        	
        	p1 = e.getPoint();
            createDot(e.getPoint());
            
            repaint();
        }
    }
    
	private class MouseHandlerLine extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
        	System.out.println("line: mouse pressed");
        	
            drawing = true;
            p1 = e.getPoint();
            p2 = p1;
            
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        	System.out.println("line: mouse released");
        	
            drawing = false;
            p2 = e.getPoint();
            
            createLine(p1,  p2);
            
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        	System.out.println("line: mouse dragged");
        	
            if (drawing) {
                p2 = e.getPoint();
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
    
}