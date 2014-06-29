package graphic_system.controller.raster;

// Trabalho 2.1. Rasterização sem checagem de profundidade no SGI
// Implementação de framebuffer com as funções de limpar o buffer,
// desenhar pixel, desenhar linha, desenha trapézio alinhado,
// desenhar polígono e integração no SGI.
public class FrameBuffer {
	int[] buffer;
	int width;
	int height;
	int size;

	// Resposta email André Puel:
	// Você usa o BufferedImage para representar os pixeis do framebuffer.
	// Usa a função setRGB(x,y,color) pra setar a cor de cada pixel.
	// Pra desenhar tem que extender o JPanel e reimplementar o paintComponents:
	// public void paintComponent(Graphics g){
	// super.paintComponent(g);
	// g.drawImage(image, 0, 0, 512, 512, null);
	// }
	public FrameBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		size = (width + height) / 2;
		buffer = new int[width * height];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void clear(int color) {
		int length = width * height;
		for (int i = 0; i < length; i++) {
			buffer[i] = color;
		}
	}

	void setPixel(int x, int y, int color) {
		buffer[y * size + x] = color;
	}

	public int getPixel(int x, int y) {
		return buffer[y * size + x];
	}

	public void drawTriangle(int[] xPoints, int[] yPoints, int nPoints,
			int color) {
		drawLine(xPoints[0], yPoints[0], color, xPoints[1], yPoints[1], color);
		drawLine(xPoints[1], yPoints[1], color, xPoints[2], yPoints[2], color);
		drawLine(xPoints[2], yPoints[2], color, xPoints[0], yPoints[0], color);

		// Fill triangle
		// int maxX = xPoints[0];
		// int minX = xPoints[0];
		// int maxY = yPoints[0];
		// int minY = yPoints[0];
		// for (int i = 0; i < 3; i++) {
		// if (xPoints[i] > maxX) {
		// maxX = xPoints[i];
		// }
		// if (xPoints[i] < minX) {
		// minX = xPoints[i];
		// }
		// if (yPoints[i] > maxY) {
		// maxY = yPoints[i];
		// }
		// if (yPoints[i] < minY) {
		// minY = yPoints[i];
		// }
		// }
		// for (int i = minY; i < maxY; i++) {
		// drawLine(minX, i, color, maxX, i, color);
		// }

	}

	public void drawLine(int x, int y, int color1, int x2, int y2, int color2) {
		int w = x2 - x;
		int h = y2 - y;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if (w < 0) {
			dx1 = -1;
		} else if (w > 0) {
			dx1 = 1;
		}
		if (h < 0) {
			dy1 = -1;
		} else if (h > 0)
			dy1 = 1;
		if (w < 0) {
			dx2 = -1;
		} else if (w > 0) {
			dx2 = 1;
		}
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if (!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0) {
				dy2 = -1;
			} else if (h > 0) {
				dy2 = 1;
			}
			dx2 = 0;
		}
		int[] colors0 = unmakeColor(color1);
		int[] colors1 = unmakeColor(color2);
		double numerator = longest >> 1;
		for (double i = 0; i <= longest; i++) {
			// t indica a porcentagem de cada cor
			double t = i / longest;
			int r0 = (int) ((1 - t) * colors0[0]);
			int g0 = (int) ((1 - t) * colors0[1]);
			int b0 = (int) ((1 - t) * colors0[2]);
			int a0 = (int) ((1 - t) * colors0[3]);
			int r1 = (int) (t * colors1[0]);
			int g1 = (int) (t * colors1[1]);
			int b1 = (int) (t * colors1[2]);
			int a1 = (int) (t * colors1[3]);

			double deltaR = r0 | r1;
			double deltaG = g0 | g1;
			double deltaB = b0 | b1;
			double deltaA = a0 | a1;
			setPixel(
					x,
					y,
					makeColor((byte) deltaR, (byte) deltaG, (byte) deltaB,
							(byte) deltaA));
			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x += dx1;
				y += dy1;
			} else {
				x += dx2;
				y += dy2;
			}
		}
	}

	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, int color) {
		for (int i = 1; i < nPoints; i++) {
			drawLine(xPoints[i - 1], yPoints[i - 1], color, xPoints[i],
					yPoints[i], color);
		}
		drawLine(xPoints[0], yPoints[0], color, xPoints[xPoints.length - 1],
				yPoints[yPoints.length - 1], color);
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
