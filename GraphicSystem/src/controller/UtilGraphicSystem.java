package controller;

public class UtilGraphicSystem {
	
	public static double getDistance(double a, double b) {
		return Math.sqrt(Math.pow((b - a), 2));
	}
	
	public static double getDistance2Points(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

}
