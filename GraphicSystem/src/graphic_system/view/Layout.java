package graphic_system.view;

import graphic_system.model.Coordinate;
import graphic_system.model.Geometry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

public class Layout implements ILayout {

	private JFrame frmComputerGraphics;

	private Viewport viewport;

	private DefaultListModel<String> listObjects;
	private JList<String> jListObjects;

	private ArrayList<IGraphicSystem> listeners = new ArrayList<>();
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtXRotation;
	private JTextField txtYRotation;

	private ObjectRotation objectRotation;

	enum ObjectRotation {
		WORLD_CENTER, OBJECT_CENTER, DOT
	}

	public void addListenerController(IGraphicSystem listener) {
		listeners.add(listener);
		viewport.addListenerController(listener);
	}

	/**
	 * Create the application.
	 * 
	 * @param viewport
	 */
	public Layout() {
		// Layout tem uma Vieport gr��fica
		viewport = new Viewport();
		viewport.addListenerLayout(this);

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmComputerGraphics = new JFrame();
		frmComputerGraphics.setTitle("Graphic System");
		// frmComputerGraphics.setBounds(100, 100, 800, 700);
		frmComputerGraphics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmComputerGraphics.getContentPane().setLayout(
				new MigLayout("", "[][" + viewport.getHeight() + "px:n,grow]",
						"[][" + viewport.getWidth() + "px:n,grow]"));
		// frmComputerGraphics.getContentPane().setLayout(new MigLayout("",
		// "[][grow]", "[][grow]"));

		JPanel panelMenu = new JPanel();
		frmComputerGraphics.getContentPane().add(panelMenu, "cell 0 1,grow");
		panelMenu.setLayout(new MigLayout("", "[225px,grow]", "[][][229px,grow][]"));

		JPanel panelTools = new JPanel();
		panelTools.setBorder(new TitledBorder(null, "Draw",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMenu.add(panelTools, "cell 0 0 1 2,growx,aligny top");

		viewport.setBorder(new CompoundBorder(
				new LineBorder(new Color(0, 0, 0)), null));
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
				viewport.setGraphicTool(Geometry.Type.POINT);
			}
		});
		panelTools.setLayout(new MigLayout("", "[34px,grow][34px][34px]", "[10px][grow]"));
		drawButtons.add(tglbtnPencil);
		panelTools.add(tglbtnPencil, "cell 0 0,alignx left,aligny top");

		final JToggleButton tglbtnLine = new JToggleButton("");
		tglbtnLine.setIcon(new ImageIcon("img/Imagetools-Line-icon.png"));
		tglbtnLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (JToggleButton jToggleButton : drawButtons) {
					jToggleButton.setSelected(false);
				}
				tglbtnLine.setSelected(true);
				viewport.setGraphicTool(Geometry.Type.LINE);
			}
		});
		drawButtons.add(tglbtnLine);
		panelTools.add(tglbtnLine, "cell 1 0,alignx left,aligny top");

		final JToggleButton tglbtnPolygon = new JToggleButton("");
		tglbtnPolygon
				.setIcon(new ImageIcon("img/Imagetools-Polygone-icon.png"));
		tglbtnPolygon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (JToggleButton jToggleButton : drawButtons) {
					jToggleButton.setSelected(false);
				}
				tglbtnPolygon.setSelected(true);
				viewport.setGraphicTool(Geometry.Type.POLYGON);
			}
		});
		drawButtons.add(tglbtnPolygon);
		panelTools.add(tglbtnPolygon, "cell 2 0,alignx left,aligny top");
		
		JPanel panelColors = new JPanel();
		panelColors.setBorder(new TitledBorder(null, "Color", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTools.add(panelColors, "cell 0 1 3 1,grow");
		
		JButton btnBlack = new JButton("");
		btnBlack.setBackground(Color.BLACK);
		btnBlack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewport.setColorTool(Color.BLACK);
			}
		});
		panelColors.add(btnBlack);
		
		JButton btnBlue = new JButton("");
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewport.setColorTool(Color.BLUE);
			}
		});
		btnBlue.setBounds(0, 0, 30, 30);
		btnBlue.setBackground(Color.BLUE);
		panelColors.add(btnBlue);
		
		JButton btnGreen = new JButton("");
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewport.setColorTool(Color.GREEN);
			}
		});
		btnGreen.setBackground(Color.GREEN);
		panelColors.add(btnGreen);
		
		JButton btnYellow = new JButton("");
		btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewport.setColorTool(Color.YELLOW);
			}
		});
		btnYellow.setBackground(Color.YELLOW);
		panelColors.add(btnYellow);
		
		JButton btnRed = new JButton("");
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewport.setColorTool(Color.RED);
			}
		});
		btnRed.setBackground(Color.RED);
		panelColors.add(btnRed);
		
		JPanel panelObjects = new JPanel();
		panelObjects.setBorder(new TitledBorder(new LineBorder(new Color(184,
				207, 229)), "Objects", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panelMenu.add(panelObjects, "cell 0 2,grow");
		panelObjects.setLayout(new MigLayout("", "[grow][grow]", "[grow][]"));

		listObjects = new DefaultListModel<String>();
		jListObjects = new JList<String>(listObjects);
		jListObjects.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String selected = jListObjects.getSelectedValue();
				for (IGraphicSystem listener : listeners) {
					listener.getCenter(selected);
				}
			}
		});

		JScrollPane scrollPaneList = new JScrollPane(jListObjects);
		panelObjects.add(scrollPaneList, "cell 0 0 2 1,grow");

		JPanel panelTransformations = new JPanel();
		panelObjects.add(panelTransformations,
				"cell 0 1 2 1,growx,aligny center");
		panelTransformations.setBorder(new TitledBorder(new LineBorder(
				new Color(184, 207, 229)), "Transformations",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTransformations.setLayout(new MigLayout("", "[grow]", "[][][]"));

		JPanel panelTranslation = new JPanel();
		panelTranslation.setBorder(new TitledBorder(null, "Translation",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTransformations.add(panelTranslation,
				"cell 0 0,growx,aligny center");
		panelTranslation.setLayout(new MigLayout("", "[][grow][][grow][grow]",
				""));

		JLabel lblX = new JLabel("X:");
		panelTranslation.add(lblX, "cell 0 0,alignx trailing");

		txtX = new JTextField();
		txtX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setCenter();
			}
		});

		panelTranslation.add(txtX, "cell 1 0,growx");
		txtX.setColumns(10);

		JLabel lblY = new JLabel("Y:");
		panelTranslation.add(lblY, "cell 2 0,alignx trailing");

		txtY = new JTextField();
		txtY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setCenter();
			}
		});
		panelTranslation.add(txtY, "cell 3 0,growx");
		txtY.setColumns(10);

		JPanel panelScale = new JPanel();
		panelScale.setBorder(new TitledBorder(null, "Scale",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTransformations.add(panelScale, "cell 0 1,grow");

		JButton buttonLess = new JButton("-");
		buttonLess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				scaleLess();
			}
		});
		panelScale.add(buttonLess);

		JButton buttonPlus = new JButton("+");
		buttonPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scalePlus();
			}
		});
		panelScale.add(buttonPlus);

		JPanel panelRotation = new JPanel();
		panelRotation.setBorder(new TitledBorder(null, "Rotation",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTransformations.add(panelRotation, "cell 0 2,growx,aligny top");
		panelRotation.setLayout(new MigLayout("", "[]", "[][][][][][][]"));

		JRadioButton rdbtnWorldCenter = new JRadioButton("World center");
		rdbtnWorldCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objectRotation = ObjectRotation.WORLD_CENTER;
			}
		});
		panelRotation.add(rdbtnWorldCenter, "cell 0 0");

		JRadioButton rdbtnObjectCenter = new JRadioButton("Object center");
		rdbtnObjectCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objectRotation = ObjectRotation.OBJECT_CENTER;
			}
		});
		panelRotation.add(rdbtnObjectCenter, "cell 0 1");

		JRadioButton rdbtnDot = new JRadioButton("Dot");
		rdbtnDot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objectRotation = ObjectRotation.DOT;
			}
		});
		panelRotation.add(rdbtnDot, "flowx,cell 0 2");

		final ButtonGroup bgRotation = new ButtonGroup();
		bgRotation.add(rdbtnWorldCenter);
		bgRotation.add(rdbtnObjectCenter);
		bgRotation.add(rdbtnDot);

//		JButton btnApply = new JButton("Apply");
//		btnApply.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (objectRotation.equals(ObjectRotation.WORLD_CENTER)) {
//					String selected = jListObjects.getSelectedValue();
//					for (IGraphicSystem listener : listeners) {
//						listener.rotateAntiClockwiseAroundOrigin(selected);
//					}
//				} else if (objectRotation.equals(ObjectRotation.OBJECT_CENTER)) {
//					String selected = jListObjects.getSelectedValue();
//					for (IGraphicSystem listener : listeners) {
//						listener.rotateClockwiseAroundCenter(selected);
//					}
//				} else if (objectRotation.equals(ObjectRotation.DOT)) {
//					String selected = jListObjects.getSelectedValue();
//					double x = Double.parseDouble(txtXRotation.getText());
//					double y = Double.parseDouble(txtYRotation.getText());
//					Coordinate dot = new Coordinate(x, y);
//					for (IGraphicSystem listener : listeners) {
//						listener.dotRotation(selected, dot);
//					}
//				}
//			}
//		});
//		panelRotation.add(btnApply, "cell 0 3");

		JLabel lblXRotation = new JLabel("X:");
		panelRotation.add(lblXRotation, "cell 0 2");

		txtXRotation = new JTextField();
		panelRotation.add(txtXRotation, "cell 0 2");
		txtXRotation.setColumns(10);

		JLabel lblYRotation = new JLabel("Y:");
		panelRotation.add(lblYRotation, "cell 0 2");

		txtYRotation = new JTextField();
		panelRotation.add(txtYRotation, "cell 0 2");
		txtYRotation.setColumns(10);
		
		JButton btnLeft = new JButton("");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (objectRotation == ObjectRotation.WORLD_CENTER) {
					String selected = jListObjects.getSelectedValue();
					for (IGraphicSystem listener : listeners) {
						listener.rotateAntiClockwiseAroundOrigin(selected);
					}
				} else if (objectRotation == ObjectRotation.OBJECT_CENTER) {
					String selected = jListObjects.getSelectedValue();
					for (IGraphicSystem listener : listeners) {
						listener.rotateAntiClockwiseAroundCenter(selected);
					}
				} else if (objectRotation == ObjectRotation.DOT) {
					String selected = jListObjects.getSelectedValue();
					double x = Double.parseDouble(txtXRotation.getText());
					double y = Double.parseDouble(txtYRotation.getText());
					Coordinate dot = new Coordinate(x, y);
					for (IGraphicSystem listener : listeners) {
						listener.rotateAntiClockwiseAroundPoint(selected, dot);
					}
				}
			}
		});
		btnLeft.setIcon(new ImageIcon("img/Arrow-turn-left-icon.png"));
		panelRotation.add(btnLeft, "flowx,cell 0 3,alignx center");
		
		JButton btnRight = new JButton("");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (objectRotation == ObjectRotation.WORLD_CENTER) {
					String selected = jListObjects.getSelectedValue();
					for (IGraphicSystem listener : listeners) {
						listener.rotateClockwiseAroundOrigin(selected);
					}
				} else if (objectRotation == ObjectRotation.OBJECT_CENTER) {
					String selected = jListObjects.getSelectedValue();
					for (IGraphicSystem listener : listeners) {
						listener.rotateClockwiseAroundCenter(selected);
					}
				} else if (objectRotation == ObjectRotation.DOT) {
					String selected = jListObjects.getSelectedValue();
					double x = Double.parseDouble(txtXRotation.getText());
					double y = Double.parseDouble(txtYRotation.getText());
					Coordinate dot = new Coordinate(x, y);
					for (IGraphicSystem listener : listeners) {
						listener.rotateClockwiseAroundPoint(selected, dot);
					}
				}
			}
		});
		btnRight.setIcon(new ImageIcon("img/Arrow-turn-right-icon.png"));
		panelRotation.add(btnRight, "cell 0 3,alignx center");

		JPanel panelWindow = new JPanel();
		panelWindow.setBorder(new TitledBorder(null, "Window",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMenu.add(panelWindow, "cell 0 3,growx,aligny bottom");
		panelWindow.setLayout(new MigLayout("", "[][][]", "[][][]"));

		JButton btnZoomOut = new JButton("");
		btnZoomOut.setIcon(new ImageIcon("img/zoom-out-icon.png"));
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGraphicSystem listener : listeners) {
					listener.zoomOut();
				}
			}
		});
		panelWindow.add(btnZoomOut, "cell 0 0");

		JButton btnMoveUp = new JButton("");
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGraphicSystem listener : listeners) {
					listener.moveUp();
				}
			}
		});
		btnMoveUp.setIcon(new ImageIcon("img/Arrows-Up-icon32.png"));
		panelWindow.add(btnMoveUp, "cell 1 0");

		JButton btnZoomIn = new JButton("");
		btnZoomIn.setIcon(new ImageIcon("img/zoom-in-icon.png"));
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGraphicSystem listener : listeners) {
					listener.zoomIn();
				}
			}
		});
		panelWindow.add(btnZoomIn, "cell 2 0");

		JButton btnMoveLeft = new JButton("");
		btnMoveLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGraphicSystem listener : listeners) {
					listener.moveLeft();
				}
			}
		});
		btnMoveLeft.setIcon(new ImageIcon("img/Arrows-Left-icon32.png"));
		panelWindow.add(btnMoveLeft, "cell 0 1");

		JButton btnMoveRight = new JButton("");
		btnMoveRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGraphicSystem listener : listeners) {
					listener.moveRight();
				}
			}
		});
		btnMoveRight.setIcon(new ImageIcon("img/Arrows-Right-icon32.png"));
		panelWindow.add(btnMoveRight, "cell 2 1");

		JButton btnTurnLeft = new JButton("");
		btnTurnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnTurnLeft.setIcon(new ImageIcon("img/Arrow-turn-left-icon.png"));
		panelWindow.add(btnTurnLeft, "cell 0 2");

		JButton btnMoveDown = new JButton("");
		btnMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGraphicSystem listener : listeners) {
					listener.moveDown();
				}
			}
		});
		btnMoveDown.setIcon(new ImageIcon("img/Arrows-Down-icon32.png"));
		panelWindow.add(btnMoveDown, "cell 1 2");

		JButton btnTurnRight = new JButton("");
		btnTurnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnTurnRight.setIcon(new ImageIcon("img/Arrow-turn-right-icon.png"));
		panelWindow.add(btnTurnRight, "cell 2 2");

		JMenuBar menuBar = new JMenuBar();
		frmComputerGraphics.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);

		frmComputerGraphics.setVisible(true);
		frmComputerGraphics.pack();
	}
	
	public static final String SELECT_A_OBJECT = "Selecione um objeto!";
	
	private void scalePlus() {
		String selected = jListObjects.getSelectedValue();
		if (selected == null) {
			JOptionPane.showMessageDialog(null, SELECT_A_OBJECT);
			return;
		}
		for (IGraphicSystem listener : listeners) {
			listener.scalePlus(selected);
		}
	}

	private void scaleLess() {
		String selected = jListObjects.getSelectedValue();
		if (selected == null) {
			JOptionPane.showMessageDialog(null, SELECT_A_OBJECT);
			return;
		}
		for (IGraphicSystem listener : listeners) {
			listener.scaleLess(selected);
		}
	}

	private void setCenter() {
		String selected = jListObjects.getSelectedValue();
		if (selected == null) {
			JOptionPane.showMessageDialog(null, SELECT_A_OBJECT);
			return;
		}
		for (IGraphicSystem listener : listeners) {
			listener.setCenter(selected, txtX.getText(), txtY.getText());
		}
	}

	public void fill(String x, String y) {
		txtX.setText(x);
		txtY.setText(y);
	}

	@Override
	public void add(String name) {
		listObjects.add(0, name);
	}

	public void redraw(List<Geometry> objects) {
		viewport.redraw(objects);
	}

}
