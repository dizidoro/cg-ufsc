package graphic_system.view;

import graphic_system.model.Coordinate;
import graphic_system.model.Geometry;

public interface IGraphicSystem {

	void addNewObject(Geometry object);
	
	void removeObject(String selected);

	void moveUp();

	void moveRight();

	void moveLeft();

	void moveDown();

	void zoomIn();

	void zoomOut();

	void getCenter(String objectName);

	void setCenter(String objectName, String x, String y);

	void scaleLess(String objectName);

	void scalePlus(String objectName);

	void rotateClockwiseAroundOrigin(String objectName);

	void rotateAntiClockwiseAroundOrigin(String objectName);

	void rotateClockwiseAroundCenter(String objectName);

	void rotateAntiClockwiseAroundCenter(String objectName);

	void rotateClockwiseAroundPoint(String objectName, Coordinate dot);

	void rotateAntiClockwiseAroundPoint(String objectName, Coordinate dot);

	void rotateWindowRight();

	void rotateWindowLeft();

	void zBufferSample();

}
