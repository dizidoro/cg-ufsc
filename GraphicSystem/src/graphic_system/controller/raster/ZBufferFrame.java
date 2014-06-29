package graphic_system.controller.raster;

import graphic_system.ApplicationConfig;
import graphic_system.model.raster.Coordinate3D;
import graphic_system.model.raster.Triangulo3D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ZBufferFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldLight;

	private ZBuffer frameBuffer;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtZ;

	private ArrayList<Triangulo3D> triangles;

	private JFrame that;

	public ZBufferFrame() {
		initialize();

		try {
			String fileName = "raster/ball.txt";
			BufferedReader b = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileName))));
			String line;
			line = b.readLine();
			Scanner sc = new Scanner(line);
			sc.close();

			// Polygons in the file
			triangles = new ArrayList<>();
			while ((line = b.readLine()) != null) {
				Scanner scan = new Scanner(line);
				ArrayList<Coordinate3D> c3d = new ArrayList<>();
				for (int v = 0; v < 3; v++) {
					float n1 = scan.nextFloat();
					float n2 = scan.nextFloat();
					float n3 = scan.nextFloat();
					c3d.add(new Coordinate3D(n1, n2, n3));
				}
				Triangulo3D triangle = new Triangulo3D(c3d, Color.BLACK);

				int c1 = scan.nextInt();
				int c2 = scan.nextInt();
				int c3 = scan.nextInt();
				triangle.setReflectivity(new Color(c1, c2, c3));

				triangles.add(triangle);
				scan.close();
			}

			double xMax = ApplicationConfig.initXMax;
			double yMax = ApplicationConfig.initYMax;

			frameBuffer = new ZBuffer((int) xMax, (int) yMax);
			frameBuffer.render(triangles);

			this.setBounds(100, 100, (int) xMax, (int) yMax);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.getContentPane().add(frameBuffer);
			this.setVisible(true);
			frameBuffer.repaint();

			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initialize() {
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblAmbientLight = new JLabel("Luz do ambiente (0-100):");
		panel.add(lblAmbientLight);

		textFieldLight = new JTextField();
		textFieldLight.setText("10");
		panel.add(textFieldLight);
		textFieldLight.setColumns(3);

		JLabel lblLightCoordinate = new JLabel("Orientação da luz: (X,Y,Z)");
		panel.add(lblLightCoordinate);

		txtX = new JTextField();
		txtX.setText("500");
		panel.add(txtX);
		txtX.setColumns(3);

		txtY = new JTextField();
		txtY.setText("20");
		panel.add(txtY);
		txtY.setColumns(3);

		txtZ = new JTextField();
		txtZ.setText("-100");
		panel.add(txtZ);
		txtZ.setColumns(3);

		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String light = textFieldLight.getText();
				String dX = txtX.getText();
				String dY = txtY.getText();
				String dZ = txtZ.getText();

				double dLight = 0;
				try {
					dLight = Double.parseDouble(light);

					if (dLight < 0 || dLight > 100) {
						throw new Exception();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null,
							"Tem que ser um valor entre 0 a 100!");
				}

				int iX = 0, iY = 0, iZ = 0;
				try {
					iX = Integer.parseInt(dX);
					iY = Integer.parseInt(dY);
					iZ = Integer.parseInt(dZ);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null,
							"Para x,y,z apenas valores inteiros!");
				}

				that.remove(frameBuffer);
				double xMax = ApplicationConfig.initXMax;
				double yMax = ApplicationConfig.initYMax;
				frameBuffer = new ZBuffer((int) xMax, (int) yMax);
				frameBuffer.setAmbientLight(dLight / 100);
				frameBuffer.setLightSource(iX, iY, iZ);
				frameBuffer.render(triangles);

				that.setBounds(100, 100, (int) xMax, (int) yMax);
				that.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				that.getContentPane().add(frameBuffer);
				that.setVisible(true);
				frameBuffer.repaint();
			}
		});
		panel.add(btnAplicar);

		that = this;
	}

}
