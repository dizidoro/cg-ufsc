package graphic_system.view;

import graphic_system.model.Geometry;

public interface IGraphicSystem {

	void addNewObject(Geometry object);

	void moveUp();

	void moveRight();

	void moveLeft();

	void moveDown();

	void zoomIn();

	void zoomOut();

}
