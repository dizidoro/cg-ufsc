package graphic_system.model;

public class Window {
	
	private double xMin, yMin,
				   xMax, yMax;
	
	//reduces size to 80% of original
	private final double zoomInPercentage = 80;
	private final double zoomInFactor = (1 - (zoomInPercentage/100))/2;
	//Increases size in 25%
	private final double zoomOutPercentage = 25;
	private final double zoomOutFactor = (zoomOutPercentage/100)/2;

	private final double moveDistance = 10;
	
	public Window(double xMin, double yMin, double xMax, double yMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	public double getXMin(){return xMin;}
	public double getYMin(){return yMin;}
	public double getXMax(){return xMax;}
	public double getYMax(){return xMax;}

	public void zoomIn(){
		double widthCompensation = (xMax - xMin) * zoomInFactor;
		double heightCompensation = (yMax - yMin) * zoomInFactor;
		
		this.xMin += widthCompensation;
		this.yMin += heightCompensation;
		
		this.xMax -= widthCompensation;
		this.yMax -= heightCompensation;
	}
	
	public void zoomOut(){
		double widthCompensation = (xMax - xMin) * zoomOutFactor;
		double heightCompensation = (yMax - yMin) * zoomOutFactor;
		
		xMin -= widthCompensation;
		yMin -= heightCompensation;
		
		xMax += widthCompensation;
		yMax += heightCompensation;
	}
	
	public void moveUp(){
		yMin += moveDistance;
		yMax += moveDistance;
	}
	
	public void moveDown(){
		yMin -= moveDistance;
		yMax -= moveDistance;
	}
	
	public void moveRight(){
		xMin += moveDistance;
		xMax += moveDistance;
	}
	
	public void moveLeft(){
		xMin -= moveDistance;
		xMax -= moveDistance;
	}
	
	@Override
	public String toString(){
		return xMin + "," + yMin + " " + xMax + "," + yMax;
	}
	
}
