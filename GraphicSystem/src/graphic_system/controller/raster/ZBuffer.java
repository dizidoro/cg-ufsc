package graphic_system.controller.raster;

import graphic_system.model.raster.Coordinate3D;
import graphic_system.model.raster.EdgeList;
import graphic_system.model.raster.Triangulo3D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ZBuffer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;

	private Coordinate3D lightSource;
	private double ambientLight = 0.1;

	private static Color[][] screen;
	private static int[][] zBuffer;

	int width;
	int height;
	int size;

	public ZBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		size = (width + height) / 2;

		screen = new Color[height][width];
		zBuffer = new int[height][width];

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		initZBuffer();
		repaint();

		// Criar controle gráfico
		lightSource = new Coordinate3D(500, 20, -100);

	}

	public void setAmbientLight(double value) {
		ambientLight = value;
	}

	public void setLightSource(int x, int y, int z) {
		lightSource = new Coordinate3D(x, y, z);
	}

	public void render(ArrayList<Triangulo3D> polygons) {
		// Algoritmo Z-Buffer
		// Dados:
		// Lista of polígonos {P1, P2, ..., Pn}
		// Matriz z-buffer[x,y] inicializado com -∞
		// Matriz Intensidade[x,y]
		// para cada polígono P na lista de polígonos faça {
		// para cada pixel (x,y) que intercepta P faça {
		// calcule profundidade-z de P na posição (x,y)
		// se prof-z < z-buffer[x,y] então {
		// Intensidade[x,y] = intensidade de P em (x,y)
		// z-buffer[x,y] = prof-z
		// }
		// }
		// }
		// Desenhe Intensidade
		for (Triangulo3D polygon : polygons) {
			if (true) {

				// TODO: Iluminação
				polygon.shading(lightSource, ambientLight);

				EdgeList[] edgelist = polygon.edgeList();
				int minY = polygon.getMinY();
				Color c = polygon.getColor();
				for (int j = 0; j < edgelist.length && edgelist[j] != null; j++) {
					int y = minY + j;
					int x = (int) Math.round(edgelist[j].getLeftX());
					int z = (int) Math.round(edgelist[j].getLeftZ());
					int mz = (int) Math
							.round((edgelist[j].getRightZ() - edgelist[j]
									.getLeftZ())
									/ (edgelist[j].getRightX() - edgelist[j]
											.getLeftX()));

					while (x <= edgelist[j].getRightX()) {
						if (z < zBuffer[x][y]) {
							zBuffer[x][y] = z;
							screen[x][y] = c;
						}
						x++;
						z += mz;
					}
				}
			}
		}
	}

	void initZBuffer() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				screen[i][j] = Color.GRAY;
				zBuffer[i][j] = Integer.MAX_VALUE;
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, screen[x][y].getRGB());
			}
		}
		g.drawImage(image, 0, 0, width, height, null);
	}

	static int[] unmakeColor(int color) {
		int[] colors = new int[4];
		colors[3] = (color >> 24) & 0x000000FF;
		colors[0] = (color & 0x00FFFFFF) >> 16;
		colors[1] = (color & 0x0000FF00) >> 8;
		colors[2] = color & 0x000000FF;
		return colors;
	}

	static int makeColor(byte r, byte g, byte b, byte a) {
		int color = (a & 0xff) << 24 | (r & 0xff) << 16 | (g & 0xff) << 8
				| (b & 0xff);
		return color;
	}

}
