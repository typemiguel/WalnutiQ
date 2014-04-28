package model.MARK_II.movement;

import java.awt.Point;
import model.Retina;

/**
 * Put all logic for updating SaccadingRetina elsewhere. In reality a retina
 * does not update itself.
 */
public class SaccadingRetina extends Retina {
    private Point position;
    private int distanceBetweenImageAndRetina;

    public SaccadingRetina(int numberOfVisionCellsAlongYAxis,
	    int numberOfVisionCellsAlongXAxis, Point retinaPosition,
	    int distanceBetweenImageAndRetina) {
	super(numberOfVisionCellsAlongYAxis, numberOfVisionCellsAlongXAxis);

	this.position = retinaPosition;
	this.distanceBetweenImageAndRetina = distanceBetweenImageAndRetina;
    }

    public Point getPosition() {
	return position;
    }

    public void setRetinaPosition(Point retinaPosition) {
	this.position = retinaPosition;
    }

    public int getDistanceBetweenImageAndRetina() {
	return distanceBetweenImageAndRetina;
    }

    public void setDistanceBetweenImageAndRetina(
	    int distanceBetweenImageAndRetina) {
	this.distanceBetweenImageAndRetina = distanceBetweenImageAndRetina;
    }
}
