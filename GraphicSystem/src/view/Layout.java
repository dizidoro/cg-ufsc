package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.ObjectType;
import net.miginfocom.swing.MigLayout;

public class Layout implements IGraphicLayout {

	private JFrame frmComputerGraphics;
	
	private Viewport viewport;
	
	private DefaultListModel<String> listObjects;
	
	public void addListenerController(IGraphicController listener) {
		viewport.addListenerController(listener);
	}
	
	public void addListenerLayout(IGraphicLayout listener) {
		viewport.addListenerLayout(listener);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Viewport viewport = new Viewport();
					Layout window = new Layout(viewport);
					window.frmComputerGraphics.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param viewport 
	 */
	public Layout(Viewport viewport) {
		this.viewport = viewport;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmComputerGraphics = new JFrame();
		frmComputerGraphics.setTitle("Graphic System");
		frmComputerGraphics.setBounds(100, 100, 800, 700);
		frmComputerGraphics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmComputerGraphics.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][grow]"));
		
		JPanel panelMenu = new JPanel();
		frmComputerGraphics.getContentPane().add(panelMenu, "cell 0 1,grow");
		panelMenu.setLayout(new MigLayout("", "[225px,grow]", "[][229px,grow][]"));
		
		JPanel panelTools = new JPanel();
		panelTools.setBorder(new TitledBorder(null, "Draw", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMenu.add(panelTools, "cell 0 0,growx,aligny top");
		panelTools.setLayout(new MigLayout("", "[][][]", "[]"));
		
		viewport.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), null));
		viewport.setBackground(Color.WHITE);
		frmComputerGraphics.getContentPane().add(viewport, "cell 1 1,grow");
		
		final ArrayList<JToggleButton> drawButtons = new ArrayList<>();
		final JToggleButton tglbtnPencil = new JToggleButton("");
		tglbtnPencil.setIcon(new ImageIcon("img/Imagetools-Edit-icon.png"));
		tglbtnPencil.addActionListener(new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
	            for (JToggleButton jToggleButton : drawButtons) {
	                jToggleButton.setSelected(false);
	            }
	            tglbtnPencil.setSelected(true);
	            viewport.setGraphicTool(ObjectType.DOT);
	        }
	    });
		drawButtons.add(tglbtnPencil);
		panelTools.add(tglbtnPencil, "cell 0 0");
		
		final JToggleButton tglbtnLine = new JToggleButton("");
		tglbtnLine.setIcon(new ImageIcon("img/Imagetools-Line-icon.png"));
		tglbtnLine.addActionListener(new ActionListener() {
        	@Override
	        public void actionPerformed(ActionEvent e) {
	            for (JToggleButton jToggleButton : drawButtons) {
	                jToggleButton.setSelected(false);
	            }
	            tglbtnLine.setSelected(true);
	            viewport.setGraphicTool(ObjectType.LINE);
	        }
	    });
		drawButtons.add(tglbtnLine);
		panelTools.add(tglbtnLine, "cell 1 0");
		
		final JToggleButton tglbtnPolygon = new JToggleButton("");
		tglbtnPolygon.setIcon(new ImageIcon("img/Imagetools-Polygone-icon.png"));
		tglbtnPolygon.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            for (JToggleButton jToggleButton : drawButtons) {
	                jToggleButton.setSelected(false);
	            }
	            tglbtnPolygon.setSelected(true);
	            viewport.setGraphicTool(ObjectType.POLYGON);
	        }
	    });
		drawButtons.add(tglbtnPolygon);
		panelTools.add(tglbtnPolygon, "cell 2 0");
		
		JPanel panelObjects = new JPanel();
		panelObjects.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Objects", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMenu.add(panelObjects, "cell 0 1,grow");
		panelObjects.setLayout(new MigLayout("", "[grow][grow]", "[grow][]"));
		
		listObjects = new DefaultListModel<String>();
		JList<String> jListObjects = new JList<String>(listObjects);
		JScrollPane scrollPaneList = new JScrollPane(jListObjects); 
		panelObjects.add(scrollPaneList, "cell 0 0 2 1,grow");
		
		JPanel panelCoordinates = new JPanel();
		panelObjects.add(panelCoordinates, "cell 0 1 2 1,growx");
		panelCoordinates.setBorder(new TitledBorder(null, "Coordinates", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCoordinates.setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Window", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMenu.add(panel, "cell 0 2,growx,aligny bottom");
		panel.setLayout(new MigLayout("", "[][][]", "[][][]"));
		
		JButton btnZoomOut = new JButton("");
		btnZoomOut.setIcon(new ImageIcon("img/zoom-out-icon.png"));
		panel.add(btnZoomOut, "cell 0 0");
		
		JButton btnMoveUp = new JButton("");
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Point location = viewport.getLocation();
				viewport.setLocation(location.x, --location.y);
			}
		});
		btnMoveUp.setIcon(new ImageIcon("img/Arrows-Up-icon32.png"));
		panel.add(btnMoveUp, "cell 1 0");
		
		JButton btnZoomIn = new JButton("");
		btnZoomIn.setIcon(new ImageIcon("img/zoom-in-icon.png"));
		panel.add(btnZoomIn, "cell 2 0");
		
		JButton btnMoveLeft = new JButton("");
		btnMoveLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Point location = viewport.getLocation();
				viewport.setLocation(--location.x, location.y);
			}
		});
		btnMoveLeft.setIcon(new ImageIcon("img/Arrows-Left-icon32.png"));
		panel.add(btnMoveLeft, "cell 0 1");
		
		JButton btnMoveRight = new JButton("");
		btnMoveRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Point location = viewport.getLocation();
				viewport.setLocation(++location.x, location.y);
			}
		});
		btnMoveRight.setIcon(new ImageIcon("img/Arrows-Right-icon32.png"));
		panel.add(btnMoveRight, "cell 2 1");
		
		JButton btnTurnLeft = new JButton("");
		btnTurnLeft.setIcon(new ImageIcon("img/Arrow-turn-left-icon.png"));
		panel.add(btnTurnLeft, "cell 0 2");
		
		JButton btnMoveDown = new JButton("");
		btnMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Point location = viewport.getLocation();
				viewport.setLocation(location.x, ++location.y);
			}
		});
		btnMoveDown.setIcon(new ImageIcon("img/Arrows-Down-icon32.png"));
		panel.add(btnMoveDown, "cell 1 2");
		
		JButton btnTurnRight = new JButton("");
		btnTurnRight.setIcon(new ImageIcon("img/Arrow-turn-right-icon.png"));
		panel.add(btnTurnRight, "cell 2 2");
		
		JMenuBar menuBar = new JMenuBar();
		frmComputerGraphics.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		frmComputerGraphics.setVisible(true);
		
		addListenerLayout(this);
	}

	@Override
	public void createObject(String name) {
		listObjects.add(0, name);
	}
	
}

