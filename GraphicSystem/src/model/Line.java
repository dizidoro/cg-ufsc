package model;

public class Line extends Geometry {
	
	private static int lineCount = 1;

	// Uma reta no plano pode ser caracterizada por:
	// dois pontos (a, b) distintos do plano;
	private Coordinate a;
	private Coordinate b;
	
	public Line(Coordinate a, Coordinate b) {
		super("line" + lineCount, ObjectType.LINE);
		
		this.a = a;
		this.b = b;
		
		lineCount++;
	}

	public Coordinate getA() {
		return a;
	}

	public void setA(Coordinate a) {
		this.a = a;
	}

	public Coordinate getB() {
		return b;
	}

	public void setB(Coordinate b) {
		this.b = b;
	}
}
